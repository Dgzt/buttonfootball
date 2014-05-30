SET( ButtonFootball_Sources
    main.cpp
    mainwindow.cpp
    table.cpp
    map.cpp
    rectangleborder.cpp
    circle.cpp
    line.cpp
)

ADD_EXECUTABLE( ${PROJECT_NAME} ${ButtonFootball_Sources} )

TARGET_LINK_LIBRARIES( ${PROJECT_NAME} ${SDL2_LIBRARY} ${GLEW_LIBRARIES} ${OPENGL_LIBRARIES} )
