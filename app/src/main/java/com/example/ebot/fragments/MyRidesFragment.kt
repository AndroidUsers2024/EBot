package com.example.ebot.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.adapters.BikesAdapter
import com.example.ebot.adapters.MyRidesAdapter
import com.example.ebot.common.Utils
import com.example.ebot.models.MyRides
import com.example.ebot.models.MyRidesResponse
import com.example.ebot.models.Vehicle
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyRidesFragment : Fragment() {


    private lateinit var progressbar: ProgressBar
    private lateinit var myRidesAdapter: MyRidesAdapter
    private var myRidesList: ArrayList<MyRides> = ArrayList()
    private lateinit var rc_myRidesList:RecyclerView
    private lateinit var ll_empty_tran:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View =  inflater.inflate(R.layout.fragment_my_rides, container, false)
        updateXML(view)
        return view
    }

    private fun updateXML(view: View){
        try{
            rc_myRidesList = view.findViewById(R.id.rc_myRidesList)
            progressbar = view.findViewById(R.id.progressbar)
            ll_empty_tran = view.findViewById(R.id.ll_empty_tran)
            rc_myRidesList.visibility=View.GONE
            ll_empty_tran.visibility=View.VISIBLE

            val id=Utils.getData(requireContext(),"user_id","") as String

            rc_myRidesList.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            myRidesAdapter= MyRidesAdapter(myRidesList,requireContext())
            rc_myRidesList.adapter=myRidesAdapter
            getMyRidesList(id)//id
            if (myRidesList.size<=0){
                rc_myRidesList.visibility=View.GONE
                ll_empty_tran.visibility=View.VISIBLE
            }else{
                rc_myRidesList.visibility=View.VISIBLE
                ll_empty_tran.visibility=View.GONE
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun getMyRidesList(id: String) {
        try{
            val dataManager: ServiceManager = ServiceManager.getDataManager()
            progressbar.visibility=View.VISIBLE
            val callback=object : Callback<MyRidesResponse> {
                override fun onResponse(
                    call: Call<MyRidesResponse>,
                    response: Response<MyRidesResponse>
                ) {
                    progressbar.visibility= View.GONE
                    if (response.isSuccessful){
                        if(response.body()!!.status==true){
                            myRidesList.clear()
                            myRidesList.addAll(response.body()!!.data)
                            if (myRidesList.size<=0){
                                rc_myRidesList.visibility=View.GONE
                                ll_empty_tran.visibility=View.VISIBLE
                            }else{
                                rc_myRidesList.visibility=View.VISIBLE
                                ll_empty_tran.visibility=View.GONE
                            }
                            myRidesAdapter.updateVehiclesList(myRidesList)

                            Log.e("Response","response"+response.body().toString())
                        }else{
                            Utils.showToast(requireContext(),"Failed to get My Rides. ${response.message()}")
                        }

                    }else{
                        println("Failed to get My Rides. ${response.message()}")
                        Utils.showToast(requireContext(),"Failed to get My Rides. ${response.message()}")
                    }


                }

                override fun onFailure(call: Call<MyRidesResponse>, t: Throwable) {
                    println("Failed to get My Rides. ${t.message}")
                    Utils.showToast(requireContext(),"Failed to get My Rides. ${t.message}")
                    if (myRidesList.size<=0){
                        rc_myRidesList.visibility=View.GONE
                        ll_empty_tran.visibility=View.VISIBLE
                    }else{
                        rc_myRidesList.visibility=View.VISIBLE
                        ll_empty_tran.visibility=View.GONE
                    }
                }

            }
            dataManager.getMyRides(callback,id)


        }catch (e:Exception){
            Log.e("HomeFragment.getMy RidesList",e.message.toString())
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

}