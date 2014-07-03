#!/bin/zsh
# requires zsh and imagemagick

if [[ $# -lt 1 ]]; then echo "need 2 args"; return 1; fi

dir=$1
res=1440x900

for png in $dir/*.png ; do
  convert $png -crop $res ${png/.png/_crop.png}
done

convert $dir/*crop-0.png -page $res $dir.pdf

echo "Generated $dir.pdf"

