package com.sxjs.jd.composition.kpibefore.moreindicators;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
import com.sxjs.common.base.baseadapter.BaseViewHolder;
import com.sxjs.jd.R;
import com.sxjs.jd.entities.AttentionIndicatorsResponse;
import com.sxjs.jd.entities.JkxYuPingResponse;

import java.util.List;

/**
 * Created by admin on 2019/9/24.
 */

public class AttentionIndicatorsPreviewsAdapter extends BaseQuickAdapter<AttentionIndicatorsResponse.DataBean, BaseViewHolder> {

    public AttentionIndicatorsPreviewsAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final AttentionIndicatorsResponse.DataBean item, int position) {

        String type = item.getSIGN_TYPE();
        if ("0".equals(type)) {
            ((TextView) helper.getView(R.id.tv_value)).setTextColor(mContext.getResources().getColor(R.color.black));
            ((TextView) helper.getView(R.id.tv_content)).setTextColor(mContext.getResources().getColor(R.color.black));
            helper.getView(R.id.iv_type).setVisibility(View.GONE);
        } else if ("1".equals(type)) {
            ((TextView) helper.getView(R.id.tv_value)).setTextColor(mContext.getResources().getColor(R.color.indicator));
            ((TextView) helper.getView(R.id.tv_content)).setTextColor(mContext.getResources().getColor(R.color.indicator));
            ((ImageView) helper.getView(R.id.iv_type)).setImageResource(R.drawable.bj_leidian);
            helper.getView(R.id.iv_type).setVisibility(View.VISIBLE);
        } else if ("2".equals(type)) {
            ((TextView) helper.getView(R.id.tv_value)).setTextColor(mContext.getResources().getColor(R.color.exception));
            ((TextView) helper.getView(R.id.tv_content)).setTextColor(mContext.getResources().getColor(R.color.exception));
            ((ImageView) helper.getView(R.id.iv_type)).setImageResource(R.drawable.bj_warn);
            helper.getView(R.id.iv_type).setVisibility(View.VISIBLE);
        }

        helper.setText(R.id.tv_name, item.getINDICATOR_NAME());
        helper.setText(R.id.tv_value, item.getINDICATOR_VALUE());
        if (item.getINDICATOR_CONTENT() != null) {
            helper.getView(R.id.tv_content).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_content, item.getINDICATOR_CONTENT());
        } else {
            helper.getView(R.id.tv_content).setVisibility(View.GONE);
        }


    }


}
