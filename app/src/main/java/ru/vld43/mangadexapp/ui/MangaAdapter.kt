package ru.vld43.mangadexapp.ui

import android.media.Image
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
import ru.vld43.mangadexapp.common.Constants.COVER_ART_URL
import ru.vld43.mangadexapp.domain.models.MangaWithCover

class MangaAdapter : ListAdapter<MangaWithCover, MangaAdapter.MangaViewHolder>(MangaItemCallback) {

    private companion object {
        const val START_TITLE_TRANSFORM_INDEX = 0
        const val MAX_TITLE_TRANSFORM_SIZE = 20
        const val ELLIPSIS = "..."

        const val IMAGE_SIZE = ".256.jpg"
    }

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

        if (itemManga.coverFileName != "") {
            Picasso.get()
                .load("$COVER_ART_URL/${itemManga.manga.id}/${itemManga.coverFileName}$IMAGE_SIZE")
                .into(holder.mangaCover)
        } else {
            holder.mangaCover.setImageResource(R.drawable.ic_not_cover)
        }

        val title =
            if (itemManga.manga.title.length > MAX_TITLE_TRANSFORM_SIZE) {
                itemManga.manga.title.substring(
                    START_TITLE_TRANSFORM_INDEX,
                    MAX_TITLE_TRANSFORM_SIZE
                ) + ELLIPSIS
            } else {
                itemManga.manga.title
            }

        holder.mangaTitle.text = title
    }

    override fun getItemCount(): Int = mangaList.size

    inner class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mangaCover: ImageView = itemView.findViewById(R.id.manga_cover_iv)
        val mangaTitle: TextView = itemView.findViewById(R.id.manga_title_tv)
    }
}