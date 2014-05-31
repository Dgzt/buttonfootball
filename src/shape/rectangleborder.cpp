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

#include "rectangleborder.h"

const int VERTICES_NUM = 4;

RectangleBorder::RectangleBorder( const Color &color ) :
    AbstractShape( GL_LINE_LOOP, VERTICES_NUM, color )
{}

void RectangleBorder::resize(const float &x, const float &y, const float &width, const float &height)
{
    Vertex vertices[4];

    vertices[0].x = x;          vertices[0].y = y;
    vertices[1].x = x + width;  vertices[1].y = y;
    vertices[2].x = x + width;  vertices[2].y = y - height;
    vertices[3].x = x;          vertices[3].y = y - height;

    AbstractShape::resize( vertices );
}
