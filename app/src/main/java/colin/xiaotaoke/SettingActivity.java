package colin.xiaotaoke;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import colin.xiaotaoke.util.Utils;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.setting_send_email)
    RelativeLayout settingSend;
    @BindView(R.id.setting_share)
    RelativeLayout settingShare;
    @BindView(R.id.cache_size)
    TextView cacheSize;
    @BindView(R.id.setting_cleancache)
    RelativeLayout settingCleancache;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);
        StatusBarUtil.setTranslucent(this, 55);
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("设置");
        settingCleancache.setOnClickListener(this);
        settingShare.setOnClickListener(this);
        settingSend.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        File file = SettingActivity.this.getExternalCacheDir();
        File glidecache = Glide.getPhotoCacheDir(SettingActivity.this);
        long total = Utils.getFolderSize(file);
        long cache = Utils.getFolderSize(glidecache);
        long cachesize = cache + total;
        String size = Utils.getFormatSize(cachesize);//格式转换
        cacheSize.setText(size);
    }

    @Override
    protected void processClick(View view) {
        switch (view.getId()) {
            case R.id.setting_cleancache:
                MobclickAgent.onEvent(this, "setting_clean");
                cleanCache();
                break;
            case R.id.setting_share:
                MobclickAgent.onEvent(this, "setting_store");
                Uri uri = Uri.parse("market://details?id=colin.xiaotaoke");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.setting_send_email:
                MobclickAgent.onEvent(this, "setting_email");
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
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "未安装邮箱应用！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //清理缓存
    private void cleanCache() {
        Utils.showDialog(SettingActivity.this, "正在清理...");

        File file = SettingActivity.this.getExternalCacheDir();
        try {
            Utils.deleteFolderFile(file.getAbsolutePath(), false);
            Glide.get(SettingActivity.this).clearMemory();
            new Thread(new Runnable() {
                public void run() {
                    Glide.get(SettingActivity.this).clearDiskCache();
                }
            }).start();
            Glide.get(SettingActivity.this).clearMemory();
            Timer timer = new Timer();
            TimerTask tk = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.closeDialog();
                            initData();
                        }
                    });
                }
            };
            timer.schedule(tk, 3000);
        } catch (Exception e) {
            e.printStackTrace();
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
