#ifndef MAP_H
#define MAP_H

#include <GL/gl.h>
#include "vertex.h"

class Map
{
    GLuint whiteMapVBO;

    GLuint greenMapVBO;

    Vertex whiteMapPos[4];

    Vertex greenMapPos[4];

public:
    Map();

    void resize( float x, float y, float width, float height, float scale );

    void draw();
};

#endif // MAP_H
