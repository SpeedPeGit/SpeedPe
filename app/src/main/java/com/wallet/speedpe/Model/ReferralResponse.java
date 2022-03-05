package com.wallet.speedpe.Model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferralResponse implements Parcelable {

    @SerializedName("availableBonus")
    @Expose
    private Integer availableBonus;
    @SerializedName("settledBonus")
    @Expose
    private Integer settledBonus;
    @SerializedName("totalBonus")
    @Expose
    private Integer totalBonus;
    @SerializedName("refferalCode")
    @Expose
    private String refferalCode;

    public final static Creator<ReferralResponse> CREATOR = new Creator<ReferralResponse>() {
        @SuppressWarnings({
                "unchecked"
        })
        public ReferralResponse createFromParcel(android.os.Parcel in) {
            return new ReferralResponse(in);
        }

        public ReferralResponse[] newArray(int size) {
            return (new ReferralResponse[size]);
        }
    };

    protected ReferralResponse(android.os.Parcel in) {
        this.availableBonus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.settledBonus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalBonus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.refferalCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ReferralResponse() {
    }

    public ReferralResponse(Integer availableBonus, Integer settledBonus, Integer totalBonus, String refferalCode) {
        super();
        this.availableBonus = availableBonus;
        this.settledBonus = settledBonus;
        this.totalBonus = totalBonus;
        this.refferalCode = refferalCode;
    }

    public Integer getAvailableBonus() {
        return availableBonus;
    }
    public void setAvailableBonus(Integer availableBonus) {
        this.availableBonus = availableBonus;
    }

    public Integer getSettledBonus() {
        return settledBonus;
    }
    public void setSettledBonus(Integer settledBonus) {
        this.settledBonus = settledBonus;
    }

    public Integer getTotalBonus() {
        return totalBonus;
    }
    public void setTotalBonus(Integer totalBonus) {
        this.totalBonus = totalBonus;
    }

    public String getRefferalCode() {
        return refferalCode;
    }
    public void setRefferalCode(String refferalCode) {
        this.refferalCode = refferalCode;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(availableBonus);
        dest.writeValue(settledBonus);
        dest.writeValue(totalBonus);
        dest.writeValue(refferalCode);
    }

    public int describeContents() {
        return 0;
    }
}