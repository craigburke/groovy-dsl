// Monitor twitter for negative tweets
'Dane Cook Sucks'.watch {
    tweet 'Hey @DaneCook, somebody said this:' << it
    tweet "LEAVE DANE ALONE, @${it.user}!!!"
}

// Reassure Dane every 2 minutes
every 2.minutes run {
    tweet "Hey @DaneCook, it's ${new Date().format('H:mm a')} and everybody still loves you"
}