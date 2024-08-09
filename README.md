# TSNsched but now with gradle and gRPC
This program uses gradle as a build system.
1.) The task `installDist` creats a shell script in the build folder that can be executed to run the server.
2.) Use the `shadow` plugin and create a fat jar which than can be executed via `java -jar TSNSCHED_New-1.0-SNAPSHOT-all.jar`. This file is located in `buil/libs` after the execution of the `shadowJar` task.
