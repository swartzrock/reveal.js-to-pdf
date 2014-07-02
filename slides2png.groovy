/**
 * Converts a reveal.js slideshow to png's
 * based on the original slides2png by Dan Allen
 *
 * @author Jason Swartz
 * @license ASLv2 
 */

@Grapes([
  @Grab("org.gebish:geb-core:0.9.3"),
  @Grab("org.seleniumhq.selenium:selenium-firefox-driver:2.42.2"),
  @Grab("org.seleniumhq.selenium:selenium-support:2.42.2")
])
import geb.Browser

if (args.length != 2) {
  println "usage: slides2png.groovy <url> <num-slides> "
  return
}
def url = args[0]
int numSlides = args[1].toInteger()

def dirName = "slides_" + new Date().time



Browser.drive {
  config.reporter = new geb.report.ScreenshotReporter()
  config.reportsDir = new File(dirName)
  cleanReportGroupDir()
  go url
  def body = $('body')
  body << 'f' // full screen
  for (idx in 1..numSlides) {
    sleep 500
    report "slide_" + idx
    body << 'n' // next slide
  }
}.quit()

println "Rendered ${numSlides} slides to ${dirName}"


