package com.wallet.speedpe.Model;

import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QRScanWithdrawReq implements Parcelable
{

    @SerializedName("memberId")
    @Expose
    private String memberId;
    @SerializedName("rechargeId")
    @Expose
    private String rechargeId;
    @SerializedName("withdrawalDate")
    @Expose
    private String withdrawalDate;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("upiId")
    @Expose
    private String upiId;
    @SerializedName("transactionStatus")
    @Expose
    private String transactionStatus;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("withdrawalTransactionId")
    @Expose
    private String withdrawalTransactionId;
    @SerializedName("withdrawalResponce")
    @Expose
    private String withdrawalResponce;
    @SerializedName("field1")
    @Expose
    private String field1;
    @SerializedName("field2")
    @Expose
    private String field2;
    public final static Creator<QRScanWithdrawReq> CREATOR = new Creator<QRScanWithdrawReq>() {


        @SuppressWarnings({
                "unchecked"
        })
        public QRScanWithdrawReq createFromParcel(android.os.Parcel in) {
            return new QRScanWithdrawReq(in);
        }

        public QRScanWithdrawReq[] newArray(int size) {
            return (new QRScanWithdrawReq[size]);
        }

    }
            ;

    protected QRScanWithdrawReq(android.os.Parcel in) {
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.rechargeId = ((String) in.readValue((String.class.getClassLoader())));
        this.withdrawalDate = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.upiId = ((String) in.readValue((String.class.getClassLoader())));
        this.transactionStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.purpose = ((String) in.readValue((String.class.getClassLoader())));
        this.withdrawalTransactionId = ((String) in.readValue((String.class.getClassLoader())));
        this.withdrawalResponce = ((String) in.readValue((String.class.getClassLoader())));
        this.field1 = ((String) in.readValue((String.class.getClassLoader())));
        this.field2 = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public QRScanWithdrawReq() {
    }

    /**
     *
     * @param amount
     * @param transactionStatus
     * @param purpose
     * @param upiId
     * @param rechargeId
     * @param withdrawalResponce
     * @param field1
     * @param withdrawalDate
     * @param withdrawalTransactionId
     * @param name
     * @param field2
     * @param memberId
     * @param status
     */
    public QRScanWithdrawReq(String memberId, String rechargeId, String withdrawalDate, Integer amount, String upiId, String transactionStatus, Boolean status, String name, String purpose, String withdrawalTransactionId, String withdrawalResponce, String field1, String field2) {
        super();
        this.memberId = memberId;
        this.rechargeId = rechargeId;
        this.withdrawalDate = withdrawalDate;
        this.amount = amount;
        this.upiId = upiId;
        this.transactionStatus = transactionStatus;
        this.status = status;
        this.name = name;
        this.purpose = purpose;
        this.withdrawalTransactionId = withdrawalTransactionId;
        this.withdrawalResponce = withdrawalResponce;
        this.field1 = field1;
        this.field2 = field2;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    public String getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(String withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getWithdrawalTransactionId() {
        return withdrawalTransactionId;
    }

    public void setWithdrawalTransactionId(String withdrawalTransactionId) {
        this.withdrawalTransactionId = withdrawalTransactionId;
    }

    public String getWithdrawalResponce() {
        return withdrawalResponce;
    }

    public void setWithdrawalResponce(String withdrawalResponce) {
        this.withdrawalResponce = withdrawalResponce;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(memberId);
        dest.writeValue(rechargeId);
        dest.writeValue(withdrawalDate);
        dest.writeValue(amount);
        dest.writeValue(upiId);
        dest.writeValue(transactionStatus);
        dest.writeValue(status);
        dest.writeValue(name);
        dest.writeValue(purpose);
        dest.writeValue(withdrawalTransactionId);
        dest.writeValue(withdrawalResponce);
        dest.writeValue(field1);
        dest.writeValue(field2);
    }

    public int describeContents() {
        return 0;
    }

}