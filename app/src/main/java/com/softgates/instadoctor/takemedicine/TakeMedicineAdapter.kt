package com.softgates.instadoctor.takemedicine
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.instadoctor.databinding.RecyclerviewtakemedicineViewBinding
import com.softgates.instadoctor.network.AddValue

class TakeMedicineAdapter (private val onClickListener: OnClick ) :  ListAdapter<AddValue, RecyclerView.ViewHolder>(symptomListDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TakeMedicineAdapter.ViewHolder).bind(getItem(position)!!,onClickListener,position)
        //     holder.bind(getItem(position)!!, onClickListener,position)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        return TakeMedicineAdapter.ViewHolder.from(parent)
        // return  ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: RecyclerviewtakemedicineViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: AddValue, clickListener: OnClick, position:Int) {
            binding.viewModel = item
            binding.index = position
         //   binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerviewtakemedicineViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class OnClick(val clickListener: (marsProperty: AddValue, type:Int, index:Int) -> Unit) {
    fun ontick(marsProperty: AddValue, index:Int) = clickListener(marsProperty,1,index)
    fun onDesc(marsProperty: AddValue, index:Int) = clickListener(marsProperty,2,index)
}

class symptomListDiffCallback : DiffUtil.ItemCallback<AddValue>() {
    override fun areItemsTheSame(oldItem: AddValue, newItem: AddValue): Boolean {
        return oldItem.addTxt == newItem.addTxt
    }
    override fun areContentsTheSame(oldItem: AddValue, newItem: AddValue): Boolean {
        return oldItem == newItem
    }
}
