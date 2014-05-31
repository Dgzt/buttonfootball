#ifndef MAP_H
#define MAP_H

#include "shape/rectangle.h"

class Circle;
class Line;

class Map : public Rectangle
{
    Rectangle *leftSector16;
    Rectangle *rightSector16;
    Rectangle *leftSector5;
    Rectangle *rightSector5;

    Circle *bigCentralCircle;

    Line *halfLine;

    Circle *leftCircle11;

    Circle *rightCircle11;

    Circle *smallCentralCircle;

    Circle *topLeftCornerArc;
    Circle *topRightCornerArc;
    Circle *bottomLeftCornerArc;
    Circle *bottomRightCornerArc;

    Circle *bigLeftArc;
    Circle *bigRightArc;

public:
    Map();
    ~Map();

    void resize( float parentX, float parentY, float parentWidth, float parentHeight, float scale );

    void draw();
};

#endif // MAP_H
