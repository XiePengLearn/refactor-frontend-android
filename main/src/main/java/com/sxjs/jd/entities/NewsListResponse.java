package com.sxjs.jd.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2019/9/20 14:30
 * @Description:
 */
public class NewsListResponse {


    /**
     * code : 200200
     * data : {"TOTAL_PAGE":1,"PAGE_NO":0,"PAGE_SIZE":20,"PAGE":[{"TITLE":"最新！安徽出台加强全省三级公立医院绩效考核工作实施方案（附考核指标）","IMAGE_URI":"","CONTENT_URI":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/news-detail?type=I010&id=8afb2a18-fd9b-4dd9-9c16-2067b75eb7e2","DATE":"2019-08-19"},{"TITLE":"百货大楼\u201c挖\u201d出一座\u201c老北京城\u201d","IMAGE_URI":"http://hospital-pa-app.oss-cn-beijing.aliyuncs.com/pa-app/image/85839e1045ad56af289a14ab85e5869ad2ef4f70.png","CONTENT_URI":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/news-detail?type=I010&id=d46b4828-ab56-4e72-ad11-8485714d55bb","DATE":"2019-08-15"},{"TITLE":"患者写信提建议 医院闻过即整改","IMAGE_URI":"http://hospital-pa-app.oss-cn-beijing.aliyuncs.com/pa-app/image/7ec360bf46e470128a9e7e6d392c100c24101099.jpeg","CONTENT_URI":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/news-detail?type=I010&id=d491daeb-56a1-4811-b18f-54a9f1f13252","DATE":"2019-08-15"}],"COUNT":3}
     * msg :
     */

    private String code;
    private DataBean data;
    private String   msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * TOTAL_PAGE : 1
         * PAGE_NO : 0
         * PAGE_SIZE : 20
         * PAGE : [{"TITLE":"最新！安徽出台加强全省三级公立医院绩效考核工作实施方案（附考核指标）","IMAGE_URI":"","CONTENT_URI":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/news-detail?type=I010&id=8afb2a18-fd9b-4dd9-9c16-2067b75eb7e2","DATE":"2019-08-19"},{"TITLE":"百货大楼\u201c挖\u201d出一座\u201c老北京城\u201d","IMAGE_URI":"http://hospital-pa-app.oss-cn-beijing.aliyuncs.com/pa-app/image/85839e1045ad56af289a14ab85e5869ad2ef4f70.png","CONTENT_URI":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/news-detail?type=I010&id=d46b4828-ab56-4e72-ad11-8485714d55bb","DATE":"2019-08-15"},{"TITLE":"患者写信提建议 医院闻过即整改","IMAGE_URI":"http://hospital-pa-app.oss-cn-beijing.aliyuncs.com/pa-app/image/7ec360bf46e470128a9e7e6d392c100c24101099.jpeg","CONTENT_URI":"http://114.247.234.146:8087/hospital-pa-ap-h5/#/news-detail?type=I010&id=d491daeb-56a1-4811-b18f-54a9f1f13252","DATE":"2019-08-15"}]
         * COUNT : 3
         */

        private int TOTAL_PAGE;
        private int            PAGE_NO;
        private int            PAGE_SIZE;
        private int            COUNT;
        private List<PAGEBean> PAGE;

        public int getTOTAL_PAGE() {
            return TOTAL_PAGE;
        }

        public void setTOTAL_PAGE(int TOTAL_PAGE) {
            this.TOTAL_PAGE = TOTAL_PAGE;
        }

        public int getPAGE_NO() {
            return PAGE_NO;
        }

        public void setPAGE_NO(int PAGE_NO) {
            this.PAGE_NO = PAGE_NO;
        }

        public int getPAGE_SIZE() {
            return PAGE_SIZE;
        }

        public void setPAGE_SIZE(int PAGE_SIZE) {
            this.PAGE_SIZE = PAGE_SIZE;
        }

        public int getCOUNT() {
            return COUNT;
        }

        public void setCOUNT(int COUNT) {
            this.COUNT = COUNT;
        }

        public List<PAGEBean> getPAGE() {
            return PAGE;
        }

        public void setPAGE(List<PAGEBean> PAGE) {
            this.PAGE = PAGE;
        }

        public static class PAGEBean {
            /**
             * TITLE : 最新！安徽出台加强全省三级公立医院绩效考核工作实施方案（附考核指标）
             * IMAGE_URI :
             * CONTENT_URI : http://114.247.234.146:8087/hospital-pa-ap-h5/#/news-detail?type=I010&id=8afb2a18-fd9b-4dd9-9c16-2067b75eb7e2
             * DATE : 2019-08-19
             */

            private String TITLE;
            private String IMAGE_URI;
            private String CONTENT_URI;
            private String DATE;

            public String getTITLE() {
                return TITLE;
            }

            public void setTITLE(String TITLE) {
                this.TITLE = TITLE;
            }

            public String getIMAGE_URI() {
                return IMAGE_URI;
            }

            public void setIMAGE_URI(String IMAGE_URI) {
                this.IMAGE_URI = IMAGE_URI;
            }

            public String getCONTENT_URI() {
                return CONTENT_URI;
            }

            public void setCONTENT_URI(String CONTENT_URI) {
                this.CONTENT_URI = CONTENT_URI;
            }

            public String getDATE() {
                return DATE;
            }

            public void setDATE(String DATE) {
                this.DATE = DATE;
            }
        }
    }
}
