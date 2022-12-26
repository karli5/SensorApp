package com.example.sensorapp

import android.se.omapi.Session
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var sensorDataList = emptyList<SensorData>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var idView : TextView = itemView.findViewById(R.id.id_row)
        var dateView : TextView = itemView.findViewById(R.id.date_row)
        var nameView : TextView = itemView.findViewById(R.id.name_row)
        var xView : TextView = itemView.findViewById(R.id.x_row)
        var yView : TextView = itemView.findViewById(R.id.y_row)
        var zView : TextView = itemView.findViewById(R.id.z_row)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row , parent, false))
    }

    override fun getItemCount(): Int {
        return sensorDataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = sensorDataList[position]
        holder.idView.text = currentData.id.toString()
        holder.dateView.text = currentData.timestamp
        holder.nameView.text = currentData.sensorName.toString()
        holder.xView.text = currentData.x.toString()
        if(currentData.y == 0f && currentData.z == 0f) {
            holder.yView.text = "NaN"
            holder.zView.text = "NaN"
        }
        else {
            holder.yView.text = currentData.y.toString()
            holder.zView.text = currentData.z.toString()
        }
    }

    fun setData(data: List<SensorData>) {
        this.sensorDataList = data
        notifyDataSetChanged()
    }
}