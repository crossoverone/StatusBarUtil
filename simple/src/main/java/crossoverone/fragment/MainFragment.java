package crossoverone.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import crossoverone.activity.R;

/**
 * Created by Administrator on 2018/1/16.
 */

public class MainFragment extends Fragment{

    private int mType;

    public static MainFragment newInstance(int type) {
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    private View mRootView;
    private ImageView mImageView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mType = arguments.getInt("type");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        initView();

        return mRootView;
    }

    private void initView() {
        String color = "#000000";
        int res = R.mipmap.test;
        switch (mType) {
            case 0:
                color = "#ff0000";
                break;
            case 1:
                color = "#00ff00";
                res = R.mipmap.icon;
                break;
            case 2:
                color = "#0000ff";
                break;
        }
        mRootView.findViewById(R.id.ll).setBackgroundColor(Color.parseColor(color));

        mImageView = (ImageView) mRootView.findViewById(R.id.icon);
        mImageView.setImageResource(res);

    }

}
