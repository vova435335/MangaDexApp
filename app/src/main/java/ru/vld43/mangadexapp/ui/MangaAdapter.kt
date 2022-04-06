package ru.vld43.mangadexapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.vld43.mangadexapp.R
import ru.vld43.mangadexapp.domain.models.MangaWithCover

class MangaAdapter : ListAdapter<MangaWithCover, MangaAdapter.MangaViewHolder>(MangaItemCallback) {

    object MangaItemCallback : DiffUtil.ItemCallback<MangaWithCover>() {

        override fun areItemsTheSame(oldItem: MangaWithCover, newItem: MangaWithCover): Boolean =
            oldItem.manga.id == newItem.manga.id

        override fun areContentsTheSame(oldItem: MangaWithCover, newItem: MangaWithCover): Boolean =
            oldItem == newItem
    }

    var mangaList: List<MangaWithCover> = emptyList()
        set(value) {
            field = value
            submitList(field)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder =
        MangaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_manga, parent, false)
        )

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val itemManga = mangaList[position]

        if (itemManga.coverUrl != "") {
            Picasso.get()
                .load(itemManga.coverUrl)
                .into(holder.mangaCover)
        } else {
            holder.mangaCover.setImageResource(R.drawable.ic_not_cover)
        }

        holder.mangaTitle.text = itemManga.manga.title
    }

    override fun getItemCount(): Int = mangaList.size

    inner class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mangaCover: ImageView = itemView.findViewById(R.id.manga_cover_iv)
        val mangaTitle: TextView = itemView.findViewById(R.id.manga_title_tv)
    }
}