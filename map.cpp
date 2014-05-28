#include "GL/glew.h"
#include <iostream>
#include "rectangleborder.h"
#include "circleborder.h"
#include "map.h"

const float MAP_WIDTH = 167.0;
const float MAP_HEIGHT = 104.0;

const float BORDER = 1.0;

const float SECTOR_16_WIDTH = 30.0;
const float SECTOR_16_HEIGHT = 60.0;

const float SECTOR_5_WIDTH = 11.0;
const float SECTOR_5_HEIGHT = 30.0;

const float BIG_CIRCLE_RADIUS = 16.0;

Map::Map()
{
    glGenBuffers( 1, &mapVBO );

    leftSector16 = new RectangleBorder();
    rightSector16 = new RectangleBorder();
    leftSector5 = new RectangleBorder();
    rightSector5 = new RectangleBorder();

    centralCircle = new CircleBorder();

    // Set the lines to smooth.
    glEnable(GL_LINE_SMOOTH);
}

Map::~Map()
{
    delete leftSector16;
    delete rightSector16;
    delete leftSector5;
    delete rightSector5;

    delete centralCircle;

    glDeleteBuffers( 1, &mapVBO );
}

void Map::resize( float parentX, float parentY, float parentWidth, float parentHeight, float scale )
{
    // Map
    float mapWidth = MAP_WIDTH * scale;
    float mapHeight = MAP_HEIGHT * scale;

    GLfloat leftMapX = parentX + (parentWidth - mapWidth)/2;
    GLfloat rightMapX = parentX + parentWidth - (parentWidth - mapWidth)/2;
    GLfloat topMapY = parentY - (parentHeight - mapHeight)/2;
    GLfloat bottomMapY = parentY - parentHeight + (parentHeight - mapHeight)/2;

    Vertex mapVertices[4];

    mapVertices[0].x = leftMapX;   mapVertices[0].y = topMapY;
    mapVertices[1].x = rightMapX;  mapVertices[1].y = topMapY;
    mapVertices[2].x = rightMapX;  mapVertices[2].y = bottomMapY;
    mapVertices[3].x = leftMapX;   mapVertices[3].y = bottomMapY;

    glBindBuffer(GL_ARRAY_BUFFER, mapVBO);

    glBufferData( GL_ARRAY_BUFFER, sizeof(mapVertices), mapVertices, GL_STATIC_DRAW );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );

    // Sector 16
    float sector16Width = SECTOR_16_WIDTH * scale;
    float sector16Height = SECTOR_16_HEIGHT * scale;
    float sector16Y = topMapY - ( mapHeight - sector16Height ) / 2;

    leftSector16->resize( leftMapX, sector16Y, sector16Width, sector16Height );
    rightSector16->resize( leftMapX + mapWidth - sector16Width, sector16Y, sector16Width, sector16Height);

    // Sector 5
    float sector5Width = SECTOR_5_WIDTH * scale;
    float sector5Height = SECTOR_5_HEIGHT * scale;
    float sector5Y = topMapY - ( mapHeight - sector5Height ) / 2;

    leftSector5->resize( leftMapX, sector5Y, sector5Width, sector5Height );
    rightSector5->resize( leftMapX + mapWidth - sector5Width, sector5Y, sector5Width, sector5Height );

    // Central big circle
    float bigCircleRadius = BIG_CIRCLE_RADIUS * scale;

    centralCircle->resize( leftMapX + mapWidth / 2, topMapY - mapHeight / 2, bigCircleRadius );

    // Set the size of the borders
    glLineWidth(BORDER*scale);
}

void Map::draw()
{
    glBindBuffer(GL_ARRAY_BUFFER, mapVBO);

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    // Green map
    glColor3f( 0.0, 1.0, 0.0 );
    glDrawArrays( GL_QUADS, 0, 4 );

    // White line
    glColor3f( 1.0, 1.0, 1.0 );
    glDrawArrays( GL_LINE_LOOP, 0, 4 );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );

    leftSector16->draw();
    rightSector16->draw();
    leftSector5->draw();
    rightSector5->draw();

    centralCircle->draw();
}
