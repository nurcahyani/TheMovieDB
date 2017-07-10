package com.udacity.nyur.themoviedb.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.support.compat.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Desy on 7/9/2017.
 */

public class Util extends Activity{
    public static String getProperty(String key,Context context) throws IOException {

        Properties properties = new Properties();;
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("app.properties");

        properties.load(inputStream);
        return properties.getProperty(key);

    }
}