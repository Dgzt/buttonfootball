#include "GL/glew.h"
#include <iostream>
#include "rectangleborder.h"
#include "map.h"

const float MAP_WIDTH = 167.0;
const float MAP_HEIGHT = 104.0;

const float BORDER = 1.0;

const float SECTOR_16_WIDTH = 30.0;
const float SECTOR_16_HEIGHT = 60.0;

const float SECTOR_5_WIDTH = 11.0;
const float SECTOR_5_HEIGHT = 30.0;

Map::Map()
{
    glGenBuffers( 1, &mapVBO );

    leftSector16 = new RectangleBorder();
    rightSector16 = new RectangleBorder();
    leftSector5 = new RectangleBorder();
    rightSector5 = new RectangleBorder();

    // Set the lines to smooth.
    glEnable(GL_LINE_SMOOTH);
}

Map::~Map()
{
    delete leftSector16;
    delete rightSector16;
    delete leftSector5;
    delete rightSector5;
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

void Map::resize( float x, float y, float width, float height, float scale )
{
    float newMapWidth = MAP_WIDTH * scale;
    float newMapHeight = MAP_HEIGHT * scale;

    setMapSize(x, y, width, height, newMapWidth, newMapHeight);

    float sector16Width = SECTOR_16_WIDTH * scale;
    float sector16Height = SECTOR_16_HEIGHT * scale;
    float sector16Y = mapPos[0].y - ( newMapHeight - sector16Height ) / 2;

    leftSector16->resize( mapPos[0].x, sector16Y, sector16Width, sector16Height );
    rightSector16->resize( mapPos[0].x + newMapWidth - sector16Width, sector16Y, sector16Width, sector16Height);

    float sector5Width = SECTOR_5_WIDTH * scale;
    float sector5Height = SECTOR_5_HEIGHT * scale;
    float sector5Y = mapPos[0].y - ( newMapHeight - sector5Height ) / 2;

    leftSector5->resize( mapPos[0].x, sector5Y, sector5Width, sector5Height );
    rightSector5->resize( mapPos[0].x + newMapWidth - sector5Width, sector5Y, sector5Width, sector5Height );

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

    leftSector16->draw();
    rightSector16->draw();
    leftSector5->draw();
    rightSector5->draw();
}
