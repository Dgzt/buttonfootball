PROJECT( buttobfootball )

CMAKE_MINIMUM_REQUIRED( VERSION 2.8 )

#SET( CMAKE_TOOLCHAIN_FILE "$EMSCRIPTEN/cmake/Platform/Emscripten.cmake" )

#FILE( GLOB ButtonFootball_Sources
#  main.cpp
#)

#ADD_EXECUTABLE( ${PROJECT_NAME}.html ${ButtonFootball_Sources} )

FIND_PACKAGE( SDL REQUIRED )

INCLUDE_DIRECTORIES( ${SDL_INCLUDE_DIR} )

SET( ButtonFootball_Sources
    main.cpp
)

ADD_EXECUTABLE( ${PROJECT_NAME} ${ButtonFootball_Sources} )

TARGET_LINK_LIBRARIES( ${PROJECT_NAME} ${SDL_LIBRARY} )
