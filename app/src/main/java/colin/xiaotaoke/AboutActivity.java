package colin.xiaotaoke;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import colin.xiaotaoke.util.ShareUtils;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.copy)
    TextView copy;
    @BindView(R.id.w_upload)
    TextView wUpload;
    @BindView(R.id.weibo)
    TextView weibo;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    ClipboardManager cmb;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mCollapsingToolbarLayout.setTitle("关于");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cmb = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void processClick(View view) {

    }

    int index = 0;

    @OnClick({R.id.copy, R.id.weibo, R.id.email, R.id.fab, R.id.name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.copy:
                MobclickAgent.onEvent(this, "about_copy");
                cmb.setText("17081086123");
                Toast.makeText(this, "已复制", Toast.LENGTH_SHORT).show();
                break;
            case R.id.weibo:
                MobclickAgent.onEvent(this, "about_weibo");
                Intent intent1 = new Intent();
                intent1.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(getString(R.string.weibo));
                intent1.setData(content_url);
                startActivity(intent1);
                break;
            case R.id.email:
                MobclickAgent.onEvent(this, "about_email");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"834569926@qq.com"});
//                intent.putExtra(Intent.EXTRA_SUBJECT, "来自" + getString(R.string.app_name) + "Android客户端");
                intent.putExtra(Intent.EXTRA_TEXT, "来自" + getString(R.string.app_name) + "Android客户端");
                try {
                    if (!(this instanceof Activity)) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    this.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(this, "未安装邮箱应用！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fab:
                MobclickAgent.onEvent(this, "about_share");
                ShareUtils.sendText(this, getString(R.string.app_name) + "APP，安卓版下载http://fir.im/colinqua\n省钱购物从这里开始！");
                break;
            case R.id.name:
                index++;
                break;
        }
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
