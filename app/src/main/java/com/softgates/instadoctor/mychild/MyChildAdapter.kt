package com.softgates.instadoctor.mychild

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.instadoctor.databinding.MychildViewBinding
import com.softgates.instadoctor.databinding.RecyclerviewMychildBinding
import com.softgates.instadoctor.databinding.RecyclerviewSessionBinding
import com.softgates.instadoctor.network.MyChildList

class MyChildAdapter(private val onClickListener: OnClicks) :  ListAdapter<MyChildList, RecyclerView.ViewHolder>(completeListDiffCallbacks()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyChildAdapter.ViewHolder).bind(getItem(position)!!,onClickListener,position)
        //     holder.bind(getItem(position)!!, onClickListener,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return MyChildAdapter.ViewHolder.from(parent)
        // return  ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RecyclerviewMychildBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MyChildList, clickListener: OnClicks, position:Int) {
            binding.viewModel = item
            binding.index = position
           binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerviewMychildBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OnClicks(val clickListener: (marsProperty: MyChildList, type:Int, index:Int) -> Unit) {
    fun onClick(marsProperty: MyChildList, index:Int) = clickListener(marsProperty,1,index)
}

class completeListDiffCallbacks : DiffUtil.ItemCallback<MyChildList>() {
    override fun areItemsTheSame(oldItem: MyChildList, newItem: MyChildList): Boolean {
        return oldItem.child_id == newItem.child_id
    }
    override fun areContentsTheSame(oldItem: MyChildList, newItem: MyChildList): Boolean {
        return oldItem == newItem
    }
}