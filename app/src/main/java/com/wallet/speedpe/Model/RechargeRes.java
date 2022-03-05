package com.wallet.speedpe.Model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RechargeRes implements Parcelable {

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
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("usedLimit")
    @Expose
    private Integer usedLimit;
    @SerializedName("pendingLimit")
    @Expose
    private Integer pendingLimit;
    @SerializedName("totalLimit")
    @Expose
    private Integer totalLimit;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("lastModificationTime")
    @Expose
    private Object lastModificationTime;
    @SerializedName("lastModifierId")
    @Expose
    private Object lastModifierId;
    @SerializedName("creationTime")
    @Expose
    private String creationTime;
    @SerializedName("creatorId")
    @Expose
    private Object creatorId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rzp_id")
    @Expose
    private String rzp_id;
    @SerializedName("rzp_orderId")
    @Expose
    private String rzp_orderId;
    @SerializedName("rzp_paymentId")
    @Expose
    private String rzp_paymentId;
    @SerializedName("rzp_signatureId")
    @Expose
    private String rzp_signatureId;

    public final static Creator<RechargeRes> CREATOR = new Creator<RechargeRes>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RechargeRes createFromParcel(android.os.Parcel in) {
            return new RechargeRes(in);
        }

        public RechargeRes[] newArray(int size) {
            return (new RechargeRes[size]);
        }

    };

    protected RechargeRes(android.os.Parcel in) {
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
        this.planId = ((String) in.readValue((String.class.getClassLoader())));
        this.rechargeDate = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.transactionId = ((String) in.readValue((String.class.getClassLoader())));
        this.usedLimit = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.pendingLimit = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalLimit = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.isActive = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.lastModificationTime = ((Object) in.readValue((Object.class.getClassLoader())));
        this.lastModifierId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.creationTime = ((String) in.readValue((String.class.getClassLoader())));
        this.creatorId = ((Object) in.readValue((Object.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.rzp_id = ((String) in.readValue((String.class.getClassLoader())));
        this.rzp_orderId = ((String) in.readValue((String.class.getClassLoader())));
        this.rzp_paymentId = ((String) in.readValue((String.class.getClassLoader())));
        this.rzp_signatureId = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public RechargeRes() {
    }

    public RechargeRes(String memberId, String planId, String rechargeDate, Integer amount, String transactionId, Integer usedLimit, Integer pendingLimit, Integer totalLimit, Boolean isActive, Object lastModificationTime, Object lastModifierId, String creationTime, Object creatorId, String id
    , String rzp_id, String rzp_orderId, String rzp_paymentId, String rzp_signatureId) {
        super();
        this.memberId = memberId;
        this.planId = planId;
        this.rechargeDate = rechargeDate;
        this.amount = amount;
        this.transactionId = transactionId;
        this.usedLimit = usedLimit;
        this.pendingLimit = pendingLimit;
        this.totalLimit = totalLimit;
        this.isActive = isActive;
        this.lastModificationTime = lastModificationTime;
        this.lastModifierId = lastModifierId;
        this.creationTime = creationTime;
        this.creatorId = creatorId;
        this.id = id;
        this.rzp_id = rzp_id;
        this.rzp_orderId = rzp_orderId;
        this.rzp_paymentId = rzp_paymentId;
        this.rzp_signatureId = rzp_signatureId;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getUsedLimit() {
        return usedLimit;
    }

    public void setUsedLimit(Integer usedLimit) {
        this.usedLimit = usedLimit;
    }

    public Integer getPendingLimit() {
        return pendingLimit;
    }

    public void setPendingLimit(Integer pendingLimit) {
        this.pendingLimit = pendingLimit;
    }

    public Integer getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(Integer totalLimit) {
        this.totalLimit = totalLimit;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Object getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Object lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public Object getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(Object lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public Object getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Object creatorId) {
        this.creatorId = creatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRzp_id() {
        return rzp_id;
    }

    public void setRzp_id(String rzp_id) {
        this.rzp_id = rzp_id;
    }

    public String getRzp_orderId() {
        return rzp_orderId;
    }

    public void setRzp_orderId(String rzp_orderId) {
        this.rzp_orderId = rzp_orderId;
    }

    public String getRzp_paymentId() {
        return rzp_paymentId;
    }

    public void setRzp_paymentId(String rzp_paymentId) {
        this.rzp_paymentId = rzp_paymentId;
    }

    public String getRzp_signatureId() {
        return rzp_signatureId;
    }

    public void setRzp_signatureId(String rzp_signatureId) {
        this.rzp_signatureId = rzp_signatureId;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(memberId);
        dest.writeValue(planId);
        dest.writeValue(rechargeDate);
        dest.writeValue(amount);
        dest.writeValue(transactionId);
        dest.writeValue(usedLimit);
        dest.writeValue(pendingLimit);
        dest.writeValue(totalLimit);
        dest.writeValue(isActive);
        dest.writeValue(lastModificationTime);
        dest.writeValue(lastModifierId);
        dest.writeValue(creationTime);
        dest.writeValue(creatorId);
        dest.writeValue(id);
        dest.writeValue(rzp_id);
        dest.writeValue(rzp_orderId);
        dest.writeValue(rzp_paymentId);
        dest.writeValue(rzp_signatureId);
    }

    public int describeContents() {
        return 0;
    }

}