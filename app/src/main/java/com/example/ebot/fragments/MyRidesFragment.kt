package com.example.ebot.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebot.R
import com.example.ebot.adapters.MyRidesAdapter


class MyRidesFragment : Fragment() {
private lateinit var rc_myRidesList:RecyclerView
    private lateinit var myRidesAdapter: MyRidesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_rides, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rc_myRidesList = view.findViewById(R.id.rc_myRidesList)

      /*  rc_myRidesList.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        myRidesAdapter = MyRidesAdapter(requireContext())
        rc_myRidesList.adapter = myRidesAdapter*/
    }
}