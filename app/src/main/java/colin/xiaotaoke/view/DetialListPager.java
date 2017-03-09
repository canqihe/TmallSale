package colin.xiaotaoke.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import colin.xiaotaoke.ApiTest;
import colin.xiaotaoke.ProductDetailActivity;
import colin.xiaotaoke.R;
import colin.xiaotaoke.bean.ProductDetailBean;
import colin.xiaotaoke.util.GlobalUrl;
import colin.xiaotaoke.util.LogUtil;
import colin.xiaotaoke.weight.XListView;

import static colin.xiaotaoke.ApiTest.signTopRequest;

/**
 * Created by Colin on 2017/2/16 12:16.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class DetialListPager extends BaseDetailPager implements XListView.IXListViewListener {

    @BindView(R.id.recycler)
    XListView mListView;
    @BindView(R.id.load_progress)
    ProgressBar mProgressBar;

    String mId;
    int pageNo = 1;
    ProductAdapter mProductAdapter;
    List<ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity> uatmTbkItemEntities = new ArrayList<>();

    public DetialListPager(Activity activity, String id) {
        super(activity);
        this.mId = id;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.detial_list, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        mListView.setPullRefreshEnable(false);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);

        mProductAdapter = new ProductAdapter(mActivity);
        mListView.setAdapter(mProductAdapter);
        getSign(pageNo);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity productInfo
                        = uatmTbkItemEntities.get(position - 1);
                Intent intent = new Intent(mActivity, ProductDetailActivity.class);
                intent.putExtra("productInfo", productInfo);
                mActivity.startActivity(intent);
            }
        });

    }


    public void requestServer(String url) {
        LogUtil.e("requestUrl:" + url);
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        mProgressBar.setVisibility(View.GONE);
                        mListView.stopLoadMore();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response) {
                        mListView.stopLoadMore();
                        mProgressBar.setVisibility(View.GONE);
                        try {
                            ProductDetailBean productDetailBean = gson.fromJson(response, ProductDetailBean.class);
                            List<ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity> entities
                                    = productDetailBean.getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item();
                            if (entities.size() > 0) {
                                uatmTbkItemEntities.addAll(entities);
                                mProductAdapter.updateData(uatmTbkItemEntities);
                                pageNo++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            pageNo = 1;
                            Toast.makeText(mActivity, "没有更多了。", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void getSign(int page) {
        try {
            Map<String, String> params = new HashMap<>();
            // 公共参数
            params.put("method", GlobalUrl.PRODUCT_LIST_ITEM);
            params.put("app_key", GlobalUrl.TAOBAO_APP_KEY);
            params.put("session", "test");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("timestamp", df.format(new Date()));
            params.put("format", "json");
            params.put("v", "2.0");
            params.put("sign_method", "hmac");
            // 业务参数
            params.put("fields", GlobalUrl.ITEM_DEITAL_FIELDS);
            params.put("page_no", String.valueOf(page));
            params.put("page_size", "20");
            params.put("favorites_id", mId);
            params.put("adzone_id", GlobalUrl.ADZONE_ID);
            // 签名参数
            params.put("sign", signTopRequest(params, GlobalUrl.TAOBAO_APP_SECRET, "hmac"));

            String p = ApiTest.buildQuery(params, "UTF-8");

            requestServer(GlobalUrl.BASE_URL + p);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        if (pageNo == 1) {
            mListView.stopLoadMore();
            return;
        }
        getSign(pageNo);
    }
}
