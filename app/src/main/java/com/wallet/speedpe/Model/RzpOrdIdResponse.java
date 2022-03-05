package com.wallet.speedpe.Model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RzpOrdIdResponse implements Parcelable
{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("memberId")
    @Expose
    private String memberId;
    @SerializedName("planId")
    @Expose
    private String planId;
    @SerializedName("rechargeDate")
    @Expose
    private String rechargeDate;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("razorOrderId")
    @Expose
    private String razorOrderId;
    @SerializedName("transactionResult")
    @Expose
    private String transactionResult;

    public final static Creator<RzpOrdIdResponse> CREATOR = new Creator<RzpOrdIdResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RzpOrdIdResponse createFromParcel(android.os.Parcel in) {
            return new RzpOrdIdResponse(in);
        }

        public RzpOrdIdResponse[] newArray(int size) {
            return (new RzpOrdIdResponse[size]);
        }

    };

    protected RzpOrdIdResponse(android.os.Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.planId = ((String) in.readValue((String.class.getClassLoader())));
        this.rechargeDate = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.razorOrderId = ((String) in.readValue((String.class.getClassLoader())));
        this.transactionResult = ((String) in.readValue((String.class.getClassLoader())));
    }

    public RzpOrdIdResponse() {
    }

    public RzpOrdIdResponse(String id, String memberId, String planId, String rechargeDate, Integer amount, String razorOrderId, String transactionResult) {
        super();
        this.id = id;
        this.memberId = memberId;
        this.planId = planId;
        this.rechargeDate = rechargeDate;
        this.amount = amount;
        this.razorOrderId = razorOrderId;
        this.transactionResult = transactionResult;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }


    public String getRechargeDate() {
        return rechargeDate;
    }

    public void setRechargeDate(String rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRazorOrderId() {
        return razorOrderId;
    }

    public void setRazorOrderId(String razorOrderId) {
        this.razorOrderId = razorOrderId;
    }

    public String getTransactionResult() {
        return transactionResult;
    }

    public void setTransactionResult(String transactionResult) {
        this.transactionResult = transactionResult;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(memberId);
        dest.writeValue(planId);
        dest.writeValue(rechargeDate);
        dest.writeValue(amount);
        dest.writeValue(razorOrderId);
        dest.writeValue(transactionResult);
    }

    public int describeContents() {
        return 0;
    }
}