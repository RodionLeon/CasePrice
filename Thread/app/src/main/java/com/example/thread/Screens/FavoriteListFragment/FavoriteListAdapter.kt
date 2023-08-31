package com.example.thread.Screens.FavoriteListFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thread.Consts
import com.example.thread.Model.Case
import com.example.thread.R
import com.example.thread.databinding.ItemRvBinding
import javax.inject.Inject

class FavoriteListAdapter @Inject constructor() : RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>() {
    var favoriteCaseList: MutableList<Case> = mutableListOf()
    var removeFavoriteItemClickListener: ((position: Int) -> Unit)? = null
    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val count = itemView.findViewById<TextView>(R.id.count)
        val buy = itemView.findViewById<TextView>(R.id.buy)
        val sell = itemView.findViewById<TextView>(R.id.sell)
        val name = itemView.findViewById<TextView>(R.id.name)
        val img = itemView.findViewById<ImageView>(R.id.imageView)
        val difImgBuy = itemView.findViewById<ImageView>(R.id.buyImg)
        val difImgSell = itemView.findViewById<ImageView>(R.id.sellImg)
        val isFavoriteImg = itemView.findViewById<ImageView>(R.id.isFavorite)

        init {
            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    removeFavoriteItemClickListener?.invoke(position)
                }
                true
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_rv, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteCase = favoriteCaseList[position]

        holder.name.text = favoriteCase.name
        holder.buy.text = "buy:${favoriteCase.lowest_price}"
        holder.count.text = "count:${favoriteCase.volume}"
        holder.sell.text = "sell:${favoriteCase.median_price}"

        if (favoriteCase.buyPriceComparison) {
            holder.difImgBuy.setImageResource(R.drawable.baseline_arrow_upward_24)
        } else {
            holder.difImgBuy.setImageResource(R.drawable.baseline_arrow_downward_24)
        }

        if (favoriteCase.sellPriceComparison) {
            holder.difImgSell.setImageResource(R.drawable.baseline_arrow_upward_24)
        } else {
            holder.difImgSell.setImageResource(R.drawable.baseline_arrow_downward_24)
        }

        if (favoriteCase.isFavorite) {
            holder.isFavoriteImg.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            holder.isFavoriteImg.setImageResource(R.drawable.baseline_favorite_border_24)
        }

        val imageIndex = Consts.cases.indexOf(favoriteCase.name)
        if (imageIndex != -1 && imageIndex < Consts.casesImg.size) {
            holder.img.setImageResource(Consts.casesImg[imageIndex])
        }
    }

    override fun getItemCount(): Int {
        return favoriteCaseList.size
    }
}