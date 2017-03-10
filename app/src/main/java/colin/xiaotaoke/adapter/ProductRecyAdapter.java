package colin.xiaotaoke.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import colin.xiaotaoke.R;
import colin.xiaotaoke.bean.ProductDetailBean;
import colin.xiaotaoke.util.Utils;

/**
 * Created by Colin on 2017/3/10 13:59.
 * 邮箱：cartier_he@163.com
 * 微信：cartier_he
 */

public class ProductRecyAdapter extends RecyclerView.Adapter<ProductRecyAdapter.ViewHolder> {
    private List<ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity> uatmTbkItemEntities;
    ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity mUatmTbkItemEntity;
    Context mContext;
    OnItemClickLitener mOnItemClickLitener;
    private boolean loading = false;
    private final int TYPE_NORMAL = 0;
    private final int TYPE_LOAD_MORE = 1;
    private LayoutInflater inflater;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ProductRecyAdapter(List<ProductDetailBean.TbkUatmFavoritesItemGetResponseEntity.ResultsEntity.UatmTbkItemEntity> datas, Context context) {
        this.uatmTbkItemEntities = datas;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        setHasStableIds(true);
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }


    public boolean canLoadMore() {
        return uatmTbkItemEntities.size() >= 20 && !loading;
    }


    //记住在使用RecyclerView的时候要主页这里的返回类型！ItemViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_LOAD_MORE:
                return new LoadMoreViewHolder(inflater.inflate(R.layout.activity_item_load_more, parent, false));
            default:
                return new ItemViewHolder(inflater.inflate(R.layout.item_recy_commodity, parent, false));
        }
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.update(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ViewHolder(View itemView) {
            super(itemView);
        }

        protected void update(int position) {

        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return uatmTbkItemEntities.size() >= 20 ? uatmTbkItemEntities.size() + 1 : uatmTbkItemEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (uatmTbkItemEntities.size() >= 20 && position == getItemCount() - 1) {
            return TYPE_LOAD_MORE;
        } else {
            return TYPE_NORMAL;
        }
    }

    public class LoadMoreViewHolder extends ViewHolder {
        protected View iconLoading;
        protected View iconFinish;

        protected LoadMoreViewHolder(View itemView) {
            super(itemView);
            iconLoading = itemView.findViewById(R.id.item_load_more_icon_loading);
            iconFinish = itemView.findViewById(R.id.item_load_more_icon_finish);
        }

        protected void update(int position) {
            iconLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
            iconFinish.setVisibility(loading ? View.VISIBLE : View.GONE);
        }

    }

    public class ItemViewHolder extends ViewHolder {
        ImageView mProImg, store;
        TextView mPrice, mOrgPrice;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mProImg = (ImageView) itemView.findViewById(R.id.pro_img);
            store = (ImageView) itemView.findViewById(R.id.store);
            mPrice = (TextView) itemView.findViewById(R.id.price);
            mOrgPrice = (TextView) itemView.findViewById(R.id.org_price);
        }

        public void update(int position) {
            mUatmTbkItemEntity = uatmTbkItemEntities.get(position);

            Glide.with(mContext).load(mUatmTbkItemEntity.getPict_url()).placeholder(Utils.getColor(position % uatmTbkItemEntities.size())).into(mProImg);
            mPrice.setText("CNY $" + mUatmTbkItemEntity.getZk_final_price());
            mOrgPrice.setText("CNY $" + mUatmTbkItemEntity.getReserve_price());
            mOrgPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            if (mUatmTbkItemEntity.getUser_type() == 0)
                store.setImageResource(R.mipmap.icon_taobao);
            else
                store.setImageResource(R.mipmap.tmall);

            if (mOnItemClickLitener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int pos = getLayoutPosition();
                        mOnItemClickLitener.onItemClick(itemView, pos);
                    }
                });
            }
        }

    }
}
