package com.softgates.instadoctor.setting
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.instadoctor.databinding.RecyclerviewSessionBinding
import com.softgates.instadoctor.network.SessionList

class SessionAdapter(private val onClickListener: OnClicks) :  ListAdapter<SessionList, RecyclerView.ViewHolder>(completeListDiffCallbacks()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SessionAdapter.ViewHolder).bind(getItem(position)!!,onClickListener,position)
        //     holder.bind(getItem(position)!!, onClickListener,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return SessionAdapter.ViewHolder.from(parent)
        // return  ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RecyclerviewSessionBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: SessionList, clickListener: OnClicks, position:Int) {
            binding.viewModel = item
            binding.index = position
             // binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerviewSessionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OnClicks(val clickListener: (marsProperty: SessionList, type:Int, index:Int) -> Unit) {
    fun onRenew(marsProperty: SessionList, index:Int) = clickListener(marsProperty,1,index)
    fun onDesc(marsProperty: SessionList, index:Int) = clickListener(marsProperty,2,index)
}

class completeListDiffCallbacks : DiffUtil.ItemCallback<SessionList>() {
    override fun areItemsTheSame(oldItem: SessionList, newItem: SessionList): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: SessionList, newItem: SessionList): Boolean {
        return oldItem == newItem
    }
}