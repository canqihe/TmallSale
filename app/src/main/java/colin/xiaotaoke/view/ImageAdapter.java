package colin.xiaotaoke.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import colin.xiaotaoke.util.Utils;

/**
 * Created by cfanr on 2015/12/4.
 */
public class ImageAdapter extends PagerAdapter {
    private List<ImageView> imgList = new ArrayList<ImageView>();

    public ImageAdapter(Context context, List<String> imgs) {
        for (int i = 0; i < imgs.size(); i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(context).load(imgs.get(i)).placeholder(Utils.getColor(i % imgs.size())).into(imageView);
            imgList.add(imageView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Warning：不要在这里调用removeView
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        //对ViewPager页号求模取出View列表中要显示的项
        if (position < 0) {
            position = imgList.size() + position;
        }
        final ImageView view = imgList.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        final int positionId = position;
        if (onItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = positionId;
                    onItemClickListener.onItemClick(view, pos);
                }
            });
        }

        return view;
    }
}