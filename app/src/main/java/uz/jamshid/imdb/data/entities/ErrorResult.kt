package uz.jamshid.imdb.data.entities

data class ErrorResult(val errorCode: Int, val errorMessage: String){

    companion object {
        const val ERROR_CODE_INTERNET_CONNECTION = 10
        const val ERROR_CODE_UNKNOWN = -1
    }
}