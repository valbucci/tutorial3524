# Compiler settings
JC = javac -cp ..

# Define .java and .class files suffixes
.SUFFIXES: .java .class

# Rule for compiling java files
.java.class:
	$(JC) $*.java

# Source files
SOURCES = \
	client/EchoClient.java \
	client/runEchoClient.java \
	server/ServerSocketHandler.java \
	server/EchoServer.java \
	server/startEchoServer.java

# Build all classes by default
default: classes

# Compiles all .java files to .class files
classes: $(SOURCES:.java=.class)

# Clean target definitions
clean:
	find . -type f -name '*.class' -exec rm {} +
