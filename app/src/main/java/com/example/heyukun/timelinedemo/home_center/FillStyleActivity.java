package com.example.heyukun.timelinedemo.home_center;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.example.heyukun.timelinedemo.R;
import com.example.heyukun.timelinedemo.home_center.image_brow.ShowImagesDialog;
import com.example.heyukun.timelinedemo.home_center.image_brow.base.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Jaeger on 16/2/24.
 *
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
public class FillStyleActivity extends FragmentActivity {

    private RecyclerView mRv;
    private PostAdapter mPostAdapter;
    private ShowImagesDialog showImagesDialog;

    private List<HomeEntity> mPostList;
    private String[] IMG_URL_LIST = {
        "https://pic4.zhimg.com/02685b7a5f2d8cbf74e1fd1ae61d563b_xll.jpg",
        "https://pic4.zhimg.com/fc04224598878080115ba387846eabc3_xll.jpg",
        "https://pic3.zhimg.com/d1750bd47b514ad62af9497bbe5bb17e_xll.jpg",
        "https://pic4.zhimg.com/da52c865cb6a472c3624a78490d9a3b7_xll.jpg",
        "https://pic3.zhimg.com/0c149770fc2e16f4a89e6fc479272946_xll.jpg",
        "https://pic1.zhimg.com/76903410e4831571e19a10f39717988c_xll.png",
        "https://pic3.zhimg.com/33c6cf59163b3f17ca0c091a5c0d9272_xll.jpg",
        "https://pic4.zhimg.com/52e093cbf96fd0d027136baf9b5cdcb3_xll.png",
        "https://pic3.zhimg.com/f6dc1c1cecd7ba8f4c61c7c31847773e_xll.jpg",
    };

    private String[] ICONS = {
            "http://pic8.nipic.com/20100623/5208937_134307859911_2.jpg",
            "http://pic2.16pic.com/00/24/38/16pic_2438497_b.jpg",
            "http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=1a3963408f94a4c20a76ef2f3bc437e3/e4dde71190ef76c66b5e79649516fdfaae5167f5.jpg"
    };

    private String[]  NAMES = {
            "刘备","张飞字益德","曹操字孟德啊","黄忠字汉升啊",
            "马超字孟起呢","张辽文远","徐晃字公明","甘宁字兴霸啊",
            "诸葛亮"
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.act_fill_style);
        getDeviceDensity();

        mRv = (RecyclerView) findViewById(R.id.rcv);
        mRv.setLayoutManager(new LinearLayoutManager(this));

        createHomes();
        mPostAdapter = new PostAdapter(this, mPostList);
        mPostAdapter.setOnImgItemClickListener(new PostAdapter.OnImgItemClick() {
            @Override
            public void OnClick(int index, List<String> list) {
                if(showImagesDialog==null){
                    showImagesDialog =  new ShowImagesDialog(FillStyleActivity.this, index,list);
                }
               showImagesDialog.show();
            }
        });
        mPostAdapter.setOnItemLikeClickListener(new PostAdapter.OnItemLikeClick() {
            @Override
            public void OnLiked(HomeEntity homeEntity) {
                homeEntity.setLikeFlag(1);
                homeEntity.getLikeNameList().add("刘备");
                mPostAdapter.notifyDataSetChanged();
            }

            @Override
            public void OnDisLiked(HomeEntity homeEntity) {
                homeEntity.setLikeFlag(0);
                homeEntity.getLikeNameList().remove("刘备");
                mPostAdapter.notifyDataSetChanged();
            }
        });
        mRv.setAdapter(mPostAdapter);
    }

    private void createHomes(){
        mPostList = new ArrayList<>();
        for(int i=0;i<9;i++) {
            HomeEntity homeEntity = new HomeEntity();

            homeEntity.setIconUrl(ICONS[new Random().nextInt(ICONS.length)]);
            String name = NAMES[new Random().nextInt(NAMES.length)];
            homeEntity.setName(name);



            List<String> imgUrls = new ArrayList<>();
            imgUrls.addAll(Arrays.asList(IMG_URL_LIST).subList(0, i + 1));
            homeEntity.setNineUrlList(imgUrls);

            List<String> nameUrls = new ArrayList<>();
            nameUrls.addAll(Arrays.asList(NAMES).subList(0, i));
            homeEntity.setLikeNameList(nameUrls);

            homeEntity.setLikeFlag(nameUrls.contains("刘备") ? 1 : 0);
            long r = System.currentTimeMillis();
            long rl = r - i*24*60*60*1000;
            homeEntity.setTime(rl);

            mPostList.add(homeEntity);
        }

    }


    /**
     * 获取当前设备的屏幕密度等基本参数
     */
    protected void getDeviceDensity() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Config.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
        Config.EXACT_SCREEN_WIDTH = metrics.widthPixels;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(showImagesDialog != null){
            showImagesDialog.dismiss();
        }
    }
}