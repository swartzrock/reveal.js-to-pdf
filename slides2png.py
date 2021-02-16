 #!/usr/bin/env python3

# Converts a reveal.js slideshow to png's
# based on the original slides2png by Dan Allen
# based on the slides2png by Jason Swartz
# 
# @author Thibaut MADELAINE
# @license ASLv2 

import glob
import os
import sys
import time
from PIL import ImageGrab
from selenium import webdriver
from subprocess import call

if len(sys.argv) != 2:
    print("usage: slides2png.py <url>\n")
    exit()

slides_dir = 'slides'
filelist = glob.glob(os.path.join(slides_dir, "slide_*.png"))
for f in filelist:
    os.remove(f)

browser = webdriver.Firefox()
browser.get(sys.argv[1])

time.sleep(5)
slide_number = 0

for slide in range(1,999):
  image = ImageGrab.grab(bbox=(0,140,1280,1005))
  image.save("{}/slide_{:03}.png".format(slides_dir, slide))
  browser.find_element_by_tag_name("body").send_keys("n")
  if not browser.execute_script("return Reveal.availableRoutes().right;"):
      image = ImageGrab.grab(bbox=(0,140,1280,1005))
      image.save("{}/slide_{:03}.png".format(slides_dir, slide+1))
      slide_number = slide+1
      break
  time.sleep(1)

call("./bin/png2pdf.sh {}".format(slides_dir), shell=True)

print("Rendered {} slides to slides.pdf".format(slide_number))

browser.close()

exit()


