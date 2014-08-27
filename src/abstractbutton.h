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
* The abstract button. This implement the function of buttons.
*/

#ifndef ABSTRACTBUTTON_H
#define ABSTRACTBUTTON_H

#include "shape/circle.h"
#include <Box2D/Box2D.h>

class Table;
class b2World;

class AbstractButton : public Circle
{
    // The parent table.
    Table *parent;

    // The body from box2d.
    b2Body *body;

    // The x coordinate value in box2d world.
    float box2DX;

    // The y coordinate value n box2d world.
    float box2DY;

public:
    /*!
     * The constructor.
     *
     * @param parent The parent table.
     * @param box2DWorld The box2d world.
     * @param color The color.
     * @param box2DX The x coordinate value in box2d world.
     * @param box2DY The y coordinate value in box2d world.
     * @param radius The radius.
     */
    AbstractButton( Table *parent,
                    b2World *box2DWorld,
                    const Color &color,
                    const float &box2DX,
                    const float &box2DY,
                    const float &radius );

    /*!
     * The destructor.
     */
    ~AbstractButton();

    /*!
     * Resize the button.
     */
    void resize();

    /*!
     * Draw the button.
     */
    void draw();

    /*!
     * Get the x coordinate value.
     *
     * @return The x coordinate value.
     */
    int getX();

    /*!
     * Get the y coordinate value.
     *
     * @return The y coordinate value.
     */
    int getY();

    /*!
     * Check the given coordinate values to contains the button.
     *
     * @param mouseX The x coordinate value of mouse button.
     * @param mouseY The y coordiante value of mouse button.
     * @return True when contains else false.
     */
    bool contains( const unsigned int &mouseX, const unsigned int &mouseY );

    void move( b2Vec2 vector );
};

#endif // ABSTRACTBUTTON_H
