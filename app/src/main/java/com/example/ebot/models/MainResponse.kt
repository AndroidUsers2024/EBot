package com.example.ebot.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MainResponse (
    @SerializedName("status"  ) var status  : String?    = "",
    @SerializedName("message" ) var message : String? = "",
    @SerializedName("data"    ) var data    : Data   = Data(),
//    @SerializedName("profile_image" ) var profile_image :String?  = ""
    )
data class Data (

   // @SerializedName("customer_data" ) var customerData : UserLogin ?=null,
    @SerializedName("about" ) var about :CMS = CMS(),
    @SerializedName("privacy" ) var privacy : CMS = CMS(),
    @SerializedName("terms" ) var terms : ArrayList<Terms> = arrayListOf(),
    @SerializedName("contact" ) var contact : ArrayList<Contact> = arrayListOf(),
    @SerializedName("packages" ) var packages : ArrayList<Packages> = arrayListOf(),
    @SerializedName("profile" ) var profile: ArrayList<ProfileData> = arrayListOf(),
    @SerializedName("profile_details" ) var profile_details: ArrayList<ProfileData> = arrayListOf(),
    @SerializedName("updated_profile") var updated_profile : UpdateProfile=UpdateProfile(),
    @SerializedName("bank_details" ) var bank_details : ArrayList<BankDetails> = arrayListOf(),
    @SerializedName("neft_details" ) var neft_details : NEFTDetails = NEFTDetails(),
    @SerializedName("withdraw_details" ) var withdraw_details : Withdraw = Withdraw(),
    @SerializedName("kyc_details" ) var kyc_details :AddKYC  = AddKYC(),
    @SerializedName("buynow_details" ) var buynow_details :PackageBuyNow  = PackageBuyNow(),
    @SerializedName("wallet_balance"    ) var wallet_amount:  Float = 0F,
    @SerializedName("wallet_details") var wallet_detailsList:  ArrayList<WithdrawList>   = arrayListOf(),
    @SerializedName("wallet_neft_details") var wallet_neft_details:  ArrayList<WalletNEFTList>   = arrayListOf(),
    @SerializedName("transactionlist") var transactionlist:  ArrayList<TransactionList>   = arrayListOf(),
    // @SerializedName("notifications" ) var notifications : ArrayList<Notifications> = arrayListOf()
    @SerializedName("payment_history" ) var payment_history : ArrayList<PurchaseHistory> = arrayListOf(),
    @SerializedName("total_amount") var total_amount:String?=""

)
data class  CMS(
    @SerializedName("information_title" ) var information_title: String? = "",
    @SerializedName("description" ) var description: String? = "",
    @SerializedName("image" ) var image: String? = "",
    @SerializedName("created_date" ) var created_date: String? = "",
    @SerializedName("created_time" ) var created_time: String? = "",
)
data class  Privacy(
    @SerializedName("id" ) var id: String? = "",
    @SerializedName("privacy" ) var privacy: String? = ""
)
data class  Terms(
    @SerializedName("id" ) var id: String? = "",
    @SerializedName("terms" ) var terms: String? = ""
)
data class Contact (

    @SerializedName("email"  ) var email  : String? = "",
    @SerializedName("landline_no" ) var landline_no : String? = "",
    @SerializedName("phone" ) var phone : String? = "",
    @SerializedName("phone_2" ) var phone_2 : String? = "",
    @SerializedName("address" ) var address : String? = "",
    @SerializedName("logo" ) var logo : String? = "",
    @SerializedName("created_date" ) var created_date : String? = "",
    @SerializedName("created_time" ) var created_time : String? = ""

)

data class FAQsMainResponse (

    @SerializedName("status"  ) var status  : Boolean?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : FAQsDataMainResponse   = FAQsDataMainResponse()

)
data class FAQsDataMainResponse (

    @SerializedName("faqs" ) var faqs : ArrayList<Faqs> = arrayListOf()


)



data class Faqs (

    @SerializedName("title") var title       : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("created_by"   ) var created_by   : String? = null,
    @SerializedName("status"   ) var status   : String? = null,
    @SerializedName("created_date"   ) var created_date   : String? = null,
    @SerializedName("created_time"   ) var created_time   : String? = null,
    var isExpand:Boolean=false

)
data class SubmitEnquiry(
    @SerializedName("user_id") var user_id       : Int? = null,
    @SerializedName("subject" ) var subject : String? = null,
    @SerializedName("message"   ) var message   : String? = null,

)

