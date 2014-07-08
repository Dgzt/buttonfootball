/*!
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

#include "GL/glew.h"
#include "shape/circle.h"
#include "shape/line.h"
#include "map.h"

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

void Map::resize(const float &x, const float &y, const float &width, const float &height, const float &scale)
{
    Rectangle::resize(x,y,width,height);

    // Sector 16
    float sector16Width = SECTOR_16_WIDTH * scale;
    float sector16Height = SECTOR_16_HEIGHT * scale;
    float sector16Y = y - ( height - sector16Height ) / 2;

    leftSector16->resize( x, sector16Y, sector16Width, sector16Height );
    rightSector16->resize( x + width - sector16Width, sector16Y, sector16Width, sector16Height);

    // Sector 5
    float sector5Width = SECTOR_5_WIDTH * scale;
    float sector5Height = SECTOR_5_HEIGHT * scale;
    float sector5Y = y - ( height - sector5Height ) / 2;

    leftSector5->resize( x, sector5Y, sector5Width, sector5Height );
    rightSector5->resize( x + width - sector5Width, sector5Y, sector5Width, sector5Height );

    // Central big circle
    float bigCircleRadius = BIG_CIRCLE_RADIUS * scale;

    bigCentralCircle->resize( x + width / 2, y - height / 2, bigCircleRadius );

    // Half line
    float halfx = x + ( width / 2 );
    float bottomy = y - height;

    halfLine->resize( halfx, y, halfx, bottomy );

    // Left circle 11
    float smallCircleDistance = CIRCLE_11_DISTANCE * scale;
    float smallCircleRadius = SMALL_CIRCLE_RADIUS * scale;
    float smallCircleY = y - height / 2;
    float leftSmallCircleX = x + smallCircleDistance;

    leftCircle11->resize( leftSmallCircleX, smallCircleY, smallCircleRadius );

    // Right circle 11
    float rightSmallCircleX = x + width - smallCircleDistance;

    rightCircle11->resize( rightSmallCircleX, smallCircleY, smallCircleRadius );

    // Central small circle
    float centralSmallCircleX = x + ( width / 2 );
    float centralSmallCircleY = y - ( height / 2 );

    smallCentralCircle->resize( centralSmallCircleX, centralSmallCircleY, smallCircleRadius );

    // Corner arcs
    float cornerArcRadius = CORNER_ARC_RADIUS * scale;

    topLeftCornerArc->resize( x, y, cornerArcRadius );
    topRightCornerArc->resize( x + width, y, cornerArcRadius );
    bottomLeftCornerArc->resize( x, bottomy, cornerArcRadius );
    bottomRightCornerArc->resize( x + width, bottomy, cornerArcRadius );

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
