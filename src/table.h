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
class Gate;

class Table : public Rectangle
{
    // TODO
    float box2DWidth;

    // TODO
    float box2DHeight;

    // The x coordinate value.
    GLfloat x;

    // The y coordiante value.
    GLfloat y;

    // The height value.
    GLfloat height;

    // The scale value.
    float scale;

    // The map.
    Map *map;

    // Left gate.
    Gate *leftGate;

    // Right gate.
    Gate *rightGate;

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
    void addWall( const float &x, const float &y, const float &width, const float &height );

    /*!
     * Add the box2d table walls.
     */
    void addBox2DTableWalls();

    /**
     * Add the box2d gate walls.
     */
    void addBox2DGateWalls();

    /*!
     * Add the buttons.
     */
    void addButtons();

public:
    /*!
     * Constructor.
     */
    Table( const float &box2DWidth, const float &box2DHeight );

    /*!
     * Destructor.
     */
    ~Table();

    /*!
     * Get the x coordinate value of the table.
     *
     * @return The x coordinate value.
     */
    GLfloat getX(){ return x; }

    /*!
     * Get the y coordinate value of the table.
     *
     * @return The y coordinate value.
     */
    GLfloat getY(){ return y; }

    /*!
     * Get the height value of the table.
     *
     * @return The height value.
     */
    GLfloat getHeight(){ return height; }

    /*!
     * Get the scale value.
     *
     * @return The scale value.
     */
    GLfloat getScale(){ return scale; }

    /*!
     * TODO.
     *
     * @param x TODO
     * @param y TODO
     * @param width TODO.
     * @param height TODO.
     * @param scale TODO
     */
    void resize( const float &x, const float &y, const float &width, const float &height, const float &scale );

    /*!
     * Draw the object to the window.
     */
    void draw();

    /*!
     * Step the box2d world.
     *
     * @param timeStep The time step value.
     */
    void stepBox2D( const float &timeStep );

    /*!
     * The coordinates of the pressed button.
     *
     * @param x The x coordinate value.
     * @param y The y coordinate value.
     */
    void buttonPressed( const unsigned int &x, const unsigned int &y );

    /*!
     * The coordinates of the moving button.
     *
     * @param x The x coordinate value.
     * @param y The y coordinate value.
     */
    void buttonMove( const unsigned int &x, const unsigned int &y );

    /*!
     * The button is released.
     */
    void buttonReleased();
};

#endif // TABLE_H
