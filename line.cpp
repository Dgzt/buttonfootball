#include <GL/glew.h>
#include <iostream>
#include "vertex.h"
#include "line.h"

/**
 * The amount of vertices.
 */
const unsigned int VERTICES_NUM = 2;

/**
 * The size of vertices.
 */
const unsigned int VERTICES_SIZE = VERTICES_NUM * sizeof(Vertex);

Line::Line()
{
    glGenBuffers( 1, &vbo );
}

Line::~Line()
{
    glDeleteBuffers( 1, &vbo );
}

void Line::resize(const float &x1, const float &y1, const float &x2, const float &y2)
{
    Vertex vertices[2] = { x1, y1, x2, y2 };

    glBindBuffer( GL_ARRAY_BUFFER, vbo );

    glBufferData( GL_ARRAY_BUFFER, VERTICES_SIZE, vertices, GL_STATIC_DRAW );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}

void Line::draw()
{
    glBindBuffer( GL_ARRAY_BUFFER, vbo );

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( 1.0, 1.0, 1.0 );
    glDrawArrays( GL_LINES, 0, VERTICES_NUM );
    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}
