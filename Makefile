.PHONY: run build clean package

WORKDIR=mortgage
APPNAME=CalculatorApp
PACKAGE=build/package.zip

$(WORKDIR)/$(APPNAME).class:
	javac $(WORKDIR)/$(APPNAME).java

build: $(WORKDIR)/$(APPNAME).class

clean:
	rm -f $(WORKDIR)/*.class
	rm -f build/package.zip

run: build
	java $(WORKDIR).$(APPNAME)

$(PACKAGE):
	zip $(PACKAGE) mortgage/*.java
	zip $(PACKAGE) README.md
	zip $(PACKAGE) Makefile

package: $(PACKAGE)