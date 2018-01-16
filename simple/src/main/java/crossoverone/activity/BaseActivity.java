package crossoverone.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import crossoverone.statuslib.StatusUtil;

/**
 * Created by Administrator on 2018/1/16.
 */

public abstract class  BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        setStatus();

        initView();
    }

    protected abstract void initView();

    protected void setStatus() {
        StatusUtil.setTransparentStatusBar(this);
        StatusUtil.setSystemStatus(this, false, true);
    }

    protected abstract int getLayoutRes();

}
