#ifndef MAP_H
#define MAP_H

#include <GL/gl.h>
#include "vertex.h"

class Map
{
    GLuint mapVBO;

    Vertex mapPos[4];

    void setMapSize( const float &parentX,
                     const float &parentY,
                     const float &parentWidth,
                     const float &parentHeight,
                     const float &width,
                     const float &height);

public:
    Map();

    void resize( float x, float y, float width, float height, float scale );

    void draw();
};

#endif // MAP_H
