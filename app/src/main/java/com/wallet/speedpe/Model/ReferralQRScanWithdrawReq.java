package com.wallet.speedpe.Model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferralQRScanWithdrawReq implements Parcelable
{

    @SerializedName("memberId")
    @Expose
    private String memberId;
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
    
    public final static Creator<ReferralQRScanWithdrawReq> CREATOR = new Creator<ReferralQRScanWithdrawReq>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ReferralQRScanWithdrawReq createFromParcel(android.os.Parcel in) {
            return new ReferralQRScanWithdrawReq(in);
        }

        public ReferralQRScanWithdrawReq[] newArray(int size) {
            return (new ReferralQRScanWithdrawReq[size]);
        }

    }
            ;

    protected ReferralQRScanWithdrawReq(android.os.Parcel in) {
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.withdrawalDate = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.upiId = ((String) in.readValue((String.class.getClassLoader())));
        this.transactionStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.purpose = ((String) in.readValue((String.class.getClassLoader())));
        this.withdrawalTransactionId = ((String) in.readValue((String.class.getClassLoader())));
        this.withdrawalResponce = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ReferralQRScanWithdrawReq() {
    }

    public ReferralQRScanWithdrawReq(String memberId, String withdrawalDate, Integer amount, String upiId, String transactionStatus, Boolean status, String name, String purpose, String withdrawalTransactionId, String withdrawalResponce) {
        super();
        this.memberId = memberId;
        this.withdrawalDate = withdrawalDate;
        this.amount = amount;
        this.upiId = upiId;
        this.transactionStatus = transactionStatus;
        this.status = status;
        this.name = name;
        this.purpose = purpose;
        this.withdrawalTransactionId = withdrawalTransactionId;
        this.withdrawalResponce = withdrawalResponce;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(memberId);
        dest.writeValue(withdrawalDate);
        dest.writeValue(amount);
        dest.writeValue(upiId);
        dest.writeValue(transactionStatus);
        dest.writeValue(status);
        dest.writeValue(name);
        dest.writeValue(purpose);
        dest.writeValue(withdrawalTransactionId);
        dest.writeValue(withdrawalResponce);
    }

    public int describeContents() {
        return 0;
    }
}
