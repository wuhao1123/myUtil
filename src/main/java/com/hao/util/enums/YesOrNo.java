package com.hao.util.enums;

public enum YesOrNo {
                     YES("y"), NO("n");

    private String value;

    public String getValue() {
        return this.value;
    }

    private YesOrNo(String value){
        this.value = value;
    }

}
