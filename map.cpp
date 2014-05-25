#include "GL/glew.h"
#include <iostream>
#include "map.h"

const float MAP_WIDTH = 167.0;
const float MAP_HEIGHT = 104.0;

const float BORDER = 1.0;

Map::Map()
{
    glGenBuffers(1, &whiteMapVBO);

    glGenBuffers(1, &greenMapVBO);
}

void Map::resize( float x, float y, float width, float height, float scale )
{
    // green map

    float newWidth = MAP_WIDTH * scale;
    float newHeight = MAP_HEIGHT * scale;

    GLfloat leftX = x + (width - newWidth)/2;
    GLfloat rightX = x + width - (width - newWidth)/2;
    GLfloat topY = y + height - (height - newHeight)/2;
    GLfloat bottomY = y + (height - newHeight)/2;

    greenMapPos[0].x = leftX;   greenMapPos[0].y = topY;
    greenMapPos[1].x = rightX;  greenMapPos[1].y = topY;
    greenMapPos[2].x = rightX;  greenMapPos[2].y = bottomY;
    greenMapPos[3].x = leftX;   greenMapPos[3].y = bottomY;

    glBindBuffer(GL_ARRAY_BUFFER, greenMapVBO);

    glBufferData( GL_ARRAY_BUFFER, sizeof(greenMapPos), greenMapPos, GL_STATIC_DRAW );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );

    // white map

    float newBorder = BORDER * scale;

    whiteMapPos[0].x = leftX - newBorder;   whiteMapPos[0].y = topY + newBorder;
    whiteMapPos[1].x = rightX + newBorder;  whiteMapPos[1].y = topY + newBorder;
    whiteMapPos[2].x = rightX + newBorder;  whiteMapPos[2].y = bottomY - newBorder;
    whiteMapPos[3].x = leftX - newBorder;   whiteMapPos[3].y = bottomY - newBorder;

    glBindBuffer( GL_ARRAY_BUFFER, whiteMapVBO );

    glBufferData( GL_ARRAY_BUFFER, sizeof(whiteMapPos), whiteMapPos, GL_STATIC_DRAW );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}

void Map::draw()
{
    // white
    glBindBuffer(GL_ARRAY_BUFFER, whiteMapVBO);

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( 1.0, 1.0, 1.0 );
    glDrawArrays( GL_QUADS, 0, sizeof(whiteMapPos) / sizeof(Vertex) );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );


    // green
    glBindBuffer(GL_ARRAY_BUFFER, greenMapVBO);

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( 0.0, 1.0, 0.0 );
    glDrawArrays( GL_QUADS, 0, sizeof(greenMapPos) / sizeof(Vertex) );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}
