package com.example.ebot.services


import com.example.ebot.models.*
import com.example.ebot.services.ServiceManager.Companion.ROOT_URL_SUB
import retrofit2.Call

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface APIInterface {


    @GET(ROOT_URL_SUB+"v1/about")/*{"mobile":""}*/
    fun about(): Call<MainResponse>

    @GET(ROOT_URL_SUB+"v1/privacy")/*{"mobile":""}*/
    fun privacy(): Call<MainResponse>

    @GET(ROOT_URL_SUB+"v1/terms")/*{"mobile":""}*/
    fun terms(): Call<MainResponse>

    @GET(ROOT_URL_SUB+"ApiContactController/contact")/*{"mobile":""}*/
    fun contact(): Call<MainResponse>

    @GET(ROOT_URL_SUB+"v1/faq/list")/*{"mobile":""}*/
    fun faqs(@Query("id") id:Int): Call<FAQsMainResponse>

    @POST(ROOT_URL_SUB+"ApiEnquiryController/submit_enquiry")
    fun submitEnquiry(@Body request: SubmitEnquiry): Call<MainResponse>

    @FormUrlEncoded
    @POST(ROOT_URL_SUB+"ApiUsersController/login")
    fun loginUser(@Field("mobile")mobile:String): Call<LoginResponse>
    @FormUrlEncoded
    @POST(ROOT_URL_SUB+"ApiUsersController/register")
    fun registerUser(@Field("mobile")mobile:String): Call<RegisterResponse>

    @FormUrlEncoded
    @POST(ROOT_URL_SUB+"ApiUsersController/verify_otp")
    fun verifyOTP( @Field("otp") otp: String,@Field("user_id") user_id: String): Call<MainResponse>

    @POST(ROOT_URL_SUB+"v1/profile/myteam")
    fun getMyTeam(@Body request: UserIdFeildJson): Call<MainResponse>


    @POST(ROOT_URL_SUB+"ApiPackagesController/packages_list")/*{"mobile":""}*/
    fun packages(): Call<MainResponse>

    @POST(ROOT_URL_SUB+"v1/profile/update")
    fun submitUpdateProfile(@Body request: UpdateProfile): Call<MainResponse>

    @POST(ROOT_URL_SUB+"v1/profile/update")
    fun submitUpdateProfileRefer(@Body request: UpdateProfile): Call<MainResponse>

    @POST(ROOT_URL_SUB+"ApiProfileController/fetch_profile")
    fun fetchProfile(@Body request: UserIdFeildJson): Call<MainResponse>

    @FormUrlEncoded
    @POST(ROOT_URL_SUB+"ApiBankDetailsController/bank_details")
    fun getBankDetails(@Field("user_id")user_id:String): Call<MainResponse>

   @FormUrlEncoded
    @POST(ROOT_URL_SUB+"ApiBankDetailsController/update_bank_details")
    fun updateBankDetails(@Body request: BankDetails): Call<MainResponse>


    @FormUrlEncoded
    @POST(ROOT_URL_SUB+"ApiBankDetailsController/save_bank_details")
    fun saveBankDetails(@Field("user_id")  user_id: String,
                        @Field("account_number")  account_number: String,
                        @Field("bank_name")  bank_name: String,
                        @Field("ifsc_code")  ifsc_code: String?,
                        @Field("account_type")  account_type: String): Call<SaveBankDetailsResponse>



    @FormUrlEncoded
    @POST(ROOT_URL_SUB+"v1/bankdetails/delete")
    fun removeBankDetails(@Field("id")id:Int):Call<MainResponse>

    @Multipart
    @POST(ROOT_URL_SUB+"v1/save_neft")
    fun saveNEFT(@Part("user_id")user_id: RequestBody, @Part("amount")amount:RequestBody,
                 @Part("transaction_utr")transaction_utr:RequestBody,
                 @Part neft_image : MultipartBody.Part):Call<MainResponse>

    @POST(ROOT_URL_SUB+"v1/withdraw")
    fun withdraw(@Body request: Withdraw):Call<MainResponse>

    @Multipart
    @POST(ROOT_URL_SUB+"v1/kycdetails/add")
    fun addKYC(@Part("user_id")user_id: RequestBody, @Part("aadhar_number")aadhar_number:RequestBody,
                 @Part("pan_number")pan_number:RequestBody,
                 @Part aadhar_front : MultipartBody.Part?,
                 @Part aadhar_back : MultipartBody.Part?,
                 @Part pan_image : MultipartBody.Part?):Call<MainResponse>

    @Multipart
    @POST(ROOT_URL_SUB+"v1/profile/update_profile_image")
    fun updateProfileImage(@Part("user_id")user_id: RequestBody,
                 @Part profile_image : MultipartBody.Part):Call<MainResponse>

    @POST(ROOT_URL_SUB+"v1/buy_now")
    fun packageBuyNow(@Body request: PackageBuyNow):Call<MainResponse>

    @POST(ROOT_URL_SUB+"v1/wallet_amount")
    fun getWalletAmount(@Body request: UserIdFeildJson):Call<MainResponse>

        @POST(ROOT_URL_SUB + "v1/profile/profile_list")
    fun getProfileList(@Body request: UserIdFeildJson): Call<MainResponse>

    @POST(ROOT_URL_SUB + "ApiKycController/kyc_details")
    fun getKYCDetails(@Body request: UserIdFeildJson): Call<FetchedKYCResponse>

    @POST(ROOT_URL_SUB + "v1/wallet_withdraw_list")
    fun getWalletWithdrawList(@Body request: UserIdFeildJson): Call<MainResponse>

    @POST(ROOT_URL_SUB + "ApiKycController/wallet_neft_list")
    fun getWalletNEFTList(@Body request: UserIdFeildJson): Call<MainResponse>

    @POST(ROOT_URL_SUB + "v1/get_user_purchase_history")
    fun getPurchaseHistory(@Body request: UserIdFeildJson): Call<MainResponse>

    @POST(ROOT_URL_SUB + "v1/transaction_history")
    fun getTransactionHistory(@Body request: UserIdFeildJson): Call<MainResponse>

}