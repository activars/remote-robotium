===============================================================================
ROBOTIUM
===============================================================================

Robotium is an Android test framework that has full support for Activities, Dialogs, Toasts, Menus and Context Menus. 

The project home page is at http://www.robotium.org/

1. Project layout
   /robotium
        |
        |
        |-/robotium-client
        | (part of the remote control client API)
        |
        |-/robotium-common
        | (common libs, protocols used by server and client)
        |
        |-/robotium-server
        | (part of the remote control server component running on Android device/emulator)
        |      |
        |      |-/robotium-server-app
        |
        |-/examples
        |      |
        |      |-/RemoteSoloExamples
        |        
        |
        |-/robotium-solo
           (standalone Robotium API that works with standard Android test suite)


2.Building the Robotium
  In order to successfully build the Robotium project, it mainly requires
  installing the building tools below:
     - m2eclipse: http://m2eclipse.sonatype.org/
     - Setup your ANDROID_HOME system environment variable to /<Android SDK Location>/platform-tools/
       

Video Demo: http://www.flickr.com/photos/activars/5861337099/in/photostream
