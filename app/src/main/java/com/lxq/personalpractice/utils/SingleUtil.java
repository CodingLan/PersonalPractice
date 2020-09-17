package com.lxq.personalpractice.utils;

/**
 * Created by lanxiaoqing on 2018/12/11.
 */
public class SingleUtil {

    private volatile static SingleUtil instance;

    private SingleUtil() {}

    /**
     * 懒汉模式，+双重检测
     *
     * @return
     */
    public static SingleUtil getInstance() {
        if (instance == null) {
            synchronized (SingleUtil.class) {
                if (instance == null) {
                    instance = new SingleUtil();
                }
            }
        }
        return instance;
    }
}
