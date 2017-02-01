package com.marekhakala.mynomadlifeapp.UI;

import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

public class PrimaryDrawerInfoItem extends PrimaryDrawerItem {
    protected String code;

    public PrimaryDrawerInfoItem() {
    }

    public PrimaryDrawerInfoItem withCode(String code) {
        this.code = code;
        return this;
    }

    public String getCode() {
        return this.code;
    }
}
