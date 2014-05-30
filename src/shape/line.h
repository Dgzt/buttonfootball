#ifndef LINE_H
#define LINE_H

#include <GL/gl.h>

class Line
{
    GLuint vbo;

public:
    Line();
    virtual ~Line();

    void resize( const float &x1,
                 const float &y1,
                 const float &x2,
                 const float &y2);

    void draw();
};

#endif // LINE_H
