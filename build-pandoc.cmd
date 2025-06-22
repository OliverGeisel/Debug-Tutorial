pandoc -f markdown -t pdf \
-s \
--toc \
--toc-depth=3 \
--number-sections \
--number-depth=3 \
--css=pandoc.css \
--metadata title="Debugging mit IDEs-Manuskript" \
-o "Debugging mit IDEs-Manuskript.pdf" \
--pdf-engine=lualatex\
--highlighting-style pandoc.theme.json \
--papersize a4 \
--margin-left 0.5cm \
--margin-right 0.5cm \
--margin-top 1cm \
--margin-bottom 2cm \
readme.md

@REM current pdf generation
pandoc -f markdown -t pdf -s --toc --toc-depth=3 --lof --lot -N readme.md -o "Debugging mit IDEs-Manuskript.pdf" --pdf-engine=wkhtmltopdf --css=pandoc.css -V papersize=a4 -V fig-align=center
