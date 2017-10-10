package com.nice.otis.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nice.otis.ui.FileUtils;
import com.nice.otis.view.GlideCircleTransform;

import java.io.File;
import java.lang.ref.WeakReference;

import static android.app.Activity.RESULT_OK;

/**
 * 作者：lv
 * 创建时间：10月11日
 * 时间：14:48
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class SinglePictureSelectHelper {

    private static final int CAMERA_REQUEST_CODE = 0x0000707;//开启相机的请求码

    private static final int ALBUM_REQUEST_CODE = 0x0000708;//开启相册的请求码

    //定义一个成员变量，用于保存相机拍照的图片
    private String capturePath = null;

    private WeakReference<Activity> mActivity;

    private static SinglePictureSelectHelper sInstance;

    private boolean isThumbnail = false;

    public synchronized static SinglePictureSelectHelper getInstance() {
        if (sInstance == null) {
            sInstance = new SinglePictureSelectHelper();
        }
        return sInstance;
    }

    public SinglePictureSelectHelper init(Activity activity) {
        mActivity = new WeakReference<>(activity);
        return sInstance;
    }

    /**
     * 开启本地相机
     * 这种方法获取到的图片是被压缩处理过的，所以会非常的不清晰，建议使用
     */
    @Deprecated
    public void openCamera() {
        if (mActivity.get() == null) return;
        isThumbnail = true;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mActivity.get().startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    /**
     * 开启相机
     */
    public void openCameraForFile() {
        if (mActivity.get() == null) return;

        isThumbnail = false;

        final Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //获取保存的路径
        final String out_file_path = FileUtils.getInstance().getFilePath(mActivity.get());
        final File dir = new File(out_file_path);
        openCamera(dir, out_file_path, getImageByCamera);

    }

    private void openCamera(File dir, String out_file_path, Intent getImageByCamera) {

        if (!dir.exists()) {
            dir.mkdirs();
        }
        //给成员变量赋值
        capturePath = out_file_path + File.separator + System.currentTimeMillis() + ".jpg";

        Log.d("pricture", "创建临时存储图片的位置是" + capturePath);
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, new File(capturePath).getAbsolutePath());
        Uri uri = mActivity.get().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);


        getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        mActivity.get().startActivityForResult(getImageByCamera, CAMERA_REQUEST_CODE);
    }

    /**
     * 开启本地相册
     */
    public void openAlbum() {
        if (mActivity.get() == null) return;
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.get().startActivityForResult(albumIntent, ALBUM_REQUEST_CODE);

    }

    public String onActivityResult(int requestCode, int resultCode, Intent data, ImageView showView, boolean isCircleImage) {
        Log.d("pricture", "在onActivityResult中Activity是否为空" + mActivity.get());
        if (mActivity.get() == null) return "";
        String imageUrl = "";
        switch (requestCode) {
            case CAMERA_REQUEST_CODE://相机的回调
                if (resultCode == RESULT_OK) {
                    if (isThumbnail) {
                        if (data != null && showView != null)
                            showView.setImageBitmap((Bitmap) data.getExtras().get("data"));
                        // Glide.with(this).load().into(mIvResultPhoto);
                    } else {

                        imageUrl = capturePath;
                        if (!TextUtils.isEmpty(capturePath)) {
                            if (showView != null) {
                                if (isCircleImage)
                                    Glide.with(mActivity.get()).load(new File(capturePath))
                                            .transform(new GlideCircleTransform(mActivity.get())).into(showView);
                                else
                                    Glide.with(mActivity.get()).load(new File(capturePath)).transform(new GlideCircleTransform(mActivity.get())).into(showView);
                            }
                        } else
                            Toast.makeText(mActivity.get(), "图片获取失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case ALBUM_REQUEST_CODE://相册的回掉
                if (resultCode == RESULT_OK) {
                    imageUrl = FileUtils.getRealFilePath(mActivity.get(), data.getData());
                    if (showView != null) {
                        if (isCircleImage)
                            Glide.with(mActivity.get()).load(imageUrl).into(showView);
                        else
                            Glide.with(mActivity.get()).load(imageUrl).into(showView);
                    }
                }
                break;
        }
        return imageUrl;
    }

}
