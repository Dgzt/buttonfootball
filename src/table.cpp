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
#include "color.h"
#include "map.h"
#include "button.h"
#include "table.h"

// The width of the table.
const float TABLE_WIDTH = 184.0;

// The height of the table.
const float TABLE_HEIGHT = 120.0;

// Color of the table.
const Color GREY_COLOR = { 0.5, 0.5, 0.5 };

// Color of the player button.
const Color RED_COLOR = { 1.0f, 0.0f, 0.0f };

// The size of walls.
const int WALL_SIZE = 10;

Table::Table() :
    Rectangle( GL_QUADS, GREY_COLOR )
{
    map = new Map;

    world = new b2World(b2Vec2( 0.0, 0.0 ));
    addBox2DWalls();

    // Add buttons
    addButtons();
}

Table::~Table()
{
    delete map;

    delete world;
}

void Table::addWall(int x, int y, int width, int height)
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

void Table::addBox2DWalls()
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

void Table::addButtons()
{
    // Add player buttons

    float buttonDistanceX = TABLE_WIDTH / 2 /4;
    float buttonDistanceY = TABLE_HEIGHT / 5;

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
    Button *forwardButton1 = new Button( this, world, RED_COLOR, 3*buttonDistanceX, TABLE_HEIGHT / 2 - buttonDistanceY );
    playerButtons.push_back( forwardButton1 );

    Button *forwardButton2 = new Button( this, world, RED_COLOR, 3*buttonDistanceX, TABLE_HEIGHT / 2 + buttonDistanceY );
    playerButtons.push_back( forwardButton2 );
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
    y = windowHeight - (windowHeight-tableHeight)/2;
    scale = tableWidth / TABLE_WIDTH;

    Rectangle::resize( x, y, tableWidth, tableHeight );

    map->resize( x, y, tableWidth, tableHeight, scale );

    //
    for( int i = 0; i < playerButtons.size(); ++i ){
        playerButtons[i]->resize();
    }
}

void Table::draw()
{
    Rectangle::draw();

    map->draw();

    for( int i = 0; i < playerButtons.size(); ++i ){
        playerButtons[i]->draw();
    }
}

void Table::stepBox2D( float step )
{
    world->Step( step, 8, 3 );
}
