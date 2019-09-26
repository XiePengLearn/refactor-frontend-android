package com.sxjs.jd.composition.kpihome.indexranking;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxjs.common.base.baseadapter.BaseQuickAdapter;
import com.sxjs.common.base.baseadapter.BaseViewHolder;
import com.sxjs.jd.R;
import com.sxjs.jd.entities.IndexRankingResponse;

import java.util.List;

/**
 * Created by admin on 2019/9/24.
 */

public class IndexRankingPreviewsAdapter extends BaseQuickAdapter<IndexRankingResponse.DataBean.INDICATIONBeanX, BaseViewHolder> {

    public IndexRankingPreviewsAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, final IndexRankingResponse.DataBean.INDICATIONBeanX info, int position) {


        List<String> indication_name = info.getINDICATION_NAME();
        if (indication_name != null) {
            if (indication_name.size() >= 2) {
                helper.setText(R.id.first_level_name, indication_name.get(0) + "/" + indication_name.get(1));
            }
        }

        LinearLayout linearLayout = helper.getView(R.id.three_level_layout);
        linearLayout.removeAllViews();
        List<IndexRankingResponse.DataBean.INDICATIONBeanX.CHILDRENBean> children = info.getCHILDREN();
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                View zhibiao_value = View.inflate(mContext, R.layout.jkx_zhibiao_sort_warn_item2, null);
                linearLayout.addView(zhibiao_value);

                TextView three_level_name = zhibiao_value.findViewById(R.id.three_level_name);
                List<String> indication_name1 = children.get(i).getINDICATION_NAME();
                if (indication_name1.size() > 0) {
                    three_level_name.setText(indication_name1.get(0));
                }


                ((LinearLayout) zhibiao_value.findViewById(R.id.forth_level_layout)).removeAllViews();
                List<IndexRankingResponse.DataBean.INDICATIONBeanX.CHILDRENBean.INDICATIONBean> indication = children.get(i).getINDICATION();
                if (indication != null) {
                    for (int j = 0; j < indication.size(); j++) {

                        IndexRankingResponse.DataBean.INDICATIONBeanX.CHILDRENBean.INDICATIONBean bean = indication.get(j);
                        View middle_value = View.inflate(
                                mContext, R.layout.jkx_zhibiao_sort_warn_item1, null);
                        ((LinearLayout) zhibiao_value.findViewById(R.id.forth_level_layout)).addView(middle_value);
                        TextView tonglei_paiming = middle_value.findViewById(R.id.tonglei_paiming);
                        TextView tonglei_paiming1 = middle_value.findViewById(R.id.tonglei_paiming1);
                        TextView tonglei_yuan_paiming = middle_value.findViewById(R.id.tonglei_yuan_paiming);
                        TextView diqu_paiming = middle_value.findViewById(R.id.diqu_paiming);
                        TextView diqu_paiming1 = middle_value.findViewById(R.id.diqu_paiming1);
                        TextView diqu_yuan_paiming = middle_value.findViewById(R.id.diqu_yuan_paiming);
                        TextView four_level_name = middle_value.findViewById(R.id.four_level_name);
                        TextView four_level_value = middle_value.findViewById(R.id.four_level_value);

                        four_level_name.setText(bean.getINDICATION_NAME().get(0));
                        four_level_value.setText(bean.getINDICATION_VALUE());
                        boolean flag = false;
                        int tPM = 0;    //同类医院排名
                        int tyPM = 0;   //同类医院原排名
                        int dPM = 0;   //地区医院排名
                        int dyPM = 0;   //地区医院原排名
                        // flag = TextUtils.isDigitsOnly(bean.getSAMETYPE_HOSPITAL_RANK());
                        //if (flag) {
                        tPM = Integer.parseInt(bean.getSAMETYPE_HOSPITAL_RANK());
                        // }
                        // flag = TextUtils.isDigitsOnly(bean.getSAMETYPE_HOSPITAL_OLD_RANK());
                        // if (flag) {
                        tyPM = Integer.parseInt(bean.getSAMETYPE_HOSPITAL_OLD_RANK());
                        // }
                        if (tPM > tyPM) {
                            tonglei_paiming.setText(bean.getSAMETYPE_HOSPITAL_RANK());
                            tonglei_paiming.setVisibility(View.VISIBLE);
                            tonglei_paiming1.setText(bean.getSAMETYPE_HOSPITAL_RANK());
                            tonglei_paiming1.setVisibility(View.GONE);
                        } else {
                            tonglei_paiming.setText(bean.getSAMETYPE_HOSPITAL_RANK());
                            tonglei_paiming.setVisibility(View.GONE);
                            tonglei_paiming1.setText(bean.getSAMETYPE_HOSPITAL_RANK());
                            tonglei_paiming1.setVisibility(View.VISIBLE);
                        }
                        tonglei_yuan_paiming.setText(bean.getSAMETYPE_HOSPITAL_OLD_RANK() + " " + "原排名");

                        //   flag = TextUtils.isDigitsOnly(bean.getPREFECTURE_HOSPITAL_RANK());
                        // if (flag) {
                        dPM = Integer.parseInt(bean.getPREFECTURE_HOSPITAL_RANK());
                        //   }
                        //  flag = TextUtils.isDigitsOnly(bean.getPREFECTURE_HOSPITAL_OLD_RANK());
                        //  if (flag) {
                        dyPM = Integer.parseInt(bean.getPREFECTURE_HOSPITAL_OLD_RANK());
                        //  }
                        if (dPM > dyPM) {
                            diqu_paiming.setText(bean.getPREFECTURE_HOSPITAL_RANK());
                            diqu_paiming.setVisibility(View.VISIBLE);
                            diqu_paiming1.setText(bean.getPREFECTURE_HOSPITAL_RANK());
                            diqu_paiming1.setVisibility(View.GONE);
                        } else {
                            diqu_paiming.setText(bean.getPREFECTURE_HOSPITAL_RANK());
                            diqu_paiming.setVisibility(View.GONE);
                            diqu_paiming1.setText(bean.getPREFECTURE_HOSPITAL_RANK());
                            diqu_paiming1.setVisibility(View.VISIBLE);
                        }
                        diqu_yuan_paiming.setText(bean.getPREFECTURE_HOSPITAL_OLD_RANK() + " " + "原排名");
                    }
                }


            }
        }


    }


}
