package colin.xiaotaoke.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Colin on 2017/3/8 20:58.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class ProductDetailBean implements Serializable {


    public TbkUatmFavoritesItemGetResponseEntity tbk_uatm_favorites_item_get_response;

    public TbkUatmFavoritesItemGetResponseEntity getTbk_uatm_favorites_item_get_response() {
        return tbk_uatm_favorites_item_get_response;
    }

    public void setTbk_uatm_favorites_item_get_response(TbkUatmFavoritesItemGetResponseEntity tbk_uatm_favorites_item_get_response) {
        this.tbk_uatm_favorites_item_get_response = tbk_uatm_favorites_item_get_response;
    }

    public static class TbkUatmFavoritesItemGetResponseEntity implements Serializable {

        public ResultsEntity results;
        public int total_results;
        public String request_id;

        public ResultsEntity getResults() {
            return results;
        }

        public void setResults(ResultsEntity results) {
            this.results = results;
        }

        public int getTotal_results() {
            return total_results;
        }

        public void setTotal_results(int total_results) {
            this.total_results = total_results;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }

        public static class ResultsEntity implements Serializable {
            public List<UatmTbkItemEntity> uatm_tbk_item;

            public List<UatmTbkItemEntity> getUatm_tbk_item() {
                return uatm_tbk_item;
            }

            public void setUatm_tbk_item(List<UatmTbkItemEntity> uatm_tbk_item) {
                this.uatm_tbk_item = uatm_tbk_item;
            }

            public static class UatmTbkItemEntity implements Serializable {

                public String click_url;
                public String event_end_time;
                public String event_start_time;
                public String item_url;
                public long num_iid;
                public String pict_url;
                public String provcity;
                public String reserve_price;
                public long seller_id;
                public String shop_title;
                public SmallImagesEntity small_images;
                public int status;
                public String title;
                public String tk_rate;
                public int type;
                public int user_type;
                public int volume;
                public String zk_final_price;
                public String zk_final_price_wap;

                public String getClick_url() {
                    return click_url;
                }

                public void setClick_url(String click_url) {
                    this.click_url = click_url;
                }

                public String getEvent_end_time() {
                    return event_end_time;
                }

                public void setEvent_end_time(String event_end_time) {
                    this.event_end_time = event_end_time;
                }

                public String getEvent_start_time() {
                    return event_start_time;
                }

                public void setEvent_start_time(String event_start_time) {
                    this.event_start_time = event_start_time;
                }

                public String getItem_url() {
                    return item_url;
                }

                public void setItem_url(String item_url) {
                    this.item_url = item_url;
                }

                public long getNum_iid() {
                    return num_iid;
                }

                public void setNum_iid(long num_iid) {
                    this.num_iid = num_iid;
                }

                public String getPict_url() {
                    return pict_url;
                }

                public void setPict_url(String pict_url) {
                    this.pict_url = pict_url;
                }

                public String getProvcity() {
                    return provcity;
                }

                public void setProvcity(String provcity) {
                    this.provcity = provcity;
                }

                public String getReserve_price() {
                    return reserve_price;
                }

                public void setReserve_price(String reserve_price) {
                    this.reserve_price = reserve_price;
                }

                public long getSeller_id() {
                    return seller_id;
                }

                public void setSeller_id(long seller_id) {
                    this.seller_id = seller_id;
                }

                public String getShop_title() {
                    return shop_title;
                }

                public void setShop_title(String shop_title) {
                    this.shop_title = shop_title;
                }

                public SmallImagesEntity getSmall_images() {
                    return small_images;
                }

                public void setSmall_images(SmallImagesEntity small_images) {
                    this.small_images = small_images;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getTk_rate() {
                    return tk_rate;
                }

                public void setTk_rate(String tk_rate) {
                    this.tk_rate = tk_rate;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getUser_type() {
                    return user_type;
                }

                public void setUser_type(int user_type) {
                    this.user_type = user_type;
                }

                public int getVolume() {
                    return volume;
                }

                public void setVolume(int volume) {
                    this.volume = volume;
                }

                public String getZk_final_price() {
                    return zk_final_price;
                }

                public void setZk_final_price(String zk_final_price) {
                    this.zk_final_price = zk_final_price;
                }

                public String getZk_final_price_wap() {
                    return zk_final_price_wap;
                }

                public void setZk_final_price_wap(String zk_final_price_wap) {
                    this.zk_final_price_wap = zk_final_price_wap;
                }

                public static class SmallImagesEntity  implements Serializable{
                    public List<String> string;

                    public List<String> getString() {
                        return string;
                    }

                    public void setString(List<String> string) {
                        this.string = string;
                    }
                }
            }
        }
    }
}
