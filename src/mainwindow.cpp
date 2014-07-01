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

#include <GL/glew.h>
#include <iostream>
#include "table.h"
#include "mainwindow.h"

// The name of the application
const char APPLICATION_NAME[] = "Button Football";

// The width and height of the main window
const int WINDOW_WIDTH = 800;
const int WINDOW_HEIGHT = 600;

// The default frame per sec
const float DEFAULT_FPS = 40;

MainWindow::MainWindow() :
    width( WINDOW_WIDTH ),
    height( WINDOW_HEIGHT )
{
    // Initialize SDL's Video subsystem
    if( SDL_Init(SDL_INIT_VIDEO) < 0 ){
        sdldie("Unable to initialize SDL");
    }

    // Turn on double buffering with a 24bit Z buffer.
    // You may need to change this to 16 or 32 for your system
    SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1);
    SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE, 24);

    // Create our window centered at 512x512 resolution
    mainwindow = SDL_CreateWindow( APPLICATION_NAME,
                                   SDL_WINDOWPOS_CENTERED,
                                   SDL_WINDOWPOS_CENTERED,
                                   WINDOW_WIDTH,
                                   WINDOW_HEIGHT,
                                   SDL_WINDOW_OPENGL | SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
    if (!mainwindow){
        sdldie("Unable to create window");
    }

    // Create our opengl context and attach it to our window
    maincontext = SDL_GL_CreateContext(mainwindow);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    glewInit();

    // This makes our buffer swap syncronized with the monitor's vertical refresh
    SDL_GL_SetSwapInterval(1);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    glClearColor( 0, 0, 0, 0 );

#ifndef __EMSCRIPTEN__
    glEnable( GL_TEXTURE_2D ); // Need this to display a texture XXX unnecessary in OpenGL ES 2.0/WebGL
#endif

    //
    table = new Table;
    //

    resizeWindow(WINDOW_WIDTH, WINDOW_HEIGHT);

    drawTable();

    // Swap our back buffer to the front
    SDL_GL_SwapWindow(mainwindow);
}

MainWindow::~MainWindow()
{
    delete table;

    // Delete our opengl context, destroy our window, and shutdown SDL
    SDL_GL_DeleteContext(maincontext);
    SDL_DestroyWindow(mainwindow);
    SDL_Quit();
}

void MainWindow::sdldie( const char* msg ){
    std::cout << msg << ": " << SDL_GetError() << std::endl;
    SDL_Quit();
    exit(1);
}

void MainWindow::resizeWindow( int width, int height ){
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

void MainWindow::drawTable()
{
    glClear( GL_COLOR_BUFFER_BIT );

    glEnableClientState(GL_VERTEX_ARRAY);
    //glEnableClientState(GL_COLOR_ARRAY);

    table->draw();

    //glDisableClientState(GL_COLOR_ARRAY);
    glDisableClientState(GL_VERTEX_ARRAY);
}

void MainWindow::mainLoop()
{
    SDL_Event event;
    bool done = false;
    Uint32 start;

    while( !done ){
        start = SDL_GetTicks();

        while( SDL_PollEvent( &event ) ){
            if (event.type == SDL_QUIT || event.type == SDL_WINDOWEVENT_CLOSE) {
                done = true;
            }

            if( event.type == SDL_WINDOWEVENT && event.window.event == SDL_WINDOWEVENT_RESIZED ){
                width = event.window.data1;
                height = event.window.data2;

                resizeWindow(width,height);
            }

            if( event.type == SDL_MOUSEBUTTONDOWN && event.button.button == SDL_BUTTON_LEFT){
                std::cout << "Button down: " << event.button.x << " x " << event.button.y << std::endl;
                table->buttonDown( event.button.x, height - event.button.y );
            }

            if( event.type == SDL_MOUSEMOTION && event.button.button == SDL_BUTTON_LEFT ){
                //table->buttonMove( event.button.x, event.button.y );
                table->buttonMove( event.button.x, height - event.button.y );
            }

            if( event.type == SDL_MOUSEBUTTONUP && event.button.button == SDL_BUTTON_LEFT ){
                std::cout << "Button up" << std::endl;
                table->buttonUp();
            }
        }

        drawTable();
        SDL_GL_SwapWindow(mainwindow);

        table->stepBox2D( 1.0/DEFAULT_FPS );

        if( 1000.0/DEFAULT_FPS > SDL_GetTicks() - start ){
            SDL_Delay( 1000.0/DEFAULT_FPS - ( SDL_GetTicks() - start ) );
        }


    }
}
