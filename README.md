# React Native: Native Social

[![github home](https://img.shields.io/badge/gaetanozappi-react--native--social-blue.svg?style=flat)](https://github.com/gaetanozappi/react-native-social)
[![github issues](https://img.shields.io/github/issues/gaetanozappi/react-native-social.svg?style=flat)](https://github.com/gaetanozappi/react-native-social/issues)

-   [Usage](#usage)
-   [License](#license)

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

## Usage

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

## License
This library is provided under the Apache License.
