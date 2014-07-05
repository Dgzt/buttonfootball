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
* The table.
*/

#ifndef TABLE_H
#define TABLE_H

#include "shape/rectangle.h"
#include <vector>

class Map;
class b2World;
class Button;
class Ball;
class Arrow;

class Table : public Rectangle
{
    // The x coordinate of the table.
    GLfloat x;

    // The bottom coordinate of the table.
    GLfloat bottom;

    // The scale value.
    float scale;

    // The map.
    Map *map;

    // The box2d world.
    b2World* world;

    // The player buttons.
    std::vector<Button*> playerButtons;

    // The opponent buttons.
    std::vector<Button*> opponentButtons;

    // The ball.
    Ball *ball;

    // The arrow.
    Arrow *arrow;

    /*!
     * Add wall to the box2d world.
     *
     * @param x The x coordiante value.
     * @param y The y coordinate value.
     * @param width The width of wall.
     * @param height The height of wall.
     */
    void addWall( const int &x, const int &y, const int &width, const int &height );

    /*!
     * Add the box2d walls.
     */
    void addBox2DWalls();

    /*!
     * Add the buttons.
     */
    void addButtons();

public:
    /*!
     * Constructor.
     */
    Table();

    /*!
     * Destructor.
     */
    ~Table();

    /*!
     * Get the x coordinate value of table.
     *
     * @return The x coordinate value.
     */
    GLfloat getX(){ return x; }

    /*!
     * Ge the bottom coordinate value of table.
     *
     * @return The bottom coordinate value.
     */
    GLfloat getBottom(){ return bottom; }

    /*!
     * Get the scale value.
     *
     * @return The scale value.
     */
    GLfloat getScale(){ return scale; }

    /*!
     * Recalculate the positions on the window.
     *
     * @param width The new width value of window.
     * @param height The new height value of window.
     */
    void resize( const float &windowWidth, const float &windowHeight );

    /*!
     * Draw the object to the window.
     */
    void draw();

    /*!
     * Step the box2d world.
     *
     * @param step The step value.
     */
    void stepBox2D( const float &step );

    /*!
     * The coordinates of the pressing button.
     *
     * @param x The x coordinate value.
     * @param y The y coordinate value.
     */
    void buttonDown( const unsigned int &x, const unsigned int &y );

    /*!
     * The coordinates of the moving button.
     *
     * @param x The x coordinate value.
     * @param y The y coordinate value.
     */
    void buttonMove( const unsigned int &x, const unsigned int &y );

    /*!
     * The button is releasing.
     */
    void buttonUp();
};

#endif // TABLE_H
