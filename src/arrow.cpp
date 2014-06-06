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

#include <iostream>
#include "arrow.h"

const Color RED_COLOR = { 1.0f, 0.0f, 0.0f };

Arrow::Arrow() :
    Line( RED_COLOR ),
    visible( false )
{}

void Arrow::setEnd( const float &x2, const float &y2)
{
    Line::resize( x1, y1, x2, y2 );
}

void Arrow::draw()
{
    if( visible ){
        Line::draw();
    }
}
