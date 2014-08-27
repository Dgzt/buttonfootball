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
#include <iostream>
#include "color.h"
#include "map.h"
#include "button.h"
#include "ball.h"
#include "arrow.h"
#include "gate.h"
#include "table.h"

// The width of the map.
const float MAP_WIDTH = 167.0;

// The height of the map.
const float MAP_HEIGHT = 104.0;

// The width of the gate.
const float GATE_WIDTH = 8.0f;

// The height of the gate.
const float GATE_HEIGHT = 13.0f;

// The size of gate wall.
const float GATE_WALL_SIZE = 0.5f;

// Color of the table.
const Color GREY_COLOR = { 0.5, 0.5, 0.5 };

// Color of the player button.
const Color RED_COLOR = { 1.0f, 0.0f, 0.0f };

// Color of the player button.
const Color BLUE_COLOR = { 0.0f, 0.0f, 1.0f };

// Color of the gates.
const Color WHITE_COLOR = { 1.0f, 1.0f, 1.0f };

// The size of walls.
const int WALL_SIZE = 10;

Table::Table(const float &box2DWidth, const float &box2DHeight) :
    Rectangle( GL_QUADS, GREY_COLOR ),
    box2DWidth( box2DWidth ),
    box2DHeight( box2DHeight )
{
    map = new Map;

    leftGate = new Gate;
    rightGate = new Gate;

    world = new b2World(b2Vec2( 0.0f, 0.0f ));
    addBox2DTableWalls();
    addBox2DGateWalls();

    // Add buttons
    addButtons();

    //Add ball
    ball = new Ball( this, world, box2DWidth/2, box2DHeight/2 );

    arrow = new Arrow;
}

Table::~Table()
{
    delete ball;

    delete arrow;

    delete map;

    delete leftGate;
    delete rightGate;

    // Delete player buttons.
    while( playerButtons.size() != 0 ){
        Button *button = playerButtons.at( playerButtons.size()-1 );
        playerButtons.pop_back();
        delete button;
    }

    // Delete opponent buttons.
    while( opponentButtons.size() != 0 ){
        Button *button = opponentButtons.at( opponentButtons.size()-1 );
        opponentButtons.pop_back();
        delete button;
    }

    delete world;
}

void Table::addWall( const float &x, const float &y, const float &width, const float &height)
{
    b2BodyDef bodydef;
    bodydef.position.Set(x,y);

    b2Body* body=world->CreateBody(&bodydef);

    b2PolygonShape shape;
    shape.SetAsBox(width/2,height/2);

    b2FixtureDef fixturedef;
    fixturedef.shape=&shape;
    fixturedef.density=1.0;
    body->CreateFixture(&fixturedef);
}

void Table::addBox2DTableWalls()
{
    // Add top wall
    addWall( box2DWidth / 2, 0 - WALL_SIZE / 2, box2DWidth, WALL_SIZE );

    // Add right wall
    addWall( box2DWidth + WALL_SIZE / 2, box2DHeight / 2, WALL_SIZE, box2DHeight );

    // Add bottom wall
    addWall( box2DWidth / 2, box2DHeight + WALL_SIZE / 2, box2DWidth, WALL_SIZE );

    // Add left wall
    addWall( 0 - WALL_SIZE / 2, box2DHeight / 2, WALL_SIZE, box2DHeight );
}

void Table::addBox2DGateWalls()
{
    const float leftGateWallX = GATE_WIDTH / 2;
    const float rightGateWallX = box2DWidth - GATE_WIDTH / 2;
    const float gateTopWallY = ( box2DHeight + GATE_HEIGHT ) / 2;
    const float gateBottomWallY = ( box2DHeight - GATE_HEIGHT ) / 2;

    // Top left gate wall
    addWall( leftGateWallX, gateTopWallY, GATE_WIDTH, GATE_WALL_SIZE );

    // Bottom left gate wall
    addWall( leftGateWallX, gateBottomWallY, GATE_WIDTH, GATE_WALL_SIZE );

    // Top right gate wall
    addWall( rightGateWallX, gateTopWallY, GATE_WIDTH, GATE_WALL_SIZE );

    // Bottom right gate wall
    addWall( rightGateWallX, gateBottomWallY, GATE_WIDTH, GATE_WALL_SIZE );
}

