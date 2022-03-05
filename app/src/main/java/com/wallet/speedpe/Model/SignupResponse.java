package com.wallet.speedpe.Model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignupResponse implements Parcelable
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
    private String name;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    public final static Creator<SignupResponse> CREATOR = new Creator<SignupResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SignupResponse createFromParcel(android.os.Parcel in) {
            return new SignupResponse(in);
        }

        public SignupResponse[] newArray(int size) {
            return (new SignupResponse[size]);
        }

    }
            ;

    protected SignupResponse(android.os.Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.shortMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.withdrawalTransactionId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.mobileNo = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public SignupResponse() {
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
    public SignupResponse(Boolean status, String shortMessage, String message, Object withdrawalTransactionId, String memberId, String name, String mobileNo) {
        super();
        this.status = status;
        this.shortMessage = shortMessage;
        this.message = message;
        this.withdrawalTransactionId = withdrawalTransactionId;
        this.memberId = memberId;
        this.name = name;
        this.mobileNo = mobileNo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(shortMessage);
        dest.writeValue(message);
        dest.writeValue(withdrawalTransactionId);
        dest.writeValue(memberId);
        dest.writeValue(name);
        dest.writeValue(mobileNo);
    }

    public int describeContents() {
        return 0;
    }

}