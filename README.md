# React Native: react-native-social

![platforms](https://img.shields.io/badge/platforms-Android-brightgreen.svg?style=flat&colorB=191A17)
[![github home](https://img.shields.io/badge/gaetanozappi-react--native--social-blue.svg?style=flat)](https://github.com/gaetanozappi/react-native-social)

[![github issues](https://img.shields.io/github/issues/gaetanozappi/react-native-social.svg?style=flat)](https://github.com/gaetanozappi/react-native-social/issues)
[![github closed issues](https://img.shields.io/github/issues-closed/gaetanozappi/react-native-social.svg?style=flat&colorB=44cc11)](https://github.com/gaetanozappi/react-native-social/issues?q=is%3Aissue+is%3Aclosed)
[![Issue Stats](https://img.shields.io/issuestats/i/github/gaetanozappi/react-native-social.svg?style=flat&colorB=44cc11)](http://github.com/gaetanozappi/react-native-social/issues)
[![github license](https://img.shields.io/github/license/gaetanozappi/react-native-social.svg)]()

-   [Usage](#-usage)
-   [License](#-license)

### Android

## Manually link

Add `react-native-social` to your `./android/settings.gradle` file as follows:

```diff
...
include ':app'
+ include ':react-native-social'
+ project(':react-native-social').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-social/android/app')
```

Include it as dependency in `./android/app/build.gradle` file:

```diff
dependencies {
    ...
    compile "com.facebook.react:react-native:+"  // From node_modules
+   compile project(':react-native-social')
}
```

Finally, you need to add the package within the `ReactInstanceManager` of your
MainActivity (`./android/app/src/main/java/your/bundle/MainActivity.java`):

```java
import com.reactlibrary.SocialPackage;  // <---- import this one
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
import Social from 'react-native-social';
```

- API Way

```javascript
Social.Youtube(url, true);
```

```javascript
Social.Facebook(url);
```

```javascript
Social.Instagram(url);
```

```javascript
Social.Twitter(url);
```

```javascript
Social.GooglePlayStore();
```

```javascript
Social.General(appIntent,setPackage,webIntent);
```

## 📜 License
This library is provided under the Apache License.
