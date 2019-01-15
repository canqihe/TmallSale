package colin.xiaotaoke.util;

/**
 * Created by Colin on 2017/3/8 12:54.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class GlobalUrl {

    public static String BASE_URL = "http://gw.api.taobao.com/router/rest?";

    public static String ADZONE_ID = "73598870";

    public static String TAOBAO_PID = "mm_120988261_22098240_73598870";

    public static String TAOBAO_APP_KEY = "23668193";
    public static String TAOBAO_APP_SECRET = "bcd1a5b09dab18e55f2e3757944aadf0";

    //(获取淘宝联盟选品库列表)
    public static String PRODUCT_LIST = "taobao.tbk.uatm.favorites.get";
    //(获取淘宝联盟选品库的宝贝信息)
    public static String PRODUCT_LIST_ITEM = "taobao.tbk.uatm.favorites.item.get ";
    // (根据nid批量查询优惠券)
    public static String PRODUCT_ITEM_COUPON = "taobao.tbk.itemid.coupon.get";

    public static String ITEM_DEITAL_FIELDS = "num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,click_url,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type";

}
