package com.softgates.instadoctor.prescription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.instadoctor.databinding.RecyclerviewMychildBinding
import com.softgates.instadoctor.databinding.RecyclerviewPrescriptionlistBinding
import com.softgates.instadoctor.network.PrescriptionList

class PrescriptionListAdapter (private val onClickListener: OnClicks) :  ListAdapter<PrescriptionList, RecyclerView.ViewHolder>(completeListDiffCallbacks()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PrescriptionListAdapter.ViewHolder).bind(getItem(position)!!,onClickListener,position)
        //     holder.bind(getItem(position)!!, onClickListener,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return PrescriptionListAdapter.ViewHolder.from(parent)
        // return  ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RecyclerviewPrescriptionlistBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: PrescriptionList, clickListener: OnClicks, position:Int) {
            binding.viewModel = item
            binding.index = position
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerviewPrescriptionlistBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OnClicks(val clickListener: (marsProperty: PrescriptionList, type:Int, index:Int) -> Unit) {
    fun onClick(marsProperty: PrescriptionList, index:Int) = clickListener(marsProperty,1,index)
}

class completeListDiffCallbacks : DiffUtil.ItemCallback<PrescriptionList>() {
    override fun areItemsTheSame(oldItem: PrescriptionList, newItem: PrescriptionList): Boolean {
        return oldItem.app_id == newItem.app_id
    }
    override fun areContentsTheSame(oldItem: PrescriptionList, newItem: PrescriptionList): Boolean {
        return oldItem == newItem
    }
}