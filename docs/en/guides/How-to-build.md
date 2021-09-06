# How to build a project
This document will help you compile and build a project in your Gradle and set your IDE.

## Building the Project
**Since we are using Git submodule, we do not recommend using the `GitHub` tag or release page to download source codes for compiling.**

### Gradle behind the Proxy
If you need to execute build commands behind a proxy, check [the Gradle doc](https://docs.gradle.org/7.2/userguide/build_environment.html#sec:accessing_the_web_via_a_proxy).

### Building from GitHub
1. Prepare git, JDK8+.
1. Clone the project.

    If you want to build a release from source codes, set a `tag name` by using `git clone -b [tag_name] ...` while cloning.
    
    ```bash
    git clone --recurse-submodules https://github.com/apache/skywalking.git
    cd skywalking/
    
    OR
    
    git clone https://github.com/apache/skywalking.git
    cd skywalking/
    git submodule init
    git submodule update
    ```

1. Run `./gradlew distTar --parallel` to build `.tar` or `./gradlew distZip --parallel` to build `.zip` file.
1. All packages are in `build/distributions`.

### Building from Apache source code release
- What is the `Apache source code release`?

For each official Apache release, there is a complete and independent source code tar, which includes all source codes.
You could download it from [SkyWalking Apache download page](http://skywalking.apache.org/downloads/).
There is no requirement related to git when compiling this. Just follow these steps.

1. Prepare JDK8+.
1. Run `./gradlew distTar --parallel` to build `.tar` or `./gradlew distZip --parallel` to build `.zip` file.
1. All packages are in `build/distributions`.

### Building docker images
You can build docker images of `backend` and `ui` with `Makefile` located in root folder.

Refer to [Build docker image](../../../docker) for more details.

## Setting up your IntelliJ IDEA
**NOTE**: If you clone the codes from GitHub, please make sure that you have finished steps 1 to 3 in section
**[Build from GitHub](#building-from-github)**.
If you download the source codes from the official website of SkyWalking, please make sure that you have followed the
steps in section **[Build from Apache source code release](#building-from-apache-source-code-release)**.

1. Run `./gradlew openIdea` to initialize the related files and open the project in IntelliJ IDEA.
1. Run `./gradlew build -x test` to compile project and generate source codes. The reason is that we use gRPC and protobuf.
