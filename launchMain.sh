#!/usr/bin/env bash

set -e

build="./gradlew :frontend:android:app:assembleDebug --stacktrace"
uninstall="adb uninstall com.sample.todo.debug"
install="adb install frontend/android/app/build/outputs/apk/debug/app-debug.apk"
run="adb shell am start -n 'com.sample.todo.debug/com.sample.todo.splash.SplashActivity'"

$build && $uninstall && $install && $run

echo "Total time: $SECONDS secs"

now=$(date)
echo "$now"