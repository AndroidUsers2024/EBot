package com.example.ebot.fragments

import CarouselAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ebot.R
import com.example.ebot.adapters.BikesAdapter
import com.example.ebot.common.Utils
import com.example.ebot.models.HomeBannerList
import com.example.ebot.models.MainResponse
import com.example.ebot.models.ProfileData
import com.example.ebot.models.ProfileResponse
import com.example.ebot.models.Vehicle
import com.example.ebot.services.ServiceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var tv_available_bal: TextView
    private lateinit var tv_home_address: TextView
    private lateinit var tv_vehicles_view: TextView
    private lateinit var dotsLayout: LinearLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var rc_vehicles: RecyclerView
    private lateinit var progressbar: ProgressBar
    private lateinit var bikeAdapter: BikesAdapter
    private var vehiclesList: ArrayList<Vehicle> = ArrayList()
    private var bannerList: ArrayList<HomeBannerList> = ArrayList()
    private lateinit var   adapter:CarouselAdapter

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
            viewPager = view.findViewById(R.id.viewPager)
            dotsLayout = view.findViewById(R.id.dotsLayout)
            bannerList= arrayListOf()
            val images = listOf(R.drawable.img_2)
             adapter = CarouselAdapter(images,bannerList,requireContext())
            viewPager.adapter = adapter
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setupDots(images.size)
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    updateDots(position)
                }
            })

            rc_vehicles = view.findViewById(R.id.rc_vehicles)
            tv_vehicles_view = view.findViewById(R.id.tv_vehicles_view)
            progressbar = view.findViewById(R.id.progressbar)
            tv_vehicles_view.setOnClickListener(View.OnClickListener {

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, BikesFragment())
                    .addToBackStack(null)
                    .commit()

//                val intent= Intent(requireContext(),PackagesListActivity::class.java)
//                startActivity(intent)
            })

            rc_vehicles.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            bikeAdapter= BikesAdapter(vehiclesList,requireContext())
            rc_vehicles.adapter=bikeAdapter
            getVehiclesList()
            val userId=Utils.getData(requireContext(),"user_id","").toString()
            getWalletAmountAPI(userId)
            getHomeBanner()
            val fullAddress=Utils.getData(requireContext(),"fullAddress","").toString()
            if (fullAddress.isNotEmpty()){
                tv_home_address.text=fullAddress
            }else{
                getProfileData(userId)
            }


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
                        println("Failed to get bikes. ${response.message()}")
                        Utils.showToast(requireContext(),"Failed to get bikes. ${response.message()}")
                    }


                }

                override fun onFailure(call: Call<List<Vehicle>>, t: Throwable) {
                    println("Failed to get bikes. ${t.message}")
                    progressbar.visibility= View.GONE

                    Utils.showToast(requireContext(),"Failed to get bikes. ${t.message}")
                }

            }
            dataManager.getVehicles(callback)


        }catch (e:Exception){
            progressbar.visibility= View.GONE

            Log.e("HomeFragment.bikes",e.message.toString())
        }
    }

    private fun getWalletAmountAPI(userId: String) {
        try {
            val dataManager = ServiceManager.getDataManager()

            val otpCallback = object : Callback<MainResponse> {
                override fun onResponse(
                    call: Call<MainResponse>,
                    response: Response<MainResponse>
                ) {
                    if (response.body()!!.status!!.toLowerCase() == "success") {
                        val responseData = response.body()!!.data.wallet_amount
                        val walletAmt: Float = responseData ?: 0.0f
                        Utils.WALLET_AMOUNT=walletAmt.toString()
                        tv_available_bal.text="₹ "+walletAmt.toString()


                        Log.v("Response", "response" + response.body()!!.message.toString())
                    } else {

                        // Handle error
                        println("Failed to getWalletAmount. ${response.message()}")
                    }

                }


                override fun onFailure(call: Call<MainResponse>, t: Throwable) {
                    println("Failed to getWalletAmount. ${t.message}")
                }
            }

            // Call the sendOtp function in DataManager
            dataManager.getWalletAmount(otpCallback, userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun getHomeBanner() {
        try {
            val dataManager = ServiceManager.getDataManager()

            val otpCallback = object : Callback<ArrayList<HomeBannerList>> {
                override fun onResponse(
                    call: Call<ArrayList<HomeBannerList>>,
                    response: Response<ArrayList<HomeBannerList>>
                ) {
                    if (response.isSuccessful) {
                        val responseData = response.body()!!
                        if (!responseData.isNullOrEmpty()) {
                            val images = listOf(R.drawable.img_2)  // Default image if API fails
                            adapter.updateBanners(responseData, images)
                            setupDots(responseData.size)
                        } else {
                            adapter.updateBanners(arrayListOf(), listOf(R.drawable.img_2))
                            setupDots(1)
                        }

                    } else {

                        // Handle error
                        println("Failed to getHomeBanner. ${response.message()}")
                    }

                }


                override fun onFailure(call: Call<ArrayList<HomeBannerList>>, t: Throwable) {
                    println("Failed to getHomeBanner. ${t.message}")
                }
            }

            dataManager.getHomeBanner(otpCallback)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupDots(count: Int) {
        dotsLayout.removeAllViews()
        val dots = Array(count) { ImageView(requireContext()) }
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }

        for (i in dots.indices) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bar_inactive))
            dots[i].layoutParams = layoutParams
            dotsLayout.addView(dots[i])
        }
        // Highlight the first dot
        if (dots.isNotEmpty()) {
            dots[0].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bar_active))
        }
    }
    private fun updateDots(position: Int) {
        for (i in 0 until dotsLayout.childCount) {
            val dot = dotsLayout.getChildAt(i) as ImageView
            dot.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    if (i == position) R.drawable.bar_active else R.drawable.bar_inactive
                )
            )
        }
    }
    fun getProfileData(user:String) {
        val dataManager = ServiceManager.getDataManager()

        val callback = object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {

                if (response.isSuccessful) {
                    if(response.body()!!.status=="success")
                    {
                        val data:ProfileData?=response.body()!!.data
                        val fullAddress =
                            """${data!!.address}, ${data.city}, ${data.state}, ${data.country} - ${data.pincode}"""

                        Utils.saveData(requireContext(),"fullAddress",fullAddress)
                        tv_home_address.text=fullAddress
                    }

                    Log.i("Response","response"+response.body().toString())

                } else {
                    // Handle error
                    println("Failed to getData  ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                // Handle failure
                println("Failed to getData. ${t.message}")

            }
        }

        dataManager.fetchProfile(callback, userId = user)
    }

}