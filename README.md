# SwiepRefreshAndLoad
1.使用as的朋友添加以下依赖既可
   compile 'swipeRefreshAndLoad:swipeRefreshAndLoad:0.0.1'
   
 2.在xml使用以下代码
 
       <swipeRefreshAndLoad.SwipeRefreshAndLoad
        android:id="@+id/tempfrag_swiperefresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v7.widget.RecyclerView
            android:id="@+id/main_recycleview"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

    </swipeRefreshAndLoad.SwipeRefreshAndLoad>
    
  3.在activity中
      //设置下拉刷新的进度条颜色
        refreshLayout.setColorSchemeColors(Color.YELLOW,Color.RED,Color.GREEN,Color.BLUE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshAndLoad.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //hd是 handler
                hd.sendEmptyMessageDelayed(0,3000);
            }
        });
        //设置上啦加载的进度条颜色
        refreshLayout.setBottomColorSchemeColors(Color.GREEN,Color.BLUE,Color.YELLOW,Color.RED);
        //在这里新增上啦加载的回调
        refreshLayout.setOnBottomRefreshListenrer(new SwipeRefreshAndLoad.OnBottomRefreshListener() {
            @Override
            public void onBottomRefresh() {

                hd.sendEmptyMessageDelayed(1,3000);

            }
        });

//handler的代码
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
