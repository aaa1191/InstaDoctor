package com.softgates.instadoctor.scheduleappointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.instadoctor.databinding.RecyclerviewMychildBinding
import com.softgates.instadoctor.databinding.RecyclerviewtimelistViewBinding
import com.softgates.instadoctor.network.ScheduleList

data class ScheduleAppointmentAdapter(private val onClickListener: OnClicks) :  ListAdapter<ScheduleList, RecyclerView.ViewHolder>(completeListDiffCallbacks()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ScheduleAppointmentAdapter.ViewHolder).bind(getItem(position)!!,onClickListener,position)
        //     holder.bind(getItem(position)!!, onClickListener,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return ScheduleAppointmentAdapter.ViewHolder.from(parent)
        // return  ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RecyclerviewtimelistViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ScheduleList, clickListener: OnClicks, position:Int) {
            binding.viewModel = item
            binding.index = position
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerviewtimelistViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OnClicks(val clickListener: (marsProperty: ScheduleList, type:Int, index:Int) -> Unit) {
    fun onClick(marsProperty: ScheduleList, index:Int) = clickListener(marsProperty,1,index)
}

class completeListDiffCallbacks : DiffUtil.ItemCallback<ScheduleList>() {
    override fun areItemsTheSame(oldItem: ScheduleList, newItem: ScheduleList): Boolean {
        return oldItem.time == newItem.time
    }
    override fun areContentsTheSame(oldItem: ScheduleList, newItem: ScheduleList): Boolean {
        return oldItem == newItem
    }
}