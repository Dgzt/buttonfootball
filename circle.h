#ifndef CIRCLE_H
#define CIRCLE_H

#include <GL/gl.h>
#include "vertex.h"

class Circle
{
    GLuint vbo;

    GLenum type;

    int verticesNum;

public:
    Circle( bool fill );
    virtual ~Circle();

    void resize( const float &x,
                 const float &y,
                 const float &r);

    void draw();

};

#endif // CIRCLE_H
