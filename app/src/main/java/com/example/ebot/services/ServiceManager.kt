package com.example.ebot.services

import com.example.ebot.models.*
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceManager {
    val ROOT_URL = "https://ritps.com/ebot/"

    companion object {
        private var dataManager: ServiceManager? = null
        const val  ROOT_URL_SUB = "api/"
        const val SUB_ROOT_URL = ""
        @JvmStatic
        fun getDataManager(): ServiceManager {
            if (dataManager == null) {
                dataManager = ServiceManager()
            }
            return dataManager as ServiceManager
        }
    }

    private val retrofit: Retrofit

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.callTimeout(5, TimeUnit.MINUTES)
        httpClient.readTimeout(5, TimeUnit.MINUTES)
        httpClient.addInterceptor(logging)
        val gson = GsonBuilder()
            .setLenient()
            .create()

        retrofit = Retrofit.Builder().baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    fun getAbout(cb: Callback<CMS>){
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.about()
        call.enqueue(cb)
    }

    fun getPrivacy(cb: Callback<CMS>) {

        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.privacy()
        call.enqueue(cb)
    }
    fun getTerms(cb: Callback<CMS>) {

        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.terms()
        call.enqueue(cb)
    }


    fun getContact(cb: Callback<Contact>) {

        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.contact()
        call.enqueue(cb)
    }

    fun getVehicles(cb: Callback<List<Vehicle>>) {
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.getVehicles()
        call.enqueue(cb)
    }
    fun faqs(cb: Callback<List<Faqs>>) {

        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.faqs()
        call.enqueue(cb)
    }

    fun submitEnquiryForm(cb: Callback<MainResponse>,data:SubmitEnquiry){
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.submitEnquiry(data)
        call.enqueue(cb)
    }
    fun verifyOTP(cb: Callback<LoginResponse>,req:UserCommonJson){
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.verifyOTP(req)
        call.enqueue(cb)
    }

    fun loginUser(cb: Callback<LoginResponse>,loginRequest:UserCommonJson){
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.loginUser(loginRequest)
        call.enqueue(cb)
    }
    fun registerUser(cb: Callback<RegisterResponse>,data:RegisterData){
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.registerUser(data)
        call.enqueue(cb)
    }

    fun submitUpateProfile(cb: Callback<MainResponse>,data: UpdateProfile){
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.submitUpdateProfile(data)
        call.enqueue(cb)
    }
    fun submitUpateProfileRefer(cb: Callback<MainResponse>,data: UpdateProfile){
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.submitUpdateProfileRefer(data)
        call.enqueue(cb)
    }

    fun fetchProfile(cb: Callback<ProfileResponse>, userId: String) {
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.getProfile(userId)
        call.enqueue(cb)
    }
    fun getHomeBanner(cb: Callback<ArrayList<HomeBannerList>>) {
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.getHomeBanner()
        call.enqueue(cb)
    }
    fun getHubList(cb: Callback<ArrayList<HubList>>) {
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.getHubList()
        call.enqueue(cb)
    }

    fun fetchBankDetails(cb: Callback<BankDataResponse>, userId: String){
        val api=retrofit.create(APIInterface::class.java)
        val user=UserCommonJson(user_id = userId)
        val call=api.getBankDetails(user)
        call.enqueue(cb)
    }

    fun updateBankDetails(cb: Callback<MainResponse>, bankDetails: BankDetails){
        val apiService=retrofit.create(APIInterface::class.java)
        val call =apiService.updateBankDetails(bankDetails)
        call.enqueue(cb)
    }
    fun saveBankDetails(cb: Callback<SaveBankDetailsResponse>, bankDetails: SaveBankDetails){
        val apiService=retrofit.create(APIInterface::class.java)
        val call =apiService.saveBankDetails(bankDetails)
        call.enqueue(cb)
    }
    fun removeBankDetails(cb: Callback<MainResponse>, id: String){
        val apiService=retrofit.create(APIInterface::class.java)
        val bank_id=UserCommonJson(bank_id=id)
        val call =apiService.removeBankDetails(bank_id)
        call.enqueue(cb)
    }
    fun saveNEFT(cb: Callback<MainResponse>, userId: RequestBody, amount:RequestBody, transaction_utr:RequestBody, imageFile: MultipartBody.Part){
        val apiService=retrofit.create(APIInterface::class.java)
        val call =apiService.saveNEFT(userId,amount,transaction_utr,imageFile)
        call.enqueue(cb)
    }

    fun withdraw(cb: Callback<MainResponse>, withdraw: Withdraw){
        val apiService=retrofit.create(APIInterface::class.java)
        val call =apiService.withdraw(withdraw)
        call.enqueue(cb)
    }

    fun addKYC(
        cb: Callback<MainResponse>, user_id: RequestBody?, aadhar_number: RequestBody?,
        pan_number: RequestBody?,
        aadhar_front: MultipartBody.Part?,
        aadhar_back: MultipartBody.Part?,
        pan_image: MultipartBody.Part?,
        account_number: RequestBody?,
        bank_name: RequestBody?,
        ifsc_code: RequestBody?,
        account_type: RequestBody?,
        face_verificaton: MultipartBody.Part?
    ) {
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.addKYC(user_id, aadhar_number, pan_number, aadhar_front,aadhar_back,pan_image,account_number,bank_name,ifsc_code,account_type,face_verificaton)
        call.enqueue(cb)
    }

    fun updateProfile(
        cb: Callback<MainResponse>,
        user_id: RequestBody,
        name: RequestBody,
        last_name: RequestBody,
        gender: RequestBody,
        dob: RequestBody,
        phone: RequestBody,
        email: RequestBody,
        address: RequestBody,
        pin_code: RequestBody,
        city: RequestBody,
        state: RequestBody,
        country: RequestBody,
        image: MultipartBody.Part?,
    ) {
        val apiService = retrofit.create(APIInterface::class.java)
        val call =
            apiService.updateProfile(
                user_id, name, last_name, gender, dob, phone, email, address, pin_code, city, state, country, image)
        call.enqueue(cb)
    }


    fun addAmount(cb: Callback<AddAmountResponse>, withdraw: Withdraw){
        val apiService=retrofit.create(APIInterface::class.java)
        val call =apiService.addAmount(withdraw)
        call.enqueue(cb)
    }

    fun packageBuyNow(cb:Callback<MainResponse>,requestBody: PackageBuyNow){
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.packageBuyNow(requestBody)
        call.enqueue(cb)
    }
    fun getWalletAmount(cb:Callback<MainResponse>,user_id:String){
        val userId=UserCommonJson(user_id)
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.getWalletAmount(userId)
        call.enqueue(cb)
    }

    fun getKYCDetails(cb:Callback<KYCResponse>,  user_id:String){
        val userId=UserCommonJson(user_id)
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.getKYCDetail(userId)
        call.enqueue(cb)
    }

    fun getWalletWithdrawList(cb:Callback<MainResponse>,user_id:String){
        val userId=UserCommonJson(user_id)
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.getWalletWithdrawList(userId)
        call.enqueue(cb)
    }
    fun getWalletNEFTList(cb:Callback<MainResponse>,user_id:String){
        val userId=UserCommonJson(user_id)
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.getWalletNEFTList(userId)
        call.enqueue(cb)
    }

    fun getPurchaseHistory(cb:Callback<MainResponse>,user_id:String){
        val userId=UserCommonJson(user_id)
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.getPurchaseHistory(userId)
        call.enqueue(cb)
    }

    fun getTransactionHistory(cb:Callback<MainResponse>,user_id:String){
        val userId=UserCommonJson(user_id)
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.getTransactionHistory(userId)
        call.enqueue(cb)
    }

    fun getTimeSlot(cb: Callback<List<TimeSlot>>, vehicle_id: String, hublist_id: String?){
        val userId=UserCommonJson(vehicle_id =vehicle_id, hublist_id = hublist_id )
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.getTimeSlot(userId)
        call.enqueue(cb)
    }
    fun vehicleBooking(cb:Callback<BookingResponse>, data:BookVehicle){
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.vehicleBooking(data)
        call.enqueue(cb)
    }

 fun getAllTransaction(cb:Callback<TransactionResponse>, userId:UserCommonJson){
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.getAllTransaction(userId)
        call.enqueue(cb)
    }

    fun getMyRides(cb: Callback<MyRidesResponse>,user_id:String) {
        val userId=UserCreatedByJson(user_id)
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.getMyRides(userId)
        call.enqueue(cb)
    }
    fun calcelRide(cb: Callback<RideCancelResponse>,id:String,reason:String) {
        val userId=CancelData(id,reason)
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.cancelRide(userId)
        call.enqueue(cb)
    }

    fun updateKYC(
        cb: Callback<MainResponse>, user_id: RequestBody, aadhar_number: RequestBody,
        pan_number: RequestBody,
        aadhar_front: MultipartBody.Part?,
        aadhar_back: MultipartBody.Part?,
        pan_image: MultipartBody.Part?,
        account_number: RequestBody,
        bank_name: RequestBody,
        ifsc_code: RequestBody,
        account_type: RequestBody,
        face_verificaton: MultipartBody.Part?
    ) {
        val apiService = retrofit.create(APIInterface::class.java)
        val call = apiService.updateKYC(user_id, aadhar_number, pan_number, aadhar_front,aadhar_back,pan_image,account_number,bank_name,ifsc_code,account_type,face_verificaton)
        call.enqueue(cb)
    }
    fun deleteAccount(cb:Callback<MainResponse>,  user_id:String){
        val user=UserCommonJson(id=user_id, status = "0")
        val apiService=retrofit.create(APIInterface::class.java)
        val call=apiService.deleteAccount(user)
        call.enqueue(cb)
    }
}