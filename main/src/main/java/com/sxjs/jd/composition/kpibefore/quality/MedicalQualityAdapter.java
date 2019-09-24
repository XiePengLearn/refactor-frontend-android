package com.sxjs.jd.composition.kpibefore.quality;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
import com.sxjs.common.base.baseadapter.BaseViewHolder;
import com.sxjs.common.util.PrefUtils;
import com.sxjs.jd.R;
import com.sxjs.jd.composition.html.messagedetails.MessageWebViewActivity;
import com.sxjs.jd.entities.MedicalQualityResponse;
import com.sxjs.jd.entities.MessageNotificationResponse;

/**
 * Created by admin on 2019/9/24.
 */

public class MedicalQualityAdapter extends BaseQuickAdapter<MedicalQualityResponse.DataBean.CLASSIFYBean, BaseViewHolder> {

    public MedicalQualityAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final MedicalQualityResponse.DataBean.CLASSIFYBean item, int position) {
        helper.setText(R.id.tv_CLASSIFY, item.getCLASSIFY());
        LinearLayout linearLayout = helper.getView(R.id.ll_item);
        linearLayout.removeAllViews();
        for (int i = 0; i < item.getPROJECT().size(); i++) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.jkx_record_quality_childitem, null);
            TextView tv_name = inflate.findViewById(R.id.tv_name);
            tv_name.setText(item.getPROJECT().get(i).getNAME());
            TextView tv_value = inflate.findViewById(R.id.tv_value);
            tv_value.setText(item.getPROJECT().get(i).getVALUE());
            linearLayout.addView(inflate);
        }
    }


}
