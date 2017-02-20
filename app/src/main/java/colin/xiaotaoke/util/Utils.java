package colin.xiaotaoke.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.Random;

import colin.xiaotaoke.R;

/**
 * Created by Colin on 2017/2/16 14:16.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class Utils {
    /**
     * 随机获取颜色背景图
     *
     * @return
     */
    public static int getColor(int position) {
        int[] pic = {R.color.fg_pink, R.color.control_normal_light_blue, R.color.control_normal_teal, R.color.fg_deep_purple, R.color.control_normal_lime, R.color.control_normal_blue_grey};
        Random r = new Random();
        int num = r.nextInt(pic.length);
        return pic[num];
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return size; // return size/1048576;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "G";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "T";
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static ProgressDialog progressDialog;

    /**
     * 打开提示框
     *
     * @param activity
     * @param msg
     */
    public static void showDialog(Activity activity, String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(msg);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    /**
     * 关闭提示框
     */
    public static void closeDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.cancel();
                progressDialog = null;
                System.gc();
            }
        } catch (Exception e) {

        }
    }

}
