#include "GL/glew.h"
#include <iostream>
#include "map.h"

const float MAP_WIDTH = 167.0;
const float MAP_HEIGHT = 104.0;

const float BORDER = 1.0;

const float SECTOR_16_WIDTH = 30.0;
const float SECTOR_16_HEIGHT = 60.0;

Map::Map()
{
    glGenBuffers(1, &mapVBO);

    // Set the lines to smooth.
    glEnable(GL_LINE_SMOOTH);
}

void Map::setMapSize(const float &parentX, const float &parentY, const float &parentWidth, const float &parentHeight, const float &width, const float &height)
{
    GLfloat leftX = parentX + (parentWidth - width)/2;
    GLfloat rightX = parentX + parentWidth - (parentWidth - width)/2;
    GLfloat topY = parentY - (parentHeight - height)/2;
    GLfloat bottomY = parentY - parentHeight + (parentHeight - height)/2;

    mapPos[0].x = leftX;   mapPos[0].y = topY;
    mapPos[1].x = rightX;  mapPos[1].y = topY;
    mapPos[2].x = rightX;  mapPos[2].y = bottomY;
    mapPos[3].x = leftX;   mapPos[3].y = bottomY;

    glBindBuffer(GL_ARRAY_BUFFER, mapVBO);

    glBufferData( GL_ARRAY_BUFFER, sizeof(mapPos), mapPos, GL_STATIC_DRAW );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}

void Map::setLeftSector16Size(const float &mapX, const float &mapY, const float &mapHeight, const float &width, const float &height)
{
    GLfloat leftX = mapX;
    GLfloat rightX = mapX + width;
    GLfloat topY = mapY - ( mapHeight - height ) / 2;
    GLfloat bottomY = mapY - mapHeight + ( mapHeight - height ) / 2;

    leftSector16Pos[0].x = leftX;  leftSector16Pos[0].y = topY;
    leftSector16Pos[1].x = rightX; leftSector16Pos[1].y = topY;
    leftSector16Pos[2].x = rightX; leftSector16Pos[2].y = bottomY;
    leftSector16Pos[3].x = leftX;  leftSector16Pos[3].y = bottomY;

    glBindBuffer( GL_ARRAY_BUFFER, leftSector16VBO );

    glBufferData( GL_ARRAY_BUFFER, sizeof(leftSector16Pos), leftSector16Pos, GL_STATIC_DRAW );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}

void Map::resize( float x, float y, float width, float height, float scale )
{
    float newMapWidth = MAP_WIDTH * scale;
    float newMapHeight = MAP_HEIGHT * scale;

    setMapSize(x, y, width, height, newMapWidth, newMapHeight);

    float newSector16Width = SECTOR_16_WIDTH * scale;
    float newSector16Height = SECTOR_16_HEIGHT * scale;

    setLeftSector16Size(mapPos[0].x, mapPos[0].y, newMapHeight, newSector16Width, newSector16Height);

    // Set the size of the borders
    glLineWidth(BORDER*scale);
}

void Map::draw()
{
    // Map
    glBindBuffer(GL_ARRAY_BUFFER, mapVBO);

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( 0.0, 1.0, 0.0 );
    glDrawArrays( GL_QUADS, 0, sizeof(mapPos) / sizeof(Vertex) );

    // White line
    glColor3f( 1.0, 1.0, 1.0 );
    glDrawArrays( GL_LINE_LOOP, 0, sizeof(mapPos) / sizeof(Vertex) );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );

    //Left sector 16
    glBindBuffer( GL_ARRAY_BUFFER, leftSector16VBO );

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( 1.0, 1.0, 1.0 );
    glDrawArrays( GL_LINE_LOOP, 0, sizeof(leftSector16Pos) / sizeof(Vertex) );
    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}
