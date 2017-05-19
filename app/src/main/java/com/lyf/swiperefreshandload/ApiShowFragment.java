package com.lyf.swiperefreshandload;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import swipeRefreshAndLoad.SwipeRefreshAndLoad;


/**
 * Created by gzoom on 2016/11/27.
 */

public class ApiShowFragment extends Fragment {

    @Bind(R.id.tempfrag_swiperefresh)
    SwipeRefreshAndLoad refreshLayout;

    @Bind(R.id.main_recycleview)
    RecyclerView recyclerView;

    /**这里不写完整试试，看看他会不会补http(结果报错了哈哈哈哈)*/
    String BaseUrl="http://gank.io/api/";


    /**context*/
    Context context;

    /**下部的加载标记*/
    boolean footIsLoad = false;

    /**间隔，从可视到需要加载的间隔*/
    static int FOOTNUM = 3;

    /**访问页数*/
    int pageRead = 0;

    /**访问数目*/
    int readNum = 10;


    /**测试用*/
    TempAdapter tempAdapter;

    Handler hd = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if(refreshLayout.isRefreshing())
                        //停止刷新
                        refreshLayout.setRefreshing(false);
                    break;
                case 1:
                    //停止上啦加载
                    refreshLayout.setBottomRefreshing(false);
                    //设置加载错误的点击事件
                    refreshLayout.setError(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            refreshLayout.setBottomRefreshing(true);
                        }
                    });
                    break;
            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.temp_fag,container,false);
        ButterKnife.bind(this,view);
      //  datas = new AndroidMainData();
    /*   recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
               super.onScrolled(recyclerView, dx, dy);
               //计算是否到达底部
               //这个判断方法不行，如果往回拉还是一样会调用刷新
               LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
               int totalCount = manager.getItemCount();
               int last = manager.findLastVisibleItemPosition();
                if(!footIsLoad&&totalCount<=(last+FOOTNUM))
                {
                    loadNewData();
                }

           }
       });*/
        //initAPI();
        initRefreshLayout();
        //初始化recycleview
        initRecycleView();
        //开始加载
     //   loadData();
        return view;
    }



    /**初始化刷新控件*/
    private void initRefreshLayout() {
        //设置下拉刷新的进度条颜色
        refreshLayout.setColorSchemeColors(Color.YELLOW,Color.RED,Color.GREEN,Color.BLUE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshAndLoad.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //loadData();
                //cancleLoading();
                hd.sendEmptyMessageDelayed(0,3000);
            }
        });
        //设置上啦加载的进度条颜色
        refreshLayout.setBottomColorSchemeColors(Color.GREEN,Color.BLUE,Color.YELLOW,Color.RED);
        refreshLayout.setRefreshBottom(false);
        //在这里新增上啦加载的回调
        refreshLayout.setOnBottomRefreshListenrer(new SwipeRefreshAndLoad.OnBottomRefreshListener() {
            @Override
            public void onBottomRefresh() {

                hd.sendEmptyMessageDelayed(1,3000);

            }
        });
    }

    /**加载新数据*/
    private void loadNewData() {
        Snackbar.make(recyclerView,"需要加载了",Snackbar.LENGTH_SHORT).show();

    }

    /**取消加载*/
    public void cancleLoading()
    {

        refreshLayout.setBottomRefreshing(false);

        refreshLayout.setError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refreshLayout.setBottomRefreshing(true);
            }
        });
    }




    /**加载数据*/
  /*  private void loadData() {
        applyApi.getAndroidNews(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AndroidMainData>() {
                    @Override
                    public void call(AndroidMainData androidMainData) {
                        adapterone.updateDatas(androidMainData);
                        cancleLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        cancleLoading();
                    }
                });
      //  cancleLoading(*);
    }*/

    /**初始化recycleview*/
    private void initRecycleView() {
//        datas.results=new ArrayList<>();
     //   adapterone  = new AndroidMainAdapter(datas,context);
        List<String>tempS = new ArrayList<>();
        for(int i = 0; i<10 ;i++)
        {
            tempS.add("GZ");
        }
        tempAdapter = new TempAdapter(context,tempS);
        //recyclerView.setAdapter(adapterone);
        recyclerView.setAdapter(tempAdapter);
        LinearLayoutManager man = new LinearLayoutManager(context);
        man.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(man);

    }

    /**初始化API*/
   /* private void initAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();

                //.client() 先不设置代理看看会不会有错
        //不会有错但是时间控制不好设置
        applyApi = retrofit.create(ApplyAPI.class);

    }*/

/* private OkHttpClient getOkHttpClient()
    {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里是设置日志等级，可以点进去看，有body\basic等等，
        //这里body是打印网络信息的head还有body
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//出错了的话自己连接
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                //.cache()这里我不用缓存，我觉得吧有数据库了不就行了。。。
                .build();

    }*/

}
