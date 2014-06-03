#include <Box2D/Box2D.h>
#include <iostream>
#include "table.h"
#include "color.h"
#include "button.h"

const float BUTTON_RADIUS = 2.5f;

const Color RED_COLOR = { 1.0f, 0.0f, 0.0f };

Button::Button( Table *parent, b2World *box2DWorld, const float x, const float y ) :
    Circle( GL_POLYGON, RED_COLOR ),
    parent( parent ),
    x(x),
    y(y)
{
    b2BodyDef bodydef;
    bodydef.position.Set(x,y);

    bodydef.type=b2_dynamicBody;

    body = box2DWorld->CreateBody(&bodydef);

    b2PolygonShape shape;
    shape.SetAsBox(BUTTON_RADIUS,BUTTON_RADIUS);

    b2FixtureDef fixturedef;
    fixturedef.shape=&shape;
    fixturedef.density=1.0;
    fixturedef.friction = 10;
    fixturedef.restitution=0.4;
    body->CreateFixture(&fixturedef);
}

void Button::resize()
{
    Circle::resize( parent->getX() + ( x * parent->getScale() ),
                    parent->getY() - ( y * parent->getScale() ),
                    BUTTON_RADIUS * parent->getScale() );
}

void Button::draw()
{
    if( x != body->GetPosition().x || y != body->GetPosition().y ){
        x = body->GetPosition().x;
        y = body->GetPosition().y;
    }

    resize();
    Circle::draw();
}
