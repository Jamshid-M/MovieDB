package uz.jamshid.imdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.jamshid.imdb.databinding.PopularMoviesFooterLayoutBinding

class PopularMoviesLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<PopularMoviesLoadStateAdapter.MovieLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MovieLoadStateViewHolder {
        return MovieLoadStateViewHolder.from(parent, retry)
    }

    class MovieLoadStateViewHolder(private val binding: PopularMoviesFooterLayoutBinding,
                                   retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState?) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState !is LoadState.Loading
            binding.errorMsg.isVisible = loadState !is LoadState.Loading
        }

        companion object {
            fun from(parent: ViewGroup, retry: () -> Unit): MovieLoadStateViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PopularMoviesFooterLayoutBinding.inflate(layoutInflater, parent, false)
                return MovieLoadStateViewHolder(binding, retry)
            }
        }
    }
}