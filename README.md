# Mortgage Amortization GUI

Week 5 assignment for CISS 438.

## Dependencies

This application is built on Java Swing using java version "1.8.0_212".
Any version of java 8 will likely work.

If you want to use the Makefile (you don't have to), you need to have GNU make on your machine.
https://www.gnu.org/software/make/

## Running

There is a make file for doing the work of building and running the application.

Use `make clean` to remove all current assets.

Use `make run` to start the app (which will `make build` as a dependency task).  This compiles
the java code and kicks off the mortgage calculator.

Use `make package` to zip the source elements up for distribution as a java project,
no git assets included.

If you do NOT have make on your machine, you can just do the java tasks yourself from
the root of this project directory.

COMPILE:
`javac mortgage/CalculatorApp.java`

RUN:
`java mortgage.CalculatorApp`

PACKAGE:
```bash
zip build/package.zip mortgage/*.java
zip build/package.zip README.md
zip build/package.zip Makefile
```