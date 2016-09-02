package com.dream.plmm.bean;

import java.util.List;

/**
 * Created by likun on 16/8/19.
 */
public class ClassifyHealthy {

    /**
     * status : true
     * tngou : [{"description":"减肥瘦身,美丽一生,减肥资讯 瘦身资讯 ,减肥瘦身健康知识,减肥瘦身信息专题","id":11,"keywords":"减肥瘦身","name":"减肥瘦身","seq":1,"title":"减肥瘦身"}]
     */

    private boolean status;
    /**
     * description : 减肥瘦身,美丽一生,减肥资讯 瘦身资讯 ,减肥瘦身健康知识,减肥瘦身信息专题
     * id : 11
     * keywords : 减肥瘦身
     * name : 减肥瘦身
     * seq : 1
     * title : 减肥瘦身
     */

    private List<TngouEntity> tngou;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<TngouEntity> getTngou() {
        return tngou;
    }

    public void setTngou(List<TngouEntity> tngou) {
        this.tngou = tngou;
    }

    public static class TngouEntity {
        private String description;
        private int id;
        private String keywords;
        private String name;
        private int seq;
        private String title;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
