#include <GL/glew.h>
#include <vector>
#include <math.h>
#include <iostream>
#include "circleborder.h"

#define degreesToRadians(angleDegrees) (angleDegrees * M_PI / 180.0)

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

    for( int i = 0; i < 360; i+=15 ){

        float angle = degreesToRadians(i);

        Vertex v;
        v.x = x + sin( angle ) * r;
        v.y = y + cos( angle ) * r;

       vertices.push_back( v );
    }

    verticesNum = vertices.size();


    glBindBuffer(GL_ARRAY_BUFFER, vbo);

    glBufferData( GL_ARRAY_BUFFER, verticesNum*sizeof(Vertex), &vertices[0], GL_STATIC_DRAW );

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
