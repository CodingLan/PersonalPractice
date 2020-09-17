package com.lxq.personalpractice.Bean;

/**
 * Created by lanxiaoqing on 2019-08-05.
 */
public class ProportionItem {

    private String name;
    private float value;

    public ProportionItem(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
