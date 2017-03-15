package colin.xiaotaoke;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import colin.xiaotaoke.bean.ProductDetailBean;
import colin.xiaotaoke.util.ApiTest;
import colin.xiaotaoke.util.GlobalUrl;
import colin.xiaotaoke.util.LogUtil;
import colin.xiaotaoke.util.ShareUtils;
import colin.xiaotaoke.view.ImageAdapter;
import colin.xiaotaoke.weight.LoopViewPager;
import me.relex.circleindicator.CircleIndicator;

import static colin.xiaotaoke.util.ApiTest.signTopRequest;


public class ProductDetailActivity extends BaseActivity {

    //请求更新显示的View
    protected static final int MSG_UPDATE_IMAGE = 1;
    //请求暂停轮播
    protected static final int MSG_KEEP_SILENT = 2;
    //请求恢复轮播
    protected static final int MSG_BREAK_SILENT = 3;
    protected static final int MSG_PAGE_CHANGED = 4;
    //轮播间隔时间
    protected static final long MSG_DELAY = 3000;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    LoopViewPager mViewPager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    @BindView(R.id.pro_title)
    TextView proTitle;
    @BindView(R.id.pro_price)
    TextView proPrice;
    @BindView(R.id.pro_oldprice)
    TextView proOldprice;
    @BindView(R.id.pro_tmall)
    TextView proTmall;
    @BindView(R.id.pro_taobao)
    TextView proTaobao;
    @BindView(R.id.pro_store)
    TextView proStore;
    @BindView(R.id.pro_location)
    TextView proLocation;
    @BindView(R.id.pro_sale)
    TextView proSale;
    @BindView(R.id.pro_coupon)
    TextView proCoupon;
    @BindView(R.id.btn_buy)
    RelativeLayout btnBuy;
    @BindView(R.id.pro_couponinfo)
    TextView proCouponInfo;
    String buyText;

    List<String> mImgs = new ArrayList<>();
    ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity productInfo;
    int currentItem = 0;
    ImageAdapter imgAdapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (handler.hasMessages(MSG_UPDATE_IMAGE)) {
                handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    mViewPager.setCurrentItem(currentItem);
                    //准备下次播放
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        btnBuy.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        productInfo = (ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity)
                getIntent().getSerializableExtra("productInfo");
        mImgs = productInfo.getSmall_images().getString();
        setProductImg(mImgs);//设置商品图片

        getSign(productInfo.getNum_iid());

    }

    //商品信息
    public void setProductImg(List<String> imgs) {
        if (imgAdapter == null) {
            imgAdapter = new ImageAdapter(ProductDetailActivity.this, imgs);
            mViewPager.setAdapter(imgAdapter);
            mIndicator.setViewPager(mViewPager);
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    handler.sendMessage(Message.obtain(handler, MSG_PAGE_CHANGED, position, 0));
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    switch (state) {
                        case ViewPager.SCROLL_STATE_DRAGGING:
                            handler.sendEmptyMessage(MSG_KEEP_SILENT);
                            break;
                        case ViewPager.SCROLL_STATE_IDLE:
                            handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                            break;
                        default:
                            break;
                    }
                }
            });
//            mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);//默认在中间，使用户看不到边界
            //开始轮播效果
            handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
        }

        proTitle.setText(productInfo.getTitle());
        proPrice.setText("$" + productInfo.getZk_final_price());
        proOldprice.setText("$" + productInfo.getReserve_price());
        proOldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线

        proStore.setText(productInfo.getShop_title());
        proLocation.setText(productInfo.getProvcity());
        proSale.setText("本月已售" + productInfo.getVolume());
        proCoupon.setText("优惠券剩余");
        if (productInfo.getUser_type() == 0) {
            proTaobao.setVisibility(View.VISIBLE);
            proTmall.setVisibility(View.GONE);
            proPrice.setTextColor(Color.parseColor("#ff5722"));
            buyText = "去淘宝下单";
            btnBuy.setBackground(this.getResources().getDrawable(R.drawable.radius_orange));
        } else {
            proTaobao.setVisibility(View.GONE);
            proTmall.setVisibility(View.VISIBLE);
            proPrice.setTextColor(Color.parseColor("#d32f2f"));
            buyText = "去天猫下单";
            btnBuy.setBackground(this.getResources().getDrawable(R.drawable.radius_oldred));
        }

        double priceOld = Double.parseDouble(productInfo.getReserve_price());
        double priceZk = Double.parseDouble(productInfo.getZk_final_price());
        double priceSale = priceOld - priceZk;
        double sale = (priceSale / priceOld) * 100;

//        proCouponInfo.setText("折扣优惠" + (int) sale + "%");
        proCouponInfo.setText(buyText);
    }

    //获取请求签名
    public void getSign(long numId) {
        try {
            Map<String, String> params = new HashMap<>();
            // 公共参数
            params.put("method", GlobalUrl.PRODUCT_ITEM_COUPON);
            params.put("app_key", GlobalUrl.TAOBAO_APP_KEY);
            params.put("session", "test");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("timestamp", df.format(new Date()));
            params.put("format", "json");
            params.put("v", "2.0");
            params.put("sign_method", "hmac");
            // 业务参数
            params.put("num_iids", String.valueOf(numId));
            params.put("pid", GlobalUrl.TAOBAO_PID);
            // 签名参数
            params.put("sign", signTopRequest(params, GlobalUrl.TAOBAO_APP_SECRET, "hmac"));

            String p = ApiTest.buildQuery(params, "UTF-8");

            requestServer(GlobalUrl.BASE_URL + p);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //优惠券信息
    public void requestServer(String url) {
        LogUtil.e("COUPON:" + url);
    }

    @Override
    protected void processClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buy:
                MobclickAgent.onEvent(this, "itme_detial");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(productInfo.getClick_url());
                intent.setData(content_url);
                startActivity(intent);
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.refresh:
                MobclickAgent.onEvent(this, "item_share");
                ShareUtils.sendText(this, productInfo.getTitle() + "\n原价" + productInfo.getReserve_price() + "\n折扣价" + productInfo.getZk_final_price() + "\n商品链接" + productInfo.getClick_url() + "\n分享自[" + this.getString(R.string.app_name) + "]安卓APP客户端，下载地址:http://fir.im/colinqua");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
