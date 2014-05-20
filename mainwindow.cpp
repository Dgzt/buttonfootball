#include <SDL.h>
#include <GLES2/gl2.h>
#include <iostream>
#include "mainwindow.h"

// The name of the application
const char APPLICATION_NAME[] = "Button Football";

// The width and height of the main window
const int WINDOW_WIDTH = 800;
const int WINDOW_HEIGHT = 600;

MainWindow::MainWindow()
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
    mainwindow = SDL_CreateWindow( APPLICATION_NAME, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, WINDOW_WIDTH, WINDOW_HEIGHT, SDL_WINDOW_OPENGL | SDL_WINDOW_SHOWN);
    if (!mainwindow){
        sdldie("Unable to create window");
    }

    // Create our opengl context and attach it to our window
    maincontext = SDL_GL_CreateContext(mainwindow);

    // This makes our buffer swap syncronized with the monitor's vertical refresh
    SDL_GL_SetSwapInterval(1);

    // Clear our buffer with a black background
    glClearColor ( 0.0, 0.0, 0.0, 1.0 );
    glClear ( GL_COLOR_BUFFER_BIT );
    // Swap our back buffer to the front
    SDL_GL_SwapWindow(mainwindow);
}

MainWindow::~MainWindow()
{
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

void MainWindow::mainLoop()
{
    // Show window while not click to the close button
    SDL_Event event;
    bool done = false;

    while( !done ){
        while( SDL_PollEvent( &event ) ){
            if (event.type == SDL_QUIT || event.type == SDL_WINDOWEVENT_CLOSE) {
                done = true;
            }
        }
    }
}