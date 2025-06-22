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
  --metadata title="Debugging mit IDEs-Manuskript" \
  -o "Debugging mit IDEs-Manuskript.pdf" \
  --pdf-engine=lualatex\
  --highlight-style pandoc.theme.json \
  -V papersize=a4 \
  -V margin-left=0.5cm \
  -V margin-right=0.5cm \
  -V margin-top=1cm \
  -V margin-bottom=2cm \
  -V fig-align=center \
  readme.md

# Current command for pdf generation:
pandoc -f markdown -t pdf -s --toc --toc-depth=3 --lof --lot -N readme.md -o "Debugging mit IDEs-Manuskript.pdf" --pdf-engine=wkhtmltopdf --css=pandoc.css -V papersize=a4 -V fig-align=center
