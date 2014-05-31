#include "GL/glew.h"
#include <iostream>
#include "shape/circle.h"
#include "shape/line.h"
#include "map.h"

const float MAP_WIDTH = 167.0;
const float MAP_HEIGHT = 104.0;

const float BORDER = 0.3f;

const float SECTOR_16_WIDTH = 30.0;
const float SECTOR_16_HEIGHT = 60.0;

const float SECTOR_5_WIDTH = 11.0;
const float SECTOR_5_HEIGHT = 30.0;

const float BIG_CIRCLE_RADIUS = 16.0;

const float SMALL_CIRCLE_RADIUS = 0.5f;
const float CIRCLE_11_DISTANCE = 20.5f;

const float CORNER_ARC_RADIUS = 3.1f;

const Color WHITE_COLOR = { 1.0f, 1.0f, 1.0f };
const Color GREEN_COLOR = { 0.0f, 1.0f, 0.0f };

Map::Map() :
    Rectangle( GL_QUADS, GREEN_COLOR )
{
    leftSector16 = new Rectangle( GL_LINE_LOOP, WHITE_COLOR );
    rightSector16 = new Rectangle( GL_LINE_LOOP, WHITE_COLOR );
    leftSector5 = new Rectangle( GL_LINE_LOOP, WHITE_COLOR );
    rightSector5 = new Rectangle( GL_LINE_LOOP, WHITE_COLOR );

    bigCentralCircle = new Circle( GL_LINE_LOOP, WHITE_COLOR );

    halfLine = new Line( WHITE_COLOR );

    leftCircle11 = new Circle( GL_POLYGON, WHITE_COLOR );
    rightCircle11 = new Circle( GL_POLYGON, WHITE_COLOR );

    smallCentralCircle = new Circle( GL_POLYGON, WHITE_COLOR );

    topLeftCornerArc = new Circle( 0, 90, WHITE_COLOR );
    topRightCornerArc = new Circle( 90, 180, WHITE_COLOR );
    bottomLeftCornerArc = new Circle( 270, 360, WHITE_COLOR );
    bottomRightCornerArc = new Circle( 180, 270, WHITE_COLOR );

    bigLeftArc = new Circle( 305, 415, WHITE_COLOR );
    bigRightArc = new Circle( 125, 235, WHITE_COLOR );

    // Set the lines to smooth.
    glEnable(GL_LINE_SMOOTH);
}

Map::~Map()
{
    delete leftSector16;
    delete rightSector16;
    delete leftSector5;
    delete rightSector5;

    delete bigCentralCircle;

    delete halfLine;

    delete leftCircle11;
    delete rightCircle11;

    delete smallCentralCircle;

    delete topLeftCornerArc;
    delete topRightCornerArc;
    delete bottomLeftCornerArc;
    delete bottomRightCornerArc;

    delete bigLeftArc;
    delete bigRightArc;
}

void Map::resize( float parentX, float parentY, float parentWidth, float parentHeight, float scale )
{
    // Map
    float mapWidth = MAP_WIDTH * scale;
    float mapHeight = MAP_HEIGHT * scale;

    float mapX = parentX + (parentWidth - mapWidth)/2;
    float mapY = parentY - (parentHeight - mapHeight)/2;

    Rectangle::resize( mapX, mapY, mapWidth, mapHeight );

    // Sector 16
    float sector16Width = SECTOR_16_WIDTH * scale;
    float sector16Height = SECTOR_16_HEIGHT * scale;
    float sector16Y = mapY - ( mapHeight - sector16Height ) / 2;

    leftSector16->resize( mapX, sector16Y, sector16Width, sector16Height );
    rightSector16->resize( mapX + mapWidth - sector16Width, sector16Y, sector16Width, sector16Height);

    // Sector 5
    float sector5Width = SECTOR_5_WIDTH * scale;
    float sector5Height = SECTOR_5_HEIGHT * scale;
    float sector5Y = mapY - ( mapHeight - sector5Height ) / 2;

    leftSector5->resize( mapX, sector5Y, sector5Width, sector5Height );
    rightSector5->resize( mapX + mapWidth - sector5Width, sector5Y, sector5Width, sector5Height );

    // Central big circle
    float bigCircleRadius = BIG_CIRCLE_RADIUS * scale;

    bigCentralCircle->resize( mapX + mapWidth / 2, mapY - mapHeight / 2, bigCircleRadius );

    // Half line
    float halfMapX = mapX + ( mapWidth / 2 );
    float bottomMapY = mapY - mapHeight;

    halfLine->resize( halfMapX, mapY, halfMapX, bottomMapY );

    // Left circle 11
    float smallCircleDistance = CIRCLE_11_DISTANCE * scale;
    float smallCircleRadius = SMALL_CIRCLE_RADIUS * scale;
    float smallCircleY = mapY - mapHeight / 2;
    float leftSmallCircleX = mapX + smallCircleDistance;

    leftCircle11->resize( leftSmallCircleX, smallCircleY, smallCircleRadius );

    // Right circle 11
    float rightSmallCircleX = mapX + mapWidth - smallCircleDistance;

    rightCircle11->resize( rightSmallCircleX, smallCircleY, smallCircleRadius );

    // Central small circle
    float centralSmallCircleX = mapX + ( mapWidth / 2 );
    float centralSmallCircleY = mapY - ( mapHeight / 2 );

    smallCentralCircle->resize( centralSmallCircleX, centralSmallCircleY, smallCircleRadius );

    // Corner arcs
    float cornerArcRadius = CORNER_ARC_RADIUS * scale;

    topLeftCornerArc->resize( mapX, mapY, cornerArcRadius );
    topRightCornerArc->resize( mapX + mapWidth, mapY, cornerArcRadius );
    bottomLeftCornerArc->resize( mapX, bottomMapY, cornerArcRadius );
    bottomRightCornerArc->resize( mapX + mapWidth, bottomMapY, cornerArcRadius );

    // Big left arc
    bigLeftArc->resize( leftSmallCircleX, smallCircleY, bigCircleRadius );
    bigRightArc->resize( rightSmallCircleX, smallCircleY, bigCircleRadius );

    // Set the size of the borders
    glLineWidth( BORDER*scale );
}

void Map::draw()
{
    Rectangle::draw();

    // White border
    glBindBuffer(GL_ARRAY_BUFFER, getVbo() );

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( WHITE_COLOR.r, WHITE_COLOR.g, WHITE_COLOR.b );
    glDrawArrays( GL_LINE_LOOP, 0, 4 );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );

    leftSector16->draw();
    rightSector16->draw();
    leftSector5->draw();
    rightSector5->draw();

    bigCentralCircle->draw();

    halfLine->draw();

    leftCircle11->draw();

    rightCircle11->draw();

    smallCentralCircle->draw();

    topLeftCornerArc->draw();
    topRightCornerArc->draw();
    bottomLeftCornerArc->draw();
    bottomRightCornerArc->draw();

    bigLeftArc->draw();
    bigRightArc->draw();
}
