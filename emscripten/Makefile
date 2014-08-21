# Makefile for generating a html5 page using Emscripten.
#
# build with       emmake make
#

FILE_PREFIX = ../src
FILES = \
Box2D/box2d.bc \
$(FILE_PREFIX)/*cpp \
$(FILE_PREFIX)/shape/*cpp

all: index.html

index.html: $(OBJECTS)
	$(CXX) -I Box2D/include -s LEGACY_GL_EMULATION=1 -o index.html $(FILES)

clean:
	rm -f index.html index.html.map index.js
