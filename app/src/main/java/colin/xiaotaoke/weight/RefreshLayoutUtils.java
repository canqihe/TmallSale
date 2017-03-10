package colin.xiaotaoke.weight;

import android.support.v4.widget.SwipeRefreshLayout;

import colin.xiaotaoke.R;
import colin.xiaotaoke.util.HandlerUtils;


public final class RefreshLayoutUtils {

    private RefreshLayoutUtils() {
    }

    public static void initOnCreate(SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener refreshListener) {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    /**
     * TODO refreshLayout无法直接在onCreate中设置刷新状态
     */
    public static void refreshOnCreate(final SwipeRefreshLayout refreshLayout, final SwipeRefreshLayout.OnRefreshListener refreshListener) {
        HandlerUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }

        }, 100);
    }

}
