pandoc -f markdown -t pdf \
-s \
--toc \
--toc-depth=3 \
--number-sections \
--number-depth=3 \
--css=pandoc.css \
--metadata title="My Document" \
-o output.pdf \
--pdf-engine=lualatex\
--highlighting-style tango \
--papersize a4 \
--margin-left 0.5cm \
--margin-right 0.5cm \
--margin-top 1cm \
-- margin-bottom 2cm \
readme.md