data class UrlencodedData(
     @SerializedName("key") var key: String?="",
     @SerializedName("value") var value: String?="",
     @SerializedName("type") var type: String?=""
)


data class  LoginResponse(
    @SerializedName("status") var status: String?="",
    @SerializedName("message") var message: String?="",
    @SerializedName("user") var user: LoginResponseData=LoginResponseData(),
    @SerializedName("otp_sent") var otp_sent: String?=null
)
data class LoginResponseData(
    @SerializedName("id") var id:String?="",
    @SerializedName("name") var name:String?="",
    @SerializedName("last_name") var last_name:String?="",
    @SerializedName("gender") var gender:String?="",
    @SerializedName("dob") var dob:String?="",
    @SerializedName("phone") var phone:String?="",
    @SerializedName("email") var email:String?="",
    @SerializedName("address") var address:String?="",
    @SerializedName("pin_code") var pin_code:String?="",
    @SerializedName("city") var city:String?="",
    @SerializedName("state") var state:String?="",
    @SerializedName("country") var country:String?="",
    @SerializedName("created_at") var created_at:String?="",
)
data class  RegisterResponse(
    @SerializedName("status") var status: String?="",
    @SerializedName("message") var message: String?="",
    @SerializedName("user_id") var user_id: String?="",
    @SerializedName("otp_sent") var otp_sent: Boolean?=null,
)
data class RegisterData(
    @SerializedName("name") var name:String?="",
    @SerializedName("last_name") var last_name:String?="",
    @SerializedName("gender") var gender:String?="",
    @SerializedName("dob") var dob:String?="",
    @SerializedName("phone") var phone:String?="",
    @SerializedName("email") var email:String?="",
    @SerializedName("address") var address:String?="",
    @SerializedName("pin_code") var pin_code:String?="",
    @SerializedName("city") var city:String?="",
    @SerializedName("state") var state:String?="",
    @SerializedName("country") var country:String?=""

)

data class CancelData(
    @SerializedName("id") var name:String?="",
    @SerializedName("reason") var last_name:String?=""
)



@Parcelize
data class Packages(
    @SerializedName("id") var id: String?="",
    @SerializedName("title") var title: String?="",
    @SerializedName("type") var type: String?="",
    @SerializedName("amount") var amount: String?="",
    @SerializedName("duration") var duration: String?="",
    @SerializedName("status") var status: String?="",
    @SerializedName("description") var description: String?="",
    @SerializedName("benefits") var benefits: String?="",
    @SerializedName("added_amount") var added_amount: String?=""
    ): Parcelable
data class UpdateProfile(
    @SerializedName("user_id") var user_id: String?="",
    @SerializedName("first_name") var first_name: String?="",
    @SerializedName("last_name") var last_name: String?=null,
    @SerializedName("gender") var gender: String?=null,
    @SerializedName("dob") var dob: String?=null,
    @SerializedName("address") var address: String?=null,
    @SerializedName("pincode") var pincode: String?=null,
    @SerializedName("city") var city: String?=null,
    @SerializedName("country") var country: String?=null,
    @SerializedName("state") var state: String?=null,
    @SerializedName("email") var email: String?=null,
    @SerializedName("refer_code") var refer_code: String?=null

    )

data class UserCommonJson(
    @SerializedName("user_id") var user_id: String?="",
    @SerializedName("email") var email: String?="",
    @SerializedName("otp") var otp: String?="",
    @SerializedName("id") var id: String?="",
    @SerializedName("bank_id") var bank_id: String?="",
    @SerializedName("vehicle_id") var vehicle_id: String?="",
    @SerializedName("status") var status: String?="",
    @SerializedName("hublist_id") var hublist_id: String?=""
)
data class UserCreatedByJson(
    @SerializedName("created_by") var user_id: String?=""
)
data class ProfileResponse(
    @SerializedName("message") var message:String?="",
    @SerializedName("status") var status:String?="",
    @SerializedName("data") var data:ProfileData?=ProfileData()
)

