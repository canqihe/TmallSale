package colin.xiaotaoke.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import colin.xiaotaoke.R;
import colin.xiaotaoke.WebViewActivity;
import colin.xiaotaoke.bean.CommodityBean;

/**
 * Created by Colin on 2017/2/16 12:16.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class DetialListPager extends BaseDetailPager {

    @BindView(R.id.recycler)
    ListView mListView;

    List<CommodityBean.DataEntity.ResultEntity> mResultEntities;

    public DetialListPager(Activity activity, List<CommodityBean.DataEntity.ResultEntity> mResultEntities) {
        super(activity);
        this.mResultEntities = mResultEntities;
    }

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.detial_list, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        mListView.setAdapter(new CommodifyAdapter(mActivity, mResultEntities));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MobclickAgent.onEvent(mActivity, "item_click");
                Intent intent = new Intent(mActivity, WebViewActivity.class);
                intent.putExtra("commoditfy", mResultEntities.get(i));
                mActivity.startActivity(intent);
            }
        });
    }

}
