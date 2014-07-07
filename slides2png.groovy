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

if (args.length != 1) {
  println "usage: slides2png.groovy <url>  "
  return
}

def url = args[0]
def dirName = "slides_" + new Date().time
def idx = 0

def MAX_SLIDES = 999
def SLEEP_MS = 500

Browser.drive {
  def fullScreen = { $('body') << 'f' }
  def nextSlide = { $('body') << 'n' }
  def hasMore = { browser.driver.executeScript("return Reveal.availableRoutes().right;") }

  config.reporter = new geb.report.ScreenshotReporter()
  config.reportsDir = new File(dirName)
  cleanReportGroupDir()

  go url
  fullScreen()
  while (idx++ < MAX_SLIDES) {
    sleep SLEEP_MS
    report sprintf("slide_%04d", idx)
    if (!hasMore()) break
    nextSlide()
  }
}.quit()

"png2pdf.sh ${dirName}".execute()

println "Rendered ${idx} slides to ${dirName}.pdf"


