package colin.xiaotaoke.view;

import android.app.Activity;
import android.view.View;

import com.google.gson.Gson;

/**
 * 菜单详情页基类
 *
 * @author Kevin
 */
public abstract class BaseDetailPager {

    public Activity mActivity;
    public View mRootView;// 根布局对象
    Gson gson;

    public BaseDetailPager(Activity activity) {
        mActivity = activity;
        mRootView = initViews();
        gson = new Gson();
    }

    /**
     * 初始化界面
     */
    public abstract View initViews();

    /**
     * 初始化数据
     */
    public void initData() {

    }


}
