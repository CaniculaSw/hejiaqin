package com.customer.framework.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.channels.FileLock;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.customer.framework.component.log.Logger;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public final class FileUtil {
    private static final String TAG = "FileUtil";

    public static final String ATTACH_TYPE_PIC = "picture";
    public static final String ATTACH_TYPE_PDF = "pdf";
    public static final String ATTACH_TYPE_VIDEO = "video";
    public static final String ATTACH_TYPE_DOC = "doc";
    public static final String ATTACH_TYPE_PPT = "ppt";
    public static final String ATTACH_TYPE_XLS = "xls";
    public static final String ATTACH_TYPE_TXT = "txt";
    public static final String ATTACH_TYPE_RAR = "rar";
    public static final String ATTACH_TYPE_HTML = "html";
    public static final String ATTACH_TYPE_AUDIO = "audio";
    public static final String ATTACH_TYPE_FILE = "file";
    private static final int DEFAULT_READ_FILE_BUFFER_SIZE = 1024;

    private static final double MS_SIZE_IN_DOUBLE = 1024 * 1024.0;

    private static final long DEFAULT_MINUM_SDCARD_SIZE = 5;

    private static final String[][] MIME_MAPTABLE = { { ".3gp", "video/3gpp" },
            { ".apk", "application/vnd.android.package-archive" }, { ".asf", "video/video/x-ms-asf" },
            { ".avi", "video/x-msvideo" }, { ".bin", "application/octet-stream" }, { ".bmp", "image/bmp" },
            { ".c", "text/plain" }, { ".class", "application/octet-stream" }, { ".conf", "text/plain" },
            { ".cpp", "text/plain" }, { ".doc", "application/msword" },
            { ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
            { ".xls", "application/vnd.ms-excel" },
            { ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
            { ".exe", "application/octet-stream" }, { ".gif", "image/gif" }, { ".gtar", "application/x-gtar" },
            { ".gz", "application/x-gzip" }, { ".h", "text/plain" }, { ".htm", "text/plain" }, { ".html", "text/html" },
            { ".jar", "application/java-archive" }, { ".java", "text/plain" }, { ".jpeg", "image/jpeg" },
            { ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" }, { ".log", "text/plain" },
            { ".m3u", "audio/x-mpegurl" }, { ".m4a", "audio/mp4a-latm" }, { ".m4b", "audio/mp4a-latm" },
            { ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" },
            { ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" }, { ".mp3", "audio/x-mpeg" },
            { ".mp4", "video/mp4" }, { ".mpc", "application/vnd.mpohun.certificate" }, { ".mpe", "video/mpeg" },
            { ".mpeg", "video/mpeg" }, { ".mpg", "video/mpeg" }, { ".mpg4", "video/mp4" }, { ".mpga", "audio/mpeg" },
            { ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" },
            { ".png", "image/pnd" }, { ".pps", "application/vnd.ms-powerpoint" },
            { ".ppt", "application/vnd.ms-powerpoint" },
            { ".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation" },
            { ".prop", "text/plain" }, { ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" },
            { ".rtf", "application/rtf" }, { ".sh", "text/plain" }, { ".tar", "application/x-tar" },
            { ".tgz", "application/x-compressed" }, { ".txt", "text/plain" }, { ".wav", "audio/x-wav" },
            { ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" }, { ".wps", "application/vnd.ms-works" },
            { ".xml", "text/plain" }, { ".z", "application/x-compress" }, { ".zip", "application/x-zip-compressed" },
            { "", "*/*" } };

    private static boolean copyStream(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[1024 * 10];
            int count = 0;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
                Logger.i(TAG, "count=" + count);
            }
            out.flush();
            return true;
        } catch (Exception e) {
            Logger.e(TAG, "fileCopy failed", e);
        } finally {
            closeStream(in);
            closeStream(out);
        }
        return false;
    }

    public static boolean fileCopy(String srcFilePath, String dstFilePath) {
        if (srcFilePath == null || dstFilePath == null) {
            Logger.w(TAG, "[copyFile] file path is null");
            return false;
        }
        File originFile = new File(srcFilePath);
        File destFile = new File(dstFilePath);
        return fileCopy(originFile, destFile);
    }

    public static boolean fileCopy(int rid, Context context, String destFileName) {
        if (context == null || TextUtils.isEmpty(destFileName)) {
            return false;
        }
        try {
            InputStream is = context.getResources().openRawResource(rid);
            OutputStream os = context.openFileOutput(destFileName, Context.MODE_PRIVATE);
            return copyStream(is, os);
        } catch (FileNotFoundException e) {
            Logger.e(TAG, "ring file not exists");
        }

        return false;
    }

    public static boolean fileCopy(File srcFile, File destFile) {
        if (srcFile == null || destFile == null) {
            return false;
        }
        if (!srcFile.exists()) {
            Logger.i(TAG, "origin File is not exsit");
            return false;
        }
        File destParent = destFile.getParentFile();
        if (destParent != null && !destParent.exists()) {
            if (!destParent.mkdirs()) {
                Logger.i(TAG, "fileCopy failed when make dest dir: " + destParent.getPath());
                return false;
            }
        }
        if (destFile.exists()) {
            destFile.delete();
        }
        try {
            FileInputStream in = new FileInputStream(srcFile);
            FileOutputStream out = new FileOutputStream(destFile);
            return copyStream(in, out);
        } catch (FileNotFoundException e) {
            Logger.e(TAG, "fileCopy failed", e);
        }
        return false;
    }

    public static boolean moveFile(File origin, File dest) {
        if (origin == null || dest == null) {
            return false;
        }
        if (!origin.exists()) {
            Logger.i(TAG, "origin File is not exsit");
            return false;
        }
        File parentFile = dest.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            if (!parentFile.mkdirs()) {
                Logger.i(TAG, "copyFile failed, cause mkdirs return false");
                return false;
            }
        }
        if (dest.exists()) {
            dest.delete();
        }
        boolean isSuc = origin.renameTo(dest);
        if (!isSuc) {
            Logger.i(TAG, "origin.renameTo fail");
            if (fileCopy(origin, dest)) {
                return deleteFile(origin);
            }
        }
        return isSuc;
    }

    public static boolean deleteFile(String path) {
        if (path == null || path.trim().length() < 1) {
            return false;
        }
        return deleteFile(new File(path));
    }

    public static boolean deleteFile(File file) {
        try {
            if (file != null && file.exists() && file.isFile()) {
                try {
                    return file.delete();
                } catch (Exception e) {
                    Logger.e(TAG, "delete file error", e);
                    file.deleteOnExit();
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static void deleteAllFile(String folderFullPath) {
        if (folderFullPath == null || folderFullPath.trim().length() == 0) {
            return;
        }
        File file = new File(folderFullPath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                if (null != fileList) {
                    for (int i = 0; i < fileList.length; i++) {
                        String filePath = fileList[i].getPath();
                        deleteAllFile(filePath);
                    }
                }
            }
            if (file.isFile()) {
                try {
                    file.delete();
                } catch (Exception e) {
                    file.deleteOnExit();
                }
            }
        }
    }

    public static boolean isFileExists(String filePath) {
        if (filePath == null) {
            Logger.w(TAG, "[isFileExists]filePath is null");
            return false;
        }
        File file = getFileByPath(filePath);
        return file == null ? false : file.exists();
    }

    public static File createFile(String filePath) throws IOException {
        File file = getFileByPath(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                return null;
            }
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static File getFileByPath(String filePath) {
        if (filePath == null) {
            Logger.w(TAG, "[getFileByPath]filePath is null");
            return null;
        }
        filePath = filePath.replaceAll("\\\\", File.separator);
        boolean isSdcard = false;
        int subIndex = 0;
        if (filePath.indexOf("/sdcard") == 0) {
            isSdcard = true;
            subIndex = 7;
        } else if (filePath.indexOf("/mnt/sdcard") == 0) {
            isSdcard = true;
            subIndex = 11;
        }
        if (isSdcard) {
            if (isExternalStorageMounted()) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                String fileName = filePath.substring(subIndex);
                return new File(sdCardDir, fileName);
            }
            return null;
        } else {
            return new File(filePath);
        }
    }

    public static String getMIMEType(String fileName) {
        String type = "*/*";
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        String end = fileName.substring(dotIndex, fileName.length()).toLowerCase(Locale.US);
        if ("".equals(end)) {
            return type;
        }
        for (int i = 0; i < FileUtil.MIME_MAPTABLE.length; i++) {
            if (end.equals(FileUtil.MIME_MAPTABLE[i][0])) {
                type = MIME_MAPTABLE[i][1];
            }
        }
        return type;
    }

    public static String getAttachType(String fileName) {
        String fileSuffix = FileUtil.getFileSuffix(fileName).toLowerCase(Locale.US);
        if (fileSuffix.equals("png") || fileSuffix.equals("jpg") || fileSuffix.equals("jpeg")
                || fileSuffix.equals("bmp") || fileSuffix.equals("gif")) {
            return ATTACH_TYPE_PIC;
        } else if (fileSuffix.equals("psd") || fileSuffix.equals("tif")) {
            return ATTACH_TYPE_PIC;
        } else if (fileSuffix.equals("pdf")) {
            return ATTACH_TYPE_PDF;
        } else if (fileSuffix.equals("avi") || fileSuffix.equals("mov") || fileSuffix.equals("mp4")
                || fileSuffix.equals("dat") || fileSuffix.equals("rmvb")) {
            return ATTACH_TYPE_VIDEO;
        } else if (fileSuffix.equals("rm") || fileSuffix.equals("wmv") | fileSuffix.equals("3gp")
                || fileSuffix.equals("vob") || fileSuffix.equals("flv")) {
            return ATTACH_TYPE_VIDEO;
        } else if (fileSuffix.equals("dvd") || fileSuffix.equals("mpg") || fileSuffix.equals("swf")
                || fileSuffix.equals("video") || fileSuffix.equals("wmp")) {
            return ATTACH_TYPE_VIDEO;
        } else if (fileSuffix.equals("doc") || fileSuffix.equals("docx")) {
            return ATTACH_TYPE_DOC;
        } else if (fileSuffix.equals("ppt") || fileSuffix.equals("pptx")) {
            return ATTACH_TYPE_PPT;
        } else if (fileSuffix.equals("xls") || fileSuffix.equals("xlsx")) {
            return ATTACH_TYPE_XLS;
        } else if (fileSuffix.equals("txt")) {
            return ATTACH_TYPE_TXT;
        } else if (fileSuffix.equals("rar") || fileSuffix.equals("zip")) {
            return ATTACH_TYPE_RAR;
        } else if (fileSuffix.equals("html") || fileSuffix.equals("mht")) {
            return ATTACH_TYPE_HTML;
        } else if (fileSuffix.equals("mp3") || fileSuffix.equals("wma") || fileSuffix.equals("wav")
                || fileSuffix.equals("aac") || fileSuffix.equals("wav")) {
            return ATTACH_TYPE_AUDIO;
        } else {
            return ATTACH_TYPE_FILE;
        }
    }

    public static String getFileSuffix(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return fileName.substring(index + 1).toLowerCase(Locale.US);
        } else {
            return "";
        }
    }

    public static String getFileName(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int dotIdx = fileName.lastIndexOf(".");
        if (dotIdx != -1) {
            return fileName.substring(0, dotIdx);
        } else {

            return fileName;
        }
    }

    public static String getFileNameWithSuffix(String fileFullPath) {
        String fileName;
        if (fileFullPath == null || fileFullPath.trim().length() < 1) {
            fileName = null;
        }
        try {
            File file = new File(fileFullPath);
            fileName = file.getName();
        } catch (Exception e) {
            fileName = null;
        }
        return fileName;
    }

    public static long getFileSize(String fileFullPath) {
        if (fileFullPath == null || fileFullPath.trim().length() < 1) {
            return 0;
        }
        try {
            File file = new File(fileFullPath);
            return file.length();
        } catch (Exception e) {
            return 0;
        }
    }

    public static String getFileSizeString(String fileFullPath) {
        long fileLength = getFileSize(fileFullPath);
        return convertFileSize(fileLength);
    }

    public static String convertFileSize(long size) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        int kBSize = 1024;
        int bSize = 1;
        if (size >= MS_SIZE_IN_DOUBLE) {
            return decimalFormat.format(size / MS_SIZE_IN_DOUBLE) + "MB";
        } else if (size >= kBSize) {
            return Long.valueOf(size / kBSize) + "KB";
        } else if (size >= bSize) {
            return Long.valueOf(size / bSize) + "B";
        } else {
            return "0B";
        }
    }

    public static byte[] fileToByte(String fileName) {
        try {
            return fileToByte(new File(fileName));
        } catch (IOException e) {
            Logger.e(TAG, "fileToByte error", e);
        }
        return new byte[] {};
    }

    public static byte[] fileToByte(File file) throws IOException {
        return fileToByte(file, DEFAULT_READ_FILE_BUFFER_SIZE);
    }

    public static byte[] fileToByte(File file, int bufferLen) {
        InputStream in = null;
        ByteArrayOutputStream buffer = null;
        try {
            in = new FileInputStream(file);
            buffer = new ByteArrayOutputStream();
            byte[] barr = new byte[bufferLen];
            while (true) {
                int r = in.read(barr);
                if (r <= 0) {
                    break;
                }
                buffer.write(barr, 0, r);
            }
            buffer.flush();
            return buffer.toByteArray();
        } catch (OutOfMemoryError e) {
            Logger.e(TAG, "", e);
            return null;
        } catch (IOException e) {
            Logger.e(TAG, "", e);
            return null;
        } finally {
            closeStream(buffer);
            closeStream(in);
        }
    }

    public static File byteToFile(byte[] b, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        FileLock fileLock = null;
        Logger.d(TAG, "byteToFile: fileName = " + fileName);
        try {
            file = new File(fileName);
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (parent != null && !parent.exists() && !parent.mkdirs()) {
                    return null;
                }
            }
            fos = new FileOutputStream(file);
            fileLock = fos.getChannel().tryLock();
            if (fileLock != null) {
                bos = new BufferedOutputStream(fos);
                bos.write(b);
                bos.flush();
            }
        } catch (IOException e) {
            if (file != null) {
                file.delete();
                file = null;
            }
            Logger.e(TAG, "byteToFile error", e);
        } finally {
            if (fileLock != null) {
                try {
                    fileLock.release();
                } catch (IOException e) {
                    Logger.e(TAG, "fileLock.release() error", e);
                }
            }
            closeStream(bos);
            closeStream(fos);
        }
        return file;
    }

    public static void closeStream(Closeable beCloseStream) {
        if (beCloseStream != null) {
            try {
                beCloseStream.close();
            } catch (IOException e) {
                Logger.w(TAG, "close stream error", e);
            }
        }
    }

    public static String getNextFilePath(String path) {
        Pattern pattern = Pattern.compile("\\(\\d{1,}\\)\\.");
        Matcher matcher = pattern.matcher(path);
        String str = null;
        while (matcher.find()) {
            str = matcher.group(matcher.groupCount());
        }
        if (str == null) {
            int index = path.lastIndexOf(".");
            if (index != -1) {
                path = path.substring(0, index) + "(1)" + path.substring(index);
            } else {
                path += "(1)";
            }
        } else {
            int index = Integer.parseInt(str.replaceAll("[^\\d]*(\\d)[^\\d]*", "$1")) + 1;
            path = path.replace(str, "(" + index + ").");
        }
        return path;
    }

    public static boolean isPictureType(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return false;
        }
        String fileExt = getFileExtension(fileName);
        if (TextUtils.isEmpty(fileExt)) {
            return false;
        }
        if ("png".equals(fileExt) || "gif".equals(fileExt) || "jpg".equals(fileExt) || "bmp".equals(fileExt)
                || "jpeg".equals(fileExt)) {
            return true;
        }
        return false;
    }

    public static boolean isVideoType(String fileName) {
        String type = getFileExtension(fileName);
        return "mp4".equals(type) || "3gp".equals(type) || "rmvb".equals(type);
    }

    public static String getFileExtension(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return "";
        }
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return fileName.substring(index + 1).toLowerCase(Locale.US);
        } else {
            return "";
        }
    }

    public Object unserialize(String filePath) {
        Object obj = new Object();
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(filePath);
                ois = new ObjectInputStream(fis);
                obj = ois.readObject();
            } catch (Exception e) {
                Logger.e(TAG, "unserialize", e);
            } finally {
                closeStream(ois);
                closeStream(fis);
            }
        }
        return obj;
    }

    public void serialize(Object obj, String filePath) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            File pvStoreFile = new File(filePath);
            if (!pvStoreFile.exists()) {
                pvStoreFile.createNewFile();
            }
            fos = new FileOutputStream(filePath);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
        } catch (Exception e) {
            Logger.e(TAG, "serialize", e);
        } finally {
            closeStream(oos);
            closeStream(fos);
        }
    }

    public static boolean setNoMediaFlag(File dir) {
        if (dir == null) {
            Logger.e(TAG, "setNoMediaFlag dir is null");
            return false;
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File noMediaFile = new File(dir, ".nomedia");
        if (!noMediaFile.exists()) {
            try {
                return noMediaFile.createNewFile();
            } catch (IOException e) {
                Logger.e(TAG, "setNoMediaFlag", e);
                return false;
            }
        }
        return true;
    }

    public static String getFilePath(String fullPath) {
        File file = new File(fullPath);
        return file.getParent();
    }

    public static String appendPath(String path, String name) {
        if (TextUtils.isEmpty(path)) {
            return name;
        } else {
            return path + File.separator + name;
        }
    }

    public static boolean changeFileMode(String filePath, String mode) {
        String[] command = { "chmod", mode, filePath };
        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
            return true;
        } catch (IOException e) {
            Logger.e(TAG, "changeFileMode failed", e);
        }
        return false;
    }

    public static boolean sdcardHasEnoughStorage() {
        return sdcardHasEnoughStorage(DEFAULT_MINUM_SDCARD_SIZE);
    }

    public static boolean sdcardHasEnoughStorage(long minSize) {
        if (isExternalStorageMounted()) {
            try {
                String sdCardDir = Environment.getExternalStorageDirectory().getPath();
                StatFs statfs = new StatFs(sdCardDir);
                long nBlockSize = statfs.getBlockSize();
                long nAvailaBlock = statfs.getAvailableBlocks();
                long nSDFreeSize = nBlockSize * nAvailaBlock / 1024 / 1024;
                return nSDFreeSize > minSize;
            } catch (Exception e) {
                Logger.e(TAG, "check sdcard avalible size failed.", e);
            }
        }
        return false;
    }

    public static boolean makeDirInAppDir(Context context, String dir) {
        File f = context.getDir(dir, Context.MODE_PRIVATE | Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
        if (f.exists() || f.mkdirs()) {
            return changeFileMode(f.getAbsolutePath(), "777");
        }
        return false;
    }

    public static long getAvailableStorage(String path) {
        StatFs statFs = new StatFs(path);
        long blockSize = statFs.getBlockSize();
        long availaBlock = statFs.getAvailableBlocks();
        return availaBlock * blockSize;
    }

    public static long getTotalStorage(String path) {
        StatFs statFs = new StatFs(path);
        long blockSize = statFs.getBlockSize();
        long totalBlock = statFs.getAvailableBlocks();
        return totalBlock * blockSize;
    }

    public static long getExternalAvailableStorage() {
        return getAvailableStorage(Environment.getExternalStorageDirectory().getPath());
    }

    public static long getExternalTotalStorage() {
        return getTotalStorage(Environment.getExternalStorageDirectory().getPath());
    }

    public static long getInternalAvailableStorage() {
        return getAvailableStorage(Environment.getDataDirectory().getPath());
    }

    public static long getInternalTotalStorage() {
        return getTotalStorage(Environment.getDataDirectory().getPath());
    }

    public static boolean isExternalStorageMounted() {
        if (android.os.Build.MODEL.equals("sdk")) {
            return true;
        }
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getAppPackagePath(Context context) {
        return context.getFilesDir().getParent();
    }
}
