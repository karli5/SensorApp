package com.example.sensorapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sensorapp.databinding.FragmentSensorsBinding
import java.time.Instant

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

    private lateinit var viewModel: SensorsViewModel
    private val arrayTest = listOf("Test1", "Test2", "Test3", "Test4")
    private lateinit var binding : FragmentSensorsBinding
    @RequiresApi(Build.VERSION_CODES.O)
    var timestemp : Long = Instant.now().toEpochMilli()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Initializing the ViewModel for the Sensors Activity
        viewModel = ViewModelProvider(this)[SensorsViewModel::class.java]

        //Initializing the list of sensors for the smartphone + the sensor manager for establishing the SensorEvent Listener
        var sm : SensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        var sensorsList = sm.getSensorList((Sensor.TYPE_ALL))
        var sensorNameList = mutableListOf<String>()

        for (sensor in sensorsList) {
            sensorNameList.add(sensor.name)
        }
        //Setting up a default Sensor
        var sensorVar = sensorsList[0]
        //Accessing the layout through the FragmentSensorBinding class and setting up the dropdown menu with an adapter
        binding = FragmentSensorsBinding.inflate(layoutInflater)
        val adapter = ArrayAdapter(this.requireContext(), R.layout.list_item, sensorNameList)
        binding.dropdownField.setAdapter(adapter)

        //Hier muss evt später der Sensor rein!
        //Setting up the start/stop Button click Listener Event by accessing the button state in viewModel and adjusting the button text
        binding.buttonStart.setOnClickListener {
            viewModel.updateButton()
            //If Start was pressed then watch the current sensor data
            //Establish an event listener for the current Sensor and show the values in textView. All sensors have a dimension of 3 for the x,y and z component in space
            if(viewModel.buttonIsStart.value == true) {
                binding.buttonStart.text = "Stop"

                    var se = object : SensorEventListener {
                    override fun onSensorChanged(sensorEvent: SensorEvent?) {
                        var values = sensorEvent?.values

                        //Differentiation if values will have dimension 1 or 3
                        if (values != null) {
                            if(values.size == 3) {
                                var x = values?.get(0)
                                var y = values?.get(1)
                                var z = values?.get(2)
                                binding.sensorData.text = "Live data: \n" +  x.toString() + "\n" + y.toString() + "\n" + z.toString() + "\n" + sensorVar.name
                                if(Instant.now().toEpochMilli() - timestemp >= 10000) {
                                    timestemp = Instant.now().toEpochMilli()
                                    viewModel.updateData(x, y, z)
                                }
                            }
                            else {
                                var x = values?.get(0)
                                binding.sensorData.text = "Live data: \n" +  x.toString() + "\n" + sensorVar.name
                                if(Instant.now().toEpochMilli() - timestemp >= 10000) {
                                    timestemp = Instant.now().toEpochMilli()
                                    viewModel.updateData(x)
                                }
                            }
                        }
                    }

                    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                    }

                }
                //Register eventListener and give it to ViewModel for data accessibility in the rest of the script
                viewModel.initializeEventListener(se)
                sm.registerListener(se, sensorVar, SensorManager.SENSOR_DELAY_NORMAL)

            }
            //If currently stop is selected, stop the event Listener called from viewModel and change the text field
            else {
                sm.unregisterListener(viewModel.eventListener)
                binding.sensorData.text = "No sensor selected"
                binding.buttonStart.text = "Start"
            }
        }


        //If a dropdown element is chosen, give more details in the defined TextView which includes the name, vendor and version of the sensor.
        //Additionally redefine the current chosen sensor
        binding.dropdownField.setOnItemClickListener { adapterView, view, i, l ->
            binding.sensorDescription.text = "Name: " + sensorsList[i].name + "\n" + "Vendor: " + sensorsList[i].vendor + "\n" + "Version: " + sensorsList[i].version
            sensorVar = sensorsList[i]
        }

        //Placeholder for old position of eventListener code
        /*
        var se = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent?) {
                var values = sensorEvent?.values
                var x = values?.get(0)
                var y = values?.get(1)
                var z = values?.get(2)
                binding.sensorData.text = "Live data: \n" +  x.toString() + "\n" + y.toString() + "\n" + z.toString() + "\n" + sensorVar.name
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }

        }
        sm.registerListener(se, sensorVar, SensorManager.SENSOR_DELAY_NORMAL)
        */
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