/*!
* @file
* @author Zsuro Tibor <zsurotibor@gmail.com>
*
* @section LICENSE
*
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
*
* @section DESCRIPTION
*
* The main file.
*/

#ifdef __EMSCRIPTEN__
#include <SDL/SDL.h>
#include <emscripten.h>
#else
#include <SDL2/SDL.h>
#endif

#include <GL/glew.h>
#include <iostream>
#include "mainwindow.h"

//
// ~~~ The constants. ~~~
//

// The name of the application
const char APPLICATION_NAME[] = "Button Football";

// The width and height of the main window
const int WINDOW_WIDTH = 800;
const int WINDOW_HEIGHT = 600;

// The default frame per sec
const float DEFAULT_FPS = 40;

//
// ~~~ The function declarations. ~~~
//

void initSdl();
void sdldie( const char* );
void mainLoop();

//
// ~~~ The variable declarations. ~~~
//

#ifdef __EMSCRIPTEN__
SDL_Surface *screen;
#else
SDL_Window *mainwindow; /* Our window handle */
SDL_GLContext maincontext; /* Our opengl context handle */
#endif

MainWindow *mainWindow;

int width = WINDOW_WIDTH;
int height = WINDOW_HEIGHT;

//
// ~~~ Functions. ~~~
//

/*!
 * The main function.
 */
int main()
{
    initSdl();

    mainWindow = new MainWindow;

    mainWindow->resize( width, height );
    mainWindow->draw();

    // Swap our back buffer to the front
#ifdef __EMSCRIPTEN__
    SDL_GL_SwapBuffers();
#else
    SDL_GL_SwapWindow(mainwindow);
#endif

#ifdef __EMSCRIPTEN__
    emscripten_set_main_loop( mainLoop, DEFAULT_FPS, 0 );
#else
    bool done = false;

    while( !done ){
        mainLoop();
    }
#endif

    return 0;
}

/*!
 * Initialize the SDL.
 */
void initSdl()
{
    // Initialize SDL's Video subsystem
    if( SDL_Init(SDL_INIT_VIDEO) < 0 ){
        sdldie("Unable to initialize SDL");
    }

    // Turn on double buffering with a 24bit Z buffer.
    // You may need to change this to 16 or 32 for your system
    SDL_GL_SetAttribute(SDL_GL_DOUBLEBUFFER, 1);
    SDL_GL_SetAttribute(SDL_GL_DEPTH_SIZE, 24);

#ifdef __EMSCRIPTEN__
    screen = SDL_SetVideoMode( WINDOW_WIDTH, WINDOW_HEIGHT, 0, SDL_OPENGL );
#else
    // Create our window
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
#endif

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    glewInit();

    // This makes our buffer swap syncronized with the monitor's vertical refresh
    SDL_GL_SetSwapInterval(1);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    glClearColor( 0, 0, 0, 0 );

#ifndef __EMSCRIPTEN__
    glEnable( GL_TEXTURE_2D ); // Need this to display a texture XXX unnecessary in OpenGL ES 2.0/WebGL
#endif
}

/*!
 * Show error message and quit.
 *
 * @msg The message.
 */
void sdldie( const char* msg )
{
    std::cout << msg << ": " << SDL_GetError() << std::endl;
    SDL_Quit();
    exit(1);
}

/*!
 * Correctly quit the program.
 */
void quit()
{
    delete mainWindow;

    // Delete our opengl context, destroy our window, and shutdown SDL
#ifdef __EMSCRIPTEN__
    SDL_FreeSurface( screen );
#else
    SDL_GL_DeleteContext( maincontext );
    SDL_DestroyWindow( mainwindow );
#endif
    SDL_Quit();

    exit(0);
}

/*!
 * The main loop.
 */
void mainLoop()
{
    SDL_Event event;
    Uint32 start = SDL_GetTicks();

    while( SDL_PollEvent( &event ) ){
        if (event.type == SDL_QUIT || event.type == SDL_WINDOWEVENT_CLOSE) {
            quit();
        }

        if( event.type == SDL_WINDOWEVENT && event.window.event == SDL_WINDOWEVENT_RESIZED ){
            width = event.window.data1;
            height = event.window.data2;

            mainWindow->resize( width, height );
        }

        if( event.type == SDL_MOUSEBUTTONDOWN && event.button.button == SDL_BUTTON_LEFT){
            mainWindow->buttonPressed( event.button.x, height - event.button.y );
        }

        if( event.type == SDL_MOUSEMOTION && event.button.button == SDL_BUTTON_LEFT ){
            mainWindow->buttonMove( event.button.x, height - event.button.y );
        }

        if( event.type == SDL_MOUSEBUTTONUP && event.button.button == SDL_BUTTON_LEFT ){
            mainWindow->buttonReleased();
        }

    }

    mainWindow->draw();

#ifdef __EMSCRIPTEN__
    SDL_GL_SwapBuffers();
#else
    SDL_GL_SwapWindow(mainwindow);
#endif

    mainWindow->stepBox2D( 1.0/DEFAULT_FPS );

#ifndef __EMSCRIPTEN__
    if( 1000.0/DEFAULT_FPS > SDL_GetTicks() - start ){
        SDL_Delay( 1000.0/DEFAULT_FPS - ( SDL_GetTicks() - start ) );
    }
#endif
}
