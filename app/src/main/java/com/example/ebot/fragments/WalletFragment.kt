package com.example.ebot.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.actvities.MainActivity
import com.example.ebot.actvities.TransactionDetails
import com.example.ebot.actvities.TransactionsHistory
import com.example.ebot.actvities.WithdrawScreen
import com.example.ebot.adapters.HistoryAdapter
import com.example.ebot.common.Utils
import com.example.ebot.models.AddAmountResponse
import com.example.ebot.models.MainResponse
import com.example.ebot.models.TransactionList
import com.example.ebot.models.TransactionResponse
import com.example.ebot.models.UserCommonJson
import com.example.ebot.models.Withdraw
import com.example.ebot.services.ServiceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WalletFragment : Fragment() {
    private lateinit var tv_total_Balance: TextView
    private lateinit var btn_AddMoney: Button
    private lateinit var btn_Withdraw: Button
    private lateinit var ll_empty_tran: LinearLayout
    private lateinit var ll_last_trans: LinearLayout
    private lateinit var ll_view_all: LinearLayout
    private lateinit var rc_lastTransactions: RecyclerView
    private lateinit var viewAll: TextView
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private  var transactionList:ArrayList<TransactionList> = ArrayList()
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var dialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View =inflater.inflate(R.layout.fragment_wallet, container, false)
        updateXML(view)
        return view
    }
    private fun updateXML(view:View){
        try{
            tv_total_Balance= view.findViewById(R.id.tv_total_Balance)
            tv_total_Balance.text="0.0"
            btn_AddMoney= view.findViewById(R.id.btn_AddMoney)
            btn_Withdraw= view.findViewById(R.id.btn_Withdraw)
            ll_empty_tran= view.findViewById(R.id.ll_empty_tran)
            ll_last_trans= view.findViewById(R.id.ll_last_trans)
            ll_view_all= view.findViewById(R.id.ll_view_all)
            rc_lastTransactions= view.findViewById(R.id.rc_lastTransactions)
            viewAll= view.findViewById(R.id.viewAll)
            tv_total_Balance.text= Utils.WALLET_AMOUNT







            viewAll.setOnClickListener(View.OnClickListener {
                val intent= Intent(requireContext(), TransactionsHistory::class.java)
                intent.putExtra("history",transactionList)
                startActivity(intent)


            })
            btn_AddMoney.setOnClickListener(View.OnClickListener {
                showAddMoney()
            })
            btn_Withdraw.setOnClickListener(View.OnClickListener {
                val intent= Intent(requireContext(), WithdrawScreen::class.java)
                intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)


            })




        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionList= arrayListOf()
        showHistroy()
        getTransactionHistory()
        rc_lastTransactions.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        historyAdapter= HistoryAdapter(transactionList,requireContext())
        rc_lastTransactions.adapter=historyAdapter
        showTransactions(transactionList)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val currentFragment =
                        requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer)
                    if (currentFragment is HomeFragment) {
                        // If you're already on the first fragment, you can either exit or show a confirmation dialog
                        requireActivity().finish()  // This will exit the app, or do something else here
                    } else {
                        (requireActivity() as MainActivity).onBackPressed()

                    }
                }
            })
    }

    private fun showAddMoney(){
        try{
            bottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetTheme)
            val view:View=LayoutInflater.from(requireContext()).inflate(R.layout.add_money_view,null )
            val close: ShapeableImageView =view.findViewById(R.id.close)
            val et_money: EditText =view.findViewById(R.id.et_money)
            val et_note: EditText =view.findViewById(R.id.et_note)
            val btn_cancel:Button=view.findViewById(R.id.btn_cancel)
            val btn_continue:Button=view.findViewById(R.id.btn_continue)
            close.setOnClickListener(View.OnClickListener {
                bottomSheetDialog.dismiss()
            })
            btn_cancel.setOnClickListener(View.OnClickListener {
                et_money.setText("")
                et_note.setText("")
                bottomSheetDialog.dismiss()

            })
            btn_continue.setOnClickListener(View.OnClickListener {
               val amount=et_money.text.toString().trim()
               val note=et_money.text.toString().trim()
                if (amount.isEmpty()|| amount.toFloatOrNull()!! <=0){
                    Utils.showToast(requireContext(),"Please enter valid amount")

                }else if (note.isEmpty()){
                    Utils.showToast(requireContext(),"Please enter note")

                }else{
                    if (Utils.isNetworkAvailable(requireContext())){
                        val  userId= Utils.getData(requireContext(),"user_id","").toString()
                        val req=Withdraw(user_id = userId,amount=amount, notes = note)
                        addAmountApi(req)
                    }else{
                        Utils.showToast(requireContext(),"Please check network connection")

                    }
                }

            })
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
            bottomSheetDialog.setCancelable(false)

        }catch (e:Exception){
            Log.e("Wallet.showAddMoney()",e.message.toString())
        }
    }

    private fun showHistroy(){

        if (transactionList.size<=0){
            ll_empty_tran.visibility=View.VISIBLE
            ll_last_trans.visibility=View.GONE
        }else{
            ll_empty_tran.visibility=View.GONE
            ll_last_trans.visibility=View.VISIBLE
        }

    }
    private fun showTransactions(transactionList:ArrayList<TransactionList>){

        if (transactionList.size<=0){
            ll_empty_tran.visibility=View.VISIBLE
            ll_last_trans.visibility=View.GONE
        }else{
            ll_empty_tran.visibility=View.GONE
            ll_last_trans.visibility=View.VISIBLE
            historyAdapter.updateData(transactionList)
        }

        if(transactionList.size<6){
            viewAll.visibility = View.GONE
        }else{
            viewAll.visibility = View.VISIBLE
        }

    }

    private fun getTransactionHistory() {
        try {
            val dataManager = ServiceManager.getDataManager()

            val otpCallback = object : Callback<TransactionResponse> {
                override fun onResponse(
                    call: Call<TransactionResponse>,
                    response: Response<TransactionResponse>
                ) {
                    if (response.body()!!.status == "success") {
                        val responseData = response.body()!!.data

                        transactionList.clear()
                        transactionList.addAll(responseData)
                        historyAdapter.updateData(transactionList)
                        showTransactions(responseData)
                        Log.v("Response", "getAllTransaction.response" + response.body()!!.message.toString())
                    } else {
                        // Handle error
                        println("Failed to getAllTransaction. ${response.message()}")
                    }


                }


                override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                    println("Failed to getWalletAmount. ${t.message}")
                }
            }

            val  userId= Utils.getData(requireContext(),"user_id","").toString()
            val req=UserCommonJson(user_id = userId)

            dataManager.getAllTransaction(otpCallback, req)
        } catch (e: Exception) {
            Log.e("getAllTransaction",e.message.toString())
        }
    }

    private fun addAmountApi(req: Withdraw) {
        try {
            dialog = Utils.openDialog(requireContext())
            val dataManager = ServiceManager.getDataManager()
            val callback = object : Callback<AddAmountResponse> {
                override fun onResponse(
                    call: Call<AddAmountResponse>,
                    response: Response<AddAmountResponse>
                ) {
                    Utils.closeDialog(dialog)
                    if (response.isSuccessful) {
                        if (response.body()!!.status == "success") {
                            if (bottomSheetDialog.isShowing){
                                bottomSheetDialog.dismiss()
                            }
                            Utils.showToast(requireContext(), response.body()!!.message.toString())


                        }else{
                            Utils.showToast(requireContext(), response.body()!!.message.toString())
                        }
                    } else {
                        println("Failed to get bankDetail. ${response.message()}")
                        Utils.showToast(
                            requireContext(),
                            "Failed to AddAmount. ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<AddAmountResponse>, t: Throwable) {
                    Utils.closeDialog(dialog)
                    println("Failed to AddAmountAPI. ${t.message}")
                    Utils.showToast(requireContext(), "Failed to AddAmount. ${t.message}")
                }
            }
            dataManager.addAmount(callback,req)

        } catch (e: Exception) {
            Log.e("AddAmountAPI ", e.message.toString())
        }
    }


}