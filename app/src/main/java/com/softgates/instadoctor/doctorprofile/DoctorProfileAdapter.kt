package com.softgates.instadoctor.doctorprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.instadoctor.databinding.DoctorprofileratingListBinding
import com.softgates.instadoctor.network.DoctorReviewList

class DoctorProfileAdapter (private val onClickListener: OnClick ) :  ListAdapter<DoctorReviewList, RecyclerView.ViewHolder>(completeListDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DoctorProfileAdapter.ViewHolder).bind(getItem(position)!!,onClickListener,position)
        //     holder.bind(getItem(position)!!, onClickListener,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return DoctorProfileAdapter.ViewHolder.from(parent)
        // return  ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: DoctorprofileratingListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: DoctorReviewList, clickListener: OnClick, position:Int) {
            binding.viewModel = item
            binding.index = position
            //   binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DoctorprofileratingListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OnClick(val clickListener: (marsProperty: DoctorReviewList, type:Int, index:Int) -> Unit) {
    fun onRenew(marsProperty: DoctorReviewList, index:Int) = clickListener(marsProperty,1,index)
    fun onDesc(marsProperty: DoctorReviewList, index:Int) = clickListener(marsProperty,2,index)
}

class completeListDiffCallback : DiffUtil.ItemCallback<DoctorReviewList>() {

    override fun areItemsTheSame(oldItem: DoctorReviewList, newItem: DoctorReviewList): Boolean {
        return oldItem.patient_name == newItem.patient_name
    }

    override fun areContentsTheSame(oldItem: DoctorReviewList, newItem: DoctorReviewList): Boolean {
        return oldItem == newItem
    }
}