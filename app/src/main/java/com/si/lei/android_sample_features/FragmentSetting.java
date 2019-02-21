package com.si.lei.android_sample_features;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.content.Context.BATTERY_SERVICE;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

/**
 * @author lei.si
 * @date 2019/01/16
 * 获取系统配置信息,并显示在配置导航栏中
 * https://blog.csdn.net/qq_29634351/article/details/73348165
 * https://blog.csdn.net/pigdreams/article/details/54426322
 */
public class FragmentSetting extends Fragment {

    private static final String TAG = FragmentSetting.class.getSimpleName();

    TextView textSDTotalSize;
    TextView SDTotalSize;
    TextView textSDAvailableSize;
    TextView SDAvailableSize;
    TextView textSystemTotalMemorySize;
    TextView SystemTotalMemorySize;
    TextView textSystemAvaialbeMemorySize;
    TextView SystemAvaialbeMemorySize;
    TextView textSystemAvaialbeMemorySizePercent;
    TextView SystemAvaialbeMemorySizePercent;
    TextView textBatteryInfo;
    TextView BatteryInfo;
    TextView textIPAddress;
    TextView IPAddress;
    TextView textMacAddress;
    TextView MacAddress;
    TextView textCpuCoreNumber;
    TextView CpuCoreNumber;
    TextView textIs64System;
    TextView Is64System;
    TextView textCpuMaxHz;
    TextView CpuMaxHz;
    TextView textCpuMinHz;
    TextView CpuMinHz;
    TextView textChangeCpuHz;
    TextView ChangeCpuHz;
    TextView textChangeCpuHzWay;
    TextView ChangeCpuHzWay;
    TextView textIsChangeCpuHzWay;
    TextView IsChangeCpuHzWay;
    TextView textGetCpuCurrentHz;
    TextView GetCpuCurrentHz;
    TextView textGetCpuCurrentStatus;
    TextView GetCpuCurrentStatus;

