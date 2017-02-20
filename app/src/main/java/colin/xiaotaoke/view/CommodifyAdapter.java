package colin.xiaotaoke.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import colin.xiaotaoke.R;
import colin.xiaotaoke.bean.CommodityBean;
import colin.xiaotaoke.util.ShareUtils;
import colin.xiaotaoke.util.Utils;

/**
 * Created by Colin on 2017/2/16 13:27.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class CommodifyAdapter extends BaseAdapter {
    List<CommodityBean.DataEntity.ResultEntity> mResultEntities;
    Context mContext;

    public CommodifyAdapter(Context context, List<CommodityBean.DataEntity.ResultEntity> resultEntities) {
        this.mResultEntities = resultEntities;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mResultEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return mResultEntities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final CommodityBean.DataEntity.ResultEntity resultEntity = mResultEntities.get(i);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_commodity, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.orgPrice = (TextView) convertView.findViewById(R.id.org_price);
            viewHolder.quanPrice = (TextView) convertView.findViewById(R.id.quan_price);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic);
            viewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.progress);
            viewHolder.more = (ImageView) convertView.findViewById(R.id.more);
            viewHolder.sale = (TextView) convertView.findViewById(R.id.sale);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(resultEntity.getD_title());
        viewHolder.price.setText("CNY $" + resultEntity.getPrice());
        viewHolder.orgPrice.setText("CNY $" + resultEntity.getOrg_Price());
        viewHolder.orgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        viewHolder.quanPrice.setText("优惠券" + resultEntity.getQuan_price().substring(0, resultEntity.getQuan_price().length() - 3) + "元");
        viewHolder.sale.setText("已售" + resultEntity.getSales_num());
        viewHolder.mProgressBar.setMax((Integer.parseInt(resultEntity.getQuan_surplus()) + Integer.parseInt(resultEntity.getQuan_receive())));
        viewHolder.mProgressBar.setProgress(Integer.parseInt(resultEntity.getQuan_receive()));
        Glide.with(mContext).load(resultEntity.getPic()).placeholder(Utils.getColor(i % 6)).into(viewHolder.pic);

        viewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.getMenuInflater().inflate(R.menu.popu_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.pop_detial:
                                MobclickAgent.onEvent(mContext, "itme_detial");
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(resultEntity.getAli_click());
                                intent.setData(content_url);
                                mContext.startActivity(intent);
                                break;
                            case R.id.pop_share:
                                MobclickAgent.onEvent(mContext, "item_share");
                                ShareUtils.sendText(mContext, resultEntity.getTitle() + "\n原价" + resultEntity.getOrg_Price() + "\n可领取优惠券【" + resultEntity.getQuan_price() + "元】" +
                                        "天猫包邮\n优惠券领取" + resultEntity.getQuan_m_link() + "\n商品链接" + resultEntity.getAli_click() + "\n分享自[" + mContext.getString(R.string.app_name) + "]安卓APP客户端，下载地址:http://fir.im/colinqua");
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView price, orgPrice, quanPrice, title, sale;
        ImageView pic, more;
        ProgressBar mProgressBar;
    }
}
