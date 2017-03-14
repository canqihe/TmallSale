package colin.xiaotaoke.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import colin.xiaotaoke.ProductDetailActivity;
import colin.xiaotaoke.R;
import colin.xiaotaoke.adapter.ProductRecyAdapter;
import colin.xiaotaoke.bean.ProductDetailBean;
import colin.xiaotaoke.util.ApiTest;
import colin.xiaotaoke.util.GlobalUrl;
import colin.xiaotaoke.util.LogUtil;
import colin.xiaotaoke.weight.RecyclerViewLinearListener;
import colin.xiaotaoke.weight.RefreshLayoutUtils;

import static colin.xiaotaoke.util.ApiTest.signTopRequest;

/**
 * Created by Colin on 2017/2/16 12:16.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class DetialListRecyPager extends BaseDetailPager implements RecyclerViewLinearListener.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, ProductRecyAdapter.OnItemClickLitener {
    @BindView(R.id.load_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.small_progress)
    ProgressBar smallBar;

    String mId;
    int pageNo = 1;
    List<ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity> uatmTbkItemEntities = new ArrayList<>();
    GridLayoutManager mLayoutManager;//创建一个布局
    ProductRecyAdapter mProductRecyAdapter;
    String loadUrl;
    ProductDetailBean productDetailBean;

    public DetialListRecyPager(Activity activity, String id) {
        super(activity);
        this.mId = id;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.detial_recy_list, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        mLayoutManager = new GridLayoutManager(mActivity, 2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);//设置线性的管理器！
        recyclerView.setBackgroundColor(Color.WHITE);
        mProductRecyAdapter = new ProductRecyAdapter(uatmTbkItemEntities, mActivity);
        mProductRecyAdapter.setOnItemClickLitener(this);
        recyclerView.setAdapter(mProductRecyAdapter);
        recyclerView.addOnScrollListener(new RecyclerViewLinearListener(mLayoutManager, this, 20));
        RefreshLayoutUtils.initOnCreate(refreshLayout, this);
        RefreshLayoutUtils.refreshOnCreate(refreshLayout, this);
        refreshLayout.setEnabled(false);
        recyclerView.setBackgroundColor(Color.parseColor("#F4F5F9"));
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

            loadUrl = ApiTest.buildQuery(params, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        getSign(1);

        OkHttpUtils.get()
                .url(GlobalUrl.BASE_URL + loadUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        mProgressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response) {
                        mProgressBar.setVisibility(View.GONE);
                        try {
                            productDetailBean = gson.fromJson(response, ProductDetailBean.class);
                            List<ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity> entities
                                    = productDetailBean.getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item();
                            if (entities.size() > 0) {
                                uatmTbkItemEntities.addAll(entities);
                                pageNo++;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            pageNo = 1;
                            Toast.makeText(mActivity, "没有更多了。", Toast.LENGTH_SHORT).show();
                        }


                        refreshLayout.setRefreshing(false);
                        recyclerView.setVisibility(View.VISIBLE);
                        try {
                            productDetailBean = gson.fromJson(response, ProductDetailBean.class);
                            List<ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity> entityList = productDetailBean.getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item();
                            if (entityList.size() == 0) {
                                recyclerView.setVisibility(View.GONE);
                                return;
                            }
                            uatmTbkItemEntities.clear();
                            uatmTbkItemEntities.addAll(entityList);
                            notifyDataSetChanged();
                            pageNo = 2;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });

    }

    /**
     * 更新列表
     */
    private void notifyDataSetChanged() {
        if (uatmTbkItemEntities.size() < 20) {
            refreshLayout.setRefreshing(false);
        }
        mProductRecyAdapter.notifyDataSetChanged();
    }

    boolean loading = false;

    @Override
    public void onLoadMore() {
        getSign(pageNo);
        if (mProductRecyAdapter.canLoadMore() && !loading) {
            smallBar.setVisibility(View.VISIBLE);
            loading = true;
            mProductRecyAdapter.notifyItemChanged(mProductRecyAdapter.getItemCount() - 1);
            final int page = pageNo;
            String url = GlobalUrl.BASE_URL + loadUrl;
            LogUtil.e("onLoadMore:" + url);
            OkHttpUtils
                    .get()
                    .url(url)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Request request, Exception e) {
                            smallBar.setVisibility(View.GONE);
                            loading = false;
                            if (pageNo == page) {
                                refreshLayout.setRefreshing(false);
                                mProductRecyAdapter.notifyItemChanged(mProductRecyAdapter.getItemCount() - 1);
                            }
                        }

                        @Override
                        public void onResponse(String response) {
                            smallBar.setVisibility(View.GONE);
                            loading = false;
                            try {
                                productDetailBean = gson.fromJson(response, ProductDetailBean.class);
                                List<ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity> entityList = productDetailBean.getTbk_uatm_favorites_item_get_response().getResults().getUatm_tbk_item();
                                if (entityList.size() > 0) {
                                    uatmTbkItemEntities.addAll(entityList);
                                    refreshLayout.setRefreshing(false);
                                    mProductRecyAdapter.notifyItemRangeInserted(uatmTbkItemEntities.size() - entityList.size(), entityList.size());
                                    pageNo++;
                                } else {
                                    refreshLayout.setRefreshing(false);
                                    mProductRecyAdapter.notifyItemChanged(mProductRecyAdapter.getItemCount() - 1);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                smallBar.setVisibility(View.GONE);
                                Toast.makeText(mActivity, "没有更多了。", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity productInfo
                = uatmTbkItemEntities.get(position);
        Intent intent = new Intent(mActivity, ProductDetailActivity.class);
        intent.putExtra("productInfo", productInfo);
        mActivity.startActivity(intent);
    }
}
