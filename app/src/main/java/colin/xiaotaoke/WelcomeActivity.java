package colin.xiaotaoke;

import android.content.Intent;
import android.view.View;


import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_welcome);

        Timer timer = new Timer();
        TimerTask tk = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(intent);
                finish();
            }
        };
        timer.schedule(tk, 2000);

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
}
