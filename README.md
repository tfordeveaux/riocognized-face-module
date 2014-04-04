Rio'Cognized Face Module
======================

Facial recognition module for Riocognized Webservice

## Setup OpenCV

- You need to compile OpenCV Library following OpenCV doc.
- Create lib/ folder in this repertory
- Copy opencv-248.jar and libopencv_java248* (.so or .dll, filename may vary depending on your OS)
- Add openCV JAT to maven local repository : `mvn install:install-file -Dfile=path_to_openCV_JAR/opencv-248.jar -DgroupId=org.opencv -DartifactId=OpenCV -Dversion=2.48 -Dpackaging=jar`
- Execute maven goal to compile `mvn clean install`
- Add -Djava.library.path='lib' to your VM exec param
- Run it!

## How to Use

Detect athlete faces in a picture and frame them.

You can select your picture passing URL in param, otherwise default image is used.

Result is saved in current repertory




