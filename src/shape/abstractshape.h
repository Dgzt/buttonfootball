/*!
* @file
* @author Zsuro Tibor <zsurotibor@gmail.com>
*
* @section LICENSE
*
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
*
* @section DESCRIPTION
*
* Abstract shape.
*/

#ifndef ABSTRACTSHAPE_H
#define ABSTRACTSHAPE_H

#include <GL/gl.h>
#include "../vertex.h"
#include "../color.h"

class AbstractShape
{
    /*!
     * The vertex buffer object id.
     */
    GLuint vbo;

    /*!
     * Thy type of shape. Example: GL_LINE_LOOP.
     */
    GLenum type;

    /*!
     * The color.
     */
    Color color;

    /*!
     * The amount of verties.
     */
    int verticesNum;

    /*!
     * Generate the vertex buffer object id.
     */
    void generateVBO();

protected:
    /*!
     * Update the vertices.
     *
     * @param vertices The vertices array.
     */
    void resize( Vertex *vertices );

    /*!
     * Update the vertices.
     *
     * @param vertices The vertices array.
     * @param verticesNum The amount of vertices.
     */
    void resize( Vertex *vertices, int verticesNum );

public:
    /*!
     * Constructor.
     * Init the shape.
     *
     * @param type The type of shape.
     * @param verticesNum The amount of vertices.
     * @param color The color of shape.
     */
    AbstractShape( GLenum type, int verticesNum, const Color &color );

    /*!
     * Constructor.
     * Init the shape.
     *
     * @param type The type of shape.
     * @param color The color of shape.
     */
    AbstractShape( GLenum type, const Color &color );

    /*!
     * The desctructor.
     */
    ~AbstractShape();

    /*!
     * Draw the shape.
     */
    void draw();
};

#endif // ABSTRACTSHAPE_H
