package ru.vld43.mangadexapp.common.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.vld43.mangadexapp.databinding.PartDefaultLoadStateBinding

typealias TryAgainAction = () -> Unit

class DefaultLoadStateAdapter(
    private val tryAgainAction: TryAgainAction,
) : LoadStateAdapter<DefaultLoadStateAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        with(holder.binding) {
            messageTv.isVisible = loadState !is LoadState.Loading
            tryAgainButton.isVisible = loadState !is LoadState.Loading
            progressBar.isVisible = loadState is LoadState.Loading

            tryAgainButton.setOnClickListener { tryAgainAction() }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PartDefaultLoadStateBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    class Holder(val binding: PartDefaultLoadStateBinding) : RecyclerView.ViewHolder(binding.root)
}
