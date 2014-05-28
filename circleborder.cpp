#include <GL/glew.h>
#include <vector>
#include <math.h>
#include <iostream>
#include "circleborder.h"

const float PI = 3.141592;

CircleBorder::CircleBorder()
{
    glGenBuffers( 1, &vbo );
}

CircleBorder::~CircleBorder()
{
    glDeleteBuffers( 1, &vbo );
}

void CircleBorder::resize(const float &x, const float &y, const float &r)
{
    std::vector<Vertex> vertices;

    for( float angle = 0; angle < 8*PI*2; angle+=0.01 ){
        Vertex v;
        v.x = x + sin( angle ) * r;
        v.y = y + cos ( angle ) * r;

       vertices.push_back( v );
    }

    verticesNum = vertices.size();


    glBindBuffer(GL_ARRAY_BUFFER, vbo);

    glBufferData( GL_ARRAY_BUFFER, verticesNum, &vertices[0], GL_STATIC_DRAW );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );

    vertices.clear();
}

void CircleBorder::draw()
{
    glBindBuffer( GL_ARRAY_BUFFER, vbo );

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( 1.0, 1.0, 1.0 );
    glDrawArrays( GL_LINE_LOOP, 0, verticesNum );
    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}
