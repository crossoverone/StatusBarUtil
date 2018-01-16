package crossoverone.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

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
        StatusUtil.setTransparentStatusBar(this);

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
}
