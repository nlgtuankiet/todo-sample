#!/usr/bin/env bash
#[:frontend:android:app:assembleDebug, :frontend:android:dynamic:seedDatabase:assembleDebug, :frontend:android:dynamic:settings:assembleDebug]

set -e

install="adb install frontend/android/app/build/outputs/apk/debug/app-debug.apk"
run="adb shell am start -n 'com.sample.todo.debug/com.sample.todo.splash.SplashActivity'"

$uninstall && $install && $run

echo "Total time: $SECONDS secs"

now=$(date)
echo "$now"