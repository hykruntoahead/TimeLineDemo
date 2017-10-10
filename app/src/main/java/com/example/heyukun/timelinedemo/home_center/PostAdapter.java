package com.example.heyukun.timelinedemo.home_center;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.heyukun.timelinedemo.R;
import com.example.heyukun.timelinedemo.home_center.image_brow.adapter.GlideCircleTransform;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 17/10/9
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private LayoutInflater mInflater;
    private List<HomeEntity> mPostList;
    private Context context;
    private OnImgItemClick onImgItemClick;
    private OnItemLikeClick onItemLikeClick;

    public PostAdapter(Context context, List<HomeEntity> postList) {
        super();
        this.context = context;
        mPostList = postList;
        mInflater = LayoutInflater.from(context);
    }


    public void setOnImgItemClickListener(OnImgItemClick onImgItemClick){
        this.onImgItemClick = onImgItemClick;
    }

    public void setOnItemLikeClickListener(OnItemLikeClick onItemLikeClick){
        this.onItemLikeClick = onItemLikeClick;
    }


    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bind(mPostList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostViewHolder(mInflater.inflate(R.layout.item_home, parent, false));
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private NineGridImageView<String> mNglContent;
        private ImageView mIcon;
        private TextView mTvName, mTvTime, mTvLike;
        private View mViewLike;

        private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                imageView.getLayoutParams().height=RKDensityUtil.dpToPx(context,260);
                imageView.getLayoutParams().width =RKDensityUtil.dpToPx(context,260);
                imageView.requestLayout();


                Glide.with(context).load(s).placeholder(R.mipmap.ic_launcher).into(imageView);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<String> list) {
                super.onItemImageClick(context, index, list);
                if(onImgItemClick !=null) {
                    onImgItemClick.OnClick(index, list);
                }
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }
        };


        public PostViewHolder(View itemView) {
            super(itemView);

            mTvName = (TextView) itemView.findViewById(R.id.item_tv_name);
            mTvTime = (TextView) itemView.findViewById(R.id.item_tv_time);
            mTvLike = (TextView) itemView.findViewById(R.id.item_tv_like);

            mIcon = (ImageView)itemView.findViewById(R.id.item_iv_icon);

            mViewLike = itemView.findViewById(R.id.item_v_like);

            mNglContent = (NineGridImageView<String>) itemView.findViewById(R.id.item_nine_ngiv);
            mNglContent.setAdapter(mAdapter);

        }

        public void bind(final HomeEntity homeEntity, int pos) {

            Glide.with(context)
                    .load(homeEntity.getIconUrl())
                    .placeholder(R.mipmap.default_icon)
                    .centerCrop()
                    .transform(new GlideCircleTransform(context))
                    .into(mIcon);

            mTvName.setText(homeEntity.getName());

            mTvTime.setText(getDateToString(homeEntity.getTime(),"yyyy-MM-dd HH:mm:ss"));


            String likeStr = getHandlerLikeNames(homeEntity.getLikeNameList());
            if(TextUtils.isEmpty(likeStr)){
                mTvLike.setVisibility(View.GONE);
            }else {
                if(mTvLike.getVisibility() == View.GONE){
                    mTvLike.setVisibility(View.VISIBLE);
                }
                mTvLike.setText(likeStr);
            }


            mViewLike.setBackgroundResource(homeEntity.getLikeFlag() == 1 ? R.mipmap.familycircle_btn_thumbup_pre : R.mipmap.familycircle_btn_thumbup);

            mNglContent.setImagesData(homeEntity.getNineUrlList());

            mViewLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //取消点赞
                    if(homeEntity.getLikeFlag() == 1){
                        // TODO: 2017/9/28 取消点赞 动画暂时后做
                        if(onItemLikeClick != null){
                            onItemLikeClick.OnDisLiked(homeEntity);
                        }
                    }else {
                        // TODO: 2017/9/28 点赞 动画暂时后做
                       if(onItemLikeClick !=null){
                           onItemLikeClick.OnLiked(homeEntity);
                       }
                    }

                }
            });
        }
    }

    /**
     *
     * @param milSecond 时间戳
     * @param pattern 格式化字符串
     * @return 格式化后字符串
     */

    public  String getDateToString(long milSecond, String pattern) {
        long currentTime = System.currentTimeMillis();
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String dateStr = format.format(date);
        String[] usualDateSplits =  dateStr.split(" ");

        Log.i("usualDateSplits-0:",usualDateSplits[0]);
        Log.i("usualDateSplits-1:",usualDateSplits[1]);

        StringBuilder stringBuilder = new StringBuilder();

        //五分钟内
        if(currentTime-milSecond < 24*60*60*1000){
            return stringBuilder.append("今天 ").append(usualDateSplits[1]).toString();
        }else if(currentTime-milSecond < 2*24*60*60*1000){
            return stringBuilder.append("昨天 ").append(usualDateSplits[1]).toString();
        }else if(currentTime-milSecond < 3*24*60*60*1000){
            return stringBuilder.append("前天 ").append(usualDateSplits[1]).toString();
        }else if(currentTime-milSecond < 4*24*60*60*1000){
            return stringBuilder.append("三天前 ").append(usualDateSplits[1]).toString();
        }else {
            return dateStr;
        }
    }

    /**
     *
     * @param nameList 点赞姓名列表
     * @return 点赞姓名字符串
     */
     private String  getHandlerLikeNames(List<String> nameList){
         if(nameList==null || nameList.size() ==0){
             return  null;
         }

         StringBuilder names = new StringBuilder();
         for(int i =0 ; i<nameList.size() ; i++){
             if(i<nameList.size()-1) {
                 names.append(nameList.get(i)).append(",");
             }else {
                 names.append(nameList.get(i));
             }

         }

         return names.toString();

     }

    public interface OnImgItemClick {
      void OnClick(int index, List<String> list);
    }


    public interface OnItemLikeClick{
        void OnLiked(HomeEntity homeEntity);
        void OnDisLiked(HomeEntity homeEntity);
    }


}