    public static FragmentSetting newInstance() {
        FragmentSetting fragmentSetting = new FragmentSetting();

        return fragmentSetting;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        /**
         * SD卡总大小 : 3.45GB
         * SD剩余容量大小 : 1.81GB
         * 系统总内存大小: 1.89GB
         * 系统可用内存大小: 1.08GB
         * 系统使用内存大小百分比: 42%
         * 获得电池电量百分比: 100%
         * IP地址: 172.30.61.127
         * MAC地址: 02:00:00:00:00:02
         *
         * 与之相关的方法。
         *
         * getSDTotalSize();
         * getSDAvailableSize();
         * getSystemTotalMemorySize();
         * getSystemAvaialbeMemorySize();
         * getSystemAvaialbeMemorySizePercent();
         * getBatteryInfo();
         * getIPAddress();
         * getMacAddress();
         */

        Log.d(TAG, "获得CPU核心数: " + CpuUtils.getCPUCoreNum());
        Log.d(TAG, "64 系统判断: " + CpuUtils.isCpu64());
        Log.d(TAG, "CPU 最大频率: " + CpuUtils.getCpuMaxFreq());
        Log.d(TAG, "CPU 最小频率: " + CpuUtils.getCpuMinFreq());
        //Log.d(TAG, "可调节 CPU 频率档位: " + CpuUtils.getCpuAvailableFrequenciesSimple());
        Log.d(TAG, "可调节 CPU 频率档位: " + CpuUtils.getCpuAvailableFrequencies());
        //Log.d(TAG, "可调节 CPU 频率档位: " + CpuUtils.getCpuFreqList());
        //Log.d(TAG, "64 系统判断: " + CpuUtils.getCpuAFreqList());
        Log.d(TAG, "CPU 调频策略: " + CpuUtils.getCpuGovernor());
        //Log.d(TAG, "CPU 支持的调频策略: " + CpuUtils.getCpuAvailableGovernorsSimple());
        //Log.d(TAG, "64 系统判断: " + CpuUtils.getCpuAvailableGovernorsList());
        Log.d(TAG, "CPU 支持的调频策略: " + CpuUtils.getCpuAvailableGovernors());
        Log.d(TAG, "获取cpu当前频率: " + CpuUtils.getCpuCurFreq(getActivity()));
        Log.d(TAG, "获取cpu当前状态: " + CpuUtils.getCpuOnlineStatus(getActivity()));
        //Log.d(TAG, "CPU 场景配置文件: " + CpuUtils.getCpuSceneInfo());
        //Log.d(TAG, "CPU 电压: " + CpuUtils.getCpuVoltage());

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        SDTotalSize = (TextView) view.findViewById(R.id.SDTotalSize);
        SDAvailableSize = (TextView) view.findViewById(R.id.SDAvailableSize);
        SystemTotalMemorySize = (TextView) view.findViewById(R.id.SystemTotalMemorySize);
        SystemAvaialbeMemorySize = (TextView) view.findViewById(R.id.SystemAvaialbeMemorySize);
        SystemAvaialbeMemorySizePercent = (TextView) view.findViewById(R.id.SystemAvaialbeMemorySizePercent);
        BatteryInfo = (TextView) view.findViewById(R.id.BatteryInfo);
        IPAddress = (TextView) view.findViewById(R.id.IPAddress);
        MacAddress = (TextView) view.findViewById(R.id.MacAddress);
        CpuCoreNumber = (TextView) view.findViewById(R.id.CpuCoreNumber);
        Is64System = (TextView) view.findViewById(R.id.Is64System);
        CpuMaxHz = (TextView) view.findViewById(R.id.CpuMaxHz);
        CpuMinHz = (TextView) view.findViewById(R.id.CpuMinHz);
        ChangeCpuHz = (TextView) view.findViewById(R.id.ChangeCpuHz);
        ChangeCpuHzWay = (TextView) view.findViewById(R.id.ChangeCpuHzWay);
        IsChangeCpuHzWay = (TextView) view.findViewById(R.id.IsChangeCpuHzWay);
        GetCpuCurrentHz = (TextView) view.findViewById(R.id.GetCpuCurrentHz);
        GetCpuCurrentStatus = (TextView) view.findViewById(R.id.GetCpuCurrentStatus);

        new TimeThread().start();

        return view;
    }

    /**
     * 获得SD卡总大小
     *
     * @return blockSizeString SD卡总大小
     */
    private String getSDTotalSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        String blockSizeString = formatSize(blockSize * totalBlocks);
        Log.d(TAG, "SD卡总大小 : " + blockSizeString);
        return Formatter.formatFileSize(getActivity(), blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     *
     * @return blockSizeString SD剩余容量大小
     */
    private String getSDAvailableSize() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        String availableBlocksString = formatSize(blockSize * availableBlocks);
        Log.d(TAG, "SD剩余容量大小 : " + availableBlocksString);
        return Formatter.formatFileSize(getActivity(), blockSize * availableBlocks);
    }

    /**
     * 获得系统总内存大小
     *
     * @return totalMemStr 系统总内存大小
     */
    private String getSystemTotalMemorySize() {
        //获得ActivityManager服务的对象
        ActivityManager mActivityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        //获得MemoryInfo对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        //获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo);
        long totalSize = memoryInfo.totalMem;
        // 字符类型转换
        String totalMemStr = formatSize(totalSize);
        Log.d(TAG, "系统总内存大小: " + totalMemStr);
        return totalMemStr;
    }

    /**
     * 获得系统可用内存大小
     *
     * @return availMemStr 系统可用内存大小
     */
    private String getSystemAvaialbeMemorySize() {
        //获得ActivityManager服务的对象
        //getSystemService无法在fragment中使用，所以要先getActivity()获得context才可以
        ActivityManager mActivityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        //获得MemoryInfo对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        //获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo);
        long memSize = memoryInfo.availMem;
        //字符类型转换
        String availMemStr = formatSize(memSize);
        Log.d(TAG, "系统可用内存大小: " + availMemStr);
        return availMemStr;
    }

