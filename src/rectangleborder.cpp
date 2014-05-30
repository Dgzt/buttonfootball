#include "GL/glew.h"
#include "vertex.h"
#include "rectangleborder.h"

const int VERTICES_NUM = 4;

const int VERTICES_SIZE = VERTICES_NUM * sizeof(Vertex);

RectangleBorder::RectangleBorder()
{
    glGenBuffers( 1, &vbo );
}

RectangleBorder::~RectangleBorder()
{
    glDeleteBuffers( 1, &vbo );
}

void RectangleBorder::resize(const float &x, const float &y, const float &width, const float &height)
{
    Vertex vertices[4];

    vertices[0].x = x;          vertices[0].y = y;
    vertices[1].x = x + width;  vertices[1].y = y;
    vertices[2].x = x + width;  vertices[2].y = y - height;
    vertices[3].x = x;          vertices[3].y = y - height;

    glBindBuffer( GL_ARRAY_BUFFER, vbo );

    glBufferData( GL_ARRAY_BUFFER, VERTICES_SIZE, vertices, GL_STATIC_DRAW );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}

void RectangleBorder::draw()
{
    glBindBuffer( GL_ARRAY_BUFFER, vbo );

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( 1.0, 1.0, 1.0 );
    glDrawArrays( GL_LINE_LOOP, 0, VERTICES_NUM );
    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}
