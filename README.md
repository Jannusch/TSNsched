# TSNsched but now with gradle and gRPC
## Gradle
1.) The task `installDist` creats a shell script in the build folder that can be executed to run the server.
2.) Use the `shadow` plugin and create a fat jar which than can be executed via `java -jar TSNSCHED_New-1.0-SNAPSHOT-all.jar`. This file is located in `buil/libs` after the execution of the `shadowJar` task.

## gRPC
One of the main differences is the usage of gRPC.
The application creates a gRPC server which takes the input and then calls TSNsched the same way as it is done in the original repo.
The main entry point was moved to the gRPC server function.

## DevContainer
The repo has a DevContainer that builds everythin.

# Original Version
This is only a fork, and the main program works the same as in the original repo.
For documentation check out their repo (or the branch original-main).