@Parcelize
data class ProfileData(
    @SerializedName("id") var id: String?="",
    @SerializedName("phone") var mobile: String?="",
    @SerializedName("otp") var otp: String?="",
    @SerializedName("created_at") var created_at: String?="",
    @SerializedName("email") var email: String?=null,
    @SerializedName("name") var first_name: String?="",
    @SerializedName("last_name") var last_name: String?=null,
    @SerializedName("gender") var gender: String?=null,
    @SerializedName("dob") var dob: String?=null,
    @SerializedName("address") var address: String?=null,
    @SerializedName("pin_code") var pincode: String?=null,
    @SerializedName("city") var city: String?=null,
    @SerializedName("country") var country: String?=null,
    @SerializedName("state") var state: String?=null,
    @SerializedName("country_code") var country_code: String?=null,
    @SerializedName("location") var location: String?=null,
    @SerializedName("latitude") var latitude: String?=null,
    @SerializedName("longitude") var longitude: String?=null,
    @SerializedName("km") var km: String?=null,
    @SerializedName("password") var password: String?=null,
    @SerializedName("password_int") var password_int: String?=null,
    @SerializedName("reset_token") var reset_token: String?=null,
    @SerializedName("status") var status: String?=null,
    @SerializedName("verified") var verified: String?=null,
    @SerializedName("firebase_token") var firebase_token: String?=null,
    @SerializedName("created_by") var created_by: String?=null,
    @SerializedName("updated_at") var updated_at: String?=null,
    @SerializedName("updated_by") var updated_by: String?=null,
    @SerializedName("image") var profile_image: String?=null
) : Parcelable


data class BankDataResponse (
    @SerializedName("status"  ) var status  : String?    = "",
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ArrayList<BankDetails>   = arrayListOf(),
)
@Parcelize
data class BankDetails(
    @SerializedName("id") var id: String?="",
    @SerializedName("user_id") var user_id: String?="",
    @SerializedName("account_number") var account_number: String?="",
    @SerializedName("bank_name") var bank_name: String?="",
    @SerializedName("ifsc_code") var ifsc_code: String?="",
    @SerializedName("account_type") var account_type: String?="",
    @SerializedName("created_at") var created_at: String?="",
    @SerializedName("updated_at") var updated_at: String?="",
    var bankLogo: String?="",
    var isChoose:Boolean=false


    ):Parcelable

@Parcelize
data class BookVehicle(
    @SerializedName("vehicle_id") var vehicle_id: String?="",
    @SerializedName("hublist_id") var hublist_id: String?="",
    @SerializedName("date") var date: String?="",
    @SerializedName("time_slot") var time_slot: String?="",
    @SerializedName("location") var location: String?="",
    @SerializedName("total_amount") var total_amount: String?="",
    @SerializedName("created_by") var created_by: String?=""
):Parcelable
@Parcelize
data class BookingResponse(
    @SerializedName("status") var status: String?="",
    @SerializedName("message") var message: String?="",
    @SerializedName("vehicle_booking_id") var date: String?=""
):Parcelable


@Parcelize
data class SaveBankDetails(
    @SerializedName("user_id") var user_id: String?="",
    @SerializedName("account_number") var account_number: String?="",
    @SerializedName("bank_name") var bank_name: String?="",
    @SerializedName("ifsc_code") var ifsc_code: String?="",
    @SerializedName("account_type") var account_type: String?=""
):Parcelable

data class SaveBankDetailsResponse (

    @SerializedName("status"  ) var status  : String?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("bank_id" ) var bank_id : String? = null,
   // @SerializedName("data"    ) var data    : BankResponse   = BankResponse()

)
data class BankResponse (
    @SerializedName("bank_details") var bank_details : SaveBankDetails = SaveBankDetails()
)
data class NEFTDetails(
    @SerializedName("user_id") var user_id:String?="",
    @SerializedName("amount") var amount:String?="",
    @SerializedName("transaction_utr") var transaction_utr:String?="",
    @SerializedName("neft_image") var neft_image:String?=""
)
data class HomeBannerList(
    @SerializedName("image") var image:String?="",
    @SerializedName("created_date") var created_date:String?="",
    @SerializedName("created_time") var created_time:String?="",
)

data class Withdraw(
    @SerializedName("user_id") var user_id:String?="",
    @SerializedName("amount") var amount:String?="",
    @SerializedName("user_bank_details") var user_bank_details:String?="",
    @SerializedName("notes") var notes:String?="",
    @SerializedName("status") var status:String?="",
    @SerializedName("created_at") var created_at:String?=""
)


