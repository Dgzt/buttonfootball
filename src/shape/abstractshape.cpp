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

#include <GL/glew.h>
#include "abstractshape.h"

AbstractShape::AbstractShape( GLenum type, int verticesNum ) :
    type( type ),
    verticesNum( verticesNum )
{
    generateVBO();
}

AbstractShape::AbstractShape(GLenum type) :
    type( type )
{
    generateVBO();
}

AbstractShape::~AbstractShape()
{
    glDeleteBuffers( 1, &vbo );
}

void AbstractShape::generateVBO()
{
    glGenBuffers( 1, &vbo );
}

void AbstractShape::resize(Vertex *vertices)
{
    glBindBuffer( GL_ARRAY_BUFFER, vbo );

    glBufferData( GL_ARRAY_BUFFER, verticesNum * sizeof(Vertex), vertices, GL_STATIC_DRAW );

    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}

void AbstractShape::resize(Vertex *vertices, int verticesNum)
{
    this->verticesNum = verticesNum;

    resize( vertices );
}

void AbstractShape::draw()
{
    glBindBuffer( GL_ARRAY_BUFFER, vbo );

    glVertexPointer( 2, GL_FLOAT, sizeof(Vertex), NULL );
    glColor3f( 1.0, 1.0, 1.0 );
    glDrawArrays( type, 0, verticesNum );
    glBindBuffer( GL_ARRAY_BUFFER, 0 );
}
