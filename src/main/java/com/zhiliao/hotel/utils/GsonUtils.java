package com.zhiliao.hotel.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  @author  chenrong
 *  @created date 2020/4/20
 */
public class GsonUtils {
    private static Gson gson=null;

    static{
           //静态块初始化
        if(gson==null){
            gson=new Gson().newBuilder().create();
        }
    }

    private GsonUtils(){

    }

    /**
     *  对象转json
     * @param json
     * @param <T>
     * @return
     */
    public static <T> T objToJson(Object json){
        T t=null;
        if(gson!=null){
            t = (T) gson.toJson(json);
        }
        return t;
    }

    public static<T>  T  gsonMaps(String json,Class<T>clazz){
        T t=null;
        if(gson!=null) {
             t = gson.fromJson(json, clazz);
        }
        return t;
    }

    /**
     * json转list
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }


    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }


    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    public static void main(String[] args) {

    }
}
