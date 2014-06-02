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

class Map;

class Table : public Rectangle
{
    /*!
     * The map.
     */
    Map *map;

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
     * Recalculate the positions on the window.
     *
     * @param width The new width value of window.
     * @param height The new height value of window.
     */
    void resize( const int &windowWidth, const int &windowHeight );

    /*!
     * Draw the object to the window.
     */
    void draw();
};

#endif // TABLE_H
