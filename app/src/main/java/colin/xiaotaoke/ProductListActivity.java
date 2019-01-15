package colin.xiaotaoke;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import colin.xiaotaoke.view.BaseDetailPager;
import colin.xiaotaoke.view.BaseDetialAdapter;
import colin.xiaotaoke.view.DetialListPager;
import colin.xiaotaoke.view.DetialListRecyPager;

public class ProductListActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    String mTitle[] = {"ListView", "RecycleView"};

    @Override
    protected void initView() {
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra("favorite_name");
        String name = title.substring(3, title.length());
        getSupportActionBar().setTitle(name);
        String fId = getIntent().getStringExtra("favorites_id");
        List<BaseDetailPager> mPagerList = new ArrayList<>();
        mPagerList.add(new DetialListRecyPager(this, fId));
        mPagerList.add(new DetialListPager(this, fId));
        viewPager.setAdapter(new BaseDetialAdapter(mPagerList, mTitle));
    }

    @Override
    protected void processClick(View view) {
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.switch_layout:
                if (viewPager.getCurrentItem() == 0)
                    viewPager.setCurrentItem((viewPager.getCurrentItem() + 1), false);
                else viewPager.setCurrentItem((viewPager.getCurrentItem() + -1), false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
