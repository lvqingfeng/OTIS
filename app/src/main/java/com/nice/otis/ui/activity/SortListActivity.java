package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.ProgressSubscriber;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.api.SubscriberOnNextListener;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.BrandBean;
import com.nice.otis.bean.Girl;
import com.nice.otis.databinding.ActivitySortListBinding;
import com.nice.otis.ui.adapter.IndexAdapter;
import com.nice.otis.utils.LoginHelper;
import com.nice.otis.view.IndexBar;

import java.util.ArrayList;
import java.util.List;

public class SortListActivity extends BaseActivity<ActivitySortListBinding> {
    private List<BrandBean.InfoBean> mList;
    private ArrayList<String> letters = new ArrayList<>();
    private ArrayList<Girl> mPersons=new ArrayList<>();
    private IndexAdapter indexAdapter;

    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, SortListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("列表", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        fillNameAndSort();
        indexAdapter = new IndexAdapter(mPersons,mContext);
        mDataBinding.listView.setAdapter(indexAdapter);


        mDataBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = mList.get(i).getId();
            }
        });
    }
    private Handler mHander = new Handler();
    protected void showTextView(String letter) {
        mDataBinding.tvLetter.setVisibility(View.VISIBLE);
        mDataBinding.tvLetter.setText(letter);
        mHander.removeCallbacksAndMessages(null);
        mHander.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDataBinding.tvLetter.setVisibility(View.INVISIBLE);
            }
        }, 600);

    }
    private void fillNameAndSort() {
        mList=new ArrayList<>();
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class
                , RetrofitManager.RetrofitType.Object).updateotisList(LoginHelper.getInstance()
                .getUserToken()),new ProgressSubscriber<>(mContext, new SubscriberOnNextListener<BrandBean>() {
            @Override
            public void onNext(BrandBean result) {
                if (result.getStatusCode()==1){
                    if (result.getInfo().size()>0){
                        mList.addAll(result.getInfo());
                        for (int i = 0; i < result.getInfo().size(); i++) {
                            Girl girl = new Girl(result.getInfo().get(i).getTitle());
                            mPersons.add(girl);
                        }
                        mDataBinding.indexBar.setLetters(letters);
                        // 填充拼音, 排序
                        mDataBinding.indexBar.setOnLetterChangeListener(new IndexBar.OnLetterChangeListener() {
                            @Override
                            public void onLetterChange(int position, String letter) {
                                showTextView(letter);
                                for(int i=0; i<mPersons.size(); i++) {
                                    String firstPinyin = String.valueOf(mPersons.get(i).getPinyin().charAt(0));
                                    if(TextUtils.equals(letter, firstPinyin)) {
                                        mDataBinding.listView.setSelection(i);
                                        break;
                                    }
                                }
                            }
                        });
                        indexAdapter.notifyDataSetChanged();
                    }
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sort_list;
    }
}
