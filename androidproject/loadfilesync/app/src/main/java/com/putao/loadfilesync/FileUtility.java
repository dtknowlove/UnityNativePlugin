package com.putao.loadfilesync;

import android.os.Environment;
import android.os.StatFs;

import com.unity3d.player.UnityPlayer;

import java.io.File;
import java.io.InputStream;

/**
 * Created by admin on 2019/3/29.
 */

public class FileUtility {

    public static String loadFile(String path) {
        try {
            InputStream is = UnityPlayer.currentActivity.getAssets().open(path);
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            String result = new String(buffer, "utf8");
            is.close();
            return result;
        } catch (Throwable e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    public static long getFreeDiskSpace() {
        try {
            File file = Environment.getDataDirectory();
            StatFs sf = new StatFs(file.getPath());
            return sf.getAvailableBytes() / (1024 * 1024);
        } catch (Throwable e) {
            System.out.println(e.getLocalizedMessage());
        }
        return 1024;
    }
}
