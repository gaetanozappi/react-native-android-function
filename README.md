# React Native: react-native-android-function

[![GitHub package version](https://img.shields.io/github/package-json/v/gaetanozappi/react-native-android-function.svg?style=flat&colorB=2b7cff)](https://github.com/gaetanozappi/react-native-android-function)
[![npm home](http://img.shields.io/npm/v/react-native-android-function.svg?style=flat)](https://www.npmjs.com/package/react-native-android-function)
![platforms](https://img.shields.io/badge/platforms-Android-brightgreen.svg?style=flat&colorB=191A17)
[![github home](https://img.shields.io/badge/gaetanozappi-react--native--social-blue.svg?style=flat)](https://github.com/gaetanozappi/react-native-android-function)
[![npm](https://img.shields.io/npm/dm/react-native-android-function.svg?style=flat&colorB=007ec6)](https://www.npmjs.com/package/react-native-android-function)

[![github issues](https://img.shields.io/github/issues/gaetanozappi/react-native-android-function.svg?style=flat)](https://github.com/gaetanozappi/react-native-android-function/issues)
[![github closed issues](https://img.shields.io/github/issues-closed/gaetanozappi/react-native-android-function.svg?style=flat&colorB=44cc11)](https://github.com/gaetanozappi/react-native-android-function/issues?q=is%3Aissue+is%3Aclosed)
[![Issue Stats](https://img.shields.io/issuestats/i/github/gaetanozappi/react-native-android-function.svg?style=flat&colorB=44cc11)](http://github.com/gaetanozappi/react-native-android-function/issues)
[![github license](https://img.shields.io/github/license/gaetanozappi/react-native-android-function.svg)]()

-   [Usage](#-usage)
-   [License](#-license)

## 📖 Getting started

`$ npm install react-native-android-function --save`

`$ react-native link react-native-android-function`

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.zappi.android.function.AndroidFunctionPackage;` to the imports at the top of the file
  - Add `new AndroidFunctionPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-android-function'
  	project(':react-native-android-function').projectDir = new File(rootProject.projectDir,'../node_modules/react-native-android-function/android/app')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-android-function')
  	```

## 💻 Usage

```javascript
import AndroidFunction from 'react-native-android-function';
```

## Intents

#### Youtube

```javascript
AndroidFunction.Youtube(url);
```

|Prop|Type|Default|Note|
| - | - | - | - |
|`url`|`string`||URL of the Youtube video to open.
|`fullscreen`|`boolean `|`true`|It allows you to choose whether to view the video in fullscreen mode.

```javascript
AndroidFunction.Youtube(url,fullscreen);
```

|Prop|Type|Default|Note|
| - | - | - | - |
|`url`|`string`||URL of the YouTube video to open.
|`fullscreen`|`boolean `||It allows you to choose whether to view the video in fullscreen mode.

#### Facebook

```javascript
AndroidFunction.Facebook(id);
```

|Prop|Type|Default|Note|
| - | - | - | - |
|`id`|`string`||Id of Facebook profile to open.

#### Instagram

```javascript
AndroidFunction.Instagram(id);
```

|Prop|Type|Default|Note|
| - | - | - | - |
|`id`|`string`||Id of Instagram profile to open.

#### Twitter

```javascript
AndroidFunction.Twitter(id);
```

|Prop|Type|Default|Note|
| - | - | - | - |
|`id`|`string`||Id of Twitter profile to open.

#### Google Play Store

```javascript
AndroidFunction.GooglePlayStore(id);
```

|Prop|Type|Default|Note|
| - | - | - | - |
|`id`|`string`||Id of Google Play Store profile to open.

```javascript
AndroidFunction.GooglePlayStore();
```

Open as intent the google play store passing id in the app in use it is on the google play store.

#### General intent

```javascript
AndroidFunction.General(appIntent,setPackage,webIntent);
```

|Prop|Type|Default|Note|
| - | - | - | - |
|`appIntent`|`string`|||
|`setPackage`|`string`|||
|`webIntent`|`string`||||

```javascript
AndroidFunction.ShortCuts(urlImg,cropped,shortLabel,longLabel,appUri,setPackage);
```

|Prop|Type|Default|Note|
| - | - | - | - |
|`urlImg`|`string `||Url of the image.
|`cropped`|`boolean`||Ability of skill the rounding of the image.
|`shortLabel`|`string `|||
|`longLabel`|`string `|||
|`appUri`|`string `|||
|`setPackage`|`string `||||

```javascript
AndroidFunction.ShortCutsType(urlImg,cropped,shortLabel,longLabel,type,id);
```

|Prop|Type|Default|Note|
| - | - | - | - |
|`urlImg`|`string `||Url of the image.
|`cropped`|`boolean`||Ability of skill the rounding of the image.
|`shortLabel`|`string `||
|`longLabel`|`string `||
|`type`|`string `|| What kind of intent to use: `facebook,instagram,twitter,googleplaystore`|
|`id`|`string `|| id of the user to pass|

#### Pinned ShortCuts

<img src="https://github.com/gaetanozappi/react-native-android-function/raw/master/screenshot/shortcuts.gif" />

```javascript
import * as React from 'react';
import { Text, View, StyleSheet, ToastAndroid } from 'react-native';
import AndroidFunction from 'react-native-android-function';

AndroidFunction.pinnedShortcuts.setShortcutItems([
  {
    typeImg: 'icon',
    icon: {
      family: 'Entypo',
      name: 'browser',
      colorIcon: '#90a4ae',
      colorCircle: '#000000',
    },
    shortLabel: 'Browser',
    longLabel: 'Open Browser',
    typeIntent: 'uri',
    appUri: 'https://www.google.com/',
  },
  {
    typeImg: 'url',
    urlImg:
      'http://images.amcnetworks.com/bbcamerica.com/wp-content/uploads/2017/05/anglo_2000x1125_larapulver-e1495023889751-640x360.jpg',
    shortLabel: 'Pulver',
    longLabel: 'Lara Pulver',
    typeIntent: 'uri',
    appUri: 'https://twitter.com/larapulver',
    setPackage: 'com.twitter.android',
  },
  {
    typeImg: 'letter',
    colorText: '#ffffff',
    colorCircle: '#e57373',
    shortLabel: 'Watson',
    longLabel: 'Emma Watson',
    typeIntent: 'uri',
    appUri: 'https://www.instagram.com/_u/emmawatson/',
    setPackage: 'com.instagram.android',
  },
  {
    typeImg: 'icon',
    icon: {
      family: 'MaterialCommunityIcons',
      name: 'emoticon-happy',
    },
    shortLabel: 'App pass param',
    longLabel: 'Open App',
    typeIntent: 'app',
    infoIntent: {
      name: 'Megan',
      surname: 'Fox',
      urlImg:
        'https://scontent-mxp1-1.cdninstagram.com/vp/3c4732c2cd3566727dad10f03c04b7bd/5C9241C4/t51.2885-19/s150x150/34706107_1875460276079648_8096847319644766208_n.jpg',
      age: 32,
      height: '1.63 m',
    },
  },
]);

export default class ExampleShortcuts extends React.Component {
  constructor() {
    super();
    this.state = {
      data: {},
    };
  }

  componentDidMount() {
    AndroidFunction.pinnedShortcuts
      .popInitialAction()
      .then(data => {
        if (Object.keys(data).length == 0 || Object.keys(data.obj).length == 0)
          return;
        console.log('App3:', data);
        this.setState({ data: data.obj });
        ToastAndroid.show(data.obj.name, ToastAndroid.SHORT);
      })
      .catch(console.error);
  }

  render() {
    const { data } = this.state;
    return (
      <View style={styles.container}>
      <Text style={styles.paragraph}>Quick Action: {(data.name && data.name+" "+data.surname) || 'None'}</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    backgroundColor: '#ecf0f1',
    padding: 8,
  },
  paragraph: {
    margin: 24,
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
  },
});
```

#### AndroidFunction.pinnedShortcuts.setShortcutItem

|Prop|Type|Default|Note|
| - | - | - | - |
|`typeImg`|`string`|`letter`|`url, icon, letter`
|`icon`|`object`||[icon](#icon), you can only use it as the typeImg is icon.
|`colorText`|`string`|![#FFFFFF](https://placehold.it/15/FFFFFF/000000?text=+) `#FFFFFF`| Text color, you can only use it as the typeImg is letter.
|`colorCircle`|`string`|![#64B5F6](https://placehold.it/15/64B5F6/000000?text=+) `#64B5F6`| Circle color, you can only use it as the typeImg is letter.
|`urlImg`|`string`|| Image url, you can only use it as the typeImg is url.
|`shortLabel`|`string`||
|`longLabel`|`string`||
|`typeIntent`|`string`|`app`|`Uri`, allows you to open an intent to an external app, see: [examples](#some-examples-of-appuri-and-setpackage) `App`, allows you to open the app itself by passing the objects defined in infoIntent, see: [infoIntent](#some-examples-of-infointent)
|`appUri`|`string`|||
|`setPackage`|`string`|||
|`infoIntent`|`object`||[infoIntent](#some-examples-of-infointent), you can only use it as the typeIntent is app.

## icon
|Prop|Type|Default|Note|
| - | - | - | - |
|`family`|`string`||Icon family type
|`name`|`string`||Icon name
|`colorText`|`string`|![#FFFFFF](https://placehold.it/15/FFFFFF/000000?text=+) `#FFFFFF`| Text color.
|`colorCircle`|`string`|![#64B5F6](https://placehold.it/15/64B5F6/000000?text=+) `#64B5F6`| Circle color.

See: [react-native-vector-icons](https://github.com/oblador/react-native-vector-icons)
To search for icons: [react-native-vector-icons](https://oblador.github.io/react-native-vector-icons)

## Some examples of appUri and setPackage

#### Open page browser

|appUri|setPackage|Note|
| - | - | - |
|`https://www.google.com/`||||

#### Facebook

|appUri|setPackage|Note|
| - | - | - |
|`https://www.facebook.com/`+id|`com.facebook.katana`||
|`fb://facewebmodal/f?href=https://www.facebook.com/`+id|`com.facebook.katana`|||

#### Instagram
|appUri|setPackage|Note|
| - | - | - |
|`http://instagram.com/`+id|`com.instagram.android`||
|`http://instagram.com/_u/`+id|`com.instagram.android`||

#### Twitter
|appUri|setPackage|Note|
| - | - | - |
|`https://twitter.com/`+id|`com.twitter.android`||
|`twitter://user?screen_name=`+id|`com.twitter.android`|||
|`https://twitter.com/intent/tweet?text=%23`+text|||
|`https://twitter.com/search?f=tweets&q=`+text||||

#### Google Play Store
|appUri|setPackage|Note|
| - | - | - |
|`https://play.google.com/store/apps/details?id=`+id|`com.android.vending`||
|`market://details?id=`+id|`com.android.vending`|||

#### Youtube
|appUri|setPackage|Note|
| - | - | - |
|`http://www.youtube.com/watch?v=`+id|||
|`vnd.youtube:`+id||||

## Some examples of infoIntent

```javascript
infoIntent: {
paramOne: 21,//int
paramTwo: "James Bond",//string
paramThree: 0.07,//float
....
}
```

#### Some suggested colors

- ![#e57373](https://placehold.it/15/e57373/000000?text=+) `#e57373`
- ![#f06292](https://placehold.it/15/f06292/000000?text=+) `#f06292`
- ![#ba68c8](https://placehold.it/15/ba68c8/000000?text=+) `#ba68c8`
- ![#9575cd](https://placehold.it/15/9575cd/000000?text=+) `#9575cd`
- ![#7986cb](https://placehold.it/15/7986cb/000000?text=+) `#7986cb`
- ![#64b5f6](https://placehold.it/15/64b5f6/000000?text=+) `#64b5f6`
- ![#4fc3f7](https://placehold.it/15/4fc3f7/000000?text=+) `#4fc3f7`
- ![#4dd0e1](https://placehold.it/15/4dd0e1/000000?text=+) `#4dd0e1`
- ![#4db6ac](https://placehold.it/15/4db6ac/000000?text=+) `#4db6ac`
- ![#81c784](https://placehold.it/15/81c784/000000?text=+) `#81c784`
- ![#aed581](https://placehold.it/15/aed581/000000?text=+) `#aed581`
- ![#dce775](https://placehold.it/15/dce775/000000?text=+) `#dce775`
- ![#fff176](https://placehold.it/15/fff176/000000?text=+) `#fff176`
- ![#ffd54f](https://placehold.it/15/ffd54f/000000?text=+) `#ffd54f`
- ![#ffb74d](https://placehold.it/15/ffb74d/000000?text=+) `#ffb74d`
- ![#ff8a65](https://placehold.it/15/ff8a65/000000?text=+) `#ff8a65`
- ![#a1887f](https://placehold.it/15/a1887f/000000?text=+) `#a1887f`
- ![#e0e0e0](https://placehold.it/15/e0e0e0/000000?text=+) `#e0e0e0`
- ![#90a4ae](https://placehold.it/15/90a4ae/000000?text=+) `#90a4ae`
- ![#000000](https://placehold.it/15/000000/000000?text=+) `#000000`

## 📜 License
This library is provided under the Apache License.
