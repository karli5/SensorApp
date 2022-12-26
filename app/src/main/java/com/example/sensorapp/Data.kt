package com.example.sensorapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorapp.databinding.FragmentDataBinding
import com.example.sensorapp.databinding.FragmentSensorsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var binding : FragmentDataBinding
private lateinit var viewModel: SensorsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [Data.newInstance] factory method to
 * create an instance of this fragment.
 */
class Data : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var sensorDataViewModel: SensorDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        /*
        var adapter = ListAdapter()
        binding = FragmentDataBinding.inflate(layoutInflater)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

         */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var adapter = ListAdapter()
        binding = FragmentDataBinding.inflate(layoutInflater)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        /*var recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }

         */
        sensorDataViewModel = ViewModelProvider(this)[SensorDataViewModel::class.java]
        sensorDataViewModel.readAllData.observe(viewLifecycleOwner) { sensorData ->
            adapter.setData(sensorData)
        }
        viewModel = ViewModelProvider(this)[SensorsViewModel::class.java]
        viewModel.appDb = AppDatabase.getDatabase(requireContext())
        binding.buttonDeleteAll.setOnClickListener{
            viewModel.deleteAllData()
        }

        //TO DO return binding.root
        //return inflater.inflate(R.layout.fragment_data, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Data.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Data().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}