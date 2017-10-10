package com.nice.otis.utils;

import android.os.Environment;

import java.io.File;

/**
 * 作者：吕振鹏
 * 创建时间：08月09日
 * 时间：17:19
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class SDCardUtils {
    public static boolean isMounted() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 判断如果存在该文件就删除
     *
     * @param filePath
     */
    public static boolean isCanDownloadFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        return true;
    }

    /**
     * 获取文件的存储路径
     *
     * @return
     */
    public static String getFileStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    }
}
