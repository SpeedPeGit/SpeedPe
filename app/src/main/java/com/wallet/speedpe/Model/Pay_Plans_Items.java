package com.wallet.speedpe.Model;

public class Pay_Plans_Items {
    public String pay_id, pay_name, pay_date, pay_year, pay_amt;

    public Pay_Plans_Items(String pay_id, String pay_name, String pay_date, String pay_year, String pay_amt) {
        this.pay_id = pay_id;
        this.pay_name = pay_name;
        this.pay_date = pay_date;
        this.pay_year = pay_year;
        this.pay_amt = pay_amt;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public String getPay_year() {
        return pay_year;
    }

    public void setPay_year(String pay_year) {
        this.pay_year = pay_year;
    }

    public String getPay_amt() {
        return pay_amt;
    }

    public void setPay_amt(String pay_amt) {
        this.pay_amt = pay_amt;
    }
}
