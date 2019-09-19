package com.sxjs.jd.composition.message.attention;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
import com.sxjs.common.base.baseadapter.BaseViewHolder;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.jd.R;
import com.sxjs.jd.composition.html.homeweb.HomeWebViewActivity;
import com.sxjs.jd.composition.html.messagedetails.MessageWebViewActivity;
import com.sxjs.jd.entities.MessageNotificationResponse;

/**
 * Created by admin on 2017/3/22.
 */

public class AttentionAdapter extends BaseQuickAdapter<MessageNotificationResponse.DataBean, BaseViewHolder> {

    public AttentionAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final MessageNotificationResponse.DataBean info, int position) {
        //        helper.setText(R.id.tv_time,bean.title);
        //        helper.setText(R.id.title,bean.summary);
        //        helper.setText(R.id.news_img , bean.authorName);
        //        helper.setText(R.id.content , bean.showTime);
        //
        //        helper.setText(R.id.page_view_count , ""+bean.pageView);
        //        SimpleDraweeView simpleDraweeView = helper.getView(R.id.content_img);
        //        SimpleDraweeView authorImg = helper.getView(R.id.author_img);
        //        simpleDraweeView.setImageURI(bean.indexImage);
        //        authorImg.setImageURI(bean.authorPic);
        //        helper.addOnClickListener(R.id.find_item_layout);
        //
        //        setOnItemChildClickListener(new OnItemChildClickListener() {
        //            @Override
        //            public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        //                Toast.makeText(mContext, "第"+position+"页 ", Toast.LENGTH_SHORT).show();
        //                return false;
        //            }
        //        });


        helper.setText(R.id.tv_time, info.getDATE());
        helper.setText(R.id.title, info.getTITLE());
        helper.setText(R.id.content, info.getCONTENT());
        helper.getView(R.id.ll_newsContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String session_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
                String notifyUri = info.getNOTIFY_URI();

                String url = notifyUri + "&sessionId=" + session_id;
                Intent intent = new Intent(mContext, MessageWebViewActivity.class);
                intent.putExtra("title", "详情");
                intent.putExtra("url", url);
                mContext.startActivity(intent);

            }
        });
        helper.getView(R.id.news_img).setVisibility(View.VISIBLE);
        //                RequestOptions options = new RequestOptions()
        //                        .centerCrop()
        //                        .dontAnimate()
        //                        .placeholder(R.drawable.ic_add);
        RequestOptions options = new RequestOptions()
                .error(R.drawable.home_pic1);
        Glide.with(mContext)
                .load(info.getIMAGE_URI()).apply(options)
                .apply(options)
                .into((ImageView) helper.getView(R.id.news_img));
    }


}
