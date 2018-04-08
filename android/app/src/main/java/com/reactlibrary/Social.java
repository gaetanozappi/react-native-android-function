package com.reactlibrary;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Social extends ReactContextBaseJavaModule {
    private ReactContext mReactContext;
    private String url;

    public Social(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @ReactMethod
    public void Youtube(String id,boolean fullscreen){
      Intent appIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("vnd.youtube:"+id));
      appIntent.putExtra("force_fullscreen",fullscreen);
      Intent webIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v="+id));
      try{
          getCurrentActivity().startActivity(appIntent);
      }catch(ActivityNotFoundException ex){
          getCurrentActivity().startActivity(webIntent);
      }
    }

    @ReactMethod
    public void Youtube(String id){
      Youtube(id,true);
    }

    @ReactMethod
    public void Facebook(String id){
      Intent appIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/"+id));
      appIntent.setPackage("com.facebook.katana");
      Intent webIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/"+id));
      try{
          getCurrentActivity().startActivity(appIntent);
      }catch(ActivityNotFoundException ex){
          getCurrentActivity().startActivity(webIntent);
      }
    }

    @ReactMethod
    public void Instagram(String id){
      Intent appIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://instagram.com/_u/"+id));
      appIntent.setPackage("com.instagram.android");
      Intent webIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://instagram.com/"+id));
      try{
          getCurrentActivity().startActivity(appIntent);
      }catch(ActivityNotFoundException ex){
          getCurrentActivity().startActivity(webIntent);
      }
    }

    @ReactMethod
    public void Twitter(String id){
      Intent appIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("twitter://user?screen_name="+id));
      appIntent.setPackage("com.twitter.android");
      Intent webIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/"+id));
      try{
          getCurrentActivity().startActivity(appIntent);
      }catch(ActivityNotFoundException ex){
          getCurrentActivity().startActivity(webIntent);
      }
    }

    @Override
    public String getName() {
        return "Social";
    }

}
