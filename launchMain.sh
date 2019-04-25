#!/usr/bin/env bash
#[:frontend:android:app:assembleDebug, :frontend:android:dynamic:seedDatabase:assembleDebug, :frontend:android:dynamic:settings:assembleDebug]

set -e

stop="./gradlew --stop"
build="./gradlew :frontend:android:app:assembleDebug -PminSdk=16 --stacktrace --offline"
uninstall="adb uninstall com.sample.todo.debug"
install="adb install frontend/android/app/build/outputs/apk/debug/app-debug.apk"
run="adb shell am start -n 'com.sample.todo.debug/com.sample.todo.splash.SplashActivity'"

$stop && $build && $uninstall && $install && $run

echo "Total time: $SECONDS secs"

now=$(date)
echo "$now"