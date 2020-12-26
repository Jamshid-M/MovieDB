package uz.jamshid.imdb.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import uz.jamshid.imdb.data.PopularMoviesRepository
import uz.jamshid.imdb.data.entities.Movie

class MainViewModel @ViewModelInject constructor(private val repository: PopularMoviesRepository) : ViewModel() {


    fun getPopularMovies() : Flow<PagingData<Movie>> {
        return repository.getPopularMovies()
            .cachedIn(viewModelScope)
    }

}