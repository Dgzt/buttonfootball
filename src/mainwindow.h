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

class Table;

class MainWindow
{
    // The table.
    Table *table;

public:
    MainWindow();
    ~MainWindow();

    /*!
     * The window resized.
     *
     * @width The new width.
     * @height The new height.
     */
    void resize( const int &width, const int &height );

    /*!
     * Draw the items.
     */
    void draw();

    /*!
     * The button is pressed.
     *
     * @x The x value.
     * @y The y value.
     */
    void buttonPressed( const unsigned int &x, const unsigned int &y );

    /*!
     * The button is moved when it pressed.
     *
     * @x The x value.
     * @y The y value.
     */
    void buttonMove( const unsigned int &x, const unsigned int &y );

    /*!
     * The bittom os released.
     */
    void buttonReleased();
};

#endif // MAINWINDOW_H
