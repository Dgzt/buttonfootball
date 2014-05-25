#include <GL/glew.h>
#include <iostream>
#include "map.h"
#include "table.h"

const float TABLE_WIDTH = 184.0;
const float TABLE_HEIGHT = 120.0;

Table::Table()
{
    glGenBuffers(1, &tableVBO);

    glBindBuffer(GL_ARRAY_BUFFER, tableVBO);

    map = new Map;
}

Table::~Table()
{
    delete map;
}

void Table::resize(int width, int height)
{
    float newTableWidth;
    float newTableHeight;

    float rate = (double)width/height;

    if( TABLE_WIDTH/TABLE_HEIGHT > rate ){
        newTableWidth = width;
        newTableHeight = TABLE_HEIGHT*(width/TABLE_WIDTH);
    }else{
        newTableWidth = TABLE_WIDTH*(height/TABLE_HEIGHT);
        newTableHeight = height;
    }

    /*GLfloat leftX = (width-newTableWidth)/2;
    GLfloat rightX = width-(width-newTableWidth)/2;
    GLfloat topY = (height-newTableHeight)/2;
    GLfloat bottomY = height - (height-newTableHeight)/2;*/

    GLfloat leftX = (width-newTableWidth)/2;
    GLfloat rightX = width-(width-newTableWidth)/2;
    GLfloat topY = height - (height-newTableHeight)/2;
    GLfloat bottomY = (height-newTableHeight)/2;

    pos[0].x = leftX;  pos[0].y = topY;
    pos[1].x = rightX; pos[1].y = topY;
    pos[2].x = rightX; pos[2].y = bottomY;
    pos[3].x = leftX;  pos[3].y = bottomY;

    glBindBuffer(GL_ARRAY_BUFFER, tableVBO);

    glBufferData(GL_ARRAY_BUFFER, sizeof(pos), pos, GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, 0);

    map->resize( pos[3].x, pos[3].y, newTableWidth, newTableHeight, newTableWidth / TABLE_WIDTH );
}

void Table::draw()
{
    glBindBuffer( GL_ARRAY_BUFFER, tableVBO );

    glVertexPointer(2, GL_FLOAT, sizeof(Vertex), NULL);
    //glColorPointer(4, GL_UNSIGNED_BYTE, 0, colors);
    glColor3f(0.5f, 0.5f, 0.5f);
    glDrawArrays(GL_QUADS, 0, sizeof(pos) / sizeof(Vertex));

    glBindBuffer( GL_ARRAY_BUFFER, 0 );

    map->draw();
}
