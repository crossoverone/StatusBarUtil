package crossoverone.statuslib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class StatusUtil {

    /**
     * Setting the status bar color.
     * It must be more than 21(5.0) to be valid.
     */
    public static void setUseStatusBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
    }

    /**
     * Setting the status bar transparently.
     */
    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // after 21(5.0)
            setUseStatusBarColor(activity, Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // between 19(4.4) and 21(5.0)
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * Setting up whether or not to invade the status bar & status bar font color
     *
     * @param isTransparent Whether or not to invade the status bar?
     *                      If true, will invade the status bar,
     *                      otherwise, fits system windows.
     * @param isBlack Whether the status bar font is set to black?
     *                If true, the status bar font will be black,
     *                otherwise, it is white.
     */
    public static void setSystemStatus(Activity activity, boolean isTransparent, boolean isBlack) {
        int flag = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && isTransparent) {
            // after 16(4.1)
            flag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isBlack) {
            // after 23(6.0)
            flag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(flag);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // between 19(4.4) and 21(5.0)
            ViewGroup contentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            View childAt = contentView.getChildAt(0);
            childAt.setFitsSystemWindows(!isTransparent);
        }
    }

    /**
     * Get the height of the state bar by reflection.
     *
     * @return Status bar height if it is not equal to -1,
     */
    public static int getStatusHeight(Context context) {
        int statusBarHeight2 = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusBarHeight2 = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight2;
    }
}
