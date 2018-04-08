# React Native: Native Social

### Android

Add `react-native-social` to your `./android/settings.gradle` file as follows:

```text
include ':react-native-social'
project(':react-native-social').projectDir = new File(settingsDir, '../node_modules/react-native-social/android/app')
```

Include it as dependency in `./android/app/build.gradle` file:

```text
dependencies {
    ...
    compile project(':react-native-social')
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
The MIT License
