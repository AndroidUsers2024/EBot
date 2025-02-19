package com.example.ebot.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.adapters.BikesAdapter
import com.example.ebot.common.Utils
import com.example.ebot.models.Vehicle
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var tv_available_bal: TextView
    private lateinit var tv_home_address: TextView
    private lateinit var tv_vehicles_view: TextView
    private lateinit var rc_vehicles: RecyclerView
    private lateinit var progressbar: ProgressBar
    private lateinit var bikeAdapter: BikesAdapter
    private var vehiclesList: ArrayList<Vehicle> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View = inflater.inflate(R.layout.fragment_home, container, false)
        updateXML(view)
        return view
    }

    private fun updateXML(view: View){
        try{
            tv_available_bal = view.findViewById(R.id.tv_available_bal)
            tv_available_bal.text="₹ 0.0"
            tv_home_address = view.findViewById(R.id.tv_home_address)


            rc_vehicles = view.findViewById(R.id.rc_vehicles)
            tv_vehicles_view = view.findViewById(R.id.tv_vehicles_view)
            progressbar = view.findViewById(R.id.progressbar)
            tv_vehicles_view.setOnClickListener(View.OnClickListener {
//                val intent= Intent(requireContext(),PackagesListActivity::class.java)
//                startActivity(intent)
            })

            rc_vehicles.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            bikeAdapter= BikesAdapter(vehiclesList,requireContext())
            rc_vehicles.adapter=bikeAdapter
            getVehiclesList()


        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    private fun getVehiclesList(){
        try{
            val dataManager:ServiceManager=ServiceManager.getDataManager()
            progressbar.visibility=View.VISIBLE
            val callback=object : Callback<List<Vehicle>> {
                override fun onResponse(
                    call: Call<List<Vehicle>>,
                    response: Response<List<Vehicle>>
                ) {
                    progressbar.visibility= View.GONE
                    if (response.isSuccessful){
                        vehiclesList.clear()
                        vehiclesList.addAll(response.body()!!)
                        bikeAdapter.updateVehiclesList(vehiclesList)

                        Log.e("Response","response"+response.body().toString())

                    }else{
                        println("Failed to get packages. ${response.message()}")
                        Utils.showToast(requireContext(),"Failed to get packages. ${response.message()}")
                    }


                }

                override fun onFailure(call: Call<List<Vehicle>>, t: Throwable) {
                    println("Failed to get packages. ${t.message}")
                    Utils.showToast(requireContext(),"Failed to get packages. ${t.message}")
                }

            }
            dataManager.getVehicles(callback)


        }catch (e:Exception){
            Log.e("HomeFragment.getPackagesList",e.message.toString())
        }
    }



}