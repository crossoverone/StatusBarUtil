package crossoverone.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import crossoverone.activity.R;

/**
 * Created by Administrator on 2018/1/16.
 */

public class SecondFragment extends Fragment{

    private int mType;

    public static SecondFragment newInstance(int type) {
        SecondFragment mainFragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    private View mRootView;

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
        switch (mType) {
            case 0:
                color = "#ff0000";
                break;
            case 1:
                color = "#00ff00";
                break;
            case 2:
                color = "#0000ff";
                break;
        }
        mRootView.findViewById(R.id.ll).setBackgroundColor(Color.parseColor(color));
    }

}
