#ifndef CIRCLEBORDER_H
#define CIRCLEBORDER_H

#include <GL/gl.h>
#include "vertex.h"

class CircleBorder
{
    GLuint vbo;

    int verticesNum;

public:
    CircleBorder();
    virtual ~CircleBorder();

    void resize( const float &x,
                 const float &y,
                 const float &r);

    void draw();

};

#endif // CIRCLEBORDER_H
