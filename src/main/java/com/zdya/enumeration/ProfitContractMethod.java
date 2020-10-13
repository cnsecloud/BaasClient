package com.zdya.enumeration;

public enum ProfitContractMethod {
    addCodeAndIps("add_tfae_code_and_ip"),
    addRegister("add_tfae_reg"),
    checkInterstDetail("check_tfae_interst_detail"),
    addInterstDetail("add_tfae_interst_detail");

    private String name;

    private ProfitContractMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
