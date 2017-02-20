package colin.xiaotaoke.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Colin on 2017/2/16 12:20.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class CommodityBean implements Serializable {

    /**
     * data : {"api_type":"领券优惠v1.4","update_time":"2017/02/16 14:26","total_num":1,"api_content":"android专用API数据接口","result":[{"GoodsID":"540082894262","Title":"小猪奕奕婴儿爬行垫加厚双面回纹儿童宝宝爬爬垫泡沫垫子野餐地垫","D_title":"儿童婴儿双面回纹加厚【大尺寸爬行垫】","Pic":"http://img.alicdn.com/imgextra/i3/TB1UcKlOpXXXXa5XVXXXXXXXXXX_!!0-item_pic.jpg","Cid":"2","Org_Price":"25.90","Price":15.9,"IsTmall":"1","Sales_num":"11428","Dsr":"4.8","SellerID":"2261963021","Commission_jihua":"40.50","Commission_queqiao":"0.00","Jihua_link":"http://pub.alimama.com/myunion.htm?spm=a220o.1000855.0.0.CgsJyb#!/promo/self/campaign?campaignId=35581588&shopkeeperId=99992423&userNumberId=2261963021","Introduce":"韩国进口材质，双面精致包边，绿色环保无毒无害，防滑易清洗，宝宝的游戏天地，妈妈放心~","Quan_id":"791e7cde430840acb0e2cc4266366a61","Quan_price":"10.00","Quan_time":"2017-02-17 00:00:00","Quan_surplus":"32166","Quan_receive":"47834","Quan_condition":"单笔满25元可用，每人限领1 张","Quan_link":"http://shop.m.taobao.com/shop/coupon.htm?seller_id=2261963021&activity_id=791e7cde430840acb0e2cc4266366a61","Quan_m_link":"http://dwz.cn/5h9OL7","ali_click":"https://detail.tmall.com/item.htm?id=540082894262"}]}
     */

    public DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * api_type : 领券优惠v1.4
         * update_time : 2017/02/16 14:26
         * total_num : 1
         * api_content : android专用API数据接口
         * result : [{"GoodsID":"540082894262","Title":"小猪奕奕婴儿爬行垫加厚双面回纹儿童宝宝爬爬垫泡沫垫子野餐地垫","D_title":"儿童婴儿双面回纹加厚【大尺寸爬行垫】","Pic":"http://img.alicdn.com/imgextra/i3/TB1UcKlOpXXXXa5XVXXXXXXXXXX_!!0-item_pic.jpg","Cid":"2","Org_Price":"25.90","Price":15.9,"IsTmall":"1","Sales_num":"11428","Dsr":"4.8","SellerID":"2261963021","Commission_jihua":"40.50","Commission_queqiao":"0.00","Jihua_link":"http://pub.alimama.com/myunion.htm?spm=a220o.1000855.0.0.CgsJyb#!/promo/self/campaign?campaignId=35581588&shopkeeperId=99992423&userNumberId=2261963021","Introduce":"韩国进口材质，双面精致包边，绿色环保无毒无害，防滑易清洗，宝宝的游戏天地，妈妈放心~","Quan_id":"791e7cde430840acb0e2cc4266366a61","Quan_price":"10.00","Quan_time":"2017-02-17 00:00:00","Quan_surplus":"32166","Quan_receive":"47834","Quan_condition":"单笔满25元可用，每人限领1 张","Quan_link":"http://shop.m.taobao.com/shop/coupon.htm?seller_id=2261963021&activity_id=791e7cde430840acb0e2cc4266366a61","Quan_m_link":"http://dwz.cn/5h9OL7","ali_click":"https://detail.tmall.com/item.htm?id=540082894262"}]
         */

        public String api_type;
        public String update_time;
        public int total_num;
        public String api_content;
        public List<ResultEntity> result;

        public String getApi_type() {
            return api_type;
        }

        public void setApi_type(String api_type) {
            this.api_type = api_type;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getTotal_num() {
            return total_num;
        }

        public void setTotal_num(int total_num) {
            this.total_num = total_num;
        }

        public String getApi_content() {
            return api_content;
        }

        public void setApi_content(String api_content) {
            this.api_content = api_content;
        }

        public List<ResultEntity> getResult() {
            return result;
        }

        public void setResult(List<ResultEntity> result) {
            this.result = result;
        }

        public static class ResultEntity implements Serializable {

            public String GoodsID;
            public String Title;
            public String D_title;
            public String Pic;
            public String Cid;
            public String Org_Price;
            public double Price;
            public String IsTmall;
            public String Sales_num;
            public String Dsr;
            public String SellerID;
            public String Commission_jihua;
            public String Commission_queqiao;
            public String Jihua_link;
            public String Introduce;
            public String Quan_id;
            public String Quan_price;
            public String Quan_time;
            public String Quan_surplus;
            public String Quan_receive;
            public String Quan_condition;
            public String Quan_link;
            public String Quan_m_link;
            public String ali_click;

            public String getGoodsID() {
                return GoodsID;
            }

            public void setGoodsID(String GoodsID) {
                this.GoodsID = GoodsID;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getD_title() {
                return D_title;
            }

            public void setD_title(String D_title) {
                this.D_title = D_title;
            }

            public String getPic() {
                return Pic;
            }

            public void setPic(String Pic) {
                this.Pic = Pic;
            }

            public String getCid() {
                return Cid;
            }

            public void setCid(String Cid) {
                this.Cid = Cid;
            }

            public String getOrg_Price() {
                return Org_Price;
            }

            public void setOrg_Price(String Org_Price) {
                this.Org_Price = Org_Price;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public String getIsTmall() {
                return IsTmall;
            }

            public void setIsTmall(String IsTmall) {
                this.IsTmall = IsTmall;
            }

            public String getSales_num() {
                return Sales_num;
            }

            public void setSales_num(String Sales_num) {
                this.Sales_num = Sales_num;
            }

            public String getDsr() {
                return Dsr;
            }

            public void setDsr(String Dsr) {
                this.Dsr = Dsr;
            }

            public String getSellerID() {
                return SellerID;
            }

            public void setSellerID(String SellerID) {
                this.SellerID = SellerID;
            }

            public String getCommission_jihua() {
                return Commission_jihua;
            }

            public void setCommission_jihua(String Commission_jihua) {
                this.Commission_jihua = Commission_jihua;
            }

            public String getCommission_queqiao() {
                return Commission_queqiao;
            }

            public void setCommission_queqiao(String Commission_queqiao) {
                this.Commission_queqiao = Commission_queqiao;
            }

            public String getJihua_link() {
                return Jihua_link;
            }

            public void setJihua_link(String Jihua_link) {
                this.Jihua_link = Jihua_link;
            }

            public String getIntroduce() {
                return Introduce;
            }

            public void setIntroduce(String Introduce) {
                this.Introduce = Introduce;
            }

            public String getQuan_id() {
                return Quan_id;
            }

            public void setQuan_id(String Quan_id) {
                this.Quan_id = Quan_id;
            }

            public String getQuan_price() {
                return Quan_price;
            }

            public void setQuan_price(String Quan_price) {
                this.Quan_price = Quan_price;
            }

            public String getQuan_time() {
                return Quan_time;
            }

            public void setQuan_time(String Quan_time) {
                this.Quan_time = Quan_time;
            }

            public String getQuan_surplus() {
                return Quan_surplus;
            }

            public void setQuan_surplus(String Quan_surplus) {
                this.Quan_surplus = Quan_surplus;
            }

            public String getQuan_receive() {
                return Quan_receive;
            }

            public void setQuan_receive(String Quan_receive) {
                this.Quan_receive = Quan_receive;
            }

            public String getQuan_condition() {
                return Quan_condition;
            }

            public void setQuan_condition(String Quan_condition) {
                this.Quan_condition = Quan_condition;
            }

            public String getQuan_link() {
                return Quan_link;
            }

            public void setQuan_link(String Quan_link) {
                this.Quan_link = Quan_link;
            }

            public String getQuan_m_link() {
                return Quan_m_link;
            }

            public void setQuan_m_link(String Quan_m_link) {
                this.Quan_m_link = Quan_m_link;
            }

            public String getAli_click() {
                return ali_click;
            }

            public void setAli_click(String ali_click) {
                this.ali_click = ali_click;
            }
        }
    }
}
