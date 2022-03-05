package com.wallet.speedpe.rest;

import com.wallet.speedpe.Model.DataModel;
import com.wallet.speedpe.Model.QRScanWithdrawReq;
import com.wallet.speedpe.Model.QRScanWithdrawRes;
import com.wallet.speedpe.Model.RechargeReq;
import com.wallet.speedpe.Model.RechargeRes;
import com.wallet.speedpe.Model.ReferralQRScanWithdrawReq;
import com.wallet.speedpe.Model.ReferralQRScanWithdrawRes;
import com.wallet.speedpe.Model.ReferralRequest;
import com.wallet.speedpe.Model.ReferralResponse;
import com.wallet.speedpe.Model.RzpOrdIdRequest;
import com.wallet.speedpe.Model.RzpOrdIdResponse;
import com.wallet.speedpe.Model.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Manjunath Sunkad on 21-10-2021.
 */
public interface Api {

    @POST("member/signup")
    @Headers({
            "Accept: application/json",
            "Cache-Control: max-age=640000"
    })    Call<SignupResponse> createUser(@Body DataModel user);

    @POST("recharge")
    @Headers({
            "Accept: application/json",
            "Cache-Control: max-age=640000"
    })    Call<RechargeRes> recharge(@Body RechargeReq rechargeReq);

    @POST("referral-withdrawal/myrefferalstatus")
    @Headers({
            "Accept: application/json",
            "Cache-Control: max-age=640000"
    })    Call<ReferralResponse> myrefferalstatus(@Body ReferralRequest referralRequest);

    @POST("withdrawal/withdrawrequest")
    @Headers({
            "Accept: application/json",
            "Cache-Control: max-age=640000"
    })    Call<QRScanWithdrawRes> withdrawreq(@Body QRScanWithdrawReq qrScanWithdrawReq);

    @POST("referral-withdrawal/refferalwithdrawrequest")
    @Headers({
            "Accept: application/json",
            "Cache-Control: max-age=640000"
    })    Call<ReferralQRScanWithdrawRes> withdrawreq(@Body ReferralQRScanWithdrawReq referralQRScanWithdrawReq);

    @POST("recharge-order")
    @Headers({
            "Accept: application/json",
            "Cache-Control: max-age=640000"
    })    Call<RzpOrdIdResponse> rzpOrderId(@Body RzpOrdIdRequest rzpOrdIdRequest);

}
