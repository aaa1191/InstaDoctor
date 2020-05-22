package com.softgates.instadoctor.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.instadoctor.databinding.HomeListviewBinding
import com.softgates.instadoctor.network.HomeList

class HomeAdapter (private val onClickListener: OnClick ) :  ListAdapter<HomeList, RecyclerView.ViewHolder>(completeListDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeAdapter.ViewHolder).bind(getItem(position)!!,onClickListener,position)
        //     holder.bind(getItem(position)!!, onClickListener,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return HomeAdapter.ViewHolder.from(parent)
        // return  ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: HomeListviewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: HomeList, clickListener: OnClick, position:Int) {
           binding.viewModel = item
            binding.index = position
               binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeListviewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OnClick(val clickListener: (marsProperty: HomeList, type:Int, index:Int) -> Unit) {
    fun onView(marsProperty: HomeList, index:Int) = clickListener(marsProperty,1,index)
    fun onDesc(marsProperty: HomeList, index:Int) = clickListener(marsProperty,2,index)
}

class completeListDiffCallback : DiffUtil.ItemCallback<HomeList>() {

    override fun areItemsTheSame(oldItem: HomeList, newItem: HomeList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HomeList, newItem: HomeList): Boolean {
        return oldItem == newItem
    }
}