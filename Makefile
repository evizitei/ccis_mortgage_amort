.PHONY: run build clean

start/HelloWorldSwing.class:
	javac start/HelloWorldSwing.java

build: start/HelloWorldSwing.class

clean:
	rm start/*.class

run: build
	java start.HelloWorldSwing