    /**
     * 获得系统已用内存大小百分比
     *
     * @return usedMemStrPercent 系统已用内存大小百分比
     */
    private long getSystemAvaialbeMemorySizePercent() {
        ActivityManager mActivityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        mActivityManager.getMemoryInfo(memoryInfo);
        long totalSizeSize = memoryInfo.totalMem;
        long usedSize = totalSizeSize - memoryInfo.availMem;
        long usedMemStrPercent = usedSize * 100 / totalSizeSize;

        Log.d(TAG, "系统使用内存大小百分比: " + usedMemStrPercent + "%");
        return usedMemStrPercent;
    }

    /**
     * 获得电池电量
     *
     * @return batteryInfo.toString() 电池电量
     */
    private int getBatteryInfo() {
        int batteryInfo = 0;
        BatteryManager manager = (BatteryManager) getActivity().getSystemService(BATTERY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //batteryInfo.append(manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER));
            //batteryInfo.append(manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE));
            //batteryInfo.append(manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW));
            batteryInfo = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        }
        Log.d(TAG, "获得电池电量百分比: " + batteryInfo + "%");
        return batteryInfo;
    }

    /**
     * 获得IP地址
     * 注意无线网下和WIFI下的IP地址不同
     *
     * @return ipAddress IP地址
     */
    private String getIPAddress() {
        NetworkInfo info = ((ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                Log.d(TAG, "IP地址: " + inetAddress.getHostAddress());
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());
                Log.d(TAG, "IP地址: " + ipAddress);
                return ipAddress;
            }
        } else {
        }
        return null;
    }

    /**
     * 获得MAC地址
     *
     * @return macAddress MAC地址
     */
    private String getMacAddress() {
        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                Log.d(TAG, "MAC地址: " + "02:00:00:00:00:02");
                return "02:00:00:00:00:02";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            Log.d(TAG, "MAC地址: " + "02:00:00:00:00:02");
            return "02:00:00:00:00:02";
        }
        Log.d(TAG, "MAC地址: " + macAddress);
        return macAddress;
    }



    /**
     * 转换intIP为StringIP
     *
     * @return StringIP IP地址
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
    }

    /**
     * 转换格式
     *
     * @return resultBuffer.toString() 转换格式
     */
    public String formatSize(long size) {
        String suffix = null;
        float fSize = 0;

        if (size >= 1024) {
            suffix = "KB";
            fSize = size / 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1024) {
                suffix = "GB";
                fSize /= 1024;
            }
        } else {
            fSize = size;
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
        if (suffix != null) {
            resultBuffer.append(suffix);
        }
        return resultBuffer.toString();
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

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    SDTotalSize.setText(getSDTotalSize());
                    SDAvailableSize.setText(getSDAvailableSize());
                    SystemTotalMemorySize.setText(getSystemTotalMemorySize());
                    SystemAvaialbeMemorySize.setText(getSystemAvaialbeMemorySize());
                    SystemAvaialbeMemorySizePercent.setText(Long.toString(getSystemAvaialbeMemorySizePercent()));
                    //BatteryInfo.setText(getBatteryInfo());
                    IPAddress.setText(getIPAddress());
                    MacAddress.setText(getMacAddress());
                    //CpuCoreNumber.setText(CpuUtils.getCPUCoreNum());
                    Is64System.setText(Boolean.toString(CpuUtils.isCpu64()));
                    CpuMaxHz.setText(Long.toString(CpuUtils.getCpuMaxFreq()));
                    CpuMinHz.setText(Long.toString(CpuUtils.getCpuMinFreq()));
                    ChangeCpuHz.setText(CpuUtils.getCpuAvailableFrequencies().toString());
                    ChangeCpuHzWay.setText(CpuUtils.getCpuGovernor());
                    IsChangeCpuHzWay.setText(CpuUtils.getCpuAvailableGovernors().toString());
                    GetCpuCurrentHz.setText(CpuUtils.getCpuCurFreq(getActivity()).toString());
                    GetCpuCurrentStatus.setText(CpuUtils.getCpuOnlineStatus(getActivity()).toString());
                    break;

                    default:
            }
            return false;
        }
    });
}
