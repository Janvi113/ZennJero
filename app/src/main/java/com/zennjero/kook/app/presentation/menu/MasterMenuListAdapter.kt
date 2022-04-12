package com.zennjero.kook.app.presentation.menu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zennjero.kook.app.R
import com.zennjero.kook.app.domain.MasterMenuItem

class MasterMenuListAdapter : RecyclerView.Adapter<MasterMenuListAdapter.ViewHolder>() {
    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)
    {
        val dishName : AppCompatTextView = itemview.findViewById(R.id.dishName)
        val price : AppCompatTextView = itemview.findViewById(R.id.price)
        val description : AppCompatTextView = itemview.findViewById(R.id.description)
        val serving : AppCompatTextView = itemview.findViewById(R.id.serving)
        val checkBox:CheckBox = itemview.findViewById(R.id.checkbox)
    }
    private val differCallBack= object : DiffUtil.ItemCallback<MasterMenuItem>() {
        override fun areItemsTheSame(oldItem: MasterMenuItem, newItem: MasterMenuItem): Boolean {
            return oldItem.dishName == newItem.dishName && oldItem.price == newItem.price && oldItem.serving == newItem.serving
        }

        override fun areContentsTheSame(oldItem: MasterMenuItem, newItem: MasterMenuItem): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this,differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         return ViewHolder(LayoutInflater.from(parent.context).
         inflate(R.layout.mastermenu_itemview,parent,false))
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuitem = differ.currentList[position]
        holder.apply {
            dishName.text = menuitem.dishName
            price.text = "₹ "+menuitem.price.toString()
            serving.text = "(Serving ${menuitem.serving} People)"
            description.text = menuitem.description
        }
        holder.checkBox.setOnClickListener {
            onItemClickListener?.let {
                it(menuitem)
            }
        }
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }
    private var onItemClickListener:((MasterMenuItem)->Unit)?=null
    fun setOnItemClickListener(listner:(MasterMenuItem)->Unit)
    {
        onItemClickListener=listner
    }
}