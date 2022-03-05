package com.wallet.speedpe.Model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("referralCode")
    @Expose
    private String referralCode;


    public DataModel(String name, String mobileNo, String email, String password, Boolean isDeleted, String referralCode) {
        this.name = name;
        this.mobileNo = mobileNo;
        this.email = email;
        this.password = password;
        this.isDeleted = isDeleted;
        this.referralCode = referralCode;
    }

    public final static Creator<DataModel> CREATOR = new Creator<DataModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DataModel createFromParcel(android.os.Parcel in) {
            return new DataModel(in);
        }

        public DataModel[] newArray(int size) {
            return (new DataModel[size]);
        }

    };

    protected DataModel(android.os.Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.mobileNo = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.isDeleted = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.referralCode = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DataModel() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getreferralCode() {
        return referralCode;
    }

    public void setreferralCode(String referralCode) {
        this.referralCode = referralCode;
    }


    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(mobileNo);
        dest.writeValue(email);
        dest.writeValue(password);
        dest.writeValue(isDeleted);
        dest.writeValue(referralCode);
    }

    public int describeContents() {
        return 0;
    }

}