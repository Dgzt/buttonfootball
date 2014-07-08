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
* The map.
*/

#ifndef MAP_H
#define MAP_H

#include "shape/rectangle.h"

class Circle;
class Line;

class Map : public Rectangle
{
    Rectangle *leftSector16;
    Rectangle *rightSector16;
    Rectangle *leftSector5;
    Rectangle *rightSector5;

    Circle *bigCentralCircle;

    Line *halfLine;

    Circle *leftCircle11;

    Circle *rightCircle11;

    Circle *smallCentralCircle;

    Circle *topLeftCornerArc;
    Circle *topRightCornerArc;
    Circle *bottomLeftCornerArc;
    Circle *bottomRightCornerArc;

    Circle *bigLeftArc;
    Circle *bigRightArc;

public:
    /*!
     * The constructor.
     */
    Map();

    /*!
     * The desctructor.
     */
    ~Map();

    /*!
     * Recalculate the position on the parent (table).
     *
     * @param x The x coordinate value.
     * @param y The y coordinate value.
     * @param width The width value.
     * @param height The height value.
     * @param scale The scale of the table on window.
     */
    void resize( const float &x,
                 const float &y,
                 const float &width,
                 const float &height,
                 const float &scale );

    /*!
     * Draw the map.
     */
    void draw();
};

#endif // MAP_H
