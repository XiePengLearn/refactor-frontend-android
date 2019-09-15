package com.sxjs.common.util;

import android.text.TextUtils;

/**
 * @Auther: xp
 * @Date: 2019/9/15 08:21
 * @Description: 手机号码格式校验工具类
 *
 */
public class PhoneValidator {
    /**
     * 校验手机号码
     *
     * @param phone 手机号码
     * @return 正确则返回NULL，否则返回错误信息
     */
    // public static String validate(String phone) {
    //
    // if (TextUtils.isEmpty(phone)) {
    // return "手机号码不能为空";
    // }
    //
    // /*
    // * /**
    // * 手机号码
    // * 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
    // * 联通：130,131,132,152,155,156,185,186
    // * 电信：133,1349,153,180,189
    // */
    // String mobile = "^1(3[0-9]|5[0-35-9]|8[025-9])\\d{8}$";
    // /*
    // * 中国移动：China Mobile
    // * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188,183
    // */
    // String cm = "^1(34[0-8]|(3[5-9]|5[017-9]|8[2378])\\d)\\d{7}$";
    // /*
    // * 中国联通：China Unicom
    // * 130,131,132,152,155,156,185,186
    // */
    // String cu = "^1(3[0-2]|5[256]|8[56])\\d{8}$";
    // /*
    // * 中国电信：China Telecom
    // * 133,1349,153,180,189
    // */
    // String ct = "^1((33|53|8[019])[0-9]|349)\\d{7}$";
    // // String regex = "^(1(([35][0-9])|(47)|[8][0126789]))\\d{8}$";
    // boolean flag1 = Pattern.compile(mobile).matcher(phone).matches();
    // boolean flag2 = Pattern.compile(cm).matcher(phone).matches();
    // boolean flag3 = Pattern.compile(cu).matcher(phone).matches();
    // boolean flag4 = Pattern.compile(ct).matcher(phone).matches();
    // if (!flag1 & !flag2 & !flag3 & !flag4) {
    // return "手机号码格式有误";
    // }
    // return null;
    // }
    public static String validate(String phone) {

        if (TextUtils.isEmpty(phone)) {
            return "请填写手机号码";
        }

        if (!isMobileNO(phone)) {
            return "手机号码格式有误";
        }

        return null;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
/*
移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
联通：130、131、132、152、155、156、185、186、166
电信：133、153、180、189、199、（1349卫通）/^0?1[3|4|5|7|8][0-9]\d{8}$/
总结起来就是第一位必定为1，第二位必定为3或5或8或7（电信运营商），其他位置的可以为0-9
*/
        String telRegex = "[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }
}
