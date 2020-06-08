package com.softgates.instadoctor.setting
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.instadoctor.databinding.RecyclerviewPrescriptionBinding
import com.softgates.instadoctor.network.PrescriptionList

class PrescriptionAdapter (private val onClickListener: OnClickss ) :  ListAdapter<PrescriptionList, RecyclerView.ViewHolder>(prescriptionListDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PrescriptionAdapter.ViewHolder).bind(getItem(position)!!,onClickListener,position)
        //     holder.bind(getItem(position)!!, onClickListener,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return PrescriptionAdapter.ViewHolder.from(parent)
        // return  ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RecyclerviewPrescriptionBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: PrescriptionList, clickListener: OnClickss, position:Int) {
            binding.viewModel = item
            binding.index = position
            //   binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerviewPrescriptionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OnClickss(val clickListener: (marsProperty: PrescriptionList, type:Int, index:Int) -> Unit) {
    fun onRenew(marsProperty: PrescriptionList, index:Int) = clickListener(marsProperty,1,index)
    fun onDesc(marsProperty: PrescriptionList, index:Int) = clickListener(marsProperty,2,index)
}

class prescriptionListDiffCallback : DiffUtil.ItemCallback<PrescriptionList>() {
    override fun areItemsTheSame(oldItem: PrescriptionList, newItem: PrescriptionList): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: PrescriptionList, newItem: PrescriptionList): Boolean {
        return oldItem == newItem
    }
}