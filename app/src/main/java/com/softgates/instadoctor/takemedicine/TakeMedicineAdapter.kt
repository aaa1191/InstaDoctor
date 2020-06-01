package com.softgates.instadoctor.takemedicine
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.softgates.instadoctor.databinding.RecyclerviewtakemedicineViewBinding
import com.softgates.instadoctor.network.SymptomList

class TakeMedicineAdapter (private val onClickListener: OnClick ) :  ListAdapter<SymptomList, RecyclerView.ViewHolder>(symptomListDiffCallback()) {

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
        fun bind(item: SymptomList, clickListener: OnClick, position:Int) {
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

class OnClick(val clickListener: (marsProperty: SymptomList, type:Int, index:Int) -> Unit) {
    fun ontick(marsProperty: SymptomList, index:Int) = clickListener(marsProperty,1,index)
    fun onDesc(marsProperty: SymptomList, index:Int) = clickListener(marsProperty,2,index)
}

class symptomListDiffCallback : DiffUtil.ItemCallback<SymptomList>() {
    override fun areItemsTheSame(oldItem: SymptomList, newItem: SymptomList): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: SymptomList, newItem: SymptomList): Boolean {
        return oldItem == newItem
    }
}