data class AddKYC(
    @SerializedName("user_id") var user_id:String?="",
    @SerializedName("aadhar_number") var aadhar_number:String?="",
    @SerializedName("pan_number") var pan_number:String?="",
    @SerializedName("aadhar_front") var aadhar_front:String?="",
    @SerializedName("aadhar_back") var aadhar_back:String?="",
    @SerializedName("pan_image") var pan_image:String?="",
    @SerializedName("id") var id:String?=""
)
data class PackageBuyNow(
    @SerializedName("user_id") var user_id:String?="",
    @SerializedName("package_id") var package_id:String?="",
    @SerializedName("amount") var amount:String?="",
    @SerializedName("type") var type:String?="",
    @SerializedName("duration") var duration:String?="",
    @SerializedName("title") var title:String?="",
    @SerializedName("added_amount") var added_amount:String?="",
    @SerializedName("created_at") var created_at:String?=""
    )

@Parcelize
data class WithdrawList(
    @SerializedName("id") var id:String?="",
    @SerializedName("user_id") var user_id:String?="",
    @SerializedName("amount") var amount:String?="",
    @SerializedName("notes") var notes:String?="",
    @SerializedName("user_bank_details") var user_bank_details:String?="",
    @SerializedName("status") var status:String?="",
    @SerializedName("created_at") var created_at:String?=""
    ):Parcelable
@Parcelize
data class WalletNEFTList(
    @SerializedName("id") var id:String?="",
    @SerializedName("user_id") var user_id:String?="",
    @SerializedName("amount") var amount:String?="",
    @SerializedName("transaction_utr") var transaction_utr:String?="",
    @SerializedName("neft_image") var neft_image:String?="",
    @SerializedName("status") var status:String?="",
    @SerializedName("created_date") var created_date:String?=""
    ):Parcelable

@Parcelize
data class WalletHistory(
    var withdrawList:ArrayList<WithdrawList> = arrayListOf(),
    var walletNEFTList:ArrayList<WalletNEFTList> = arrayListOf()
):Parcelable

data class FetchedKYCResponse (
    @SerializedName("status"  ) var status  : Boolean?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : KYCResponseList   = KYCResponseList(),
)
data class KYCResponseList (
    @SerializedName("kyc_details") var kyc_details : ArrayList<AddKYC> = arrayListOf()
)

@Parcelize
data class TransactionList(
    @SerializedName("id") var id:String?="",
    @SerializedName("amount") var amount:String?="",
    @SerializedName("pro_status") var pro_status:String?="",
    @SerializedName("notes") var notes:String?="",
    @SerializedName("type") var type:String?="",
    @SerializedName("status") var status:String?="",
    @SerializedName("created_at") var created_at:String?=""
):Parcelable



@Parcelize
data class PurchaseHistory(
    @SerializedName("purchase_id") var id: String?="",
    @SerializedName("title") var title: String?="",
    @SerializedName("type") var type: String?="",
    @SerializedName("amount") var amount: String?="",
    @SerializedName("duration") var duration: String?="",
    @SerializedName("status") var status: String?="",
    @SerializedName("created_at") var created_at: String?="",
    @SerializedName("package_id") var package_id: String?="",
    @SerializedName("package_title") var package_title: String?="",
    @SerializedName("description") var description: String?="",
    @SerializedName("benefits") var benefits: String?="",
    @SerializedName("total_amount") var total_amount: String?="",
    @SerializedName("added_amount") var added_amount: String?=""
): Parcelable

@Parcelize
data class Vehicle(
    @SerializedName("id") var id:String?="",
    @SerializedName("bike_name") var bike_name:String?="",
    @SerializedName("bike_price") var bike_price:String?="",
    @SerializedName("bike_image") var bike_image:String?="",
    @SerializedName("range") var range:String?="",
    @SerializedName("speed") var speed:String?="",
    @SerializedName("battery_type") var battery_type:String?="",
    @SerializedName("charges") var charges:String?="",
    @SerializedName("milage") var milage:String?="",
    @SerializedName("drivers_license") var drivers_license:String?="",
    @SerializedName("load_capacity") var load_capacity:String?="",
    @SerializedName("assistance") var assistance:String?="",
    @SerializedName("description") var description:String?="",
    @SerializedName("created_by") var created_by:String?="",
    @SerializedName("status") var status:String?="",
    @SerializedName("note") var note:String?="",
    @SerializedName("created_date") var created_date:String?="",
    @SerializedName("created_time") var created_time:String?=""
):Parcelable


