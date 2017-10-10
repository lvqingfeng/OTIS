package com.nice.otis.utils;

import android.content.Context;

import com.nice.otis.bean.User;


/**
 * 作者：lv
 * 创建时间：10月21日
 * 时间：15:31
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class LoginHelper {

    private static LoginHelper sInstance;
    private Context mContext;
    private User mUserBean;
    private boolean isOnline;

    private LoginHelper() {
    }

    public static LoginHelper getInstance() {
        if (sInstance == null) {
            sInstance = new LoginHelper();
        }
        return sInstance;
    }

    public LoginHelper init(Context context) {
        mContext = context;
        isOnline = checkIsOnline();
        return this;
    }

    public boolean checkIsOnline() {
        boolean isOnline;
        User user = UserUtils.getUser(mContext);
        System.out.println("---获取到的用户的key--" + user.getToken());
       if (user.getToken() != null) {
           isOnline = true;
            System.out.println("--用户id为" + user.getToken());
        } else {
            System.out.println("--用户对象为空");
           isOnline = false;
        }

       if (isOnline) {
            mUserBean = user;
        }
       return isOnline;
    }


    public boolean userExit() {
        mUserBean = null;
        return UserUtils.quit(mContext);
    }

    public User getUserBean() {
        return mUserBean;
    }

    public String getUserToken() {
        if (mUserBean != null)
            return mUserBean.getToken();
        return "";
    }
    public String getUserId(){
        if (mUserBean != null)
            return mUserBean.getUid();
        return "";
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        if (online) {
            mUserBean = UserUtils.getUser(mContext);
        } else {
            userExit();
        }
        isOnline = online;
    }


}
