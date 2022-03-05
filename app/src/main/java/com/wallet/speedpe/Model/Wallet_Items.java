package com.wallet.speedpe.Model;

public class Wallet_Items {
    public String wa_id, wa_name, wa_mrp, wa_totalquota, wa_cashback;

    public Wallet_Items(String wa_id, String wa_name, String wa_mrp, String wa_totalquota, String wa_cashback) {
        this.wa_id = wa_id;
        this.wa_name = wa_name;
        this.wa_mrp = wa_mrp;
        this.wa_totalquota = wa_totalquota;
        this.wa_cashback = wa_cashback;
    }

    public String getWa_id() {
        return wa_id;
    }

    public void setWa_id(String wa_id) {
        this.wa_id = wa_id;
    }

    public String getWa_name() {
        return wa_name;
    }

    public void setWa_name(String wa_name) {
        this.wa_name = wa_name;
    }

    public String getWa_mrp() {
        return wa_mrp;
    }

    public void setWa_mrp(String wa_mrp) {
        this.wa_mrp = wa_mrp;
    }

    public String getWa_totalquota() {
        return wa_totalquota;
    }

    public void setWa_totalquota(String wa_totalquota) {
        this.wa_totalquota = wa_totalquota;
    }

    public String getWa_cashback() {
        return wa_cashback;
    }

    public void setWa_cashback(String wa_cashback) {
        this.wa_cashback = wa_cashback;
    }
}
