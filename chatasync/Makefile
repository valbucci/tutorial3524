# Compiler settings
JC = javac
JFLAGS = -g

# Define .java and .class files suffixes
.SUFFIXES: .java .class

# Rule for compiling java files
.java.class:
	$(JC) $(JFLAGS) $*.java

# Source files
SOURCES = \
	shared/Message.java \
	client/ChatClient.java \
	client/StartClient.java \
	server/ConnectionPool.java \
	server/ChatServerHandler.java \
	server/ChatServer.java \
	server/StartChatServer.java

# Default build all classes
default: classes

# Compiles all .java files to .class files
classes: $(SOURCES:.java=.class)

# Clean target definitions
clean:
	find . -type f -name '*.class' -exec rm {} +