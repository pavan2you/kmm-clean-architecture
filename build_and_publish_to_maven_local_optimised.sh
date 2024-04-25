#!/bin/bash

SECONDS=0

####################################################################################################
###########################################  Clean  ################################################
####################################################################################################

./gradlew clean

####################################################################################################
##########################################  Build  #################################################
####################################################################################################

#./gradlew :language:kmm-langx:build
#./gradlew :language:kmm-langx:kmm-langx-test:build
#
./gradlew :language:lang-androidx:build
./gradlew :language:lang-androidx:lang-androidx-test:build
#
#./gradlew :language:lang-kotlinx:build
#./gradlew :language:lang-kotlinx:lang-kotlinx-test:build
#
#./gradlew :architecture:kmm-tagd-core:build
#./gradlew :architecture:kmm-tagd-core:kmm-tagd-core-test:build
#
#./gradlew :architecture:kmm-tagd-di:build
#./gradlew :architecture:kmm-tagd-di:kmm-tagd-di-test:build
#
#./gradlew :architecture:kmm-tagd-arch:build
#./gradlew :architecture:kmm-tagd-arch:kmm-tagd-arch-test:build
#
./gradlew :architecture:kmm-tagd-android:build
./gradlew :architecture:kmm-tagd-android:kmm-tagd-android-test:build


####################################################################################################
####################################  Publish to maven local  ######################################
####################################################################################################

./gradlew :language:kmm-langx:publishToMavenLocal
./gradlew :language:kmm-langx:kmm-langx-test:publishToMavenLocal

./gradlew :language:lang-androidx:publishToMavenLocal
./gradlew :language:lang-androidx:lang-androidx-test:publishToMavenLocal

./gradlew :language:lang-kotlinx:publishToMavenLocal
./gradlew :language:lang-kotlinx:lang-kotlinx-test:publishToMavenLocal

./gradlew :architecture:kmm-tagd-core:publishToMavenLocal
./gradlew :architecture:kmm-tagd-core:kmm-tagd-core-test:publishToMavenLocal

./gradlew :architecture:kmm-tagd-di:publishToMavenLocal
./gradlew :architecture:kmm-tagd-di:kmm-tagd-di-test:publishToMavenLocal

./gradlew :architecture:kmm-tagd-arch:publishToMavenLocal
./gradlew :architecture:kmm-tagd-arch:kmm-tagd-arch-test:publishToMavenLocal

./gradlew :architecture:kmm-tagd-android:publishToMavenLocal
./gradlew :architecture:kmm-tagd-android:kmm-tagd-android-test:publishToMavenLocal

echo "total time taken $SECONDS sec"