package crossoverone.statuslib;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

public class AndroidBug5497Workaround {

    public static void assistActivity(Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;
    private Activity mActivity;

    private boolean isFirst;
    private int height;

    private int addedStatusHeight;

    private AndroidBug5497Workaround(Activity activity) {
        mActivity = activity;
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();

        if (!isFirst || usableHeightNow == height + StatusUtil.getNavigationBarHeight(mActivity) || usableHeightNow == height - StatusUtil.getNavigationBarHeight(mActivity)) {
            isFirst = true;
            height = computeUsableHeight();
        }

        if (usableHeightNow != usableHeightPrevious || getStatusHeightChange()) {
            int heightDifference = height - usableHeightNow;
            if (heightDifference > (height / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = height - heightDifference + addedStatusHeight;
            }else {
                // keyboard probably just became hidden
                frameLayoutParams.height = height + addedStatusHeight;
            }

            if (frameLayoutParams.height > mChildOfContent.getRootView().getHeight()) {
                frameLayoutParams.height = mChildOfContent.getRootView().getHeight();
            }

            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    private boolean getStatusHeightChange() {
        int systemUiVisibility = mActivity.getWindow().getDecorView().getSystemUiVisibility();
        if (addedStatusHeight == StatusUtil.getStatusBarHeight(mActivity) && (systemUiVisibility & SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) == 0) {
            addedStatusHeight = 0;
            return true;
        } else if (addedStatusHeight == 0 && (systemUiVisibility & SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) > 0) {
            addedStatusHeight = StatusUtil.getStatusBarHeight(mActivity);
            return true;
        }
        return false;
    }

}