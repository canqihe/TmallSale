package colin.xiaotaoke.util;

/**
 * Created by Colin on 2017/3/8 13:01.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class SysConfig {
    /**
     * 是否是测试
     */
    public static final boolean isTest = true;
    /**
     * 是否是测试数据库环境(供发测试版本使用，使用对象，测试人员)
     */
    public static final boolean isTestEnvironment = true;
    /**
     * 是否显示log日志
     */
    public static final boolean isShowLog = true;
    /**
     * 是否写入SD卡：需要本地路径并且设置开始和结束
     */
    public static final boolean isWriteLogToSDcard = false;
    /**
     * 是否上传到网络:需要网络服务器地址并且设置开始和结束
     */
    public static final boolean isUploadLog = false;
    /**
     * 上传服务器的任务数据是否加密
     */
    public static final boolean isEncoded = false;
    /**
     * 友盟测试开关
     */
    public static final boolean isUMengTest = false;
    /**
     * 友盟埋码开关
     */
    public static final boolean UMengOpen = true;
    /**
     * 本地数据库是否加密
     */
    public static final boolean isEncodedDB = true;
    /**
     * 是否采用系统视频录制工具
     */
    public static final boolean isSysCamera = true;
    /**
     * 是否采用分块上传
     */
    public static final boolean isPart = false;
    /**
     * 上报时是否判断距离
     */
    public static final boolean isCalDis = false;
}
