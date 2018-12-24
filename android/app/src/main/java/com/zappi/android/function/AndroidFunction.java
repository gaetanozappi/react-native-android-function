package com.zappi.android.function;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v4.graphics.drawable.IconCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class AndroidFunction extends ReactContextBaseJavaModule {
    private ReactContext mReactContext;

    JSONObject socialObj = new JSONObject();

    public AndroidFunction(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;

        try {
            JSONObject fb = new JSONObject();
            fb.put("appUri", "fb://facewebmodal/f?href=https://www.facebook.com/");
            fb.put("setPackage", "com.facebook.katana");
            fb.put("webUri", "https://www.facebook.com/");
            socialObj.put("facebook", fb);

            JSONObject ig = new JSONObject();
            ig.put("appUri", "http://instagram.com/_u/");
            ig.put("setPackage", "com.instagram.android");
            ig.put("webUri", "http://instagram.com/");
            socialObj.put("instagram", ig);

            JSONObject tw = new JSONObject();
            tw.put("appUri", "twitter://user?screen_name=");
            tw.put("setPackage", "com.twitter.android");
            tw.put("webUri", "https://twitter.com/");
            socialObj.put("twitter", tw);

            JSONObject gps = new JSONObject();
            gps.put("appUri", "market://details?id=");
            gps.put("setPackage", "com.android.vending");
            gps.put("webUri", "https://play.google.com/store/apps/details?id=");
            socialObj.put("googleplaystore", gps);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void Youtube(String id, boolean fullscreen) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        appIntent.putExtra("force_fullscreen", fullscreen);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            getCurrentActivity().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            getCurrentActivity().startActivity(webIntent);
        }
    }

    @ReactMethod
    public void Youtube(String id) {
        Youtube(id, true);
    }

    @ReactMethod
    public void General(String appUri, String setPackage, String webUri) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUri));
        appIntent.setPackage(setPackage);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUri));
        try {
            getCurrentActivity().startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            getCurrentActivity().startActivity(webIntent);
        }
    }

    @ReactMethod
    public void Facebook(String id) {
        General("fb://facewebmodal/f?href=https://www.facebook.com/" + id, "com.facebook.katana", "https://www.facebook.com/" + id);
    }

    @ReactMethod
    public void Instagram(String id) {
        General("http://instagram.com/_u/" + id, "com.instagram.android", "http://instagram.com/" + id);
    }

    @ReactMethod
    public void Twitter(String id) {
        General("twitter://user?screen_name=" + id, "com.twitter.android", "https://twitter.com/" + id);
    }

    @ReactMethod
    public void GooglePlayStore(String id) {
        General("market://details?id=" + id, "com.android.vending", "https://play.google.com/store/apps/details?id=" + id);
    }

    @ReactMethod
    public void GooglePlayStore() {
        GooglePlayStore(getCurrentActivity().getPackageName());
    }

    @ReactMethod
    public void ShortCuts(String urlImg, Boolean cropped, String shortLabel, String longLabel, String appUri, String setPackage) {
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(getCurrentActivity())) {
            URL urlP = null;
            try {
                urlP = new URL(urlImg);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(urlP.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (cropped) bmp = getCroppedBitmap(bmp);
            final IconCompat icon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) ? IconCompat.createWithAdaptiveBitmap(bmp) : IconCompat.createWithBitmap(bmp);
            final ShortcutInfoCompat shortcut = new ShortcutInfoCompat.Builder(getCurrentActivity(), UUID.randomUUID().toString())
                    .setShortLabel(shortLabel)
                    .setLongLabel(longLabel)
                    .setIcon(icon)
                    .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse(appUri)).setPackage(setPackage))
                    .build();
            ShortcutManagerCompat.requestPinShortcut(getCurrentActivity(), shortcut, null);
        }
    }

    @ReactMethod
    public void ShortCutsType(String urlImg, Boolean cropped, String shortLabel, String longLabel, String type, String id) {
        String appUri = "", setPackage = "";
        JSONArray keys = socialObj.names();
        for (int i = 0; i < keys.length(); i++) {
            try {
                String key = keys.getString(i);
                if (key.equals(type)) {
                    JSONObject obj = socialObj.getJSONObject(key);
                    appUri = obj.getString("appUri") + id;
                    setPackage = obj.getString("setPackage");
                    ShortCuts(urlImg, cropped, shortLabel, longLabel, appUri, setPackage);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        int max = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(max, max, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, max, max);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawCircle(max / 2, max / 2, max / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    @Override
    public String getName() {
        return "AndroidFunction";
    }

}
