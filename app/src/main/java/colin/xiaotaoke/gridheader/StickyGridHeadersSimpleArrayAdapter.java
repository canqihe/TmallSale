/*
 Copyright 2013 Tonic Artos

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package colin.xiaotaoke.gridheader;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import colin.xiaotaoke.R;
import colin.xiaotaoke.bean.ProductListBean;
import colin.xiaotaoke.util.LogUtil;

/**
 * @author Tonic Artos
 */
public class StickyGridHeadersSimpleArrayAdapter extends BaseAdapter implements StickyGridHeadersSimpleAdapter {

    List<ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity> mItems;
    private Context mContext;

    public void updateData(List<ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity> datas) {
        this.mItems = datas;
        notifyDataSetChanged();
    }

    public StickyGridHeadersSimpleArrayAdapter(Context context, List<ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity> datas) {
        this.mContext = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    @Override
    public ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity getItem(int position) {
        return mItems == null ? null : mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public long getHeaderId(int position) {
        ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity item = getItem(position);
        CharSequence value = item.getFavorites_title();
        return value.subSequence(0, 1).charAt(0);
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        LogUtil.e("getHeaderId:" + position);
        HeaderViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.clssify_header, null);
            holder = new HeaderViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_header);
            holder.icon = (ImageView) convertView.findViewById(R.id.classify_icon);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity item = getItem(position);
        CharSequence string = item.getFavorites_title();
        holder.textView.setText(string.subSequence(1, 3));
        if (position == 0) {
            holder.icon.setImageResource(R.mipmap.i_b);
        } else if (position == 45) {
            holder.icon.setImageResource(R.mipmap.i_a);
        } else if (position == 100) {
            holder.icon.setImageResource(R.mipmap.i_c);
        } else if (position == 106) {
            holder.icon.setImageResource(R.mipmap.i_d);
        } else if (position == 129) {
            holder.icon.setImageResource(R.mipmap.i_e);
        } else if (position == 139) {
            holder.icon.setImageResource(R.mipmap.i_f);
        } else {
            holder.icon.setImageResource(R.mipmap.i_g);
        }
        return convertView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductListBean.TbkUatmFavoritesGetResponseEntity.ResultsEntity.TbkFavoritesEntity item = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_classify, null);
            holder = new ViewHolder();
            holder.mTitle = (TextView) convertView.findViewById(R.id.item_title);
            holder.mCardView = (CardView) convertView.findViewById(R.id.card_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String title = item.getFavorites_title();
        String titleWord = title.substring(0, 1);
        String titleDisplay = title.substring(3, title.length());
        if (titleWord.equals("A")) {
            holder.mCardView.setCardBackgroundColor(Color.parseColor("#ff4081"));
        } else if (titleWord.equals("B")) {
            holder.mCardView.setCardBackgroundColor(Color.parseColor("#536dfe"));
        } else if (titleWord.equals("C")) {
            holder.mCardView.setCardBackgroundColor(Color.parseColor("#ff6e40"));
        } else if (titleWord.equals("D")) {
            holder.mCardView.setCardBackgroundColor(Color.parseColor("#00c853"));
        } else if (titleWord.equals("E")) {
            holder.mCardView.setCardBackgroundColor(Color.parseColor("#78909c"));
        } else if (titleWord.equals("F")) {
            holder.mCardView.setCardBackgroundColor(Color.parseColor("#ffab40"));
        } else if (titleWord.equals("G")) {
            holder.mCardView.setCardBackgroundColor(Color.parseColor("#e040fb"));
        }
        holder.mTitle.setText(titleDisplay);
        return convertView;
    }


    class HeaderViewHolder {
        TextView textView;
        ImageView icon;
    }

    class ViewHolder {
        TextView mTitle;
        CardView mCardView;
    }
}
