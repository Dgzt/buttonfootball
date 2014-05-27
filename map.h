#ifndef MAP_H
#define MAP_H

#include <GL/gl.h>
#include "vertex.h"

class Map
{
    GLuint mapVBO;

    GLuint leftSector16VBO;

    GLuint rightSector16VBO;

    GLuint leftSector5VBO;

    Vertex mapPos[4];

    Vertex leftSector16Pos[4];

    Vertex rightSector16Pos[4];

    Vertex leftSector5Pos[4];

    void setMapSize( const float &parentX,
                     const float &parentY,
                     const float &parentWidth,
                     const float &parentHeight,
                     const float &width,
                     const float &height);

    void setLeftSector16Size( const float &mapX,
                              const float &mapY,
                              const float &mapHeight,
                              const float &width,
                              const float &height);

    void setRightSector16Size( const float &mapX,
                               const float &mapY,
                               const float &mapWidth,
                               const float &mapHeight,
                               const float &width,
                               const float &height);

    void setLeftSector5Size( const float &mapX,
                             const float &mapY,
                             const float &mapHeight,
                             const float &width,
                             const float &height);


public:
    Map();

    void resize( float x, float y, float width, float height, float scale );

    void draw();
};

#endif // MAP_H
