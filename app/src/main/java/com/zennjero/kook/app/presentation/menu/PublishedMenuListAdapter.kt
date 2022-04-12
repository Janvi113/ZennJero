package com.zennjero.kook.app.presentation.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.zennjero.kook.app.R

class PublishedMenuListAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_DATE = 1
        const val VIEW_TYPE_MENU_ITEM = 2
    }
    //var data : List<PublishedMenu> = listOf()
    private inner class DateViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
//        var dateChip= itemView.findViewById<Chip>(R.id.dateChip)
//        fun bind(position: Int) {
//            val recyclerViewModel = data[position]
//            dateChip.text = recyclerViewModel.date
//        }
    }

    private inner class MenuItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
//        var chipGroup: ChipGroup = itemView.findViewById(R.id.timeChipGroup)
//        val chip : Chip =  LayoutInflater.from(itemView.context).inflate(R.layout.time_chip_single_item, chipGroup, false) as Chip
//        fun bind(position: Int) {
//
//            val recyclerViewModel = data[position]
//             chip.text = recyclerViewModel.timeSlot
//            chipGroup.addView(chip)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_DATE) {
            return DateViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_date_published_menu, parent, false)
            )
        }
        return MenuItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_published_menu, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 0
        //return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (data[position].viewType == VIEW_TYPE_DATE) {
//            (holder as DateViewHolder).bind(position)
//        } else {
//            (holder as MenuItemViewHolder).bind(position)
//        }
    }

    override fun getItemViewType(position: Int): Int {
        return  1
        //return data[position].viewType
    }
}