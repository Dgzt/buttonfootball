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
* The line shape.
*/

#ifndef RECTANGLEBORDER_H
#define RECTANGLEBORDER_H

#include "abstractshape.h"

class RectangleBorder : public AbstractShape
{

public:
    /*!
     * Constructor.
     * Initialize the border of rectangle.
     */
    RectangleBorder();

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

#endif // RECTANGLEBORDER_H
