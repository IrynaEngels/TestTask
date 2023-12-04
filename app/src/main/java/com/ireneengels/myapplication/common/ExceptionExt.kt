package com.ireneengels.myapplication.common

import retrofit2.HttpException

fun HttpException.convertToWrapper(): WrapperHttpException {
    return WrapperHttpException(
        responseCode = this.code(),
        errorMessage = this.message()
    )
}

data class WrapperHttpException(
    val responseCode: Int,
    val errorMessage: String? = null,
    val errorBodyMessage: String? = null
): RuntimeException()