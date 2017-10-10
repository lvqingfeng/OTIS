package com.nice.otis.utils;

import android.os.Environment;

import com.nice.otis.view.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：吕振鹏
 * 创建时间：08月09日
 * 时间：16:59
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class SdCardDirUtils {


    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */
    private static ArrayList<String> getDevMountList() {
        String path = FileUtils.readFile("/system/etc/vold.fstab");
        ArrayList<String> out = new ArrayList<String>();
        if (path != null) {
            String[] toSearch = path.split(" ");

            for (int i = 0; i < toSearch.length; i++) {
                if (toSearch[i].contains("dev_mount")) {
                    if (new File(toSearch[i + 2]).exists()) {
                        out.add(toSearch[i + 2]);
                    }
                }
            }
        }


        return out;
    }

    /**
     * 获取扩展SD卡存储目录
     * <p>
     * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     * 否则：返回内置SD卡目录
     *
     * @return
     */
    public static List<FileHolder> getExternalSdCardPath() {


        String path = null;


        ArrayList<FileHolder> fileHolderList = new ArrayList<>();
        ArrayList<String> devMountList = getDevMountList();

        for (String devMount : devMountList) {

            File file = new File(devMount);

            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();

                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                File testWritable = new File(path, "test_" + timeStamp);

                if (testWritable.mkdirs()) {
                    testWritable.delete();
                    addDirInfoToList(fileHolderList, file);
                }
            }
        }

        if (SDCardUtils.isMounted()) {
            File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            addDirInfoToList(fileHolderList, sdCardFile);
        }

        return fileHolderList;
    }

    private static void addDirInfoToList(List<FileHolder> fileHolderList, File sdCardFile) {

        FileHolder fileHolder = new FileHolder();
        fileHolder.name = sdCardFile.getName();
        fileHolder.dirPath = sdCardFile.getAbsolutePath();
        fileHolder.usableSpace = sdCardFile.getUsableSpace();
        fileHolder.totalSpace = sdCardFile.getTotalSpace();
        if (fileHolderList.contains(fileHolder))
            return;
        fileHolderList.add(fileHolder);
    }


    public static class FileHolder {
        private String dirPath;
        private String name;
        private long usableSpace;//可用大小
        private long totalSpace;//总大小

        public String getDirPath() {
            return dirPath;
        }

        public long getUsableSpace() {
            return usableSpace;
        }

        public long getTotalSpace() {
            return totalSpace;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (this == obj) {
                return true;
            }
            if (obj instanceof FileHolder) {
                FileHolder fileHolder = (FileHolder) obj;
                return (fileHolder.dirPath).equals(this.dirPath);
            }
            return false;
        }

        @Override
        public String toString() {
            return "当前存储卡的名称为[" + name + "]当前存储路径为[" + dirPath + "]可用大小为[" + usableSpace / 1024 / 1024 + "M]" + "]总大小为[" + ((float) totalSpace / 1024 / 1024 / 1024) + "G]";
        }
    }

}
