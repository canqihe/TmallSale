package colin.xiaotaoke;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import colin.xiaotaoke.bean.CommodityBean;
import colin.xiaotaoke.util.ShareUtils;

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.web_close)
    ImageView webClose;
    @BindView(R.id.web_title)
    TextView webTitle;
    @BindView(R.id.web_xiadan)
    TextView webXiadan;
    @BindView(R.id.home_title)
    RelativeLayout homeTitle;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.wv_home)
    WebView webView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private myWebChromeClient xwebchromeclient;

    private WebSettings settings;
    CommodityBean.DataEntity.ResultEntity commoditfyData;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_web_view);
        StatusBarUtil.setTranslucent(this, 55);
        ButterKnife.bind(this);
    }

    @Override
    protected void initListener() {
        webClose.setOnClickListener(this);
        webXiadan.setOnClickListener(this);
        fab.setOnClickListener(this);
        Toast.makeText(WebViewActivity.this, "先领券，再下单购买~", Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    @Override
    protected void initData() {
        commoditfyData = (CommodityBean.DataEntity.ResultEntity) getIntent().getSerializableExtra("commoditfy");
        settings = webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }
        });
        xwebchromeclient = new myWebChromeClient();
        webView.setWebChromeClient(xwebchromeclient);
        webXiadan.setText("下单购买");
        webView.loadUrl(commoditfyData.getQuan_m_link());
    }


    public class myWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
            if (progress == 100) {
                progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            webTitle.setText(title);
        }
    }

    @Override
    protected void processClick(View view) {
        switch (view.getId()) {
            case R.id.web_close:
                this.finish();
                break;
            case R.id.web_xiadan:
                MobclickAgent.onEvent(this, "web_buy");
                Intent intent1 = new Intent();
                intent1.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(commoditfyData.getAli_click());
                intent1.setData(content_url);
                startActivity(intent1);
                break;
            case R.id.fab:
                MobclickAgent.onEvent(this, "web_share");
                ShareUtils.sendText(WebViewActivity.this, commoditfyData.getTitle() + "\n原价" + commoditfyData.getOrg_Price() + "\n可领取优惠券【" + commoditfyData.getQuan_price() + "元】" +
                        "\n优惠券地址" + commoditfyData.getQuan_m_link() + "\n商品链接" + commoditfyData.getAli_click() + "\n分享自[" + getString(R.string.app_name) + "]安卓APP客户端，下载地址:http://fir.im/colinqua");
                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setVisibility(View.GONE);
        long timeout = ViewConfiguration.getZoomControlsTimeout();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.destroy();
            }
        }, timeout);
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
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
