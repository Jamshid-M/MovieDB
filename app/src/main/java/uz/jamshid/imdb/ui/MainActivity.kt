package uz.jamshid.imdb.ui

import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.jamshid.imdb.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapter = PopularMoviesAdapter()
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMovies.adapter = adapter.withLoadStateFooter(
            PopularMoviesLoadStateAdapter { adapter.retry() }
        )

        binding.btnRetry.setOnClickListener {
            adapter.retry()
        }

        adapter.addLoadStateListener {
            binding.rvMovies.isVisible = it.source.refresh is LoadState.NotLoading
            binding.pbMovies.isVisible = it.source.refresh is LoadState.Loading
            binding.btnRetry.isVisible = it.source.refresh is LoadState.Error

            val errorState = it.source.append as? LoadState.Error
                ?: it.source.prepend as? LoadState.Error
                ?: it.append as? LoadState.Error
                ?: it.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        getPopularMovies()
    }

    private fun getPopularMovies() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getPopularMovies().collect {
                adapter.submitData(it)
            }
        }
    }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()