#!/usr/bin/env bash

set -e

build="./gradlew :app:assembleDebug --stacktrace"
install="adb install app/build/outputs/apk/debug/app-debug.apk"
run="adb shell am start -n 'com.sample.todo.debug/com.sample.todo.splash.SplashActivity' -a android.intent.action.MAIN -c android.intent.category.LAUNCHER"

$build && $uninstall && $install && $run

echo "Total time: $SECONDS secs"

now=$(date)
echo "$now"