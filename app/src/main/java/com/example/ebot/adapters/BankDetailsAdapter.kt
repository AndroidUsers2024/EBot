package com.example.ebot.adapters

import android.app.ProgressDialog
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ebot.R
import com.example.ebot.common.Utils
import com.example.ebot.models.BankDetails
import com.example.ebot.models.MainResponse
import com.example.ebot.services.ServiceManager
import com.google.android.material.imageview.ShapeableImageView

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BankDetailsAdapter(private var bankDetailsList: List<BankDetails>, private val context: Context, private val onBankSelected: ((BankDetails) -> Unit)? // Callback as lambda
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val className = context::class.java.simpleName
    private lateinit var openDialog: ProgressDialog



    class ViewBankDetailsViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val img_accountLogo:ShapeableImageView=view.findViewById(R.id.img_accountLogo)
        val tv_titleBankName:TextView=view.findViewById(R.id.tv_titleBankName)
        val tv_AccountNumber:TextView=view.findViewById(R.id.tv_AccountNumber)
        val tv_AccountNo:TextView=view.findViewById(R.id.tv_AccountNo)
        val tv_BankName:TextView=view.findViewById(R.id.tv_BankName)
        val tv_IFSCCode:TextView=view.findViewById(R.id.tv_IFSCCode)
        val tv_accountType:TextView=view.findViewById(R.id.tv_accountType)
        val ll_headLayout:LinearLayout=view.findViewById(R.id.ll_headLayout)
        val ll_showBankDetails:LinearLayout=view.findViewById(R.id.ll_showBankDetails)
        val vw_isOpen:View=view.findViewById(R.id.vw_isOpen)
        val btn_Remove:Button=view.findViewById(R.id.btn_Remove)

    }
    class ChooseBankDetailsViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val img_accountLogo:ShapeableImageView=view.findViewById(R.id.img_accountLogo)
        val tv_BankName:TextView=view.findViewById(R.id.tv_BankName)
        val tv_AccountNumber:TextView=view.findViewById(R.id.tv_AccountNumber)
        val vw_isChoose:View=view.findViewById(R.id.vw_isChoose)
        val cardview:CardView=view.findViewById(R.id.cardview)
        val ll_BankAcounts:LinearLayout=view.findViewById(R.id.ll_BankAcounts)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View? =null
        if (className.equals("WithdrawScreen")){
            view=LayoutInflater.from(parent.context).inflate(R.layout.choose_bank_account_view, parent, false)
            return ChooseBankDetailsViewHolder(view)
        }else{
            view= LayoutInflater.from(parent.context).inflate(R.layout.view_added_bank_list, parent, false)
            return ViewBankDetailsViewHolder(view)

        }
    }

    override fun getItemCount(): Int {
       return bankDetailsList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj:BankDetails =bankDetailsList[position]
        val maskedAccountNo=Utils.maskAccountNumber(obj.account_number!!)
       if (holder.javaClass==ChooseBankDetailsViewHolder::class.java){
           val viewHolder = holder as ChooseBankDetailsViewHolder
           viewHolder.tv_BankName.text=obj.bank_name
           viewHolder.tv_AccountNumber.text=maskedAccountNo
           viewHolder.vw_isChoose
           if (!obj.bankLogo.isNullOrEmpty()) {
               obj.bankLogo.let { url ->
                   Glide.with(context).load(url)
                       .into(viewHolder.img_accountLogo)

               }
           }
           if (obj.isChoose) {
               holder.vw_isChoose.setBackgroundResource(R.drawable.ic_verified)

           } else {
               holder.vw_isChoose.setBackgroundResource(R.drawable.ic_round)
           }

           holder.vw_isChoose.tag = position
           holder.vw_isChoose.setOnClickListener { v ->
               val pos = v.tag as Int
               for (k in bankDetailsList.indices) {
                   if (pos != k) bankDetailsList[k].isChoose = false
               }
               bankDetailsList[pos].isChoose = !bankDetailsList[pos].isChoose
               notifyDataSetChanged()
               if (bankDetailsList[pos].isChoose) {
                   onBankSelected?.let { it(bankDetailsList[pos]) }
               }

           }

           holder.ll_BankAcounts.tag = position
           holder.ll_BankAcounts.setOnClickListener { v ->
               val pos = v.tag as Int
               for (k in bankDetailsList.indices) {
                   if (pos != k) bankDetailsList[k].isChoose = false
               }
               bankDetailsList[pos].isChoose = !bankDetailsList[pos].isChoose
               notifyDataSetChanged()
               if (bankDetailsList[pos].isChoose) {
                   onBankSelected?.let { it(bankDetailsList[pos]) }
               }
           }



       }else{
           val viewHolder = holder as ViewBankDetailsViewHolder
           viewHolder.tv_titleBankName.text=obj.bank_name
           viewHolder.tv_AccountNumber.text=maskedAccountNo
           viewHolder.tv_AccountNo.text=obj.account_number
           viewHolder.tv_BankName.text=obj.bank_name
           viewHolder.tv_IFSCCode.text=obj.ifsc_code
           viewHolder.ll_showBankDetails.visibility=View.GONE
           viewHolder.tv_accountType.text=obj.account_type
           val tintColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.primary))
           holder.vw_isOpen.backgroundTintList=tintColor
           if (!obj.bankLogo.isNullOrEmpty()) {
               obj.bankLogo.let { url ->
                   Glide.with(context).load(url)
                       .into(viewHolder.img_accountLogo)

               }
           }
           if (obj.isChoose) {
               holder.ll_showBankDetails.visibility = View.VISIBLE
               holder.vw_isOpen.setBackgroundResource(R.drawable.ic_arrow_up)

           } else {
               holder.ll_showBankDetails.visibility = View.GONE
               holder.vw_isOpen.setBackgroundResource(R.drawable.ic_arrow_down)
           }
           holder.vw_isOpen.tag = position
           viewHolder.vw_isOpen.setOnClickListener{
                   v ->
               val pos = v.tag as Int
               for (k in bankDetailsList.indices) {
                   if (pos != k) bankDetailsList[k].isChoose = false
               }
               bankDetailsList[pos].isChoose = !bankDetailsList[pos].isChoose
               notifyDataSetChanged()
               if (bankDetailsList[pos].isChoose) {
                   onBankSelected?.let { it(bankDetailsList[pos]) }
               }

           }

           viewHolder.vw_isOpen
           viewHolder.btn_Remove.setOnClickListener(View.OnClickListener {
               Utils.showsConfirmation(context,"Are you sure you want to remove your saved bank details?",
                   { removeBankDetails(obj.id!!.toString()) })

           })



       }
    }

    fun openDialog() {
        openDialog = ProgressDialog(context).apply {
            setMessage("please wait...")
            setCancelable(false)
            show()
        }

    }

    fun updateBankList(list: List<BankDetails>){
        bankDetailsList=list
        notifyDataSetChanged()
    }


    private fun removeBankDetails(value: String){

        openDialog()
        val dataManager=ServiceManager.getDataManager()
        val callBackRemove=object :Callback<MainResponse>{
            override fun onResponse(call: Call<MainResponse>, response: Response<MainResponse>) {
                openDialog.dismiss()
                println("response remove bank Details. ${response}")
                if (response.isSuccessful){

                    if (response.body()!!.status=="success"){
                        val indexToRemove = bankDetailsList.indexOfFirst { it.id == value.toString() }
                        if (indexToRemove != -1) {
                            bankDetailsList = bankDetailsList.toMutableList().apply {
                                removeAt(indexToRemove)
                            }

                            notifyItemRemoved(indexToRemove)
                            notifyItemRangeChanged(indexToRemove, itemCount)
                        }
                    }
                    Utils.showToast(context, response.body()!!.message.toString())
                }else{
                    Utils.showToast(context, response.message())
                }

            }

            override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                openDialog.dismiss()
                println("Failed to remove bank Details. ${t.message}")
                Utils.showToast(context, "${t.message} Please try again")
            }

        }

        dataManager.removeBankDetails(callBackRemove,value)


    }



}