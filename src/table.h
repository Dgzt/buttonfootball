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

class Table : public Rectangle
{
    GLfloat x;

    GLfloat y;

    float scale;

    /*!
     * The map.
     */
    Map *map;

    b2World* world;

    //Button *tmpButton;
    std::vector<Button*> playerButtons;

    void addWall( int x, int y, int width, int height );

    void addBox2DWalls();

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

    GLfloat getX(){ return x; }
    GLfloat getY(){ return y; }
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

    void stepBox2D( float step );
};

#endif // TABLE_H
