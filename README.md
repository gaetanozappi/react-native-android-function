# React Native: react-native-android-function

![platforms](https://img.shields.io/badge/platforms-Android-brightgreen.svg?style=flat&colorB=191A17)
[![github home](https://img.shields.io/badge/gaetanozappi-react--native--social-blue.svg?style=flat)](https://github.com/gaetanozappi/react-native-android-function)

[![github issues](https://img.shields.io/github/issues/gaetanozappi/react-native-android-function.svg?style=flat)](https://github.com/gaetanozappi/react-native-android-function/issues)
[![github closed issues](https://img.shields.io/github/issues-closed/gaetanozappi/react-native-android-function.svg?style=flat&colorB=44cc11)](https://github.com/gaetanozappi/react-native-android-function/issues?q=is%3Aissue+is%3Aclosed)
[![Issue Stats](https://img.shields.io/issuestats/i/github/gaetanozappi/react-native-android-function.svg?style=flat&colorB=44cc11)](http://github.com/gaetanozappi/react-native-android-function/issues)
[![github license](https://img.shields.io/github/license/gaetanozappi/react-native-android-function.svg)]()

-   [Usage](#-usage)
-   [License](#-license)

### Android

## Manually link

Add `react-native-android-function` to your `./android/settings.gradle` file as follows:

```diff
...
include ':app'
+ include ':react-native-android-function'
+ project(':react-native-android-function').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-android-function/android/app')
```

Include it as dependency in `./android/app/build.gradle` file:

```diff
dependencies {
    ...
    compile "com.facebook.react:react-native:+"  // From node_modules
+   compile project(':react-native-android-function')
}
```

Finally, you need to add the package within the `ReactInstanceManager` of your
MainActivity (`./android/app/src/main/java/your/bundle/MainActivity.java`):

```java
import com.zappi.android.function.SocialPackage;  // <---- import this one
...
@Override
protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
        new MainReactPackage(),
        new SocialPackage()  // <---- add this line
    );
}
```

After that, you will need to recompile
your project with `react-native run-android`.

## 💻 Usage

```javascript
import AndroidFunction from 'react-native-android-function';
```

- API Way

```javascript
AndroidFunction.Youtube(url, true);
```

```javascript
AndroidFunction.Facebook(url);
```

```javascript
AndroidFunction.Instagram(url);
```

```javascript
AndroidFunction.Twitter(url);
```

```javascript
AndroidFunction.GooglePlayStore();
```

```javascript
AndroidFunction.General(appIntent,setPackage,webIntent);
```

## 📜 License
This library is provided under the Apache License.
