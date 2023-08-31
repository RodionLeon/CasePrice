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
    private var caseList: MutableList<Case> = mutableListOf()
    var onItemLongClickListener: ((position: Int) -> Unit)? = null

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val count = itemView.findViewById<TextView>(R.id.count)
        val buy = itemView.findViewById<TextView>(R.id.buy)
        val sell = itemView.findViewById<TextView>(R.id.sell)
        val name = itemView.findViewById<TextView>(R.id.name)
        val img = itemView.findViewById<ImageView>(R.id.imageView)
        val difImgBuy = itemView.findViewById<ImageView>(R.id.buyImg)
        val difImgSell = itemView.findViewById<ImageView>(R.id.sellImg)
        val isFavoriteImg = itemView.findViewById<ImageView>(R.id.isFavorite)

    }

    fun setCases(cases: List<Case>) {
        val updatedCases = cases.map { case ->
            val existingCase = caseList.find { it.name == case.name }
            if (existingCase != null) {
                case.copy(isFavorite = existingCase.isFavorite)
            } else {
                case
            }
        }
        caseList = updatedCases.toMutableList()
        notifyDataSetChanged()
        Log.d("!!!!", "setCases: $cases")

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
        if (case.buyPriceComparison) holder.difImgBuy.setImageResource(R.drawable.baseline_arrow_upward_24)
        else holder.difImgBuy.setImageResource(R.drawable.baseline_arrow_downward_24)

        if (case.sellPriceComparison) holder.difImgSell.setImageResource(R.drawable.baseline_arrow_upward_24)
        else holder.difImgSell.setImageResource(R.drawable.baseline_arrow_downward_24)

        if (case.isFavorite) {
            holder.isFavoriteImg.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            holder.isFavoriteImg.setImageResource(R.drawable.baseline_favorite_border_24)
        }

        if (imageIndex != -1 && imageIndex < Consts.casesImg.size) {
            holder.img.setImageResource(Consts.casesImg[imageIndex])
        }


        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.invoke(position)
            val updatedCase = case.copy(isFavorite = !case.isFavorite)
            caseList[position] = updatedCase

            if (updatedCase.isFavorite) {
                holder.isFavoriteImg.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                holder.isFavoriteImg.setImageResource(R.drawable.baseline_favorite_border_24)
            }

            true
        }
    }

    fun getCaseAtPosition(position: Int): Case {
        return caseList[position]
    }

    override fun getItemCount(): Int {
        return caseList.size
    }

}