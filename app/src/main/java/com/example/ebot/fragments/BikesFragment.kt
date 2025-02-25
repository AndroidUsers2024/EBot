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
import com.example.ebot.common.Utils
import com.example.ebot.models.Vehicle
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BikesFragment : Fragment() {
    private lateinit var progressbar: ProgressBar
    private lateinit var bikeAdapter: BikesAdapter
    private var vehiclesList: ArrayList<Vehicle> = ArrayList()
    private lateinit var rc_addedBank:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_bikes, container, false)
        updateXML(view)
        return view
    }

    private fun updateXML(view: View){
        try {
            progressbar = view.findViewById(R.id.progressbar)
            rc_addedBank = view.findViewById(R.id.rc_addedBank)

        }catch (e:Exception){
            Log.e("AllBikes.updateXML()",e.message.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc_addedBank.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        bikeAdapter= BikesAdapter(vehiclesList,requireContext())
        rc_addedBank.adapter=bikeAdapter
        getVehiclesList()
    }

    private fun getVehiclesList(){
        try{
            val dataManager: ServiceManager = ServiceManager.getDataManager()
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
                        println("BikesFragment_Failed to get All bikes. ${response.message()}")
                        Utils.showToast(requireContext(),"Failed to get All bikes. ${response.message()}")
                    }


                }

                override fun onFailure(call: Call<List<Vehicle>>, t: Throwable) {
                    progressbar.visibility= View.GONE

                    println("BikesFragment_Failed to get All bikes. ${t.message}")
                    Utils.showToast(requireContext(),"Failed to get packages. ${t.message}")
                }

            }
            dataManager.getVehicles(callback)


        }catch (e:Exception){
            progressbar.visibility= View.GONE

            Log.e("BikesFragment_Failed to get All bikes",e.message.toString())
        }
    }


}