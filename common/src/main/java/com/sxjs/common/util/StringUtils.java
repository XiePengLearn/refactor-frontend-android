package com.sxjs.common.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: xp
 * @Date: 2019/9/15 09:25
 * @Description:
 */
public class StringUtils {

    /**
     * 电话号码中间四位****号显示
     */
    public static String getXPhoneNum(String phoneNum) {

        if (phoneNum == null) {
            return "";
        }
        if (phoneNum.length() == 11) {
            return phoneNum.substring(0, 3) + "****" + phoneNum.substring(7);

        }
        return phoneNum;
    }

    public static String getDateString(String date) {
        if (date == null) {
            return date;
        }
        return date.replace('-', '.');
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 处理字符串
     *
     * @param str
     * @return
     */
    public static String isNullStr(String str) {
        if (str == null || str.equals(null) || str.equals("null")) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 处理数字类字符串
     *
     * @param str
     * @return
     */
    public static String isNullNumber(String str) {
        if (str == null || str.equals(null) || str.equals("null")) {
            return "0.00";
        } else {
            return str;
        }
    }

    /**
     * 根据身份证号获取年龄
     *
     * @param sfcard 身份证号
     * @return 年龄
     */
  /*  public static String sFcardToAge(String sfcard) {
        if (TextUtils.isEmpty(sfcard)) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String years = simpleDateFormat.format(new Date());
        if (sfcard.length() == 15) {
            // 获取出生日期
            int year = Integer.parseInt("19" + sfcard.substring(6, 8));
            // 获取年龄
            int age = Integer.parseInt(years) - year;
            return age + "";
        } else if (sfcard.length() == 18) {
            // 获取出生年份
            int year = Integer.parseInt(sfcard.substring(6, 10));
            int age = Integer.parseInt(years) - year;
            return age + "";
        } else {
            return "";
        }
    }*/

    /**
     * 根据生日计算当前周岁数
     */
    public static String sFcardToAge(String sfcard) {
        if (TextUtils.isEmpty(sfcard)) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date birthday;
        try {
            if (sfcard.length() == 15) {
                // 获取出生日期
                birthday = simpleDateFormat.parse("19" + sfcard.substring(6, 12));

            } else if (sfcard.length() == 18) {
                birthday = simpleDateFormat.parse(sfcard.substring(6, 14));
            } else {
                return "";
            }
            // 当前时间
            Calendar curr = Calendar.getInstance();
            // 生日
            Calendar born = Calendar.getInstance();
            born.setTime(birthday);
            // 年龄 = 当前年 - 出生年
            int age = curr.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (age <= 0) {
                return "0";
            }
            // 如果当前月份小于出生月份: age-1
            // 如果当前月份等于出生月份, 且当前日小于出生日: age-1
            int currMonth = curr.get(Calendar.MONTH);
            int currDay = curr.get(Calendar.DAY_OF_MONTH);
            int bornMonth = born.get(Calendar.MONTH);
            int bornDay = born.get(Calendar.DAY_OF_MONTH);
            if ((currMonth < bornMonth) || (currMonth == bornMonth && currDay <= bornDay)) {
                age--;
            }
            return age < 0 ? "0" : age + "";

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据身份证算性别
     *
     * @param sfcard
     * @return
     */
    public static String sFcardToSex(String sfcard) {
        String gender = "";
        if (sfcard.length() == 15) {
            String str = sfcard.substring(13, 14);

            if (Integer.parseInt(str) % 2 != 0) {
                gender = "1";
            } else {
                gender = "2";
            }
            return gender;
        } else if (sfcard.length() == 18) {
            String str = sfcard.substring(16, 17);
            if (Integer.parseInt(str) % 2 != 0) {
                gender = "1";
            } else {
                gender = "2";
            }
            return gender;
        } else {
            return "";
        }
    }

    /**
     * 处理日期类字符串
     *
     * @param str
     * @return
     */
    public static String interceptDate(String str) {
        if (str == null || str.equals(null) || str.equals("null")) {
            return "";
        } else if (str.indexOf("T") >= 0) {
            str = str.substring(0, str.indexOf("T"));
        }
        return str;
    }

    /**
     * 身份证号前三位后三位显示中间用*显示
     */
    public static String getXIDCard(String idcard) {
        if (idcard == null) {
            return "";
        }

        if (idcard.length() == 15) {
            return idcard.substring(0, 3) + "*********" + idcard.substring(12);
        }
        if (idcard.length() == 18) {
            return idcard.substring(0, 3) + "************" + idcard.substring(15);
        }
        return idcard;
    }

    /**
     * 根据身份证验证年龄 和性别
     *
     * @param idCard 输入的身份证号
     * @param sex    输入的性别(男 ，女)
     * @param age    输入的年龄
     * @return
     */
    public static boolean verifySaveInformation(String idCard, String sex,
                                                String age, Context context) {
        if (idCard == null) {
            return false;
        }
        if (TextUtils.isEmpty(sex)) {
            return true;
        }
        if (TextUtils.isEmpty(age)) {
            return true;
        }
        if (idCard.length() == 15) {
            return SaveInformationBy15bit(idCard, sex, age, context);
        } else if (idCard.length() == 18) {
            return SaveInformationBy18bit(idCard, sex, age, context);
        } else {
            ToastUtil.showToast(context, "输入的身份证号不正确", Toast.LENGTH_SHORT);
            return false;
        }
    }

    /**
     * 15位的身份证校验 性别与出生日期
     *
     * @param idCard
     * @param context
     * @return
     */
    public static boolean SaveInformationBy15bit(String idCard, String sex,
                                                 String age, Context context) {
        String id14 = idCard.substring(13, 14);
        String gender = "";
        if (Integer.parseInt(id14) % 2 != 0) {
            gender = "1";
        } else {
            gender = "2";
        }
        if ("男".equals(sex)) {
            sex = "1";
        } else {
            sex = "2";
        }
        if (gender.equals(sex)) {
            return verifyAge15(idCard, age, context);
        } else {
            ToastUtil.showToast(context, "身份证性别与选择性别不匹配", Toast.LENGTH_SHORT);
            return false;
        }
    }

    /**
     * 18位的身份证校验 性别与出生日期
     *
     * @param idcard
     * @param age
     * @param sex
     * @param context
     * @return
     */
    public static boolean SaveInformationBy18bit(String idcard, String sex,
                                                 String age, Context context) {

        // 获取性别
        String id17 = idcard.substring(16, 17);
        String gender = "";
        if (Integer.parseInt(id17) % 2 != 0) {
            gender = "1";
        } else {
            gender = "2";
        }
        if ("男".equals(sex)) {
            sex = "1";
        } else {
            sex = "2";
        }
        if (gender.equals(sex)) {
            return verifyAge18(idcard, age, context);
        } else {
            ToastUtil.showToast(context, "身份证性别与选择性别不匹配", Toast.LENGTH_SHORT);
            return false;
        }
    }

    /**
     * 15位身份证验证年龄
     *
     * @param idCard
     * @param age
     * @param context
     * @return
     */
    public static boolean verifyAge15(String idCard, String age, Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String years = simpleDateFormat.format(new Date());
        // 获取出生日期
        int year = Integer.parseInt("19" + idCard.substring(6, 8));
        // 获取年龄
        int lAge = Integer.parseInt(years) - year;
        if (lAge == Integer.parseInt(age)) {
            return true;
        } else {
            ToastUtil.showToast(context, "输入年龄与身份证不符", Toast.LENGTH_SHORT);
            return false;
        }
    }

    /**
     * 18位身份证验证年龄
     *
     * @param idCard
     * @param age
     * @param context
     * @return
     */
    public static boolean verifyAge18(String idCard, String age, Context context) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String years = simpleDateFormat.format(new Date());
        int year = Integer.parseInt(idCard.substring(6, 10));
        int lAge = Integer.parseInt(years) - year;
        if (lAge == Integer.parseInt(age)) {
            return true;
        } else {
            ToastUtil.showToast(context, "输入年龄与身份证不符", Toast.LENGTH_SHORT);
            return false;
        }
    }

    /**
     * 通过身份证获取出生日期
     *
     * @param sfcard 身份证
     * @return 出生日期 格式 1992-03-12
     */
    public static String birthdayFromSfcard(String sfcard) {
        String birthday = "";
        if (TextUtils.isEmpty(sfcard)) {
            return "";
        }
        if (sfcard.length() == 15) {
            birthday = "19" + sfcard.substring(6, 8) + "-" + sfcard.substring(8, 10) + "-" + sfcard.substring(10, 12);

        } else if (sfcard.length() == 18) {
            birthday = sfcard.substring(6, 10) + "-" + sfcard.substring(10, 12) + "-" + sfcard.substring(12, 14);

        }

        return birthday;
    }

    /**
     * 根据数字返回对应的性别
     *
     * @param number
     * @return 1、 男 2 、女
     */
    public static String sexFromNumber(String number) {
        if ("1".equals(number)) {
            return "男";
        } else if ("2".equals(number)) {
            return "女";
        } else {
            return "";
        }
    }

    /**
     * 根据性别返回对应的数字
     *
     * @param sex
     * @return 1、 男 2 、女
     */
    public static String numberFromSex(String sex) {
        if ("男".equals(sex)) {
            return "1";
        } else if ("女".equals(sex)) {
            return "2";
        } else {
            return "";
        }
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

    /**
     * 根据传入的字符串返回相应的数字
     *
     * @param str
     * @return
     */
    public static int numFromString(String str) {
        return TextUtils.isEmpty(str) ? 0 : TextUtils.isDigitsOnly(str) ? Integer.valueOf(str) : 0;
    }


    /**
     * 密码验证规则
     * 密码长度8-16个字符，要包含大小写字母和数字
     *
     * @param pws
     * @return
     */
    public static boolean isPasswordRegex(String pws) {
        // String pwsRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$"; //密码长度8-16个字符，要包含大小写字母和数字
        String pwsRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";//密码长度6-16个字符，要包含字母和数字
        if (TextUtils.isEmpty(pws)) {
            return false;
        } else {
            return pws.matches(pwsRegex);
        }
    }
}
