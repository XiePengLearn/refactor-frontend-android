package com.sxjs.common.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.sxjs.common.R;

/**
 * @Auther: xp
 * @Date: 2019/9/15 08:04
 * @Description:
 */
public class SelectPicPopupWindow extends PopupWindow implements View.OnClickListener  {

    private Button btn_select_1, btn_select_2, btn_cancel;
    private View         mMenuView;
    private LinearLayout pop_layout ;
    private Context      context;
    private View         view;
    public SelectPicPopupWindow(Context context , View view) {
        super(context);
        this.context=context;
        this.view =view;
        mMenuView = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null);
        btn_select_1 = (Button) mMenuView.findViewById(R.id.btn_select_1);
        btn_select_2 = (Button) mMenuView.findViewById(R.id.btn_select_2);
        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
        pop_layout = (LinearLayout) mMenuView.findViewById(R.id.pop_layout);
        //取消按钮
        btn_cancel.setOnClickListener(this);
        //设置按钮监听
        btn_select_1.setOnClickListener(this);
        btn_select_2.setOnClickListener(this);
        //设置SelectPicPopupWindow的View
        setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                        pop_layout.clearAnimation();
                    }
                }
                return true;
            }
        });


    }
    public void showPopWindow(){
        pop_layout.startAnimation(AnimationUtils.loadAnimation(
                context, R.anim.activity_translate_in));
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
    /**
     * 设置选项内容
     * @param select1 选项一
     * @param select2  选项二
     * @param btnName
     */
    public void setData(String select1, String select2, String btnName) {
        if (!TextUtils.isEmpty(select1)) {
            btn_select_1.setText(select1);

        }
        if (!TextUtils.isEmpty(select2)) {
            btn_select_2.setText(select2);

        }
        if (!TextUtils.isEmpty(btnName)) {
            btn_cancel.setText(btnName);

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_cancel) {//销毁弹出框
            dismiss();
        } else if (i == R.id.btn_select_1) {//销毁弹出框
            onSelectItemOnclickListener.selectItem(btn_select_1.getText().toString());
            dismiss();
            pop_layout.clearAnimation();
        } else if (i == R.id.btn_select_2) {//销毁弹出框
            onSelectItemOnclickListener.selectItem(btn_select_2.getText().toString());
            dismiss();
            pop_layout.clearAnimation();
        }
    }
    private  OnSelectItemOnclickListener onSelectItemOnclickListener;
    public interface  OnSelectItemOnclickListener{
        void selectItem(String str);
    }

    public void setOnSelectItemOnclickListener(OnSelectItemOnclickListener onSelectItemOnclickListener) {
        this.onSelectItemOnclickListener = onSelectItemOnclickListener;
    }
}
