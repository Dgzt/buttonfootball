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

#include <vector>
#include <math.h>
#include "circle.h"

/*!
 * Change the degrees to radian.
 */
#define degreesToRadians(angleDegrees) (angleDegrees * M_PI / 180.0)

/**
 * The default starting degrees.
 */
const float DEFAULT_DEGREES = 90;

Circle::Circle( GLenum type, const Color &color ) :
    AbstractShape( type, color ),
    startDegrees( 0 ),
    endDegrees( 360 )
{}

Circle::Circle( float startDegrees, float endDegrees, const Color &color ) :
    AbstractShape( GL_LINE_STRIP, color ),
    startDegrees( startDegrees ),
    endDegrees( endDegrees +5 )
{}

void Circle::resize(const float &x, const float &y, const float &r)
{
    std::vector<Vertex> vertices;

    for( int i = DEFAULT_DEGREES + startDegrees; i < DEFAULT_DEGREES + endDegrees; i+=5 ){

        float angle = degreesToRadians(i);

        Vertex v;
        v.x = x + sin( angle ) * r;
        v.y = y + cos( angle ) * r;

       vertices.push_back( v );
    }

    AbstractShape::resize( &vertices[0], (int)vertices.size() );
}
