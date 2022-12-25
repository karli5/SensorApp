package com.example.sensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.getSystemService
import com.example.sensorapp.databinding.ActivityMainBinding
import com.example.sensorapp.databinding.FragmentSensorsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Sensors.newInstance] factory method to
 * create an instance of this fragment.
 */
class Sensors : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val arrayTest = listOf("Test1", "Test2", "Test3", "Test4")
    private lateinit var binding : FragmentSensorsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var sm : SensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        var sensorsList = sm.getSensorList((Sensor.TYPE_ALL))
        var sensorNameList = mutableListOf<String>()
        for (sensor in sensorsList) {
            sensorNameList.add(sensor.name)
        }

        binding = FragmentSensorsBinding.inflate(layoutInflater)
        val adapter = ArrayAdapter(this.requireContext(), R.layout.list_item, sensorNameList)
        binding.dropdownField.setAdapter(adapter)


        binding.dropdownField.setOnItemClickListener { adapterView, view, i, l ->
            println(i.toString())
            binding.sensorDescription.text = "Name: " + sensorsList[i].name + "\n" + "Vendor: " + sensorsList[i].vendor + "\n" + "Version: " + sensorsList[i].version
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Sensors.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Sensors().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}