package crossoverone.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

import crossoverone.statuslib.AndroidBug5497Workaround;
import crossoverone.statuslib.StatusUtil;


/**
 * Created by Administrator on 2018/1/15.
 */

public class FirstActivity extends AppCompatActivity{

    SeekBar mProgressBar1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        AndroidBug5497Workaround.assistActivity(this);
        StatusUtil.setUseStatusBarColor(this, Color.parseColor("#ff0000"), StatusUtil.USE_CUR_COLOR);

        mProgressBar1 = (SeekBar) findViewById(R.id.progpress1);

        mProgressBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                StatusUtil.setUseStatusBarColor(FirstActivity.this, Color.argb(progress * 0xff / 0x64, 0xff, 0x00, 0x00));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private boolean invade;
    private boolean frontBlack;

    public void btn1(View view) {
        invade = true;
        StatusUtil.setSystemStatus(this, invade, frontBlack);
    }

    public void btn2(View view) {
        invade = false;
        StatusUtil.setSystemStatus(this, invade, frontBlack);
    }

    public void btn3(View view) {
        frontBlack = true;
        StatusUtil.setSystemStatus(this, invade, frontBlack);
    }

    public void btn4(View view) {
        frontBlack = false;
        StatusUtil.setSystemStatus(this, invade, frontBlack);
    }

    public void btn5(View view) {
        // 6.0 以下有用
        StatusUtil.setUseStatusBarColor(this, Color.parseColor("#ff0000"), StatusUtil.USE_DEFAULT_COLOR);
    }

    public void btn6(View view) {
        // 6.0 以下有用
        StatusUtil.setUseStatusBarColor(this, Color.parseColor("#ff0000"), StatusUtil.USE_CUR_COLOR);
    }
}
