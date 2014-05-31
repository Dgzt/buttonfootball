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

#include "line.h"

/**
 * The amount of vertices.
 */
const unsigned int VERTICES_NUM = 2;

Line::Line( const Color &color ) :
    AbstractShape( GL_LINES, VERTICES_NUM, color )
{}

void Line::resize(const float &x1, const float &y1, const float &x2, const float &y2)
{
    Vertex vertices[2] = { x1, y1, x2, y2 };

    AbstractShape::resize( vertices );
}
