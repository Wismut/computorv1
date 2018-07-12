#SRC_NAME = Main.java


#SRC_PATH = ./src/main/java/computor/
#OBJ_PATH = ./bin/main/java/computor/

#NAME = computor.jar

#MANIFEST = $(SRC_PATH)manifest.txt

#CC = javac

#OBJ_NAME = $(SRC_NAME:.java=.class)

#SRC = $(addprefix $(SRC_PATH),$(SRC_NAME))
#OBJ = $(addprefix $(OBJ_PATH),$(OBJ_NAME))

#all : $(NAME)


#$(NAME) : $(OBJ)

#$(OBJ_PATH)%.class: $(SRC_PATH)%.java
#	mkdir $(OBJ_PATH) 2> /dev/null || true
#	echo "compiling $<"
#	$(CC) -d bin/ -cp $(SRC_PATH) $<

#clean: fclean

#fclean:
#	/bin/rm -fv $(OBJ)
#	rm -rf $(OBJ_PATH) 2> /dev/null || true
#	rm -fv $(NAME)


#re: fclean all

#.PHONY : all clean fclean re




#JFLAGS = -g
#JC = javac
#.SUFFIXES: .java .class
#.java.class:
#	$(JC) $(JFLAGS) $*.java

#CLASSES = \
#	Main.java

#default: classes

#classes: $(CLASSES:.java=.class)

#clean:
#	$(RM) *.class



NAME = computor

FLAGS = java -jar

SRC_NAME = ComputorV1-1.0-SNAPSHOT-jar-with-dependencies.jar

JAVAC=javac
sources = $(wildcard src/main/java/computor/*.java)
OBJ = $(sources:.java=.class)

all: $(NAME)

$(NAME): $(OBJ)
	@mvn package

clean :
	rm -f src/main/java/computor/*.class

%.class : %.java
	$(JAVAC) $<

jar:
	@echo "Manifest-Version: 1.0" > manifest.txt
	@echo "Class-Path: ." >> manifest.txt
	@echo "Main-Class: computor.Main" >> manifest.txt
	@echo "" >> manifest.txt
	jar -cmf manifest.txt JARNAME.jar $(classes)
