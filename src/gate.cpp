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

#include <vector>
#include "shape/line.h"
#include "gate.h"

// Color of the gates.
const Color WHITE_COLOR = { 1.0f, 1.0f, 1.0f };

// The number of row line in the grid.
const int ROW_OF_GRID = 4;

// The number of column line in the grid.
const int COLUMN_OF_GRID = 3;

Gate::Gate() :
    Rectangle( GL_LINE_LOOP, WHITE_COLOR )
{}

Gate::~Gate()
{
    if( !lines.empty() ){
        while( !lines.empty() ){
            Line *line = lines.at( lines.size() - 1 );
            lines.pop_back();
            delete line;
        }
    }
}

void Gate::resize(const float &x, const float &y, const float &width, const float &height)
{
    // Resize the border
    Rectangle::resize(x,y,width,height);

    // Resize the grid
    const unsigned int rowLineDistance = height / ( ROW_OF_GRID + 1 );
    const unsigned int columnLineDistance = width / ( COLUMN_OF_GRID + 1 );

    // If the vector is empty, than create lines
    if( lines.empty() ){
        for( unsigned int i = 0; i < ROW_OF_GRID; ++i ){
            Line *line = new Line( WHITE_COLOR );
            lines.push_back( line );
        }
        for( unsigned int i = 0; i < COLUMN_OF_GRID; ++i ){
            Line *line = new Line( WHITE_COLOR );
            lines.push_back( line );
        }
    }

    // Resize row lines
    for( unsigned int i = 0; i < lines.size(); ++i ){
        unsigned int lineY = y - ( i + 1 ) * rowLineDistance;
        lines.at( i )->resize( x, lineY, x + width, lineY );
    }

    // Resize column lines
    for( unsigned int i= ROW_OF_GRID, j = 0; i < ROW_OF_GRID + COLUMN_OF_GRID; ++i, ++j ){
        unsigned int lineX = x + ( j + 1 ) * columnLineDistance;
        lines.at( i )->resize( lineX, y, lineX, y - height );
    }
}

void Gate::draw()
{
    // Draw the border
    Rectangle::draw();

    // Draw the grid lines
    for( unsigned int i = 0; i < lines.size(); ++i ){
        lines.at( i )->draw();
    }
}
