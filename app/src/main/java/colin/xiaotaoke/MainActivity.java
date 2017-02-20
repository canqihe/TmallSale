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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import colin.xiaotaoke.bean.CommodityBean;
import colin.xiaotaoke.util.PreUtils;
import colin.xiaotaoke.view.BaseDetailPager;
import colin.xiaotaoke.view.BaseDetialAdapter;
import colin.xiaotaoke.view.DetialListPager;

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
    int index = 1;
    int pagePosition;

    List<CommodityBean.DataEntity.ResultEntity> mResultEntities = new ArrayList<>();
    String[] arr = {"女装", "男装", "母婴", "美妆", "居家", "鞋包", "美食", "数码家电", "内衣", "文体车品"};

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
        getDataFromServer();
    }

    public void getDataFromServer() {
        OkHttpUtils.get()
                .url("http://api.dataoke.com/index.php?r=goodsLink/android&type=android_quan&appkey=1tl14s3oay&v=2&page=" + index)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        mProgressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "访问失败，请联系开发者！", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        index++;
                        parseJson(response);
                    }
                });
    }

    public void parseJson(String response) {
        CommodityBean commodityBean = gson.fromJson(response, CommodityBean.class);
        CommodityBean.DataEntity dataEntity = commodityBean.getData();
        mResultEntities = dataEntity.getResult();
        if (mResultEntities.size() == 0) {
            index = 1;
            refrefhProgerss.setVisibility(View.GONE);
            return;
        }
        List<CommodityBean.DataEntity.ResultEntity> nvzlist = new ArrayList<>();
        List<CommodityBean.DataEntity.ResultEntity> nanzlist = new ArrayList<>();
        List<CommodityBean.DataEntity.ResultEntity> muyinglist = new ArrayList<>();
        List<CommodityBean.DataEntity.ResultEntity> meizlist = new ArrayList<>();
        List<CommodityBean.DataEntity.ResultEntity> jujialist = new ArrayList<>();
        List<CommodityBean.DataEntity.ResultEntity> xiebaolist = new ArrayList<>();
        List<CommodityBean.DataEntity.ResultEntity> meislist = new ArrayList<>();
        List<CommodityBean.DataEntity.ResultEntity> shumlist = new ArrayList<>();
        List<CommodityBean.DataEntity.ResultEntity> neiylist = new ArrayList<>();
        List<CommodityBean.DataEntity.ResultEntity> wentlist = new ArrayList<>();
        for (CommodityBean.DataEntity.ResultEntity resultEntity : mResultEntities) {
            if (resultEntity.getCid().equals("1")) {//女装
                nvzlist.add(resultEntity);
            }
            if (resultEntity.getCid().equals("9")) {//男装
                nanzlist.add(resultEntity);
            }
            if (resultEntity.getCid().equals("2")) {//母婴
                muyinglist.add(resultEntity);
            }
            if (resultEntity.getCid().equals("3")) {//美妆
                meizlist.add(resultEntity);
            }
            if (resultEntity.getCid().equals("4")) {//居家
                jujialist.add(resultEntity);
            }
            if (resultEntity.getCid().equals("5")) {//鞋包
                xiebaolist.add(resultEntity);
            }
            if (resultEntity.getCid().equals("6")) {//美食
                meislist.add(resultEntity);
            }
            if (resultEntity.getCid().equals("8")) {//数码家电
                shumlist.add(resultEntity);
            }
            if (resultEntity.getCid().equals("10")) {//内衣
                neiylist.add(resultEntity);
            }
            if (resultEntity.getCid().equals("7")) {//文体车品
                wentlist.add(resultEntity);
            }
        }
        List<BaseDetailPager> mPagerList = new ArrayList<BaseDetailPager>();
        mPagerList.add(new DetialListPager(MainActivity.this, nvzlist));
        mPagerList.add(new DetialListPager(MainActivity.this, nanzlist));
        mPagerList.add(new DetialListPager(MainActivity.this, muyinglist));
        mPagerList.add(new DetialListPager(MainActivity.this, meizlist));
        mPagerList.add(new DetialListPager(MainActivity.this, jujialist));
        mPagerList.add(new DetialListPager(MainActivity.this, xiebaolist));
        mPagerList.add(new DetialListPager(MainActivity.this, meislist));
        mPagerList.add(new DetialListPager(MainActivity.this, shumlist));
        mPagerList.add(new DetialListPager(MainActivity.this, neiylist));
        mPagerList.add(new DetialListPager(MainActivity.this, wentlist));
        viewPager.setOffscreenPageLimit(9);
        viewPager.setAdapter(new BaseDetialAdapter(mPagerList, arr));
        tabLayout.setViewPager(viewPager, arr);

        viewPager.setCurrentItem(pagePosition);

        tabLayout.setVisibility(View.VISIBLE);
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
                        TapTarget.forToolbarMenuItem(toolbar, R.id.refresh, "刷新商品", "点击后所有分类的商品都会刷新\n另外，商品每天都有上新哦~").id(0),
                        TapTarget.forToolbarNavigationIcon(toolbar, "菜单栏", "这里还有侧边菜单功能，你看见了吗？").id(1))
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.refresh:
                refrefhProgerss.setVisibility(View.VISIBLE);
                getDataFromServer();
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
