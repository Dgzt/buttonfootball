#ifndef BUTTON_H
#define BUTTON_H

#include "shape/circle.h"

class Table;
class b2World;
class b2Body;

class Button : public Circle
{
    Table *parent;

    b2Body *body;

    float x;

    float y;

public:
    Button( Table *parent, b2World *box2DWorld, Color color, const float x, const float y  );

    void resize();

    void draw();
};

#endif // BUTTON_H
