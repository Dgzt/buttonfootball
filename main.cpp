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

/*!
 * The main function.
 */
int 
main(){
  std::cout << "Hello World!" << std::endl;
  
  //Initialize the SDL library.
  SDL_Init(SDL_INIT_VIDEO);
  
  //Setup the video mode.
  SDL_Surface *screen = SDL_SetVideoMode(256, 256, 32, SDL_SWSURFACE);

  if (SDL_MUSTLOCK( screen ) ){ 
    SDL_LockSurface(screen);
  }
  
  //Code for here.

  if ( SDL_MUSTLOCK( screen ) ){ 
    SDL_UnlockSurface(screen);
  }
  
  //Swaps screen buffers.
  SDL_Flip(screen);

  //SDL_Quit();
  
  return 0;
}
