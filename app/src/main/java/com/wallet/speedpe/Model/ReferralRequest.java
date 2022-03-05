package com.wallet.speedpe.Model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReferralRequest implements Parcelable
{

    @SerializedName("memberId")
    @Expose
    private String memberId;

    public final static Creator<ReferralRequest> CREATOR = new Creator<ReferralRequest>() {
        @SuppressWarnings({
                "unchecked"
        })
        public ReferralRequest createFromParcel(android.os.Parcel in) {
            return new ReferralRequest(in);
        }

        public ReferralRequest[] newArray(int size) {
            return (new ReferralRequest[size]);
        }

    };

    protected ReferralRequest(android.os.Parcel in) {
        this.memberId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ReferralRequest() {
    }

    public ReferralRequest(String memberId) {
        super();
        this.memberId = memberId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(memberId);
    }

    public int describeContents() {
        return 0;
    }

}
