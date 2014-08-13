PROJECT( buttobfootball )

CMAKE_MINIMUM_REQUIRED( VERSION 2.8 )

IF( HTML5 )
    MESSAGE( STATUS "HTML5 mode" )
    INCLUDE( CMakeLists-html5.txt )
ELSE( HTML5 )
    MESSAGE( STATUS "Executable mode" )
    INCLUDE( CMakeLists-exec.txt )
ENDIF( HTML5 )
