package com.softgates.instadoctor.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.instadoctor.databinding.RecycleviewChatBinding
import com.softgates.instadoctor.network.ChatList

class ChatAdapter (private val onClickListener: OnClick ) :  ListAdapter<ChatList, RecyclerView.ViewHolder>(completeListDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ChatAdapter.ViewHolder).bind(getItem(position)!!,onClickListener,position)
        //     holder.bind(getItem(position)!!, onClickListener,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return ChatAdapter.ViewHolder.from(parent)
        // return  ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RecycleviewChatBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ChatList, clickListener: OnClick, position:Int) {
            binding.viewModel = item
            binding.index = position
            //   binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecycleviewChatBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OnClick(val clickListener: (marsProperty: ChatList, type:Int, index:Int) -> Unit) {
    fun onRenew(marsProperty: ChatList, index:Int) = clickListener(marsProperty,1,index)
    fun onDesc(marsProperty: ChatList, index:Int) = clickListener(marsProperty,2,index)
}

class completeListDiffCallback : DiffUtil.ItemCallback<ChatList>() {
    override fun areItemsTheSame(oldItem: ChatList, newItem: ChatList): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: ChatList, newItem: ChatList): Boolean {
        return oldItem == newItem
    }
}