data class MyRidesResponse (
    @SerializedName("status"  ) var status  : Boolean? = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : List<MyRides> = emptyList(),
)


@Parcelize
data class MyRides(
    @SerializedName("id") var id: String? = "",
    @SerializedName("vehicle_id") var vehicle_id: String? = "",
    @SerializedName("hublist_id") var hublist_id: String? = "",
    @SerializedName("date") var date: String? = "",
    @SerializedName("time_slot") var time_slot: String? = "",
    @SerializedName("total_amount") var total_amount: String? = "",
    @SerializedName("location") var location: String? = "",
    @SerializedName("created_at") var created_at: String? = "",
    @SerializedName("created_by") var created_by: String? = "",
    @SerializedName("updated_at") var updated_at: String? = "",
    @SerializedName("updated_by") var updated_by: String? = "",
    @SerializedName("status") var status: String? = "",
    @SerializedName("reason") var reason: String? = "",
    @SerializedName("created_date") var created_date: String? = "",
    @SerializedName("created_time") var created_time: String? = ""
) : Parcelable



data class RideCancelResponse(
    @SerializedName("status") var status: Int? = 0,
    @SerializedName("message") var message: String? = "",
    @SerializedName("reason") var reason: String? = "",
    @SerializedName("post_data") var post_data: PostData? = PostData()
)

@Parcelize
data class PostData(
    @SerializedName("id") var id: String? = "",
    @SerializedName("reason") var reason: String? = ""
) : Parcelable

@Parcelize
data class HubList(
    @SerializedName("id") var id:String?="",
    @SerializedName("title") var title:String?="",
    @SerializedName("direction") var direction:String?="",
    @SerializedName("description") var description:String?="",
    @SerializedName("created_by") var created_by:String?="",
    @SerializedName("status") var status:String?="",
    @SerializedName("created_date") var created_date:String?="",
    @SerializedName("created_time") var created_time:String?="",
    var isChoose:Boolean=false

):Parcelable

@Parcelize
data class TimeSlot(
    @SerializedName("id") val id: String,
    @SerializedName("vehicle_id") val vehicleId: String,
    @SerializedName("timeslot") val timeslot: String,
    @SerializedName("created_date") val createdDate: String,
    @SerializedName("created_time") val createdTime: String
):Parcelable

@Parcelize
data class BlockTimeSlot(
    @SerializedName("id") val id: String,
    @SerializedName("vehicle_id") val vehicle_id: String,
    @SerializedName("hublist_id") val hublist_id: String,
    @SerializedName("date") val date: String,
    @SerializedName("time_slot") val time_slot: String,
    @SerializedName("status") val status: String
):Parcelable

data class TransactionResponse (
    @SerializedName("status"  ) var status  : String?    = "",
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ArrayList<TransactionList>   = arrayListOf(),
)
data class AddAmountResponse (
    @SerializedName("status"  ) var status  : String?    = "",
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : AddedAmountData=AddedAmountData()
)
data class AddedAmountData(
    @SerializedName("transaction_id"  ) var transaction_id  : String?    = "",
   // @SerializedName("added_amount" ) var added_amount : String? = null
)

data class KYCResponse(
    @SerializedName("status"  ) var status  : String?    = "",
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : ArrayList<GetKYCData> = arrayListOf()
)

data class GetKYCData(
    @SerializedName("user_id") var user_id:String?="",
    @SerializedName("aadhar_number") var aadhar_number:String?="",
    @SerializedName("pan_number") var pan_number:String?="",
    @SerializedName("aadhar_front") var aadhar_front:String?="",
    @SerializedName("aadhar_back") var aadhar_back:String?="",
    @SerializedName("pan_image") var pan_image:String?="",
    @SerializedName("id") var id:String?="",
    @SerializedName("account_number") var account_number:String?="",
    @SerializedName("bank_name") var bank_name:String?="",
    @SerializedName("ifsc_code") var ifsc_code:String?="",
    @SerializedName("account_type") var account_type:String?="",
    @SerializedName("face_verificaton") var face_verificaton:String?=""
)



