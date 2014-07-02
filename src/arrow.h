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

class Button;

class Arrow : public Line
{
    Button* button;

    float x2;

    float y2;

public:
    /*!
     * The constructor.
     */
    Arrow();

    /*!
     * Set the button.
     *
     * @param button The new button.
     */
    void setButton( Button *button );

    /*!
     * Return true when button isn't null else false.
     *
     * @return true/false.
     */
    bool isButtonSelected(){ return button != NULL; }

    /*!
     * Set the end point and show the line.
     *
     * @param x2 The x coordinate value of end point.
     * @param y2 The y coordinate value of end point.
     */
    void setEnd( const float &x2, const float &y2 );

    /*!
     * Draw the arrow.
     */
    void draw();

    /*!
     * Move the button of arrow.
     */
    void moveButton();
};

#endif // ARROW_H
