# TSNsched but now with gradle and gRPC
With this project, I try to make TSNsched a bit more usable for my use case, coupling it with INET to simulate TSN.
At the same time, the original functionality of the scheduler should not be changed.
For the documentation of TSNsched check out the original repo (or the branch original-main).
Some aspects that were changed, and in my opinion, are relevant are explained in more detail below. 
Otherwise, the changelog in short form follows:

### Changelog
- Build system changed to gradle
- gRPC server added
- Scheduler returns JSON instead of writing it to a file
- DevContainer and Dockerfile added as replacements for the installation script
- Removed scripts that are not used by the gRPC server


## Gradle
We changed the build system to gradle. 
To run the program, two tasks can be used:
1. The task `installDist` creates a shell script in the build folder that can be executed to run the server.
2. The task `shadowJar` uses the `shadow` plugin and creates a fat jar which than can be executed via `java -jar TSNSCHED_New-1.0-SNAPSHOT-all.jar`. This file is located in `build/libs`.

## gRPC
One of the main differences is the usage of gRPC.
The application creates a gRPC server which takes the input and then calls TSNsched the same way as it is done in the original repo.
The main entry point was moved to the gRPC server function.
To be able to answer the gRPC requests with a valid JSON, a function was added, which creates the JSON object instead of writing it to a file.
Other scripts from the original repo, how one can start the scheduler, are removed.

## DevContainer
The repo has a DevContainer and the underlying Dockerfile.
This replaces the install script and hopefully makes it easier to run the project.

## Planed Features
- [ ] Create Docker container with entry point to automatically start scheduler



