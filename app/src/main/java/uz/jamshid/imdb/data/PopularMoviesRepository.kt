package uz.jamshid.imdb.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import uz.jamshid.imdb.data.entities.Movie
import uz.jamshid.imdb.data.remote.MovieService
import javax.inject.Inject

class PopularMoviesRepository @Inject constructor(private val service: MovieService) {

    fun getPopularMovies(): Flow<PagingData<Movie>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(service)
            }
        ).flow
    }
}