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

    /*!
     * Set the screen size.
     *
     * @param width The new width value.
     * @param height The new height value.
     */
    void setScreen( const int &width, const int &height );

public:
    MainWindow();
    ~MainWindow();

    /*!
     * The window resized.
     *
     * @param width The new width value.
     * @param height The new height value.
     */
    void resize( const int &width, const int &height );

    /*!
     * Draw the items.
     */
    void draw();

    /*!
     * The button is pressed.
     *
     * @param x The x value.
     * @param y The y value.
     */
    void buttonPressed( const unsigned int &x, const unsigned int &y );

    /*!
     * The button is moved when it pressed.
     *
     * @param x The x value.
     * @param y The y value.
     */
    void buttonMove( const unsigned int &x, const unsigned int &y );

    /*!
     * The bittom os released.
     */
    void buttonReleased();

    /*!
     * Step the box2d world.
     *
     * @param timeStep The time step value.
     */
    void stepBox2D( const double &timeStep );
};

#endif // MAINWINDOW_H
