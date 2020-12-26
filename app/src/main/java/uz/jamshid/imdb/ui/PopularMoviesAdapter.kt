package uz.jamshid.imdb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import kotlinx.android.synthetic.main.popular_movies_item_layout.view.*
import uz.jamshid.imdb.data.entities.Movie
import uz.jamshid.imdb.databinding.PopularMoviesItemLayoutBinding

class PopularMoviesAdapter :
    PagingDataAdapter<Movie, PopularMoviesAdapter.ViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: PopularMoviesItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie?) {
            data?.apply {
                val corners = 10.dp.toFloat()
                binding.root.tvTitle.text = title
                binding.root.tvDescription.text = overview
                binding.root.tvRating.text = vote_average.toString()
                binding.root.ivImage.load("https://image.tmdb.org/t/p/w500$poster_path"){
                    crossfade(true)
                    transformations(RoundedCornersTransformation(corners))
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PopularMoviesItemLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}