package colin.xiaotaoke.view;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import colin.xiaotaoke.R;
import colin.xiaotaoke.bean.ProductDetailBean;
import colin.xiaotaoke.util.Utils;

/**
 * Created by Colin on 2017/2/16 13:27.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class ProductAdapter extends BaseAdapter {
    List<ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity> uatmTbkItemEntities;
    Context mContext;
    public ProductAdapter(Context context) {
        this.mContext = context;
    }

    public void updateData(List<ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity> datas) {
        this.uatmTbkItemEntities = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (uatmTbkItemEntities != null)
            return uatmTbkItemEntities.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return uatmTbkItemEntities == null ? null : uatmTbkItemEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity resultEntity = uatmTbkItemEntities.get(i);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_commodity, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.orgPrice = (TextView) convertView.findViewById(R.id.org_price);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic);
            viewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.progress);
            viewHolder.more = (ImageView) convertView.findViewById(R.id.more);
            viewHolder.sale = (TextView) convertView.findViewById(R.id.sale);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(resultEntity.getTitle());
        viewHolder.price.setText("CNY $" + resultEntity.getZk_final_price());
        viewHolder.orgPrice.setText("CNY $" + resultEntity.getReserve_price());
        viewHolder.orgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        viewHolder.sale.setText("已售" + resultEntity.getVolume());
        if (resultEntity.getUser_type() == 0)
            viewHolder.more.setImageResource(R.mipmap.icon_taobao);
        else
            viewHolder.more.setImageResource(R.mipmap.icon_tmall);
//        viewHolder.sale.setText("已售" + resultEntity.getVolume());
//        viewHolder.mProgressBar.setMax((Integer.parseInt(resultEntity.getQuan_surplus()) + Integer.parseInt(resultEntity.getQuan_receive())));
//        viewHolder.mProgressBar.setProgress(Integer.parseInt(resultEntity.getQuan_receive()));
        Glide.with(mContext).load(resultEntity.getPict_url()).placeholder(Utils.getColor(i % 6)).into(viewHolder.pic);

     /*   viewHolder.more.setOnClickListener(new View.OnClickListener() {
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
*/
        return convertView;
    }

    class ViewHolder {
        TextView price, orgPrice, title, sale;
        ImageView pic, more;
        ProgressBar mProgressBar;
    }
}
