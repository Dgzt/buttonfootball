#include <SDL.h>
#include <GL/gl.h>
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
    mainwindow = SDL_CreateWindow( APPLICATION_NAME, SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, WINDOW_WIDTH, WINDOW_HEIGHT, SDL_WINDOW_OPENGL | SDL_WINDOW_SHOWN | SDL_WINDOW_RESIZABLE);
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

    resizeWindow( WINDOW_WIDTH, WINDOW_HEIGHT );
    drawTable(WINDOW_WIDTH, WINDOW_HEIGHT);

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

void MainWindow::resizeWindow( int width, int height ){
    glViewport( 0, 0, width, height );

    glMatrixMode( GL_PROJECTION );
    glLoadIdentity();
    glOrtho( 0.0,width,0.0,height,0.0,1.0 );

}

void MainWindow::drawTable( int width, int height ){
    glMatrixMode( GL_MODELVIEW );
    glLoadIdentity();

    glShadeModel(GL_SMOOTH);

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // Grey
    glColor3f(0.5f, 0.5f, 0.5f);

    int tableWidth = 184;
    int tableHeight = 120;

    int newTableWidth;
    int newTableHeight;

    double rate = (double)width/rate;
    if( (double)tableWidth/tableHeight > rate ){
        newTableWidth = width;
        newTableHeight = tableHeight*((double)width/tableWidth);
    }else{
        newTableWidth = tableWidth*((double)height/tableHeight);
        newTableHeight = height;
    }

    int leftX = (width-newTableWidth)/2;
    int rightX = width-(width-newTableWidth)/2;
    int topY = (height-newTableHeight)/2;
    int bottomY = height - (height-newTableHeight)/2;

    glBegin(GL_QUADS );
        glVertex2f( leftX, topY );
        glVertex2f( rightX, topY );
        glVertex2f( rightX, bottomY );
        glVertex2f( leftX, bottomY );
    glEnd();
}

void MainWindow::mainLoop()
{
    SDL_Event event;
    bool done = false;

    while( !done ){
        SDL_WaitEvent(&event);
        do{
            if (event.type == SDL_QUIT || event.type == SDL_WINDOWEVENT_CLOSE) {
                done = true;
            }

            if( event.type == SDL_WINDOWEVENT && event.window.event == SDL_WINDOWEVENT_RESIZED ){
                resizeWindow(event.window.data1,event.window.data2);
                drawTable( event.window.data1, event.window.data2 );
                SDL_GL_SwapWindow(mainwindow);
            }


        }while(SDL_PollEvent(&event));

    }
}
