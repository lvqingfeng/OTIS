package com.nice.otis.utils;

import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liaoinstan.springview.widget.SpringView;

import java.lang.ref.WeakReference;

/**
 * 作者： lv
 * 创建日期： 2016/4/11
 * 时间：19:29
 * 版本 v1.0.0
 * 类描述：用于上拉加载，做延时操作的Handler
 * 修改时间：
 * <p/>
 * **********使用教程***********
 * <p/>
 * 这是关于刷新控件的上拉加载和下拉刷新的Handler
 * <p/>
 * 1，直接定义一个成员变量并且实例化它
 * 2，定义一个成员变量isEnd用来判断是否为最后一页（如果是最后一页，后台的end标签会返回一个1）
 * 3，使用Message将这两个参数传递过来(isEnd和)
 * 4，在getListDatas（）方法中只需要调用
 * <p/>
 * if (page == 1){
 * mListData.clear();
 * }
 * getListData(page);当前Activity获取列表数据的方法
 * <p/>
 * 注意：1.要在当前的Activity中在加载完数据后，调用refresh的onRefreshComplete方法
 * 2.传入的PullToRefreshBase对象一定要在初始化完成后再传入
 */
public abstract class RefreshHandler extends Handler {

    /**
     * 发送handler的what标签
     */
    //下拉刷新
    private static final int DOWN_REFRESH = 0;
    //上拉加载
    private static final int UP_UPDATE = 1;

    /**
     * 默认加载时间
     */
    //下拉刷新
    private static int DOWN_DEFAULT_TIME = 800;
    //上拉加载
    private static int UP_DEFAULT_TIME = 200;

    //private PullToRefreshBase mRefreshListView;
    private WeakReference<ViewGroup> mRefreshView;

    private int mPage = 1;

    public RefreshHandler(ViewGroup refreshView) {
        mRefreshView = new WeakReference<>(refreshView);
    }


    @Override
    public void handleMessage(Message msg) {
        if (mRefreshView.get() == null) return;

        //用来判断
        int index = msg.what;

        boolean isEndPage = false;//判断是否为最后一页

        Object obj = msg.obj;
        if (obj != null) {
            isEndPage = (boolean) obj;
        }
        switch (index) {
            case UP_UPDATE://上拉加载数据

                mPage++;
                //如果已经是最后一页了，就让页码减一，然后弹出土司提示
                if (isEndPage) {
                    mPage--;
                    Toast.makeText(mRefreshView.get().getContext(), "哥,这回真没了^_^！", Toast.LENGTH_SHORT).show();
                    if (mRefreshView.get() instanceof SpringView) {
                        ((SpringView) mRefreshView.get()).onFinishFreshAndLoad();
                    }
                    //否则不是最后一页时，调用抽象方法，进行页面数据加载
                } else {
                    getListDatas(mPage);//当上拉时分页加载网络数据
                }

                break;
            //当执行下拉刷新的时候，将页码数值重置为1并在此调用抽象方法进行页面数据加载
            case DOWN_REFRESH://下拉刷新数据

                resetPage();
                getListDatas(mPage);

                return;
        }
        //当为最后一页时，将不再去请求数据，那么RefreshListView的onRefreshComplete方法也就不会被执行，
        // 所以在此做判断，如果是最后一页的时候就让它在这里停止刷新动画
//
//        else if (mRefreshView.get() instanceof XRecyclerView) {
//            ((XRecyclerView) mRefreshView.get()).setNoMore(true);
//        }   这一部分可以加在springView的停止刷新的后面
        if (isEndPage && mRefreshView != null) {
            if (mRefreshView.get() instanceof SpringView) {
                ((SpringView) mRefreshView.get()).onFinishFreshAndLoad();
            }
        }
    }

    public abstract void getListDatas(int page);


    /**
     * 重载下拉刷新时的处理
     *
     * @param isEnd
     */
    public void onPullDownToRefresh(boolean isEnd) {
        Message message = Message.obtain();
        message.what = DOWN_REFRESH;
        message.obj = isEnd;
        onPullDownToRefresh(message);
    }

    /**
     * 下拉刷新时做出的处理
     *
     * @param msg
     */
    public void onPullDownToRefresh(Message msg) {
        if (msg == null) {
            sendEmptyMessage(DOWN_REFRESH);
        } else {
            this.sendMessageDelayed(msg, DOWN_DEFAULT_TIME);
        }
    }

    /**
     * 重载上拉加载时做出的处理
     *
     * @param isEnd
     */
    public void onPullUpToRefresh(boolean isEnd) {
        Message message = Message.obtain();
        message.what = UP_UPDATE;
        message.obj = isEnd;
        onPullUpToRefresh(message);
    }

    /**
     * 上拉加载时做出的处理
     *
     * @param msg
     */
    public void onPullUpToRefresh(Message msg) {
        if (msg == null) {
            sendEmptyMessage(UP_UPDATE);
        } else {
            this.sendMessageDelayed(msg, UP_DEFAULT_TIME);
        }
    }

    public void onActivityFinish() {
        removeMessages(UP_UPDATE);
        removeMessages(DOWN_REFRESH);
        mRefreshView = null;
    }

    public void resetPage() {
        mPage = 1;
    }

}
