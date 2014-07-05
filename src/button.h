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
* The button.
*/

#ifndef BUTTON_H
#define BUTTON_H

#include "abstractbutton.h"

class Button : public AbstractButton
{
public:
    /*!
     * The constructor.
     *
     * @param parent The parent table.
     * @param box2DWorld The box2d world.
     * @param color The color.
     * @param box2DX The x coordinate in box2d.
     * @param box2DY The y coordinate in box2d.
     */
    Button( Table *parent,
            b2World *box2DWorld,
            const Color &color,
            const float &box2DX,
            const float &box2DY );
};

#endif // BUTTON_H
