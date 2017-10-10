package com.example.heyukun.timelinedemo.home_center;

import android.content.Context;

public class RKDensityUtil {

 public static int dpToPx(Context context, float dpValue) {//dp转换为px
  float scale = context.getResources().getDisplayMetrics().density;//获得当前屏幕密度
  return (int) (dpValue * scale + 0.5f);
 }
}
 