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
* The circle shape.
*/

#ifndef CIRCLE_H
#define CIRCLE_H

#include "abstractshape.h"

class Circle : public AbstractShape
{
    /*!
     * The start degrees to draw.
     */
    float startDegrees;

    /*!
     * The end degrees to draw.
     */
    float endDegrees;

public:
    /*!
     * Full circle, 0-360 degrees.
     * If the type is GL_POLYGON then filled.
     * If the type is GL_LINE_LOOP then border of circle.
     *
     * @param type Type of the circle.
     */
    Circle( GLenum type );

    /*!
     * Arc.
     *
     * @param startDegrees The start degrees.
     * @param endDegrees The end degrees.
     */
    Circle( float startDegrees, float endDegrees );

    /*!
     * Resize the shape.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param r The radius.
     */
    void resize( const float &x,
                 const float &y,
                 const float &r);

};

#endif // CIRCLE_H
