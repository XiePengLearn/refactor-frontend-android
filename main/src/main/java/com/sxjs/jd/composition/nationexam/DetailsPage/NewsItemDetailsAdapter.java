package com.sxjs.jd.composition.nationexam.DetailsPage;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
import com.sxjs.common.base.baseadapter.BaseViewHolder;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.common.util.ToastUtil;
import com.sxjs.jd.R;
import com.sxjs.jd.composition.html.messagedetails.MessageWebViewActivity;
import com.sxjs.jd.entities.MessageNotificationResponse;
import com.sxjs.jd.entities.NewsListResponse;
import com.sxjs.jd.entities.PolicyElucidationResponse;

import java.util.List;

/**
 * Created by admin on 2017/3/22.
 */

public class NewsItemDetailsAdapter extends BaseQuickAdapter<NewsListResponse.DataBean.PAGEBean, BaseViewHolder> {

    public NewsItemDetailsAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final NewsListResponse.DataBean.PAGEBean item, int position) {
        helper.setText(R.id.tv_title, item.getTITLE());
        //            helper.setText(R.id.tv_count, "666阅读");
        helper.setText(R.id.tv_time, item.getDATE());
        RequestOptions options = new RequestOptions()
                .error(R.drawable.home_pic1);


        Glide.with(mContext)
                .load(item.getIMAGE_URI())
                .apply(options)
                .into((ImageView) helper.getView(R.id.iv_imgUrl));

        helper.addOnClickListener(R.id.find_item_layout);
        setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(adapter.getItem(position) instanceof  NewsListResponse.DataBean.PAGEBean ){
                    NewsListResponse.DataBean.PAGEBean item1 = (NewsListResponse.DataBean.PAGEBean) adapter.getItem(position);
                    String contentUri = item1.getCONTENT_URI();

                    String url;
                    if (!TextUtils.isEmpty(contentUri)) {
                        url = contentUri;
                    } else {
                        url = "";
                    }

                    Intent intent = new Intent(mContext, MessageWebViewActivity.class);
                    intent.putExtra("title", "详情");
                    intent.putExtra("url", url);
                    mContext.startActivity(intent);
                }



                return false;
            }
        });

    }


}
