package ru.vld43.mangadexapp.ui.chapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.vld43.mangadexapp.databinding.ItemChapterBinding
import ru.vld43.mangadexapp.domain.models.Chapter

class ChaptersAdapter :
    PagingDataAdapter<Chapter, ChaptersAdapter.ChapterViewHolder>(ChapterItemCallBack) {

    object ChapterItemCallBack : DiffUtil.ItemCallback<Chapter>() {

        override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean =
            oldItem == newItem
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val itemChapter = getItem(position) ?: return

        holder.binding.chapterTitleTv.text = itemChapter.title
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ChapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChapterBinding.inflate(inflater, parent, false)
        return ChapterViewHolder(binding)
    }

    inner class ChapterViewHolder(val binding: ItemChapterBinding) :
        RecyclerView.ViewHolder(binding.root)
}
