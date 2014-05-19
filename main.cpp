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
#include <iostream>
#include <SDL.h>
#include <SDL_opengl.h>

/*!
 * The main function.
 */
int 
main(){
  
  //Initialize the SDL library.
  SDL_Init(SDL_INIT_VIDEO);
  
  //Setup the video mode.
  SDL_Surface *screen = SDL_SetVideoMode(800, 600, 32, SDL_OPENGL);

  //if (SDL_MUSTLOCK( screen ) ){ 
  //  SDL_LockSurface(screen);
  //}
  
  //OpenGL info.
  //std::cout << "Renderer: " << glGetString (GL_RENDERER) << std::endl;
  //std::cout << "Version: " << glGetString (GL_VERSION) << std::endl;

  //if ( SDL_MUSTLOCK( screen ) ){ 
  //  SDL_UnlockSurface(screen);
  //}
  
  //Swaps screen buffers.
  //SDL_Flip(screen);

  //SDL_Quit();
  
  return 0;
}
