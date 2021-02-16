 #!/usr/bin/env python3

# Converts a reveal.js slideshow to png's
# based on the original slides2png by Dan Allen
# based on the slides2png by Jason Swartz
# 
# @author Thibaut MADELAINE
# @license ASLv2 

import argparse
import glob
import os
import sys
import time
from PIL import ImageGrab
from selenium import webdriver
from subprocess import call

parser = argparse.ArgumentParser()
parser.add_argument("url", help="URL of the Reveal.js slides")
parser.add_argument('-b', '--bbox', type=int, nargs='+',
                    default=[0,140,1280,1005],
                    help='')
parser.add_argument('-c', '--clean', action='store',
                    dest='clean_files', default=True,
                    help='Clean individual slide images after pdf creation')
parser.add_argument('-o', '--output', action='store',
                    dest='output_slide', default='slides.pdf',
                    help='File where the slides in pdf are stored')
parser.add_argument('-j', '--jpeg-quality', action='store',
                    dest='jpeg_quality', default='50')
parser.add_argument('-m', '--max-slides', action='store', type=int,
                    dest='max_slides', default='999')
parser.add_argument('-s', '--sleep', action='store', type=float,
                    dest='sleep', default='0.8',
                    help='Sleep time between snapshots (in seconds)')

args = parser.parse_args()

slides_dir = 'slides'
if not os.path.exists(slides_dir):
    os.mkdir(slides_dir)
filelist = glob.glob(os.path.join(slides_dir, "slide_*.png"))
for f in filelist:
    os.remove(f)

browser = webdriver.Firefox()
browser.get(args.url)

time.sleep(5)
slide_number = 0

for slide in range(1,args.max_slides):
  image = ImageGrab.grab(bbox=(args.bbox))
  image.save("{}/slide_{:03}.png".format(slides_dir, slide))
  if not browser.execute_script("return Reveal.availableRoutes().right;"):
      slide_number = slide
      break
  browser.find_element_by_tag_name("body").send_keys("n")
  time.sleep(args.sleep)

call("convert {}/*.png -quality {} {}".format(slides_dir, args.jpeg_quality, args.output_slide), shell=True)

print("Rendered {} slides to slides.pdf".format(slide_number))

browser.close()

if (args.clean_files):
    for f in filelist:
        os.remove(f)

sys.exit(0)


