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
  - Add `import com.zappi.android.function.SocialPackage;` to the imports at the top of the file
  - Add `new SocialPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-android-function'
  	project(':react-native-android-function').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-android-function/android/app')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-android-function')
  	```

## 💻 Usage

```javascript
import AndroidFunction from 'react-native-android-function';
```

- API Way

```javascript
AndroidFunction.Youtube(url,true);
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

```javascript
AndroidFunction.ShortCuts(urlImg,cropped,shortLabel,longLabel,appUri,setPackage);
```

```javascript
AndroidFunction.ShortCutsType(urlImg,cropped,shortLabel,longLabel,type,id);
```

|Prop|Type|Default|Note|
| - | - | - | - |
|`type`|`string`|`facebook,instagram,twitter,googleplaystore`| What kind of intent to use.
|`id`|`string`|| id of the user to pass.

```javascript
AndroidFunction.pinnedShortcuts
(
  [{
    "urlImg": "https://scontent-mxp1-1.cdninstagram.com/vp/3c4732c2cd3566727dad10f03c04b7bd/5C9241C4/t51.2885-19/s150x150/34706107_1875460276079648_8096847319644766208_n.jpg",
    "shortLabel": "Fox",
    "longLabel": "Megan Fox",
    "appUri": "https://www.google.com/search?q=megan+fox"
  },
  {
    "urlImg": "http://images.amcnetworks.com/bbcamerica.com/wp-content/uploads/2017/05/anglo_2000x1125_larapulver-e1495023889751-640x360.jpg",
    "shortLabel": "Pulver",
    "longLabel": "Lara Pulver",
    "appUri": "https://twitter.com/larapulver",
    "setPackage" :"com.twitter.android"
    },
    {
      "urlImg": "https://i.pinimg.com/originals/cd/3c/b9/cd3cb912cbcaafd13af7c774f4e4ba37.jpg",
      "shortLabel": "Watson",
      "longLabel": "Emma Watson",
      "appUri": "https://www.instagram.com/_u/emmawatson/",
      "setPackage" :"com.instagram.android"
    },
    {
      "urlImg": "https://www.syfy.com/sites/syfy/files/styles/1200x680/public/2018/03/alba-ff2.jpg?itok=iH_tTKqw",
      "shortLabel": "Alba",
      "longLabel": "Jessica Alba",
      "appUri": "fb://facewebmodal/f?href=https://www.facebook.com/jessicaalba",
      "setPackage" :"com.facebook.katana"
    }]
);
```

## 📜 License
This library is provided under the Apache License.
