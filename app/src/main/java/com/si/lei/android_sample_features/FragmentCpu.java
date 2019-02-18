package com.si.lei.android_sample_features;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author lei.si
 * @date 2019/02/18
 * Cpu不同核心的使用状态
 */
public class FragmentCpu extends Fragment {

    private static final String TAG = FragmentCpu.class.getSimpleName();

    TextView textView;

    public static FragmentCpu newInstance() {
        FragmentCpu fragmentCpu = new FragmentCpu();



        return fragmentCpu;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cpu, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        return view;
    }
}