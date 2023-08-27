package com.example.thread.MainFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thread.Consts
import com.example.thread.Model.Case
import com.example.thread.R

class MainAdapter(val caseList: MutableList<Case>, val activity: MainActivity) : RecyclerView.Adapter<MainAdapter.UserViewHolder>() {
    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val count = itemView.findViewById<TextView>(R.id.count)
        val buy = itemView.findViewById<TextView>(R.id.buy)
        val sell = itemView.findViewById<TextView>(R.id.sell)
        val name = itemView.findViewById<TextView>(R.id.name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_rv, parent, false)
        val holder = UserViewHolder(itemView)
        return holder
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val case  = caseList[position]
        holder.name.text = "${Consts.cases[position]}"
        holder.buy.text = "buy:${case.lowest_price}"
        holder.count.text = "count:${case.volume}"
        holder.sell.text = "sell:${case.median_price}"

    }

    override fun getItemCount(): Int {
        return caseList.size
    }

}