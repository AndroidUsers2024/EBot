package com.example.ebot.fragments

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
import com.example.ebot.actvities.TransactionsHistory
import com.example.ebot.actvities.WithdrawScreen
import com.example.ebot.common.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView

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
            val btn_cancel:Button=view.findViewById(R.id.btn_cancel)
            val btn_continue:Button=view.findViewById(R.id.btn_continue)
            close.setOnClickListener(View.OnClickListener {
                bottomSheetDialog.dismiss()
            })
            btn_cancel.setOnClickListener(View.OnClickListener {
                et_money.setText("")
                bottomSheetDialog.dismiss()

            })
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
            bottomSheetDialog.setCancelable(false)

        }catch (e:Exception){
            Log.e("Wallet.showAddMoney()",e.message.toString())
        }
    }


}