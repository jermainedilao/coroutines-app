package com.jermaine.coroutines.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.jermaine.coroutines.data.Item

class ItemDiffUtilCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.trackId == newItem.trackId
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.artistName == newItem.artistName &&
                return oldItem.trackName == newItem.trackName
    }
}