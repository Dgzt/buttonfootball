#include <GL/glew.h>
#include <vector>
#include <math.h>
#include <iostream>
#include "circle.h"

#define degreesToRadians(angleDegrees) (angleDegrees * M_PI / 180.0)

/**
 * The default starting degrees.
 */
const float DEFAULT_DEGREES = 90;

Circle::Circle( bool fill ) :
    startDegrees( 0 ),
    endDegrees( 360 )
{
    if( fill ){
        type = GL_POLYGON;
    }else{
        type = GL_LINE_LOOP;
    }

    glGenBuffers( 1, &vbo );
}

Circle::Circle(float startDegrees, float endDegrees) :
    type( GL_LINE_STRIP ),
    startDegrees( startDegrees ),
    endDegrees( endDegrees + 10 )
{
    glGenBuffers( 1, &vbo );
}

Circle::~Circle()
{
    glDeleteBuffers( 1, &vbo );
}

void Circle::resize(const float &x, const float &y, const float &r)
{
    std::vector<Vertex> vertices;

    for( int i = DEFAULT_DEGREES + startDegrees; i < DEFAULT_DEGREES + endDegrees; i+=15 ){

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

void Circle::draw()
{
    glBindBuffer( GL_ARRAY_BUFFER, vbo );

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( 1.0, 1.0, 1.0 );
    glDrawArrays( type, 0, verticesNum );
    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}
