# Reveal.js To PDF

Converts a Reveal.js script to PDF, written in Python.

* Renders slides in Firefox to PNG
* Crops images to 1440x900 and converts to PDF with ImageMagick
* Based on the original script by [Dan Allen](https://github.com/mojavelinux/dzslides) and [swartzrock](https://github.com/swartzrock/reveal.js-to-pdf)

## Install


Requires python3, PIL, selenium and ImageMagick. And, of course, Firefox with
geckodriver.

```bash
#install pillow
python3 -m pip install --upgrade Pillow

#install selenium
python3 -m pip install --upgrade selenium

# install geckodriver
wget https://github.com/mozilla/geckodriver/releases/download/v0.29.0/geckodriver-v0.29.0-linux32.tar.gz
sudo tar -C /usr/local/bin/ -xvf geckodriver-v0.29.0-linux64.tar.gz 

# install ImageMagick
sudo apt install imagemagick
```


## How To Use

```bash
usage: slides2png.py [-h] [-b BBOX [BBOX ...]] [-c CLEAN_FILES]
                     [-o OUTPUT_SLIDE] [-j JPEG_QUALITY] [-m MAX_SLIDES]
                     [-s SLEEP]
                     url

positional arguments:
  url                   URL of the Reveal.js slides

optional arguments:
  -h, --help            show this help message and exit
  -b BBOX [BBOX ...], --bbox BBOX [BBOX ...]
  -c CLEAN_FILES, --clean CLEAN_FILES
                        Clean individual slide images after pdf creation
  -o OUTPUT_SLIDE, --output OUTPUT_SLIDE
                        File where the slides in pdf are stored
  -j JPEG_QUALITY, --jpeg-quality JPEG_QUALITY
  -m MAX_SLIDES, --max-slides MAX_SLIDES
  -s SLEEP, --sleep SLEEP
                        Sleep time between snapshots (in seconds)
```

Example:

```bash
python3 ./slides2png.py file:///home/thibaut/my_reveal_slides.html
```




