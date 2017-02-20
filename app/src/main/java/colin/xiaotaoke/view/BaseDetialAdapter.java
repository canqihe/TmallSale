package colin.xiaotaoke.view;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ViewAdapter 基类
 * Created by Colin on 15/12/2.
 */
public class BaseDetialAdapter extends PagerAdapter {

    private BaseDetialAdapter pager;
    private List<BaseDetailPager> mPageList;
    String arr[];

    public BaseDetialAdapter(List<BaseDetailPager> pageList, String[] arr) {
        this.arr = arr;
        this.mPageList = pageList;
    }

    public int getCount() {
        return arr.length;
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        BaseDetailPager pager = mPageList.get(position);
        container.addView(pager.mRootView);
        pager.initData();
        return pager.mRootView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
