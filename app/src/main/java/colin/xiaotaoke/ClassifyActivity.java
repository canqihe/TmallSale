package colin.xiaotaoke;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.umeng.analytics.MobclickAgent;
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
import colin.xiaotaoke.bean.ProductListBean;
import colin.xiaotaoke.gridheader.StickyGridHeadersGridView;
import colin.xiaotaoke.gridheader.StickyGridHeadersSimpleArrayAdapter;
import colin.xiaotaoke.util.ApiTest;
import colin.xiaotaoke.util.GlobalUrl;
import colin.xiaotaoke.util.LogUtil;

import static colin.xiaotaoke.util.ApiTest.signTopRequest;

public class ClassifyActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.grid_classify)
    StickyGridHeadersGridView gridClassify;

    List<ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity> mTbkFavoritesEntityArrayList
            = new ArrayList<>();
    StickyGridHeadersSimpleArrayAdapter mAdapter;
    int page = 1;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_classify);
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("所有分类");

        gridClassify.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (gridClassify.getLastVisiblePosition() > mTbkFavoritesEntityArrayList.size()) {
                        page++;
                        getSign(page);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        gridClassify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MobclickAgent.onEvent(ClassifyActivity.this, "prolist_click");
                ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity favoritesEntity = mTbkFavoritesEntityArrayList.get(position);
                Intent intent = new Intent(ClassifyActivity.this, ProductListActivity.class);
                intent.putExtra("favorites_id", String.valueOf(favoritesEntity.getFavorites_id()));
                intent.putExtra("favorite_name", favoritesEntity.getFavorites_title());
                ClassifyActivity.this.startActivity(intent);

            }
        });

    }

    @Override
    protected void initData() {
        mAdapter = new StickyGridHeadersSimpleArrayAdapter(this, mTbkFavoritesEntityArrayList);
        gridClassify.setAdapter(mAdapter);
        mTbkFavoritesEntityArrayList.clear();
        getSign(1);

    }

    public void getSign(int page) {
        try {
            Map<String, String> params = new HashMap<>();
            // 公共参数
            params.put("method", GlobalUrl.PRODUCT_LIST);
            params.put("app_key", GlobalUrl.TAOBAO_APP_KEY);
            params.put("session", "test");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("timestamp", df.format(new Date()));
            params.put("format", "json");
            params.put("v", "2.0");
            params.put("sign_method", "hmac");
            // 业务参数
            params.put("fields", "favorites_title,favorites_id,type");
            params.put("page_no", String.valueOf(page));
            params.put("page_size", "100");
            // 签名参数
            params.put("sign", signTopRequest(params, GlobalUrl.TAOBAO_APP_SECRET, "hmac"));

            String p = ApiTest.buildQuery(params, "UTF-8");

            getDataFromServer(GlobalUrl.BASE_URL + p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataFromServer(String url) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ClassifyActivity.this, "尴尬了，网络好像出了点问题。", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtil.e("数据：" + response);
                        try {
                            ProductListBean proList = gson.fromJson(response, ProductListBean.class);
                            List<ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity> taokeList =
                                    proList.getTbk_uatm_favorites_get_response().getResults().getTbk_favorites();
                            if (taokeList.size() > 0) {
                                if (page == 1) {
                                    for (int i = 7; i < taokeList.size(); i++) {
                                        mTbkFavoritesEntityArrayList.add(taokeList.get(i));
                                    }
                                } else {
                                    mTbkFavoritesEntityArrayList.addAll(taokeList);
                                }
                                mAdapter.updateData(mTbkFavoritesEntityArrayList);
                            } else {
                                mTbkFavoritesEntityArrayList.clear();
                                page = 1;
                                Toast.makeText(ClassifyActivity.this, "没有更多了。", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void processClick(View view) {

    }
}
