REPORT_FILE=2026-03-10_Functional_Transactions

compile:
	pdflatex \
		-halt-on-error \
		-shell-escape $(REPORT_FILE) \
		-o $(REPORT_FILE).pdf


watch: compile
	while true ; do inotifywait -e modify *.tex ; make compile ; done


clean:
	rm *.toc *.aux *.out *.pdf *.idx *.log

