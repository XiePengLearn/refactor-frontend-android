package com.sxjs.jd.composition.kpibefore.national;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
import com.sxjs.common.base.baseadapter.BaseViewHolder;
import com.sxjs.jd.R;
import com.sxjs.jd.entities.JkxYuPingResponse;

/**
 * Created by admin on 2019/9/24.
 */

public class NationalPreviewsAdapter extends BaseQuickAdapter<JkxYuPingResponse.DataBean, BaseViewHolder> {

    public NationalPreviewsAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final JkxYuPingResponse.DataBean info, int position) {

        helper.setText(R.id.first_level_name, info.getINDICATION_NAME().get(0));
        helper.setText(R.id.second_level_name, info.getINDICATION_NAME().get(1));

        helper.setText(R.id.three_level_name, info.getINDICATION_NAME().get(2));

        LinearLayout linearLayout = helper.getView(R.id.zhibiao_value_layout);

        linearLayout.removeAllViews();
        for (int i = 0; i < info.getFILL_IN_ITEMS().size(); i++) {
            View zhibiao_value;
            if (i == info.getFILL_IN_ITEMS().size() - 1) {
                zhibiao_value = View.inflate(mContext, R.layout.jkx_zhibiao_value_item, null);
            } else {
                zhibiao_value = View.inflate(mContext, R.layout.jkx_zhibiao_value_item_no_radius, null);
            }
            TextView item_name = zhibiao_value.findViewById(R.id.item_name);
            item_name.setText(info.getFILL_IN_ITEMS().get(i).getITEM_NAME());
            TextView item_value = zhibiao_value.findViewById(R.id.item_value);
            item_value.setText(info.getFILL_IN_ITEMS().get(i).getITEM_VALUE());
            linearLayout.addView(zhibiao_value);
        }

        LinearLayout llView = helper.getView(R.id.zhibiao_desc_layout);
        llView.removeAllViews();

        for (int i = 0; i < info.getREMIND().size(); i++) {
            View zhibiao_desc = View.inflate(mContext, R.layout.jkx_zhibiao_desc_item, null);
            TextView content = zhibiao_desc.findViewById(R.id.content);
            content.setText((i + 1) + "." + info.getREMIND().get(i).getITEM_CONTENT());
            llView.addView(zhibiao_desc);
        }

    }


}
