@Grab('org.twitter4j:twitter4j-core:4.0.4')
@Grab('joda-time:joda-time:2.9.4')

import org.joda.time.DateTime
import org.joda.time.Interval

import twitter4j.Query
import twitter4j.RateLimitStatus
import twitter4j.StatusUpdate
import twitter4j.Twitter
import twitter4j.TwitterException
import twitter4j.TwitterFactory

final Long POLL_DELAY = 500L

class TwitterBot {

    static final String STATUS_LIMIT_KEY = '/application/rate_limit_status'
    static final String SEARCH_LIMIT_KEY = '/search/tweets'

    private Twitter twitter = new TwitterFactory().instance

    List<SimpleTweet> search(SearchParams params) {
        checkRateLimitAndWait(SEARCH_LIMIT_KEY)

        Query query = new Query(params.searchText)
        query.resultType = Query.ResultType.recent

        if (params.sinceId) {
            query.sinceId(params.sinceId)
        }

        twitter.search(query).tweets.collect {
            new SimpleTweet(id: it.id, user: it.user.screenName, content: it.text )
        }
    }

    void tweet(String text) {
        StatusUpdate status = new StatusUpdate(text)
        try {
            twitter.updateStatus(status)
        }
        catch (TwitterException ex) {
            println "Unable to send tweet: ${ex.message}"
        }
    }

    private void checkRateLimitAndWait(String key) {
        Map<String, RateLimitStatus> statuses = twitter.rateLimitStatus

        RateLimitStatus checkStatus = statuses[STATUS_LIMIT_KEY]
        RateLimitStatus status = statuses[key]

        if (checkStatus.remaining && status.remaining) {
            return
        }

        Long checkStatusSleepTime = (checkStatus.remaining ? 0 : checkStatus.resetTimeInSeconds) * 1000L
        Long statusSleepTime = (status.remaining ? 0 : status.resetTimeInSeconds) * 1000L

        DateTime now = new DateTime()
        DateTime sleepUntilDate = new DateTime(Math.max(checkStatusSleepTime, statusSleepTime))

        if (now < sleepUntilDate) {
            Interval interval = new Interval(now, sleepUntilDate)
            long sleepTime = interval.toDurationMillis() + 1000L
            println "Rate Limit exceeded. Waiting for ${sleepTime / 1000L} seconds"
            Thread.sleep(sleepTime)
        }

    }
}

class SearchParams {
    String searchText
    Long sinceId
    Long pollDelay
}

class SimpleTweet {
    Long id
    String user
    String content

    String toString() {
        "@${user}: ${content}"
    }
}

TwitterBot twitterBot = new TwitterBot()

String.metaClass.leftShift = { SimpleTweet tweet ->
    "${delegate} http://twitter.com/${tweet.user}/status/${tweet.id}"
}

String.metaClass.watch = { Map params = [:], Closure closure ->
    String searchText = delegate

    Thread.start {
        Closure clonedClosure = closure.rehydrate(twitterBot, this, this)
        SearchParams searchParams = new SearchParams(searchText: searchText, pollDelay: POLL_DELAY)

        while(true) {
            List<SimpleTweet> tweets = twitterBot.search(searchParams)
            tweets.each {
                clonedClosure.call(it)
            }
            searchParams.sinceId = tweets ? tweets.sort { it.id }.last().id : searchParams.sinceId
            Thread.sleep POLL_DELAY
        }
    }
}

Number.metaClass.getMinutes = {
    60000L * (delegate as Long)
}

def run = { long delay, Closure closure ->
    Thread.start {
        Closure clonedClosure = closure.rehydrate(twitterBot, this, this)

        while (true) {
            clonedClosure()
            Thread.sleep delay
        }
    }
}

def every = { long delay ->
    [run: { Closure closure -> run(delay, closure) }]
}

def binding = new Binding(every: every)
def shell = new GroovyShell(binding)
shell.evaluate(new File('command.groovy'))
