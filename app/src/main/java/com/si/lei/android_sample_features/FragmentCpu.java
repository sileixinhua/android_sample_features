package com.si.lei.android_sample_features;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;

/**
 * @author lei.si
 * @date 2019/02/18
 * Cpu不同核心的使用状态
 *
 * @date 2019/04/06
 * 终于明白adb打印CPU根本就不对，这个需要链接USB，都链接USB了，直接在电脑上adb不就行了？
 * 此举没有意义，所以
 * 直接打印出进程信息就可以了
 */
public class FragmentCpu extends Fragment {

    private static final String TAG = FragmentCpu.class.getSimpleName();

    TextView textView;
    StringBuilder printStr = new StringBuilder();

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
        new TimeThread().start();
        return view;
    }

    public class TimeThread extends Thread{
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);

        }
    }

    //打印thread和stackTraceElements
    private void printThread() {
        Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
        Set<Thread> set = stacks.keySet();
        for (Thread key : set) {
            StackTraceElement[] stackTraceElements = stacks.get(key);
            Log.d(TAG, "---- print thread: " + key.getName() + " start ----");
            Log.d(TAG, "---- print thread: " + key.toString());
            Log.d(TAG, "---- print thread: " + key.getContextClassLoader());
            Log.d(TAG, "---- print thread: " + key.getId());
            Log.d(TAG, "---- print thread: " + key.getPriority());
            Log.d(TAG, "---- print thread: " + key.getStackTrace());
            Log.d(TAG, "---- print thread: " + key.getThreadGroup());
            Log.d(TAG, "---- print thread: " + key.getState());
            printStr.append("Name : " + key.getName() + "\n\n");
            //printStr.append(key.toString() + "\n\n");
            //printStr.append("ContextClassLoader:" + key.getContextClassLoader() + "\n\n");
            printStr.append("Id : " + key.getId() + "\n\n");
            printStr.append("Priority : " + key.getPriority() + "\n\n");
            printStr.append("StackTrace : " + key.getStackTrace() + "\n\n");
            printStr.append("ThreadGroup : " + key.getThreadGroup() + "\n\n");
            printStr.append("State : " + key.getState() + "\n-----------------------\n");
            for (StackTraceElement st : stackTraceElements) {
                Log.d(TAG, "StackTraceElement: " + st.toString());
            }
            Log.d(TAG, "---- print thread: " + key.getName() + " end ----");
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //textView.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));
                    //textView.setText();
                    printThread();
                    textView.setText(printStr);
                    break;
                    default:
            }
            return false;
        }
    });
}