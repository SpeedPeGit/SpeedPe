package com.wallet.speedpe.Model;

public class Plans_Items {
    public String pl_id, pl_name, pl_mrp, pl_totalquota, pl_cashback, perDayLimit, cash_back, wallet_credit, ref_bonus;

    public Plans_Items(String pl_id, String pl_name, String pl_mrp, String pl_totalquota, String pl_cashback, String perDayLimit, String cash_back, String wallet_credit, String ref_bonus) {
        this.pl_id = pl_id;
        this.pl_name = pl_name;
        this.pl_mrp = pl_mrp;
        this.pl_totalquota = pl_totalquota;
        this.pl_cashback = pl_cashback;
        this.perDayLimit = perDayLimit;
        this.cash_back = cash_back;
        this.wallet_credit = wallet_credit;
        this.ref_bonus = ref_bonus;
    }

    public String getPl_id() {
        return pl_id;
    }

    public void setPl_id(String pl_id) {
        this.pl_id = pl_id;
    }

    public String getPl_name() {
        return pl_name;
    }

    public void setPl_name(String pl_name) {
        this.pl_name = pl_name;
    }

    public String getPl_mrp() {
        return pl_mrp;
    }

    public void setPl_mrp(String pl_mrp) {
        this.pl_mrp = pl_mrp;
    }

    public String getPl_totalquota() {
        return pl_totalquota;
    }

    public void setPl_totalquota(String pl_totalquota) {
        this.pl_totalquota = pl_totalquota;
    }

    public String getPl_cashback() {
        return pl_cashback;
    }

    public void setPl_cashback(String pl_cashback) {
        this.pl_cashback = pl_cashback;
    }

    public String getPerDayLimit() {
        return perDayLimit;
    }

    public void setPerDayLimit(String perDayLimit) {
        this.perDayLimit = perDayLimit;
    }

    public String getCash_back() {
        return cash_back;
    }

    public void setCash_back(String cash_back) {
        this.cash_back = cash_back;
    }

    public String getWallet_credit() {
        return wallet_credit;
    }

    public void setWallet_credit(String wallet_credit) {
        this.wallet_credit = wallet_credit;
    }

    public String getRef_bonus() {
        return ref_bonus;
    }

    public void setRef_bonus(String ref_bonus) {
        this.ref_bonus = ref_bonus;
    }
}
