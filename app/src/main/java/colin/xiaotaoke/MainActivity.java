package colin.xiaotaoke;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.jaeger.library.StatusBarUtil;
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
import colin.xiaotaoke.bean.CommodityBean;
import colin.xiaotaoke.bean.ProductListBean;
import colin.xiaotaoke.util.GlobalUrl;
import colin.xiaotaoke.util.LogUtil;
import colin.xiaotaoke.util.PreUtils;
import colin.xiaotaoke.view.BaseDetailPager;
import colin.xiaotaoke.view.BaseDetialAdapter;
import colin.xiaotaoke.view.DetialListPager;

import static colin.xiaotaoke.ApiTest.signTopRequest;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.load_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.refresh_progress)
    ProgressBar refrefhProgerss;

    private long exitTime = 0;
    int pagePosition;
    String mTitle[];

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        if (Build.VERSION.SDK_INT >= 20)
            StatusBarUtil.setTranslucent(this, 55);
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (PreUtils.getBoolean(this, "guide", true)) {
            showGuide();
        }

    }

    @Override
    protected void initData() {
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
            params.put("page_size", "20");
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
                        mProgressBar.setVisibility(View.GONE);
                        refrefhProgerss.setVisibility(View.GONE);
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "尴尬了，网络好像出了点问题。", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtil.e("数据：" + response);
                        parseJson(response);
                    }
                });
    }

    public void parseJson(String response) {

        List<BaseDetailPager> mPagerList = new ArrayList<>();

        ProductListBean proList = gson.fromJson(response, ProductListBean.class);
        List<ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity> taokeList =
                proList.getTbk_uatm_favorites_get_response().getResults().getTbk_favorites();

        mTitle = new String[taokeList.size()];
        String mId[] = new String[taokeList.size()];
        for (int i = 0, j = taokeList.size(); i < j; i++) {
            mTitle[i] = taokeList.get(i).getFavorites_title();
            mId[i] = String.valueOf(taokeList.get(i).getFavorites_id());
            mPagerList.add(new DetialListPager(MainActivity.this, mId[i]));
        }

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new BaseDetialAdapter(mPagerList, mTitle));

        if (taokeList.size() > 5) tabLayout.setTabSpaceEqual(false);
        else tabLayout.setTabSpaceEqual(true);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setViewPager(viewPager, mTitle);

        viewPager.setCurrentItem(pagePosition);
        mProgressBar.setVisibility(View.GONE);
        refrefhProgerss.setVisibility(View.GONE);
    }

    @Override
    protected void processClick(View view) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.nav_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showGuide() {
        TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        TapTarget.forToolbarMenuItem(toolbar, R.id.refresh, "刷新商品", "随机刷新所有类目的商品").id(0),
                        TapTarget.forToolbarNavigationIcon(toolbar, "菜单栏", "遵循Android设计规范\n菜单功能在放在侧边").id(1))
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        Toast.makeText(MainActivity.this, "开始你的购物之旅吧", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                });
        sequence.start();
        PreUtils.setBoolean(this, "guide", false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2500) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
