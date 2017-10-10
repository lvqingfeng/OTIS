package com.nice.otis.api;

import android.content.Context;
import android.util.Log;

import com.nice.otis.ui.FileUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Tongfang on 2017/7/7.
 */

public class ApiClient {
    /**
     * 获取请求请求网络用的Service
     *
     * @param tClass
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getApiService(Class<T> tClass, RetrofitManager.RetrofitType type) {
        return RetrofitManager.getInstance().getRetrofit(type).create(tClass);
    }

    public static <T> Map<String, String> createBodyMap(T t) {
        Class tClass = t.getClass();
        Map<String, String> bodyMap = new HashMap<>();
        try {
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);// 修改访问权限
                if (field.getType() == String.class)
                    bodyMap.put(field.getName(), field.get(t) == null ? "" : String.valueOf(field.get(t)));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bodyMap;
    }

    /**
     * 单图片上传的Body
     * 在{@link ApiService}中需要如下定义接口
     * ----@POST("Api/Hx/AddHx")
     * ----@Multipart
     * Observable<> uploadGroupCreate(@Part MultipartBody.Part photo);
     *
     * @param file
     * @return
     */
    public static MultipartBody.Part getFileBody(File file) {
        Log.d("需要上传图片的大小和路径", "图片大小：" + file.length() + "图片路径：" + file.getPath());

        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
        String fileName = file.getName();
        return MultipartBody.Part.createFormData("image", fileName, photoRequestBody);
    }

    /**
     * 多图片上传的body
     * 在{@link ApiService}中需要如下定义接口
     * <p>
     * ---- @POST("Api/MutualAid/launchMutualAid")
     * --- @Multipart
     * Observable<> launchMutualAid(@PartMap Map<String, RequestBody> params);
     * </p>
     *
     * @param imageList
     * @return
     */
    public static Map<String, RequestBody> getImageParams(String loadKey, List<String> imageList) {

        Map<String, RequestBody> params = new HashMap<>();
        if (imageList.size() == 1) {
            RequestBody photo = RequestBody.create(MediaType.parse("image/png"), new File(imageList.get(0)));
            params.put(loadKey + "\"; filename=\"icon.png", photo);
            return params;
        }
        for (int i = 0; i < imageList.size(); i++) {
            RequestBody photo = RequestBody.create(MediaType.parse("image/png"), new File(imageList.get(i)));
            params.put(loadKey + "[]\"; filename=\"icon" + i + ".png", photo);
        }
        return params;
    }

    public static Map<String, RequestBody> getVideoParam(String path) {
        String[] endName = path.split("\\.");
        Map<String, RequestBody> params = new HashMap<>();
        RequestBody photo = RequestBody.create(MediaType.parse("video/mp4"), new File(path));
        params.put("file\"; filename=\"icon." + endName[1], photo);
        return params;
    }


    /**
     * 图片压缩处理后进行上传
     *
     * @param file
     * @param listener
     */
    public static void pictureCompression(Context context, File file, OnCompressListener listener) {
        Luban.get(context)
                .load(file)                     //传人要压缩的图片
//                .putGear(Luban.THIRD_GEAR)//设定压缩档次，默认三挡
                .setFilename(FileUtils.getInstance().getTempFilePath(context) + System
                        .currentTimeMillis() + ".jpg")//设置上传后的名称
                .setCompressListener(listener).launch();
    }
}
