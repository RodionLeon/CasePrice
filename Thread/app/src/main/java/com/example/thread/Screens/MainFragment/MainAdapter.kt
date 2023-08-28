package com.example.thread.Screens.MainFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thread.Consts
import com.example.thread.Model.Case
import com.example.thread.R
import javax.inject.Inject

class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainAdapter.UserViewHolder>() {
   private var caseList = emptyList<Case>()

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val count = itemView.findViewById<TextView>(R.id.count)
        val buy = itemView.findViewById<TextView>(R.id.buy)
        val sell = itemView.findViewById<TextView>(R.id.sell)
        val name = itemView.findViewById<TextView>(R.id.name)
        val img = itemView.findViewById<ImageView>(R.id.imageView)
    }

    fun setCases(cases: List<Case>) {
        caseList = cases
        Log.d("!!!!", "setCases: $cases")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_rv, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val case = caseList[position]
        val imageIndex = Consts.cases.indexOf(case.name)

        holder.name.text = case.name
        holder.buy.text = "buy:${case.lowest_price}"
        holder.count.text = "count:${case.volume}"
        holder.sell.text = "sell:${case.median_price}"

        if (imageIndex != -1 && imageIndex < Consts.casesImg.size) {
            holder.img.setImageResource(Consts.casesImg[imageIndex])
        }
    }

    override fun getItemCount(): Int {
        return caseList.size
    }


}