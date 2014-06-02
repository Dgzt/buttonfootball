/*!
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
*/

#include "color.h"
#include "map.h"
#include "table.h"

/*!
 * The width of table.
 */
const float TABLE_WIDTH = 184.0;

/*!
 * The height of table.
 */
const float TABLE_HEIGHT = 120.0;

/*!
 * The color of table.
 */
const Color GREY_COLOR = { 0.5, 0.5, 0.5 };

Table::Table() :
    Rectangle( GL_QUADS, GREY_COLOR )
{
    map = new Map;
}

Table::~Table()
{
    delete map;
}

void Table::resize(const float &windowWidth, const float &windowHeight)
{
    float tableWidth;
    float tableHeight;

    float rate = (double)windowWidth/windowHeight;

    if( TABLE_WIDTH/TABLE_HEIGHT > rate ){
        tableWidth = windowWidth;
        tableHeight = TABLE_HEIGHT*(windowWidth/TABLE_WIDTH);
    }else{
        tableWidth = TABLE_WIDTH*(windowHeight/TABLE_HEIGHT);
        tableHeight = windowHeight;
    }

    GLfloat x = (windowWidth-tableWidth)/2;
    GLfloat y = windowHeight - (windowHeight-tableHeight)/2;

    Rectangle::resize( x, y, tableWidth, tableHeight );

    map->resize( x, y, tableWidth, tableHeight, tableWidth / TABLE_WIDTH );
}

void Table::draw()
{
    Rectangle::draw();

    map->draw();
}
