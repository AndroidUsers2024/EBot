package com.example.ebot.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MainResponse (
    @SerializedName("status"  ) var status  : Boolean?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : Data   = Data(),
    @SerializedName("profile_image" ) var profile_image :String?  = ""

    )
data class Data (

   // @SerializedName("customer_data" ) var customerData : UserLogin ?=null,
    @SerializedName("about" ) var about :ArrayList<About> = arrayListOf(),
    @SerializedName("privacy" ) var privacy : ArrayList<Privacy> = arrayListOf(),
    @SerializedName("terms" ) var terms : ArrayList<Terms> = arrayListOf(),
    @SerializedName("contact" ) var contact : ArrayList<Contact> = arrayListOf(),
    @SerializedName("packages" ) var packages : ArrayList<Packages> = arrayListOf(),
    @SerializedName("profile" ) var profile: ArrayList<addFetchProfile> = arrayListOf(),
    @SerializedName("profile_details" ) var profile_details: ArrayList<addFetchProfile> = arrayListOf(),
    @SerializedName("updated_profile") var updated_profile : UpdateProfile=UpdateProfile(),
    @SerializedName("bank_details" ) var bank_details : ArrayList<BankDetails> = arrayListOf(),
    @SerializedName("myteam_details" ) var team : ArrayList<MyTeam> = arrayListOf(),
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
data class  About(
    @SerializedName("id" ) var id: String? = "",
    @SerializedName("about" ) var about: String? = ""
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
    @SerializedName("id" ) var id: String? = "",
    @SerializedName("email"  ) var email  : String? = "",
    @SerializedName("landline" ) var landline : String? = "",
    @SerializedName("mobile1" ) var mobile1 : String? = "",
    @SerializedName("mobile2" ) var mobile2 : String? = "",
    @SerializedName("address" ) var address : String? = ""

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

    @SerializedName("id") var id       : String? = null,
    @SerializedName("question" ) var question : String? = null,
    @SerializedName("answer"   ) var answer   : String? = null,
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

data class LoginRequest(
     @SerializedName("mode") var  mode: String?="",
     @SerializedName("urlencoded") var  urlencoded: ArrayList<UrlencodedData>?=arrayListOf()
)
data class  LoginResponse(
    @SerializedName("status") var status: Boolean?=null,
    @SerializedName("message") var message: String?="",
    @SerializedName("data") var data: LoginResponseData?=null
)
data class LoginResponseData(
    @SerializedName("mobile") var mobile: String?="",
    @SerializedName("user_id") var user_id: Int?=null,
    @SerializedName("otp") var otp: Int?=null
)
data class  RegisterResponse(
    @SerializedName("status") var status: Boolean?=null,
    @SerializedName("message") var message: String?="",
    @SerializedName("data") var data: RegisterData?=RegisterData()
)
data class RegisterData(
    @SerializedName("data") var data:RegisterResponseData?=RegisterResponseData()

)
data class RegisterResponseData(
    @SerializedName("mobile") var mobile: String?="",
    @SerializedName("created_at") var created_at: String?="",
    @SerializedName("otp") var otp: String?="",
    @SerializedName("id") var id: Int?=null
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

data class UserIdFeildJson(
    @SerializedName("user_id") var user_id: String?="",
    @SerializedName("refer_code") var refer_code: String?="",
    @SerializedName("id") var id: String?=""
)

@Parcelize
data class addFetchProfile(
    @SerializedName("id") var id: String?="",
    @SerializedName("mobile") var mobile: String?="",
    @SerializedName("otp") var otp: String?="",
    @SerializedName("created_at") var created_at: String?="",
    @SerializedName("email") var email: String?=null,
    @SerializedName("first_name") var first_name: String?="",
    @SerializedName("last_name") var last_name: String?=null,
    @SerializedName("gender") var gender: String?=null,
    @SerializedName("dob") var dob: String?=null,
    @SerializedName("address") var address: String?=null,
    @SerializedName("pincode") var pincode: String?=null,
    @SerializedName("city") var city: String?=null,
    @SerializedName("country") var country: String?=null,
    @SerializedName("state") var state: String?=null,
    @SerializedName("refer_code") var refer_code: String?=null,
    @SerializedName("status") var status: String?=null,
    @SerializedName("profile_image") var profile_image: String?=null
) : Parcelable

@Parcelize
data class BankDetails(
    @SerializedName("id") var id: String?="",
    @SerializedName("user_id") var user_id: String?="",
    @SerializedName("account_number") var account_number: String?="",
    @SerializedName("bank_name") var bank_name: String?="",
    @SerializedName("ifsc_code") var ifsc_code: String?="",
    @SerializedName("account_type") var account_type: String?="",
    var bankLogo: String?="",
    var isChoose:Boolean=false


    ):Parcelable

@Parcelize
data class MyTeam(
    @SerializedName("team_id") var team_id: String?="",
    @SerializedName("id") var id: String?="",
    @SerializedName("parent_user_id") var parent_user_id: String?="",
    @SerializedName("child_user_id") var child_user_id: String?="",
    @SerializedName("mobile") var mobile: String?="",
    @SerializedName("otp") var otp: String?="",
    @SerializedName("created_at") var created_at: String?="",
    @SerializedName("first_name") var first_name: String?="",
    @SerializedName("last_name") var last_name: String?="",
    @SerializedName("gender") var gender: String?="",
    @SerializedName("dob") var dob: String?="",
    @SerializedName("email") var email: String?="",
    @SerializedName("address") var address: String?="",
    @SerializedName("pincode") var pincode: String?="",
    @SerializedName("city") var city: String?="",
    @SerializedName("state") var state: String?="",
    @SerializedName("country") var country: String?="",
    @SerializedName("refer_code") var refer_code: String?="",
    @SerializedName("profile_image") var profile_image: String?=""
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

    @SerializedName("status"  ) var status  : Boolean?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : BankResponse   = BankResponse()

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

data class Withdraw(
    @SerializedName("user_id") var user_id:String?="",
    @SerializedName("amount") var amount:String?="",
    @SerializedName("user_bank_details") var user_bank_details:String?="",
    @SerializedName("notes") var notes:String?=""
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
    @SerializedName("user_id") var user_id:String?="",
    @SerializedName("amount") var amount:String?="",
    @SerializedName("notes") var notes:String?="",
    @SerializedName("source") var source:String?="",
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
