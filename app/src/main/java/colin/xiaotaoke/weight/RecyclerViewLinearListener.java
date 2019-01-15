package colin.xiaotaoke.weight;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Colin on 2017/3/10 13:56.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class RecyclerViewLinearListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager linearLayoutManager;
    private OnLoadMoreListener onLoadMoreListener;
    private int limit;

    public RecyclerViewLinearListener(@NonNull LinearLayoutManager linearLayoutManager, @NonNull OnLoadMoreListener onLoadMoreListener, int limit) {
        super();
        this.linearLayoutManager = linearLayoutManager;
        this.onLoadMoreListener = onLoadMoreListener;
        this.limit = limit;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (linearLayoutManager.getItemCount() >= limit && linearLayoutManager.findLastVisibleItemPosition() == linearLayoutManager.getItemCount() - 1) { // 向下滑动，判断最后一个item是不是显示中
            onLoadMoreListener.onLoadMore();
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

}