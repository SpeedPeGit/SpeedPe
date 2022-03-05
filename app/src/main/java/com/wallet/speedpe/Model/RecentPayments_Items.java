package com.wallet.speedpe.Model;

public class RecentPayments_Items {
    public String payment_id, payment_img, bunk_name, payment_date, payment_amt, payment_type;

    public RecentPayments_Items(String payment_id, String payment_img, String bunk_name, String payment_date, String payment_amt, String payment_type) {
        this.payment_id = payment_id;
        this.payment_img = payment_img;
        this.bunk_name = bunk_name;
        this.payment_date = payment_date;
        this.payment_amt = payment_amt;
        this.payment_type = payment_type;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_img() {
        return payment_img;
    }

    public void setPayment_img(String payment_img) {
        this.payment_img = payment_img;
    }

    public String getBunk_name() {
        return bunk_name;
    }

    public void setBunk_name(String bunk_name) {
        this.bunk_name = bunk_name;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_amt() {
        return payment_amt;
    }

    public void setPayment_amt(String payment_amt) {
        this.payment_amt = payment_amt;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }
}
