package com.sxjs.common.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;

import com.sxjs.common.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:03
 * @Description:
 */
public class Tool {
    // 格式：年－月－日
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 使用MD5算法进行加密
     *
     * @param plainText 明文
     * @return 32位密文
     */
    public static String encryption2(String plainText) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte tmp[] = md.digest();

            char str[] = new char[16 * 2];
            int k = 0;
            byte b;
            for (int i = 0; i < 16; i++) {
                b = tmp[i];
                str[k++] = hexDigits[b >>> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            plainText = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plainText;
    }

    public static boolean isNumeric(String str) {
        if ((str == null) || "".equals(str))
            return false;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static File saveImgFromURL(String url) {
        URL urls = null;
        File imageFile = null;
        try {
            urls = new URL(url);

            //打开链接
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) urls.openConnection();
                conn.setDoInput(true);
                conn.connect();
                //通过输入流获取图片数据
                InputStream inStream = conn.getInputStream();
                //得到图片的二进制数据，以二进制封装得到数据，具有通用性
                byte[] data = new byte[0];
                try {
                    data = readInputStream(inStream);

                    //new一个文件对象用来保存图片，默认保存当前工程根目录
                    imageFile = new File(System.currentTimeMillis() + ".jpg");
                    //创建输出流
                    FileOutputStream outStream = new FileOutputStream(imageFile);
                    //写入数据
                    outStream.write(data);
                    //关闭输出流
                    outStream.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static String formatTime(long time) {
        time = time / 1000;
        String strHour = "" + (time / 3600);
        String strMinute = "" + time % 3600 / 60;
        String strSecond = "" + time % 3600 % 60;

        strHour = strHour.length() < 2 ? "0" + strHour : strHour;
        strMinute = strMinute.length() < 2 ? "0" + strMinute : strMinute;
        strSecond = strSecond.length() < 2 ? "0" + strSecond : strSecond;

        String strRsult = "";

        if (!strHour.equals("00")) {
            strRsult += strHour + ":";
        }

        strRsult += strMinute + ":";

        strRsult += strSecond;

        return strRsult;
    }

    /**
     * 获取一个首位为字母后加随机数字的指定长度的随机字符串
     *
     * @param length 指定的长度
     * @return 长度最小为1的随机字符串
     */
    public static String GetRandomNumber(int length) {

        String[] first = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"};
        String result = "";
        // 随机数
        Random random = new Random();
        result += first[random.nextInt(26)];

        for (int i = 1; i < length; i++) {
            result += random.nextInt(10);
        }
        return result;
    }

    /**
     * 描 述：密度转换成像素<br/>
     * 作 者：ruji<br/>
     * 历 史: (版本) 作者 时间 注释 <br/>
     *
     * @param dipValue
     * @param context
     * @return
     */
    public static int dipTopx(float dipValue, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 描 述：像素转换成密度<br/>
     * 作 者：ruji<br/>
     * 历 史: (版本) 作者 时间 注释 <br/>
     *
     * @param pxValue
     * @param context
     * @return
     */
    public static int psTopx(float pxValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int dipValue = (int) (pxValue / scale + 0.5f);
        return (int) (dipValue * scale + 0.5f);
    }

    // 格式到天
    public static String getDay(long time) {

        return new SimpleDateFormat("yyyy-MM-dd").format(time);

    }

    // 格式到分钟
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日    HH:mm");
        return sf.format(d);
    }

    /**
     * @param //2014-09-01
     */
    @SuppressWarnings({"deprecation", "unused"})
    public static String getWeekOfDate(String str) {
        String[] ymd = str.split("-");
        Date date = new Date(Integer.parseInt(ymd[0]),
                Integer.parseInt(ymd[1]) - 1, Integer.parseInt(ymd[2]) - 1);
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                "星期六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
    }

    /**
     * 获取日期
     *
     * @param date
     * @return
     */
    public static String formatDateString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String formatDateString2(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        return formatter.format(date);
    }


    /**
     * 获取时间
     *
     * @param date
     * @return
     */

    public static String formatTimeString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(date);
    }

    /**
     * 获取时间
     *
     * @param date
     * @return 格式为： 2017-17-26 上午 08:20
     */

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd a hh:mm");
        return format.format(date);
    }

    /**
     * 比较两个日期的年差
     *
     * @param before
     * @param after
     * @return
     */
    public static int yearDiff(String before, String after) {
        Date beforeDay = stringtoDate(before, LONG_DATE_FORMAT);
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(afterDay) - getYear(beforeDay);
    }

    /**
     * 返回日期的年
     *
     * @param date Date
     * @return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @param dateStr
     * @return
     */
    public static Date stringtoDate(String dateStr, String format) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            // log.error(e);
            d = null;
        }
        return d;
    }

    /**
     * 获取日期
     *
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    /**
     * 获取时间
     *
     * @param date
     * @return
     */

    public static String getTimeString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(new Date());
    }

    public static String getCutString(String str) {
        if (str == null || str.equals("")) {
            return "";
        }
        if (str.length() > 12) {
            return str.substring(0, 12) + "...";
        }
        return str;
    }

    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {

                System.out.println("dt1 在dt2前");
                return 1;

            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 比较时间（与当前日期相比）
     *
     * @param date 指定日期  格式例：2017-10-18
     * @return 小于0时代表指定日期大于当前日期 大于0时 代表指定日期小于当前日期 等于0时 代表指定日期与当前日期相等
     */
    public static long compareTime(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0;
        try {
            Date beginDate = format.parse(date);
            Date endDate = format.parse(format.format(new Date()));
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 强制隐藏键盘
     *
     * @param context
     * @param view
     */
    public static void ForcedHiddenInputMethod(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }

    }

    /**
     * 根据传入控件的坐标和用户的焦点坐标，判断是否隐藏键盘，如果点击的位置在控件内，则不隐藏键盘
     *
     * @param view  控件view
     * @param event 焦点位置
     * @return 是否隐藏
     */
    public static void hideKeyboard(MotionEvent event, View view,
                                    Activity activity) {
        try {
            if (view != null && view instanceof EditText) {
                int[] location = {0, 0};
                view.getLocationInWindow(location);
                int left = location[0], top = location[1], right = left
                        + view.getWidth(), bootom = top + view.getHeight();
                // 判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.getRawX() < left || event.getRawX() > right
                        || event.getY() < top || event.getRawY() > bootom) {
                    // 隐藏键盘
                    IBinder token = view.getWindowToken();
                    InputMethodManager inputMethodManager = (InputMethodManager) activity
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(token,
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void releaseAllWebViewCallback() {
        if (android.os.Build.VERSION.SDK_INT < 16) {
            try {
                Field field = WebView.class.getDeclaredField("mWebViewCore");
                field = field.getType().getDeclaredField("mBrowserFrame");
                field = field.getType().getDeclaredField("sConfigCallback");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                Field sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
                if (sConfigCallback != null) {
                    sConfigCallback.setAccessible(true);
                    sConfigCallback.set(null, null);
                }
            } catch (NoSuchFieldException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            } catch (IllegalAccessException e) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }


}
