#ifndef MAP_H
#define MAP_H

#include <GL/gl.h>
#include "vertex.h"

class RectangleBorder;
class Circle;
class Line;

class Map
{
    GLuint mapVBO;

    RectangleBorder *leftSector16;
    RectangleBorder *rightSector16;
    RectangleBorder *leftSector5;
    RectangleBorder *rightSector5;

    Circle *bigCentralCircle;

    Line *halfLine;

    Circle *leftCircle11;

    Circle *rightCircle11;

    Circle *smallCentralCircle;

public:
    Map();
    virtual ~Map();

    void resize( float parentX, float parentY, float parentWidth, float parentHeight, float scale );

    void draw();
};

#endif // MAP_H
