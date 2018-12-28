package cn.idaddy.idaddysdkdemo.utils;

import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by wlx on 2018/12/19 16:12.
 */
public class RotateUtils {
    /**
     * 根据当前的状态来旋转箭头。
     */
    @SuppressWarnings("all")
    public static void rotateArrow(ImageView arrow, boolean flag) {
        float pivotX = arrow.getWidth() / 2f;
        float pivotY = arrow.getHeight() / 2f;
        float fromDegrees = 0f;
        float toDegrees = 0f;
        // flag为true则向上
        if (flag) {
            fromDegrees = 0f;
            toDegrees = -90f;
        } else {
            fromDegrees = -90f;
            toDegrees = 0f;
        }
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees,
                pivotX, pivotY);
        animation.setDuration(100);
        animation.setFillAfter(true);
        arrow.startAnimation(animation);
    }
}
