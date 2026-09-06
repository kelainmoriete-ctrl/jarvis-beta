# Jarvis Voice Assistant — Browser APK Build

This version intentionally does **not** require a Gradle Wrapper JAR. GitHub Actions installs Gradle 8.10.2 automatically, so you can build the APK entirely in the browser.

## Steps
1. Create/sign in to a free GitHub account.
2. Create a new repository, e.g. `JarvisVoiceAssistant`.
3. Upload all files and folders from this ZIP to the repository root. Keep `.github/workflows/build-apk.yml` in its exact location.
4. Commit to the `main` branch.
5. Open **Actions** → **Build Jarvis APK**.
6. If it hasn't started automatically, choose **Run workflow**.
7. Wait for the green checkmark.
8. Open the completed workflow run and download **JarvisVoice-debug-apk** under Artifacts.
9. Extract it and install `app-debug.apk` on your Android phone.

## Current Version 1 features
- Microphone permission
- Urdu/Hindi-style speech recognition
- Text-to-speech replies
- YouTube, Instagram and Google/browser opening commands
- Simple stop/hello commands

## Next version
Android Accessibility Service can be added for explicit, user-approved screen actions such as scrolling and pressing visible buttons. The app should never silently take control of the device.
