# Project on Hold
Please note that this project is currently on hold. A full mail stack in pure Dart is being worked on at https://github.com/enough-software/enough_mail which is planned to replace Delta Chat Core for future email messaging apps by this team. Of course Delta Chat continues to thrive and is not affected by this fork's change.

# Delta Chat Core Plugin

The Delta Chat core plugin provides a Flutter / Dart wrapper for the [Delta Chat Core](https://github.com/deltachat/deltachat-core-rust) (DCC). This plugin interacts with the native platform and calls DCC to enable IMAP / SMTP based chats.

- **Android state:** Currently in development
- **iOS state:** Currently in development

## Information
- [Documentation](https://github.com/open-xchange/flutter-deltachat-core/wiki)

## Requirements
- The latest Flutter stable version is used (if problems occur try the [Flutter Dev Channel](https://github.com/flutter/flutter/wiki/Flutter-build-release-channels))
- The latest Delta Chat Core develop branch of this [repository](https://github.com/open-xchange/deltachat-core-rust) is used
- The used Delta Chat Core is currently only out of the box buildable using Linux (Debian / Ubuntu is recommended)
- Setup the Rust toolchain
- Download the [Android NDK](https://developer.android.com/ndk/downloads/) stable version (r20b)
- The Android NDK must be on the PATH (*ndk-build* must be callable)

## Execution
- Execute *git submodule update --init --recursive*
- Build and run the project via your IDE / Flutter CLI (the project contains an example app to test the plugin)

## Development
To be able to edit / extend this project the following steps are important:

- Create an issue
- Perform all actions mentioned under **Execution**
- Within this repository only Flutter / Dart and Java files should get edited. C files shouldn't get changed as they are provided by sub repositories or other sources
- Everything located in the [com.b44t.messenger](https://github.com/open-xchange/flutter-deltachat-core/tree/master/android/src/main/java/com/b44t/messenger) package is mainly provided by the Delta Chat core team. This code should not get altered.
- Implement your changes (if the platform part is changed a rebuild of this the Android / iOS project could be required)
- Add tests
- Create a pull request

### Flutter 

For help getting started with Flutter, view our online
[documentation](https://flutter.io/).

For help on editing plugin code, view the [documentation](https://flutter.io/developing-packages/#edit-plugin-package).

### Dart

Flutter is based on Dart, more information regarding Dart can be found on the [official website](https://www.dartlang.org/).
