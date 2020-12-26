package uz.jamshid.imdb.data.entities

data class MovieResponse(
    val page: Int,
    val results: List<Movie>
)