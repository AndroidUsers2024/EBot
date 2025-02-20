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


    @GET(ROOT_URL_SUB+"aboutus")/*{"mobile":""}*/
    fun about(): Call<CMS>

    @GET(ROOT_URL_SUB+"privacy_policy")/*{"mobile":""}*/
    fun privacy(): Call<CMS>

    @GET(ROOT_URL_SUB+"terms_conditions")/*{"mobile":""}*/
    fun terms(): Call<CMS>

    @GET(ROOT_URL_SUB+"contactus")/*{"mobile":""}*/
    fun contact(): Call<Contact>

    @GET(ROOT_URL_SUB+"faq")/*{"mobile":""}*/
    fun faqs(): Call<List<Faqs>>

    @POST(ROOT_URL_SUB+"enquiry")
    fun submitEnquiry(@Body request: SubmitEnquiry): Call<MainResponse>

    @POST(ROOT_URL_SUB+"login")
    fun loginUser(@Body request: UserCommonJson): Call<LoginResponse>

    @POST(ROOT_URL_SUB+"registration")
    fun registerUser(@Body reqData:RegisterData): Call<MainResponse>

    @POST(ROOT_URL_SUB+"verify_otp")
    fun verifyOTP( @Body verifyOTP:UserCommonJson): Call<LoginResponse>

    @GET(ROOT_URL_SUB + "profile_get")
    fun getProfile(@Query("id") id: String): Call<ProfileResponse>

    @POST(ROOT_URL_SUB+"v1/profile/myteam")
    fun getMyTeam(@Body request: UserCommonJson): Call<MainResponse>


    @POST(ROOT_URL_SUB+"vehicle")/*{"mobile":""}*/
    fun getVehicles(): Call<List<Vehicle>>

    @POST(ROOT_URL_SUB+"v1/profile/update")
    fun submitUpdateProfile(@Body request: UpdateProfile): Call<MainResponse>

    @POST(ROOT_URL_SUB+"v1/profile/update")
    fun submitUpdateProfileRefer(@Body request: UpdateProfile): Call<MainResponse>



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
    @POST(ROOT_URL_SUB + "save_kyc_details")
    fun addKYC(
        @Part("user_id") user_id: RequestBody, @Part("aadhar_number") aadhar_number: RequestBody,
        @Part("pan_number") pan_number: RequestBody,
        @Part aadhar_front: MultipartBody.Part?,
        @Part aadhar_back: MultipartBody.Part?,
        @Part pan_image: MultipartBody.Part?,
        @Part("account_number") account_number: RequestBody,
        @Part("bank_name") bank_name: RequestBody,
        @Part("ifsc_code") ifsc_code: RequestBody,
        @Part("account_type") account_type: RequestBody,
        @Part face_verificaton: MultipartBody.Part?
    ): Call<MainResponse>


  @Multipart
    @POST(ROOT_URL_SUB + "save_kyc_details")
    fun updateProfile(
        @Part("user_id") user_id: RequestBody, @Part("name") name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("email") email: RequestBody,
        @Part("address") address: RequestBody,
        @Part("pin_code") pin_code: RequestBody,
        @Part("city") city: RequestBody,
        @Part("state") state: RequestBody,
        @Part("country") country: RequestBody,
        @Part image: MultipartBody.Part?,

    ): Call<MainResponse>



    @POST(ROOT_URL_SUB+"v1/buy_now")
    fun packageBuyNow(@Body request: PackageBuyNow):Call<MainResponse>

    @POST(ROOT_URL_SUB+"v1/wallet_amount")
    fun getWalletAmount(@Body request: UserCommonJson):Call<MainResponse>



    @POST(ROOT_URL_SUB + "ApiKycController/kyc_details")
    fun getKYCDetails(@Body request: UserCommonJson): Call<FetchedKYCResponse>

    @POST(ROOT_URL_SUB + "v1/wallet_withdraw_list")
    fun getWalletWithdrawList(@Body request: UserCommonJson): Call<MainResponse>

    @POST(ROOT_URL_SUB + "ApiKycController/wallet_neft_list")
    fun getWalletNEFTList(@Body request: UserCommonJson): Call<MainResponse>

    @POST(ROOT_URL_SUB + "v1/get_user_purchase_history")
    fun getPurchaseHistory(@Body request: UserCommonJson): Call<MainResponse>

    @POST(ROOT_URL_SUB + "v1/transaction_history")
    fun getTransactionHistory(@Body request: UserCommonJson): Call<MainResponse>

}