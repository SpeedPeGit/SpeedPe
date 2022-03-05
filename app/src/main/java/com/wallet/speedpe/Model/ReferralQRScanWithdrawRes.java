package com.wallet.speedpe.Model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferralQRScanWithdrawRes implements Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("shortMessage")
    @Expose
    private String shortMessage;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("withdrawalTransactionId")
    @Expose
    private Object withdrawalTransactionId;
    @SerializedName("memberId")
    @Expose
    private String memberId;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("mobileNo")
    @Expose
    private Object mobileNo;
    @SerializedName("refferalCode")
    @Expose
    private String refferalCode;
    
    public final static Creator<ReferralQRScanWithdrawRes> CREATOR = new Creator<ReferralQRScanWithdrawRes>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ReferralQRScanWithdrawRes createFromParcel(android.os.Parcel in) {
            return new ReferralQRScanWithdrawRes(in);
        }

        public ReferralQRScanWithdrawRes[] newArray(int size) {
            return (new ReferralQRScanWithdrawRes[size]);
        }

    };

    protected ReferralQRScanWithdrawRes(android.os.Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.shortMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.withdrawalTransactionId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((Object) in.readValue((Object.class.getClassLoader())));
        this.mobileNo = ((Object) in.readValue((Object.class.getClassLoader())));
        this.refferalCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public ReferralQRScanWithdrawRes() {
    }

    /**
     *
     * @param withdrawalTransactionId
     * @param shortMessage
     * @param name
     * @param mobileNo
     * @param message
     * @param status
     * @param memberId
     */
    public ReferralQRScanWithdrawRes(Boolean status, String shortMessage, String message, Object withdrawalTransactionId, String memberId, Object name, Object mobileNo) {
        super();
        this.status = status;
        this.shortMessage = shortMessage;
        this.message = message;
        this.withdrawalTransactionId = withdrawalTransactionId;
        this.memberId = memberId;
        this.name = name;
        this.mobileNo = mobileNo;
        this.refferalCode = refferalCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getWithdrawalTransactionId() {
        return withdrawalTransactionId;
    }

    public void setWithdrawalTransactionId(Object withdrawalTransactionId) {
        this.withdrawalTransactionId = withdrawalTransactionId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Object mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getRefferalCode() {
        return refferalCode;
    }

    public void setRefferalCode(String refferalCode) {
        this.refferalCode = refferalCode;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(shortMessage);
        dest.writeValue(message);
        dest.writeValue(withdrawalTransactionId);
        dest.writeValue(memberId);
        dest.writeValue(name);
        dest.writeValue(mobileNo);
        dest.writeValue(refferalCode);
    }

    public int describeContents() {
        return 0;
    }
}
