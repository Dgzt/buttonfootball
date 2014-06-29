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
#include <Box2D/Box2D.h>
#include "button.h"
#include "arrow.h"

const Color RED_COLOR = { 1.0f, 0.0f, 0.0f };

Arrow::Arrow() :
    Line( RED_COLOR )
{}

void Arrow::setButton(Button *button)
{
    this->button = button;

    setEnd( button->getX(), button->getY() );
}

void Arrow::setEnd( const float &x2, const float &y2)
{
    this->x2 = x2;
    this->y2 = y2;

    Line::resize( button->getX(), button->getY(), x2, y2 );
}

void Arrow::draw()
{
    if( button ){
        Line::draw();
    }
}

void Arrow::moveButton()
{
    b2Vec2 vector;
    vector.x = button->getX() - x2;
    vector.y = button->getY() - y2;

    button->move( vector );

    button = NULL;
}