void Table::addButtons()
{
    // Add player buttons
    const float buttonDistanceX = box2DWidth / 2 /4;
    const float buttonDistanceY = box2DHeight / 5;

    // Add goalkeeper
    const float playerGoalkeeperX = ( box2DWidth - MAP_WIDTH ) / 2 + Button::getRadius();
    const float goalkeeperY = box2DHeight / 2;
    playerButtons.push_back( new Button( this, world, RED_COLOR, playerGoalkeeperX, goalkeeperY ) );

    // Add defends
    for( int i = 0; i < 4; ++i ){
        Button *button = new Button( this, world, RED_COLOR, buttonDistanceX, (i+1) * buttonDistanceY );
        playerButtons.push_back( button );
    }

    // Add midfielders
    for( int i = 0; i < 4; ++i ){
        Button *button = new Button( this, world, RED_COLOR, 2*buttonDistanceX, (i+1) * buttonDistanceY );
        playerButtons.push_back( button );
    }

    // Add forwards
    playerButtons.push_back( new Button( this, world, RED_COLOR, 3*buttonDistanceX, box2DHeight / 2 - buttonDistanceY ) );
    playerButtons.push_back( new Button( this, world, RED_COLOR, 3*buttonDistanceX, box2DHeight / 2 + buttonDistanceY ) );

    // Add opponent buttons

    // Add goalkeeper
    const float opponentGoalkeeperX = box2DWidth - ( box2DWidth - MAP_WIDTH ) / 2 - Button::getRadius();
    opponentButtons.push_back( new Button( this, world, BLUE_COLOR, opponentGoalkeeperX, goalkeeperY ) );

    // Add defends
    for( int i = 0; i < 4; ++i ){
        Button *button = new Button( this, world, BLUE_COLOR, box2DWidth - buttonDistanceX, (i+1) * buttonDistanceY );
        opponentButtons.push_back( button );
    }

    // Add midfielders
    for( int i = 0; i < 4; ++i ){
        Button *button = new Button( this, world, BLUE_COLOR, box2DWidth - 2*buttonDistanceX, (i+1) * buttonDistanceY );
        opponentButtons.push_back( button );
    }

    // Add forwards
    opponentButtons.push_back( new Button( this, world, BLUE_COLOR, box2DWidth - 3*buttonDistanceX, box2DHeight / 2 - buttonDistanceY ) );
    opponentButtons.push_back( new Button( this, world, BLUE_COLOR, box2DWidth - 3*buttonDistanceX, box2DHeight / 2 + buttonDistanceY ) );
}

void Table::resize(const float &x, const float &y, const float &width, const float &height, const float &scale)
{
    this->x = x;
    this->y = y;
    this->height = height;
    this->scale = scale;

    Rectangle::resize( x, y, width, height );

    // Map
    float mapWidth = MAP_WIDTH * scale;
    float mapHeight = MAP_HEIGHT * scale;

    float mapX = x + (width - mapWidth)/2;
    float mapY = y - (height - mapHeight)/2;

    map->resize( mapX, mapY, mapWidth, mapHeight, scale );

    // Buttons
    for( int i = 0; i < playerButtons.size(); ++i ){
        playerButtons[i]->resize();
    }

    for( int i = 0; i < opponentButtons.size(); ++i ){
        opponentButtons[i]->resize();
    }

    // Ball
    ball->resize();

    // Left gate
    const float gateWidth = GATE_WIDTH * scale;
    const float gateHeight = GATE_HEIGHT * scale;

    const float leftGateX = mapX - gateWidth;
    const float gateY = mapY - ( mapHeight - gateHeight ) / 2;

    leftGate->resize(leftGateX, gateY, gateWidth, gateHeight);

    // Right gate
    const float rightGateX = mapX + mapWidth;

    rightGate->resize( rightGateX, gateY, gateWidth, gateHeight );
}

void Table::draw()
{
    Rectangle::draw();

    map->draw();

    for( int i = 0; i < playerButtons.size(); ++i ){
        playerButtons[i]->draw();
    }

    for( int i = 0; i < opponentButtons.size(); ++i ){
        opponentButtons[i]->draw();
    }

    ball->draw();

    leftGate->draw();

    rightGate->draw();

    arrow->draw();
}

void Table::stepBox2D( const float &timeStep )
{
    world->Step( timeStep, 8, 3 );
}

void Table::buttonPressed( const unsigned int &x, const unsigned int &y)
{
    for( int i = 0; i < playerButtons.size(); ++i ){
        if( playerButtons[i]->contains( x,y ) ){
            arrow->setButton( playerButtons[i] );
        }
    }
}

void Table::buttonMove( const unsigned int &x, const unsigned int &y)
{
    if( arrow->isButtonSelected() ){
        arrow->setEnd( x, y );
    }
}

void Table::buttonReleased()
{
    if( arrow->isButtonSelected() ){
        arrow->moveButton();

    }
}
