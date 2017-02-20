package colin.xiaotaoke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (gson == null) {
            gson = new Gson();
        }
        initView();
        initListener();
        initData();
    }

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void processClick(View view);

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
