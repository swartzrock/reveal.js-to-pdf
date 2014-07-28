#!/usr/bin/env sbt -Dsbt.main.class=sbt.ScriptMain

/***
scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.seleniumhq.selenium" % "selenium-java" % "2.42.2",
  "org.scalatest" % "scalatest_2.11" % "2.2.0"
)
*/

import org.scalatest._
import org.scalatest.selenium._
import scala.annotation.tailrec


def renderSlidesToImages(url: String, dirName: String): Int = {

  import Firefox._

  val MAX_SLIDES = 999
  val SLEEP_MS = 500

  def fullscreen = pressKeys("f")
  def nextSlide = pressKeys("n")
  def executeAsBoolean(s: String): Boolean = executeScript(s).asInstanceOf[Boolean]
  def hasRightRoute = executeAsBoolean("return Reveal.availableRoutes().right;")
  def screenshot(file: String) = { Thread.sleep(SLEEP_MS); capture to file }

  @tailrec
  def renderSlide(slides: List[String]): List[String] = {

    val slide = "slide_%04d.png" format slides.size
    screenshot(slide)
    val allSlides = slide :: slides
    
    if (hasRightRoute && allSlides.size < MAX_SLIDES) {
      nextSlide
      renderSlide(allSlides)
    }
    else {
      allSlides
    }
  }

  setCaptureDir(dirName)
  go to url
  fullscreen
  val slides = renderSlide(Nil)
  close()

  slides.size
}  


def renderSlidesToPDF(url: String) {
  val dirName = "slides_" + System.currentTimeMillis
  val count = renderSlidesToImages(url, dirName)

  val script = s"bin/png2pdf.sh $dirName"
  sys.process.Process(script).run  
  println(s"Rendered $count slides")
}


// Entry point for our script
args.toList match {
  case List(x) => 
    renderSlidesToPDF(x)
  case _ => 
    System.err.println("usage: Slides2Png.scala <url>")
}



