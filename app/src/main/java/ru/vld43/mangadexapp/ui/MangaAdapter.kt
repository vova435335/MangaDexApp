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
import ru.vld43.mangadexapp.domain.models.Manga

class MangaAdapter : ListAdapter<Manga, MangaAdapter.MangaViewHolder>(MangaItemCallback) {

    object MangaItemCallback : DiffUtil.ItemCallback<Manga>() {

        override fun areItemsTheSame(oldItem: Manga, newItem: Manga): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Manga, newItem: Manga): Boolean =
            oldItem == newItem
    }

    var mangaList: List<Manga> = emptyList()
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
        Picasso.get()
            .load("https://uploads.mangadex.org/covers/558c2122-7342-4649-85e9-a866727bb2e8/c2cc3ce4-a84c-4036-938f-a8312358505c.jpg.256.jpg")
            .into(holder.mangaCover)

        val itemManga = mangaList[position]

        if (itemManga.title.length > 20) {
            val incompleteTitle = itemManga.title.substring(
                0,
                20
            ) + "..."
            holder.mangaTitle.text = incompleteTitle
        } else {
            holder.mangaTitle.text = itemManga.title
        }

    }

    override fun getItemCount(): Int = mangaList.size

    inner class MangaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mangaCover: ImageView = itemView.findViewById(R.id.manga_cover_iv)
        val mangaTitle: TextView = itemView.findViewById(R.id.manga_title_tv)
    }
}