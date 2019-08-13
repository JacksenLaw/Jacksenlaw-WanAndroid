package com.jacksen.wanandroid.util;

import com.blankj.utilcode.utils.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 作者： LuoM
 * 创建时间：2019/7/4/0004
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/7/4/0004
 * 更新内容：
 */
public class AssetsUtils {
    public static String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader(Utils.getContext().getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder result=new StringBuilder("");
            while((line = bufReader.readLine()) != null)
                result.append(line);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
