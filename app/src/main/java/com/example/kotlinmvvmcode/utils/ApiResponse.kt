package com.example.kotlinmvvmcode.utils



data class ApiResponse<out T>(val status : Status, val data : T?,val message : String?) {
    companion object{
        fun <T> success(data : T) : ApiResponse<T> =
            ApiResponse(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data : T?, message: String?):ApiResponse<T> =
            ApiResponse(status = Status.ERROR, data = data, message = message)

        fun <T> loading(data : T?) : ApiResponse<T> =
            ApiResponse(status = Status.LOADING, data, null)
    }
}

//sealed class ApiResponse<out T> {
//    open fun get(): T? = null
//}
//
//data class ApiResponseSuccess<T>(var status: Status = Status.SUCCESS, val data : T, val message : String?) : ApiResponse<T>() {
//    override fun get(): T = data
//}
//
//data class ApiResponseError<T>(var status: Status = Status.ERROR, val data : T, val message : String?) : ApiResponse<T>() {
//    override fun get(): T = data
//}
//
//data class ApiResponseLoading<T>(var status: Status = Status.SUCCESS, val data : T, val message : String?) : ApiResponse<T>() {
//    override fun get(): T = data
//}
