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
* The rectangle shape.
*/

#ifndef RECTANGLE_H
#define RECTANGLE_H

#include "abstractshape.h"

class Rectangle : public AbstractShape
{

public:
    /*!
     * Constructor.
     * If the type is 'GL_QUADS' then filled rectangle.
     * If the type is 'GL_LINE_LOOP' then border of the rectangle.
     *
     * @param type The type of the rectangle.
     * @param color The color of the rectangle.
     */
    Rectangle( const GLenum &type, const Color &color );

    /*!
     * Resize the shape.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param width The width of rectangle.
     * @param height The height of rectangle.
     */
    void resize( const float &x,
                 const float &y,
                 const float &width,
                 const float &height);

};

#endif // RECTANGLE_H
