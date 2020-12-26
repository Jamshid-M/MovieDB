package uz.jamshid.imdb.data

import androidx.paging.PagingSource
import retrofit2.HttpException
import uz.jamshid.imdb.data.entities.Movie
import uz.jamshid.imdb.data.remote.MovieService
import java.io.IOException

class MoviesPagingSource(
    private val service: MovieService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        val page = params.key ?: 1
        return try {
            val response = service.getPopularMovies(page = page)
            val movies = response.results
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        }catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}