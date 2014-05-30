#ifndef CIRCLE_H
#define CIRCLE_H

#include <GL/gl.h>
#include "vertex.h"

class Circle
{
    GLuint vbo;

    GLenum type;

    int verticesNum;

    float startDegrees;

    float endDegrees;

public:
    /**
     * Full circle, 0-360 degrees.
     *
     * @param fill Fill the circle.
     */
    Circle( bool fill );

    /**
     * Create arc.
     *
     * @param startDegrees The start degrees.
     * @param endDegrees The end degrees.
     */
    Circle( float startDegrees, float endDegrees );

    virtual ~Circle();

    void resize( const float &x,
                 const float &y,
                 const float &r);

    void draw();

};

#endif // CIRCLE_H
