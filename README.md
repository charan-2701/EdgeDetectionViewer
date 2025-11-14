# Edge Detection Viewer

Minimal Android + Native C++ project skeleton with a simple TypeScript web viewer.

Instructions

1. Open `EdgeDetectionViewer` in Android Studio.
2. Install required SDK/NDK/CMake via SDK Manager.
3. (Optional) Download OpenCV Android SDK from https://opencv.org/releases/ and extract to project root as `opencv/`.
4. If `opencv/` is present, copy the native libs to `app/src/main/jniLibs/<abi>/`.
5. Build via Android Studio or run `./gradlew assembleDebug`.

Web viewer

cd `web` and run `tsc viewer.ts` (requires TypeScript), then open `index.html` (use a local server).

This scaffold provides working JNI hooks and a Canny edge-detection example using OpenCV (if SDK is added).
