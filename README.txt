===============================================================================
ROBOTIUM
===============================================================================

The project home page is at http://www.robotium.org/

All documentation is there.

1. Project layout
   /robotium
        |
        |
        |-robotium-client
        | (part of the remote control client API)
        |
        |-robotium-server
        | (part of the remote control server component running on Android device/emulator)
        |      |
        |      |-robotium-server-app
        |
        |-robotium-solo
          (standalone Robotium API that works with standard Android test suite)


2.Building the Robotium
  In order to successfully build the Robotium project, it mainly requires
  installing the building tools below:
     - Maven
     - Maven Enforcer plugin
     - Maven Versions plugin
 For those who working on Eclipse, there are plugins available at
     - m2eclipse: http://m2eclipse.sonatype.org/
