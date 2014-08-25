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
#include "table.h"
#include "mainwindow.h"

MainWindow::MainWindow()
{
    table = new Table;
}

MainWindow::~MainWindow()
{
    delete table;
}

void MainWindow::resize( const int &width, const int &height ){
    glViewport( 0, 0, width, height );

    glMatrixMode( GL_MODELVIEW );
    glLoadIdentity();

    // Clear the screen before drawing
    glClear( GL_COLOR_BUFFER_BIT );

    // Projection to the table
    glMatrixMode( GL_PROJECTION );
    glLoadIdentity();
    glOrtho( 0.0,width,0.0,height,0.0,1.0 );

    table->resize( width, height );
}

void MainWindow::draw()
{
    glClear( GL_COLOR_BUFFER_BIT );

    glEnableClientState(GL_VERTEX_ARRAY);
    //glEnableClientState(GL_COLOR_ARRAY);

    table->draw();

    //glDisableClientState(GL_COLOR_ARRAY);
    glDisableClientState(GL_VERTEX_ARRAY);
}

void MainWindow::buttonPressed( const unsigned int &x, const unsigned int &y )
{
    table->buttonPressed(x, y);
}

void MainWindow::buttonMove( const unsigned int &x, const unsigned int &y )
{
    table->buttonMove(x, y);
}

void MainWindow::buttonReleased()
{
    table->buttonReleased();
}

void MainWindow::stepBox2D( const double &timeStep )
{
    table->stepBox2D( timeStep );
}
