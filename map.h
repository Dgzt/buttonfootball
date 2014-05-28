#ifndef MAP_H
#define MAP_H

#include <GL/gl.h>
#include "vertex.h"

class RectangleBorder;

class Map
{
    GLuint mapVBO;

    Vertex mapPos[4];

    RectangleBorder *leftSector16;
    RectangleBorder *rightSector16;
    RectangleBorder *leftSector5;
    RectangleBorder *rightSector5;

    void setMapSize( const float &x,
                     const float &y,
                     const float &parentWidth,
                     const float &parentHeight,
                     const float &width,
                     const float &height);

public:
    Map();
    virtual ~Map();

    void resize( float x, float y, float width, float height, float scale );

    void draw();
};

#endif // MAP_H
