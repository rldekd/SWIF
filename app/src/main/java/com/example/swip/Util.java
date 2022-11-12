package com.example.swip;


import android.app.Activity;
import android.util.Patterns;
import android.widget.Toast;

import java.net.URLConnection;

public class Util {
    public Util(){
        /**/
    }
    public  static final int GALLERY_IMAGE = 0;
    public  static final int GALLERY_VIDEO = 1;

    public static final String INTENT_PATH = "path";
    public static final String INTENT_MEDIA = "media";

    public static void showToast(Activity activity, String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
    public static boolean isStorageUri(String uri){
        return Patterns.WEB_URL.matcher(uri).matches() && uri.contains("https://firebasestorage.googleapis.com/v0/b/simple-snsproject.appspot.com/o/post");
    }

    public static String storageUriToName(String uri){
        return uri.split("\\?")[0].split("%2F")[uri.split("\\?")[0].split("%2F").length-1];
    }

    public static boolean isImageFile(String path){
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isVideoFile(String path){
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }
}

