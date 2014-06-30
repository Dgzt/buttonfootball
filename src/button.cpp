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

#include <Box2D/Box2D.h>
#include <stdlib.h>
#include <iostream>
#include "table.h"
#include "color.h"
#include "button.h"

// The radius of the button.
const float BUTTON_RADIUS = 2.5f;

// The linear damping.
const float LINEAR_DAMPING = 3.0f;

Button::Button( Table *parent, b2World *box2DWorld, Color color, const float box2DX, const float box2DY ) :
    Circle( GL_POLYGON, color ),
    parent( parent ),
    box2DX(box2DX),
    box2DY(box2DY)
{
    b2BodyDef bodydef;
    bodydef.position.Set(box2DX,box2DY);

    bodydef.type=b2_dynamicBody;

    body = box2DWorld->CreateBody(&bodydef);

    b2CircleShape shape;
    shape.m_radius = BUTTON_RADIUS;

    b2FixtureDef fixturedef;
    fixturedef.shape=&shape;
    fixturedef.density=1.0;
    fixturedef.friction = 10;
    fixturedef.restitution=0.4;
    body->CreateFixture(&fixturedef);

    // Set the damping.
    body->SetLinearDamping( 3.0f );
}

void Button::resize()
{
    Circle::resize( getX(), getY(), BUTTON_RADIUS * parent->getScale() );
}

void Button::draw()
{
    if( box2DX != body->GetPosition().x || box2DY != body->GetPosition().y ){
        box2DX = body->GetPosition().x;
        box2DY = body->GetPosition().y;

        resize();
    }

    Circle::draw();
}

int Button::getX()
{
    return parent->getX() + ( box2DX * parent->getScale() );
}

int Button::getY()
{
    return parent->getBottom() + ( box2DY * parent->getScale() );
}

bool Button::contains( const unsigned int &mouseX, const unsigned int &mouseY)
{
    return abs( getX() - mouseX ) < BUTTON_RADIUS * parent->getScale() &&
           abs( getY() - mouseY ) < BUTTON_RADIUS * parent->getScale();
}

void Button::move( b2Vec2 vector )
{
    body->SetLinearVelocity( vector );
}
