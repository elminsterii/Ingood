package com.fff.ingood.Tool;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by yoie7 on 2018/5/21.
 */

public class SerializableHashMap implements Serializable {
    private HashMap<String,Object> newsItems;

    public HashMap<String,Object> getObjectItems() {
        return newsItems;
    }
    public void setObjectItems(HashMap<String,Object> newsItems) {
        this.newsItems = newsItems;
    }
}