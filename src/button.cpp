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

#include "button.h"

// The radius of the button.
const float RADIUS = 2.5f;

Button::Button( Table *parent,
                b2World *box2DWorld,
                const Color &color,
                const float &box2DX,
                const float &box2DY ) :
    AbstractButton( parent,
                    box2DWorld,
                    color,
                    box2DX,
                    box2DY,
                    RADIUS )
{}

float Button::getRadius()
{
    return RADIUS;
}
