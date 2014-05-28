#ifndef RECTANGLEBORDER_H
#define RECTANGLEBORDER_H

#include <GL/gl.h>

class RectangleBorder
{
    GLuint vbo;

public:
    RectangleBorder();

    void resize( const float &x,
                 const float &y,
                 const float &width,
                 const float &height);

    void draw();

};

#endif // RECTANGLEBORDER_H
