#include <GL/glew.h>
#include <iostream>
#include "table.h"

const float TABLE_WIDTH = 184.0;
const float TABLE_HEIGHT = 120.0;

Table::Table()
{
    glGenBuffers(1, &tableVBO);

    glBindBuffer(GL_ARRAY_BUFFER, tableVBO);
}

void Table::resize(int width, int height)
{
    int newTableWidth;
    int newTableHeight;

    double rate = (double)width/height;
    if( TABLE_WIDTH/TABLE_HEIGHT > rate ){
        newTableWidth = width;
        newTableHeight = TABLE_HEIGHT*(width/TABLE_WIDTH);
    }else{
        newTableWidth = TABLE_WIDTH*(height/TABLE_HEIGHT);
        newTableHeight = height;
    }

    GLfloat leftX = (width-newTableWidth)/2;
    GLfloat rightX = width-(width-newTableWidth)/2;
    GLfloat topY = (height-newTableHeight)/2;
    GLfloat bottomY = height - (height-newTableHeight)/2;

    pos[0].x = leftX; pos[0].y = topY;
    pos[1].x = rightX; pos[1].y = topY;
    pos[2].x = rightX; pos[2].y = bottomY;
    pos[3].x = leftX; pos[3].y = bottomY;

    glBufferData(GL_ARRAY_BUFFER, sizeof(pos), pos, GL_STATIC_DRAW);

    glBindBuffer(GL_ARRAY_BUFFER, tableVBO);
}

void Table::draw()
{
    glVertexPointer(2, GL_FLOAT, sizeof(Vertex), NULL);
    //glColorPointer(4, GL_UNSIGNED_BYTE, 0, colors);
    glColor3f(0.5f, 0.5f, 0.5f);
    glDrawArrays(GL_QUADS, 0, sizeof(pos) / sizeof(Vertex));
}
