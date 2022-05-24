package ru.vld43.mangadexapp.ui.read_manga

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.databinding.ItemReadMangaBinding

class ReadMangaAdapter :
    PagingDataAdapter<String, ReadMangaAdapter.ReadMangaViewHolder>(ChapterItemCallBack) {

    object ChapterItemCallBack : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: ReadMangaViewHolder, position: Int) {
        val itemPageImage = getItem(position) ?: return

        Log.d("TAG", "onBindViewHolder: $itemPageImage")

        Picasso.get()
            .load(itemPageImage)
            .error(R.drawable.ic_not_cover)
            .placeholder(R.drawable.progress_bar)
            .into(holder.binding.readMangaIv)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReadMangaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemReadMangaBinding.inflate(inflater, parent, false)
        return ReadMangaViewHolder(binding)
    }

    inner class ReadMangaViewHolder(val binding: ItemReadMangaBinding) :
        RecyclerView.ViewHolder(binding.root)
}
