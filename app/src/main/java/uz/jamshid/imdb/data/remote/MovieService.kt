package uz.jamshid.imdb.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import uz.jamshid.imdb.data.entities.MovieResponse

interface MovieService {

    @GET("popular")
    suspend fun getPopularMovies(@Query("api_key") key: String = "1e4bd8d111dee9d361d168d54f8b05dc",
                                 @Query("language") lang: String = "en-US",
                                 @Query("page") page: Int): MovieResponse
}