package com.test.utils;

import com.alibaba.fastjson.JSONObject;
import com.test.base.TestBase;

public class YamlUtils extends TestBase {

    public static Object getValue(String key){
        // 对jsonObject 进行深拷贝
        JSONObject object = JSONObject.parseObject(jsonObject.toJSONString());
        // 遍历查询
        String[] keyArr = key.split("\\.");
        for (int i = 0; i < keyArr.length - 1; i++){
            object = (JSONObject)object.get(keyArr[i]);
        }
        return object.get(keyArr[keyArr.length-1]);
    }

}
