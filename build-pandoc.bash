#!/bin/bash
pandoc -f markdown -t pdf \
  -s \
  --toc \
  --toc-depth=3 \
  --lof \
  --lot \
  --number-sections \
  -V number-depth=3 \
  --css=pandoc.css \
  --metadata title="Debug-Tutorial" \
  -o output.pdf \
  --pdf-engine=lualatex\
  --highlighting-style tango \
  -V papersize=a4 \
  -V margin-left=0.5cm \
  -V margin-right=0.5cm \
  -V margin-top=1cm \
  -V margin-bottom=2cm \
  -V fig-align=center \
readme.md