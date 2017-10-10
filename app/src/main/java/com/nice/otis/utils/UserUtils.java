package com.nice.otis.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.nice.otis.bean.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class UserUtils {

    private static final String SHARED_NAME = "otis_Shared";
    private static final String SHARE_PASSWORD = "otis_password";
    private static final String FILE_NAME = "otis.txt";

    /**
     * 保存user到本地文件
     *
     * @param context
     * @param user
     */
    public static boolean saveUser(Context context, User user) {

        //先判断该对象是否为空，不为空时直接删除
        if (getUser(context) != null) {
            quit(context);
        }
        try {
            FileOutputStream fos = new FileOutputStream(context.getFilesDir()
                    + "/" + FILE_NAME);
            ObjectOutputStream os = new ObjectOutputStream(fos);

            os.writeObject(user);
            os.flush();
            os.close();
            System.out.println("--------保存对象完成-------");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从本地文件获取user对象
     *
     * @param context
     * @return
     */
    public static User getUser(Context context) {
        User user = new User();
        try {
            FileInputStream fos = new FileInputStream(context.getFilesDir()
                    + "/" + FILE_NAME);
            ObjectInputStream os = new ObjectInputStream(fos);
            user = (User) os.readObject();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 清除本地user对象
     *
     * @param context
     * @return
     */
    public static boolean removeUser(Context context) {
        boolean isDelete = false;
        File file = new File(context.getFilesDir() + "/" + FILE_NAME);
        if (file.exists()) {
            isDelete = file.delete();
        }
        return isDelete;
    }

    public static boolean quit(Context context) {
        return removeUser(context);
    }

    public static void putUserName(Context context, String userName) {
        SharedPreferences preferences = context.getSharedPreferences(
                SHARED_NAME, Context.MODE_PRIVATE);
        Editor edit = preferences.edit();
        edit.putString("userName", userName);
        boolean flag = edit.commit();
        System.out.println("----是否保存成功----" + flag);
    }

    public static String getUserName(Context context) {

        SharedPreferences preferences = context.getSharedPreferences(
                SHARED_NAME, Context.MODE_PRIVATE);

        return preferences.getString("userName", "");

    }

    public static void setSavePassword(Context context, boolean isSave) {
        SharedPreferencesUtils.setParam(context, "is_save_password", isSave);
    }

    public static boolean isSavePassword(Context context) {
        return (boolean) SharedPreferencesUtils.getParam(context, "is_save_password", true);
    }

    public static void putUserPassword(Context context, String password) {
        SharedPreferencesUtils.setParam(context, SHARE_PASSWORD, password);
    }

    public static String getUserPassword(Context context) {
        if (isSavePassword(context))
            return (String) SharedPreferencesUtils.getParam(context, SHARE_PASSWORD, "");
        else
            return "";
    }
}
