.PHONY: run build clean

WORKDIR=mortgage
APPNAME=CalculatorApp

mortgage/CalculatorApp.class:
	javac $(WORKDIR)/$(APPNAME).java

build: $(WORKDIR)/$(APPNAME).class

clean:
	rm $(WORKDIR)/*.class

run: build
	java $(WORKDIR).$(APPNAME)