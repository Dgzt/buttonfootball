#include "GL/glew.h"
#include <iostream>
#include "map.h"

const float MAP_WIDTH = 168.0;
const float MAP_HEIGHT = 105.0;

Map::Map()
{
    glGenBuffers(1, &vbo);

    glBindBuffer(GL_ARRAY_BUFFER, vbo);
}

void Map::resize( float x, float y, float width, float height, float scale )
{
    glBindBuffer(GL_ARRAY_BUFFER, vbo);

    float newWidth = MAP_WIDTH * scale;
    float newHeight = MAP_HEIGHT * scale;

    GLfloat leftX = x + (width - newWidth)/2;
    GLfloat rightX = x + width - (width - newWidth)/2;
    GLfloat topY = y + height - (height - newHeight)/2;
    GLfloat bottomY = y + (height - newHeight)/2;

    pos[0].x = leftX;   pos[0].y = topY;
    pos[1].x = rightX;  pos[1].y = topY;
    pos[2].x = rightX;  pos[2].y = bottomY;
    pos[3].x = leftX;   pos[3].y = bottomY;

    glBufferData( GL_ARRAY_BUFFER, sizeof(pos), pos, GL_STATIC_DRAW );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}

void Map::draw()
{
    glBindBuffer(GL_ARRAY_BUFFER, vbo);

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( 1.0, 1.0, 1.0 );
    glDrawArrays( GL_QUADS, 0, sizeof(pos) / sizeof(Vertex) );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}
