#!/bin/bash
# requires zsh and imagemagick

if [[ $# -lt 1 ]]; then echo "need a dir name"; return 1; fi

dir=$1

JPEG_QUALITY=50

convert $dir/*.png -quality $JPEG_QUALITY $dir.pdf

echo "Generated $dir.pdf"
