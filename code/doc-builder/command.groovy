def responses = [
	[question: 'How are you?', response: 'Great. Profits are up and costs are down. We are doing fantastically well at Serious Business Inc Corporation.'],
	[question: 'Any advice for other businesses', response: 'Be great. Never let anybody tell you to be less than great. If you do that then both you and your business will be great']
]

String PERSON_IMAGE_URL = 'https://pbs.twimg.com/profile_images/517709113107554305/1nyjs8Dr_400x400.jpeg'
byte[] personImageData = new URL(PERSON_IMAGE_URL).bytes

String BUSINESS_CHART_URL = 'http://chart.googleapis.com/chart?cht=bvg&chs=250x150&chd=s:Profits&chxt=x,y&chxs=0,ff0000,12,0,lt|1,0000ff,10,1,lt'
byte[] chartData = new URL(BUSINESS_CHART_URL).bytes

document {
	heading1 "Important Business Document"

	table(columns: [1.75, 4], width: 6.inches) {
		row {
			cell(background: '#E0FFFF') {
				image(data: personImageData, width: 1.5.inches)
				lineBreak()
				text 'Important business man'
			}
			cell(style: 'responses') {
				image(data: chartData)
				lineBreak()

				responses.each { response ->
					text "${response.question}\n", style: 'question', font: [bold: true]
					text response.response, style: 'response'
					2.times { lineBreak() }
				}
			}
		}
	}
}

