package ru.vld43.mangadexapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.databinding.ItemMangaBinding
import ru.vld43.mangadexapp.domain.models.MangaWithCover

class MangaAdapter(private val onClickListener: (String) -> Unit) :
    PagingDataAdapter<MangaWithCover, MangaAdapter.MangaViewHolder>(MangaItemCallback) {

    object MangaItemCallback : DiffUtil.ItemCallback<MangaWithCover>() {

        override fun areItemsTheSame(oldItem: MangaWithCover, newItem: MangaWithCover): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MangaWithCover, newItem: MangaWithCover): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMangaBinding.inflate(inflater, parent, false)
        return MangaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val itemManga = getItem(position) ?: return

        Picasso.get()
            .load(itemManga.coverUrl)
            .error(R.drawable.ic_not_cover)
            .placeholder(R.drawable.progress_bar)
            .into(holder.binding.mangaCoverIv)

        holder.binding.mangaTitleTv.text = itemManga.title

        holder.binding.root.setOnClickListener {
            onClickListener(itemManga.id)
        }
    }

    inner class MangaViewHolder(val binding: ItemMangaBinding) :
        RecyclerView.ViewHolder(binding.root)
}
