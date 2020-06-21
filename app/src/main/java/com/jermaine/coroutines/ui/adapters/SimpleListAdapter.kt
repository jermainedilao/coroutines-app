package com.jermaine.coroutines.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jermaine.coroutines.BR
import com.jermaine.coroutines.R

class SimpleListAdapter<T : Any, B : ViewDataBinding>(
    diffUtil: DiffUtil.ItemCallback<T>,
    @LayoutRes private val layoutId: Int,
    private val itemClickListener: (T) -> Unit
) : ListAdapter<T, ViewHolder<B>>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
        return ViewHolder<B>(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            )
        ).apply {
            binding
                .root
                .findViewById<View>(R.id.itemClickable)
                .setOnClickListener {
                    itemClickListener.invoke(getItem(adapterPosition))
                }
        }
    }

    override fun getItemViewType(position: Int): Int = 0

    override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) {
        holder.bind(getItem(position))
    }
}

open class ViewHolder<B : ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Any) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}