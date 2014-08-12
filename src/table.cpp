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

// The width of the table.
const float TABLE_WIDTH = 184.0;

// The height of the table.
const float TABLE_HEIGHT = 120.0;

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

Table::Table() :
    Rectangle( GL_QUADS, GREY_COLOR )
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
    ball = new Ball( this, world, TABLE_WIDTH/2, TABLE_HEIGHT/2 );

    arrow = new Arrow;
}

Table::~Table()
{
    delete ball;

    delete arrow;

    delete world;

    delete map;

    delete leftGate;
    delete rightGate;
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
    addWall( TABLE_WIDTH / 2, 0 - WALL_SIZE / 2, TABLE_WIDTH, WALL_SIZE );

    // Add right wall
    addWall( TABLE_WIDTH + WALL_SIZE / 2, TABLE_HEIGHT / 2, WALL_SIZE, TABLE_HEIGHT );

    // Add bottom wall
    addWall( TABLE_WIDTH / 2, TABLE_HEIGHT + WALL_SIZE / 2, TABLE_WIDTH, WALL_SIZE );

    // Add left wall
    addWall( 0 - WALL_SIZE / 2, TABLE_HEIGHT / 2, WALL_SIZE, TABLE_HEIGHT );
}

void Table::addBox2DGateWalls()
{
    const float leftGateWallX = GATE_WIDTH / 2;
    const float rightGateWallX = TABLE_WIDTH - GATE_WIDTH / 2;
    const float gateTopWallY = ( TABLE_HEIGHT + GATE_HEIGHT ) / 2;
    const float gateBottomWallY = ( TABLE_HEIGHT - GATE_HEIGHT ) / 2;

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
    const float buttonDistanceX = TABLE_WIDTH / 2 /4;
    const float buttonDistanceY = TABLE_HEIGHT / 5;

    // Add goalkeeper
    const float playerGoalkeeperX = ( TABLE_WIDTH - MAP_WIDTH ) / 2 + Button::getRadius();
    const float goalkeeperY = TABLE_HEIGHT / 2;
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
    playerButtons.push_back( new Button( this, world, RED_COLOR, 3*buttonDistanceX, TABLE_HEIGHT / 2 - buttonDistanceY ) );
    playerButtons.push_back( new Button( this, world, RED_COLOR, 3*buttonDistanceX, TABLE_HEIGHT / 2 + buttonDistanceY ) );

    // Add opponent buttons

    // Add goalkeeper
    const float opponentGoalkeeperX = TABLE_WIDTH - ( TABLE_WIDTH - MAP_WIDTH ) / 2 - Button::getRadius();
    opponentButtons.push_back( new Button( this, world, BLUE_COLOR, opponentGoalkeeperX, goalkeeperY ) );

    // Add defends
    for( int i = 0; i < 4; ++i ){
        Button *button = new Button( this, world, BLUE_COLOR, TABLE_WIDTH - buttonDistanceX, (i+1) * buttonDistanceY );
        opponentButtons.push_back( button );
    }

    // Add midfielders
    for( int i = 0; i < 4; ++i ){
        Button *button = new Button( this, world, BLUE_COLOR, TABLE_WIDTH - 2*buttonDistanceX, (i+1) * buttonDistanceY );
        opponentButtons.push_back( button );
    }

    // Add forwards
    opponentButtons.push_back( new Button( this, world, BLUE_COLOR, TABLE_WIDTH - 3*buttonDistanceX, TABLE_HEIGHT / 2 - buttonDistanceY ) );
    opponentButtons.push_back( new Button( this, world, BLUE_COLOR, TABLE_WIDTH - 3*buttonDistanceX, TABLE_HEIGHT / 2 + buttonDistanceY ) );
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

    x = (windowWidth-tableWidth)/2;
    GLfloat y = windowHeight - (windowHeight-tableHeight)/2;
    bottom = (windowHeight-tableHeight)/2;
    scale = tableWidth / TABLE_WIDTH;

    Rectangle::resize( x, y, tableWidth, tableHeight );

    // Map
    float mapWidth = MAP_WIDTH * scale;
    float mapHeight = MAP_HEIGHT * scale;

    float mapX = x + (tableWidth - mapWidth)/2;
    float mapY = y - (tableHeight - mapHeight)/2;

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

void Table::stepBox2D( const float &step )
{
    world->Step( step, 8, 3 );
}

void Table::buttonDown( const unsigned int &x, const unsigned int &y)
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

void Table::buttonUp()
{
    if( arrow->isButtonSelected() ){
        arrow->moveButton();

    }
}
