package com.zappi.android.function;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
import android.util.DisplayMetrics;
import android.util.Log;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.views.text.ReactFontManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PinnedShortcuts extends ReactContextBaseJavaModule {
    private ReactContext mReactContext;
    ShortcutManager shortcutManager;

    static final String REACT_NAME = "PinnedShortcuts";
    private static final String ACTION_SHORTCUT = "ACTION_SHORTCUT";
    private static final String SHORTCUT_ITEM = "SHORTCUT_ITEM";

    public PinnedShortcuts(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mReactContext = reactContext;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
            shortcutManager = mReactContext.getSystemService(ShortcutManager.class);
    }

    @ReactMethod
    public void addPinnedShortcuts(ReadableArray map) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {

            List<ShortcutInfo> array = new ArrayList<ShortcutInfo>();
            for (int i = 0; i < map.size(); i++) {
                ReadableMap frame = map.getMap(i);
                String urlImg = frame.hasKey("urlImg") ? frame.getString("urlImg") : "";
                String shortLabel = frame.hasKey("shortLabel") ? frame.getString("shortLabel") : "DiCaprio";
                String longLabel = frame.hasKey("longLabel") ? frame.getString("longLabel") : "Leonardo Wilhelm DiCaprio";
                String appUri = frame.hasKey("appUri") ? frame.getString("appUri") : "https://www.google.com";
                String setPackage = frame.hasKey("setPackage") ? frame.getString("setPackage") : "";
                String typeImg = frame.hasKey("typeImg") ? frame.getString("typeImg") : "letter";
                String colorText = frame.hasKey("colorText") ? frame.getString("colorText") : "#ffffff";
                String colorCircle = frame.hasKey("colorCircle") ? frame.getString("colorCircle") : "#64B5F6";
                Bitmap bmp = null;

                String typeIntent = frame.hasKey("typeIntent") ? frame.getString("typeIntent") : "";

                if (typeImg.equals("url")) {
                    URL urlP = null;
                    try {
                        urlP = new URL(urlImg);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    try {
                        bmp = BitmapFactory.decodeStream(urlP.openConnection().getInputStream());
                        bmp = scale(bmp);
                        bmp = getCroppedBitmap(bmp);
                    } catch (IOException e) {
                        bmp = generateIconLetter(mReactContext, longLabel, colorText, colorCircle);
                        e.printStackTrace();
                    }
                } else if (typeImg.equals("icon")) {
                    ReadableMap icn = frame.getMap("icon");
                    if (icn.hasKey("name") && !icn.isNull("name")) {
                        Drawable o = generateVectorIcon(icn);
                        bmp = drawableToBitmap(o);
                    } else
                        bmp = generateIconLetter(mReactContext, longLabel, colorText, colorCircle);
                } else {
                    bmp = generateIconLetter(mReactContext, longLabel, colorText, colorCircle);
                }

                Icon icon = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    icon = Icon.createWithAdaptiveBitmap(bmp);
                } else {
                    icon = Icon.createWithBitmap(bmp);
                }

                Intent appIntent;

                if (typeIntent.equals("uri")) {
                    appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUri));
                    if (frame.hasKey("setPackage")) appIntent.setPackage(setPackage);
                } else {
                    appIntent = new Intent(mReactContext, getCurrentActivity().getClass());
                    appIntent.setAction(ACTION_SHORTCUT);

                    if (frame.hasKey("infoIntent")) {
                        ReadableMap infoIntent = frame.getMap("infoIntent");
                        try {
                            JSONObject oo = convertMapToJson(infoIntent);
                            appIntent.putExtra(SHORTCUT_ITEM, oo.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

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

    @ReactMethod
    @TargetApi(25)
    public void popInitialAction(Promise promise) {
        try {
            Activity currentActivity = getCurrentActivity();
            WritableMap map = new WritableNativeMap();

            if (currentActivity != null) {
                Intent intent = currentActivity.getIntent();

                if (ACTION_SHORTCUT.equals(intent.getAction())) {
                    String obj = intent.getStringExtra(SHORTCUT_ITEM);
                    JSONObject mJsonObject = (obj != null) ? new JSONObject(obj) : new JSONObject();
                    map.putMap("obj", convertJsonToMap(mJsonObject));
                }
            }

            promise.resolve(map);
        } catch (Exception e) {
            promise.reject(new JSApplicationIllegalArgumentException("AppShortcuts.popInitialAction error. " + e.getMessage()));
        }
    }

    public Bitmap scale(Bitmap bitmap) {
        int max = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
        Bitmap scaledBitmap = Bitmap.createBitmap(max, max, Bitmap.Config.ARGB_8888);
        float ratioX = max / (float) bitmap.getWidth();
        float ratioY = max / (float) bitmap.getHeight();
        float middleX = max / 2.0f;
        float middleY = max / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
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
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, max / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    @TargetApi(21)
    private Drawable generateVectorIcon(ReadableMap icon) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String family = icon.hasKey("family") ? icon.getString("family") : "MaterialIcons";
        String name = icon.hasKey("name") ? icon.getString("name") : "face";
        String glyph = icon.hasKey("glyph") ? icon.getString("glyph") : " ";
        String colorIcon = icon.hasKey("colorIcon") ? icon.getString("colorIcon") : "#ffffff";
        String colorCircle = icon.hasKey("colorCircle") ? icon.getString("colorCircle") : "#64B5F6";

        if (name != null && name.length() > 0 && name.contains(".")) {
            Resources resources = getReactApplicationContext().getResources();
            name = name.substring(0, name.lastIndexOf("."));
            final int resourceId = resources.getIdentifier(name, "drawable", getReactApplicationContext().getPackageName());
            return getReactApplicationContext().getDrawable(resourceId);
        }

        Typeface typeface = ReactFontManager.getInstance().getTypeface(family, 0, getReactApplicationContext().getAssets());

        float diameterDP = 80;
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float diameterPixels = diameterDP * (metrics.densityDpi / 160f);
        float radiusPixels = diameterPixels / 2;

        // Create the bitmap
        Bitmap output = Bitmap.createBitmap((int) diameterPixels, (int) diameterPixels, Bitmap.Config.ARGB_8888);

        // Create the canvas to draw on
        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);

        // Draw the circle
        final Paint paintC = new Paint();
        paintC.setAntiAlias(true);
        paintC.setColor(Color.parseColor(colorCircle));
        canvas.drawCircle(radiusPixels, radiusPixels, radiusPixels, paintC);

        final Paint paintT = new Paint();
        paintT.setTypeface(typeface);
        paintT.setColor(Color.parseColor(colorIcon));
        paintT.setTextSize(radiusPixels);
        paintT.setAntiAlias(true);
        final Rect textBounds = new Rect();
        paintT.getTextBounds(glyph, 0, glyph.length(), textBounds);

        canvas.drawText(glyph, radiusPixels - textBounds.exactCenterX(), radiusPixels - textBounds.exactCenterY(), paintT);

        return new BitmapDrawable(getReactApplicationContext().getResources(), output);
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) return ((BitmapDrawable) drawable).getBitmap();

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return output;
    }

    public Bitmap generateIconLetter(Context context, String longLabel, String colorText, String colorCircle) {
        String name = "";
        String[] splitLetter = longLabel.split(" ");
        for (String el : splitLetter) name += String.valueOf(el.charAt(0)).toUpperCase();
        return generateCircleBitmap(context, Color.parseColor(colorText), Color.parseColor(colorCircle), 80, name);
    }

    public Bitmap generateCircleBitmap(Context context, int colorText, int colorCircle, float diameterDP, String text) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float diameterPixels = diameterDP * (metrics.densityDpi / 160f);
        float radiusPixels = diameterPixels / 2;

        // Create the bitmap
        Bitmap output = Bitmap.createBitmap((int) diameterPixels, (int) diameterPixels, Bitmap.Config.ARGB_8888);

        // Create the canvas to draw on
        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);

        // Draw the circle
        final Paint paintC = new Paint();
        paintC.setAntiAlias(true);
        paintC.setColor(colorCircle);
        canvas.drawCircle(radiusPixels, radiusPixels, radiusPixels, paintC);

        // Draw the text
        if (text != null && text.length() > 0) {
            final Paint paintT = new Paint();
            paintT.setColor(colorText);
            paintT.setAntiAlias(true);
            paintT.setTextSize(radiusPixels / 2);
            paintT.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            final Rect textBounds = new Rect();
            paintT.getTextBounds(text, 0, text.length(), textBounds);
            canvas.drawText(text, radiusPixels - textBounds.exactCenterX(), radiusPixels - textBounds.exactCenterY(), paintT);
        }

        return output;
    }

    private static WritableMap convertJsonToMap(JSONObject jsonObject) throws JSONException {
        WritableMap map = new WritableNativeMap();
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.putMap(key, convertJsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                map.putArray(key, convertJsonToArray((JSONArray) value));
            } else if (value instanceof Boolean) {
                map.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                map.putInt(key, (Integer) value);
            } else if (value instanceof Double) {
                map.putDouble(key, (Double) value);
            } else if (value instanceof String) {
                map.putString(key, (String) value);
            } else {
                map.putString(key, value.toString());
            }
        }
        return map;
    }

    private static WritableArray convertJsonToArray(JSONArray jsonArray) throws JSONException {
        WritableArray array = new WritableNativeArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                array.pushMap(convertJsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                array.pushArray(convertJsonToArray((JSONArray) value));
            } else if (value instanceof Boolean) {
                array.pushBoolean((Boolean) value);
            } else if (value instanceof Integer) {
                array.pushInt((Integer) value);
            } else if (value instanceof Double) {
                array.pushDouble((Double) value);
            } else if (value instanceof String) {
                array.pushString((String) value);
            } else {
                array.pushString(value.toString());
            }
        }
        return array;
    }

    private static JSONObject convertMapToJson(ReadableMap readableMap) throws JSONException {
        JSONObject object = new JSONObject();
        ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            switch (readableMap.getType(key)) {
                case Null:
                    object.put(key, JSONObject.NULL);
                    break;
                case Boolean:
                    object.put(key, readableMap.getBoolean(key));
                    break;
                case Number:
                    object.put(key, readableMap.getDouble(key));
                    break;
                case String:
                    object.put(key, readableMap.getString(key));
                    break;
                case Map:
                    object.put(key, convertMapToJson(readableMap.getMap(key)));
                    break;
                case Array:
                    object.put(key, convertArrayToJson(readableMap.getArray(key)));
                    break;
            }
        }
        return object;
    }

    private static JSONArray convertArrayToJson(ReadableArray readableArray) throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < readableArray.size(); i++) {
            switch (readableArray.getType(i)) {
                case Null:
                    break;
                case Boolean:
                    array.put(readableArray.getBoolean(i));
                    break;
                case Number:
                    array.put(readableArray.getDouble(i));
                    break;
                case String:
                    array.put(readableArray.getString(i));
                    break;
                case Map:
                    array.put(convertMapToJson(readableArray.getMap(i)));
                    break;
                case Array:
                    array.put(convertArrayToJson(readableArray.getArray(i)));
                    break;
            }
        }
        return array;
    }

    @Override
    public String getName() {
        return REACT_NAME;
    }

}
