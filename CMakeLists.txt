PROJECT( buttobfootball )

CMAKE_MINIMUM_REQUIRED( VERSION 2.8 )

SET( CMAKE_TOOLCHAIN_FILE "$EMSCRIPTEN/cmake/Platform/Emscripten.cmake" )

FIND_PACKAGE( SDL REQUIRED )

FILE( GLOB ButtonFootball_Sources
  main.cpp
)

ADD_EXECUTABLE( ${PROJECT_NAME}.html ${ButtonFootball_Sources} )
