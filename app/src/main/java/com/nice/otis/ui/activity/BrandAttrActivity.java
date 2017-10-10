package com.nice.otis.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nice.otis.R;
import com.nice.otis.api.ApiClient;
import com.nice.otis.api.ApiService;
import com.nice.otis.api.ProgressSubscriber;
import com.nice.otis.api.RetrofitManager;
import com.nice.otis.api.SubscriberOnNextListener;
import com.nice.otis.base.BaseActivity;
import com.nice.otis.bean.BrandAttrBean;
import com.nice.otis.databinding.ActivityBrandAttrBinding;
import com.nice.otis.recycler_listener.OnRecyclerItemClickListener;
import com.nice.otis.ui.adapter.BrandAttrAdapter;
import com.nice.otis.utils.LoginHelper;
import com.nice.otis.view.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class BrandAttrActivity extends BaseActivity<ActivityBrandAttrBinding> {
    private String id;
    private String brand;
    private List<BrandAttrBean.InfoBean> mList;
    private BrandAttrAdapter brandAttrAdapter;

    public static void actionStart(Activity activity, String id,String brand) {
        Intent intent = new Intent(activity, BrandAttrActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("brand",brand);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupTitle() {
        openTitleLeftView(true);
        setTextTitleView("型号查询", DEFAULT_TITLE_TEXT_COLOR);
    }

    @Override
    protected void initViews() {
        id = getIntent().getStringExtra("id");
        brand=getIntent().getStringExtra("brand");
        mList = new ArrayList<>();
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDataBinding.recyclerView.addItemDecoration(new DividerGridItemDecoration(mContext));
        brandAttrAdapter = new BrandAttrAdapter(mList);
        mDataBinding.recyclerView.setAdapter(brandAttrAdapter);
        loadData();

        mDataBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mDataBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                String attr_id = mList.get(vh.getLayoutPosition()).getId();
                String model=mList.get(vh.getLayoutPosition()).getTitle();
                TroubleshootingActivity.actionStart(BrandAttrActivity.this, id, attr_id,brand,model);
            }
        });
    }

    private void loadData() {
        RetrofitManager.toSubscribe(ApiClient.getApiService(ApiService.class, RetrofitManager.RetrofitType.Object)
                .updateBrandAttrList(id, LoginHelper.getInstance().getUserToken()), new ProgressSubscriber<BrandAttrBean>(mContext, new SubscriberOnNextListener<BrandAttrBean>() {
            @Override
            public void onNext(BrandAttrBean result) {
                if (result.getStatusCode() == 1) {
                    mList.addAll(result.getInfo());
                    brandAttrAdapter.notifyDataSetChanged();
                }
            }
        }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_brand_attr;
    }
}
