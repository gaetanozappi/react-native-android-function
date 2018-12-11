package com.zappi.android.function;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v4.graphics.drawable.IconCompat;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.views.text.ReactFontManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Social extends ReactContextBaseJavaModule {
    private ReactContext mReactContext;
    ShortcutManager shortcutManager;

    JSONObject socialObj = new JSONObject();

    public Social(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            shortcutManager = mReactContext.getSystemService(ShortcutManager.class);
        }

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

    @ReactMethod
    public void pinnedShortcuts(ReadableArray map) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {

            List<ShortcutInfo> array = new ArrayList<ShortcutInfo>();
            ;
            for (int i = 0; i < map.size(); i++) {
                ReadableMap frame = map.getMap(i);
                String urlImg = frame.getString("urlImg");
                String shortLabel = frame.getString("shortLabel");
                String longLabel = frame.getString("longLabel");
                String appUri = frame.getString("appUri");
                String setPackage = frame.hasKey("setPackage") ? frame.getString("setPackage") : "";

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
                bmp = getCroppedBitmap(bmp);

                //Drawable o = generateVectorIcon("FontAwesome", "share", longLabel, "#2a60bd", 40);
                //ReadableMap icn = frame.getMap("icon");
                //Drawable o = generateVectorIcon(icn);
                //Bitmap bmp = drawableToBitmap(o);

                Icon icon = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    icon = Icon.createWithAdaptiveBitmap(bmp);
                } else {
                    icon = Icon.createWithBitmap(bmp);
                }

                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUri));
                if (frame.hasKey("setPackage")) appIntent.setPackage(setPackage);

                ShortcutInfo shortcut = new ShortcutInfo.Builder(mReactContext, "id" + i)
                        .setShortLabel(shortLabel)
                        .setLongLabel(longLabel)
                        .setIcon(icon)
                        .setIntent(appIntent)
                        .build();

                array.add(shortcut);
            }
            shortcutManager.setDynamicShortcuts(array);
        }
    }

    @TargetApi(21)
    //private Drawable generateVectorIcon(String family, String name, String glyph, String color, int size) {
    private Drawable generateVectorIcon(ReadableMap icon) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        Log.e("generateVectorIcon2","*"+icon.getMap("NativeMap"));
        //ReadableMap props = icon.getMap("NativeMap");
        /*Log.e("generateVectorIcon","/ok"+icon.hasKey("family"));
        String family = icon.getString("family");
        String name = icon.getString("name");
        String glyph = icon.getString("glyph");
        String color = icon.getString("color");
        int size = icon.getInt("size");*/

        String family = "FontAwesome";
        String name = "facebook";
        String glyph = "facebook";
        String color = "#000000";
        int size = 30;

        if (name != null && name.length() > 0 && name.contains(".")) {
            Resources resources = getReactApplicationContext().getResources();
            name = name.substring(0, name.lastIndexOf("."));

            final int resourceId = resources.getIdentifier(name, "drawable", getReactApplicationContext().getPackageName());
            return getReactApplicationContext().getDrawable(resourceId);
        }

        float scale = getReactApplicationContext().getResources().getDisplayMetrics().density;
        String scaleSuffix = "@" + (scale == (int) scale ? Integer.toString((int) scale) : Float.toString(scale)) + "x";
        int fontSize = Math.round(size * scale);

        Typeface typeface = ReactFontManager.getInstance().getTypeface(family, 0, getReactApplicationContext().getAssets());
        Paint paint = new Paint();
        paint.setTypeface(typeface);
        paint.setColor(Color.parseColor(color));
        paint.setTextSize(fontSize);
        paint.setAntiAlias(true);
        Rect textBounds = new Rect();
        paint.getTextBounds(glyph, 0, glyph.length(), textBounds);

        Bitmap bitmap = Bitmap.createBitmap(textBounds.width(), textBounds.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(glyph, -textBounds.left, -textBounds.top, paint);

        return new BitmapDrawable(getReactApplicationContext().getResources(), bitmap);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        int max = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(max, max, Bitmap.Config.ARGB_8888);
        //Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, max, max);
        //final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        //canvas.drawCircle(rect.left + (rect.width()/2), rect.top + (rect.height()/2), max / 2, paint);
        canvas.drawCircle(max / 2, max / 2, max / 2, paint);
        //canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    @Override
    public String getName() {
        return "Social";
    }

}
