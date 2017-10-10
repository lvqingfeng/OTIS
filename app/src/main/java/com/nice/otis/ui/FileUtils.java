package com.nice.otis.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.nice.otis.R;
import com.nice.otis.utils.SdCardDirUtils;

import java.util.List;

/**
 * 作者：lv
 * 创建时间：09月26日
 * 时间：9:52
 * 版本：v1.0.0
 * 类描述：想要获取内存的路径，必须要在Appliction中去初始化该类，可以参见TestApt
 * 修改时间：
 */

public class FileUtils {

    private FileUtils() {
    }

    private static FileUtils sInstance;

    public synchronized static FileUtils getInstance() {
        if (sInstance == null) {
            sInstance = new FileUtils();
        }
        return sInstance;
    }

    public void init(Context context) {

        SharedPreferences sp = context.getSharedPreferences("file_path_shared", Context.MODE_PRIVATE);
        String filePath = sp.getString("filePath", "");

        if (TextUtils.isEmpty(filePath)) {

            List<SdCardDirUtils.FileHolder> fileHolderList = SdCardDirUtils.getExternalSdCardPath();
            String maxPath = "";
            long maxUsableSpace = 0;
            for (SdCardDirUtils.FileHolder holder : fileHolderList) {
                long usableSpace = holder.getUsableSpace();
                if (maxUsableSpace < usableSpace) {
                    maxUsableSpace = usableSpace;
                    maxPath = holder.getDirPath();
                }
            }
            SharedPreferences.Editor editor = sp.edit().putString("filePath", maxPath);
            editor.apply();
        }
    }

    public String getFilePath(Context context) {
        SharedPreferences sp = context.getSharedPreferences("file_path_shared", Context.MODE_PRIVATE);
        String filePath = sp.getString("filePath", "");
        return filePath + "/" + context.getString(R.string.app_name);
    }

    public String getTempFilePath(Context context) {
        return getFilePath(context) + "/temp/";
    }

    /**
     * 从Uri中获取绝对路径
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


}
