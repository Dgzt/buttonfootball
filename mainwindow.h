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
* The main window.
*/

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <SDL.h>
#include <GL/gl.h>

typedef struct Vertex {
    GLfloat x;
    GLfloat y;
} Vertex;

/*typedef struct Color {
    GLubyte r;
    GLubyte g;
    GLubyte b;
    GLubyte a;
} Color;*/

class string;

class MainWindow
{
    SDL_Window *mainwindow; /* Our window handle */
    SDL_GLContext maincontext; /* Our opengl context handle */

    //
    GLuint tableId;
    float tableData[];
    //

    void sdldie( const char* msg );

    void resizeWindow( int width, int height );

    void drawTable( int width, int height );

public:
    MainWindow();
    ~MainWindow();

    void mainLoop();
};

#endif // MAINWINDOW_H
