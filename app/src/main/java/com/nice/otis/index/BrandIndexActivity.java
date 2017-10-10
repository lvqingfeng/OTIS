package com.nice.otis.index;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.ProgressSubscriber;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.api.SubscriberOnNextListener;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.BrandBean;
import com.nice.otis.databinding.ActivityBrandIndexBinding;
import com.nice.otis.ui.activity.BrandAttrActivity;
import com.nice.otis.utils.LoginHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BrandIndexActivity extends BaseActivity<ActivityBrandIndexBinding>
        implements WordsNavigation.onWordsChangeListener, AbsListView.OnScrollListener {
    private Handler handler;
    private List<Person> mList;
    private MyAdapter adapter;
    private List<BrandBean.InfoBean> mData;
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, BrandIndexActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("电梯列表",DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {

        mList=new ArrayList<>();
        mData=new ArrayList<>();
        initData();

        if (mList.size()>0){
            mDataBinding.listView.setOnScrollListener(this);
        }
        handler = new Handler();
        //设置列表点击滑动监听
        mDataBinding.words.setOnWordsChangeListener(this);

        mDataBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = mList.get(i).getName();
                for (int j = 0; j <mData.size(); j++) {
                    if (name.equals(mData.get(j).getTitle())){
                        String id = mData.get(j).getId();
                        BrandAttrActivity.actionStart(BrandIndexActivity.this,id,name);
                        break;
                    }
                }
            }
        });
    }

    private void initData(){
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateotisList(LoginHelper.getInstance().getUserToken()), new ProgressSubscriber<BrandBean>(mContext, new SubscriberOnNextListener<BrandBean>() {
            @Override
            public void onNext(BrandBean result) {
                if (result.getStatusCode()==1){
                    if (result.getInfo().size()>0){
                        mData.addAll(result.getInfo());
                        for (int i = 0; i <result.getInfo().size(); i++) {
                            mList.add(new Person(result.getInfo().get(i).getTitle()));
                        }
                        //对集合排序
                        Collections.sort(mList, new Comparator<Person>() {
                            @Override
                            public int compare(Person lhs, Person rhs) {
                                //根据拼音进行排序
                                return lhs.getPinyin().compareTo(rhs.getPinyin());
                            }
                        });
                        adapter = new MyAdapter(mContext, mList);
                        mDataBinding.listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }));
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_brand_index;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //当滑动列表的时候，更新右侧字母列表的选中状态
        mDataBinding.words.setTouchIndex(mList.get(firstVisibleItem).getHeaderWord());
    }

    @Override
    public void wordsChange(String words) {
        updateWord(words);
        updateListView(words);
    }
    /**
     * @param words 首字母
     */
    private void updateListView(String words) {
        for (int i = 0; i < mList.size(); i++) {
            String headerWord = mList.get(i).getHeaderWord();
            //将手指按下的字母与列表中相同字母开头的项找出来
            if (words.equals(headerWord)) {
                //将列表选中哪一个
                mDataBinding.listView.setSelection(i);
                //找到开头的一个即可
                return;
            }
        }
    }
    /**
     * 更新中央的字母提示
     *
     * @param words 首字母
     */
    private void updateWord(String words) {
        mDataBinding.tv.setText(words);
        mDataBinding.tv.setVisibility(View.VISIBLE);
        //清空之前的所有消息
        handler.removeCallbacksAndMessages(null);
        //1s后让tv隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDataBinding.tv.setVisibility(View.GONE);
            }
        }, 500);
    }
}
