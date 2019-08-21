package com.jacksen.wanandroid.util;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import java.lang.reflect.Field;

/**
 * 作者： LuoM
 * 时间： 2019/4/18 0018
 * 描述： 28.0.0以下使用，28.0.0以上 在xml中设置app:labelVisibilityMode="labeled"即可均分宽度
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class BottomNavUtil {

    /**
     * 设置底部导航tab均分宽度
     *
     * @param navigationView navigationView
     */
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setClipChildren(true);
//                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
