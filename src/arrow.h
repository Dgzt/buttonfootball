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
* The arrow.
*/

#ifndef ARROW_H
#define ARROW_H

#include "shape/line.h"

class Arrow : public Line
{
    // Visible status.
    bool visible;

    // The x1 coordinate value.
    float x1;

    // The y2 coordinate Value.
    float y1;

public:
    /*!
     * The constructor.
     */
    Arrow();

    /*!
     * Set the start point.
     *
     * @param x The x coordinate value of start point.
     * @param y The y coordinate value of start point.
     */
    void setStart( const float &x, const float &y ){ x1 = x; y1 = y; }

    /*!
     * Set the end point and show the line.
     *
     * @param x2 The x coordinate value of end point.
     * @param y2 The y coordinate value of end point.
     */
    void setEnd( const float &x2, const float &y2 );

    /*!
     * Set the visible of arrow.
     *
     * @param visible The visible status.
     */
    void setVisible( const bool &visible ){ this->visible = visible; }

    /*!
     * Draw the arrow.
     */
    void draw();
};

#endif // ARROW_H
