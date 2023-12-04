package com.ireneengels.myapplication.domain.base_usecase

import com.ireneengels.myapplication.common.convertToWrapper
import com.ireneengels.myapplication.common.safeLaunch
import com.ireneengels.myapplication.util.logError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

abstract class BaseUseCase<RESULT, PARAMS> : UseCase<RESULT, PARAMS> {

    fun execute(
        uiDispatcher: CoroutineScope,
        result: ResultCallbacks<RESULT>,
        params: PARAMS? = null
    ) {
        uiDispatcher.safeLaunch {

            withContext(Dispatchers.Main) {
                result.onLoading?.invoke(true)
                try {
                    val resultOfWork = remoteWork(params)
                    result.onSuccess?.invoke(resultOfWork)
                    result.onLoading?.invoke(false)
                } catch (e: Throwable) {
                    logError(e.toString())
                    when (e) {
                        is UnknownHostException -> result.onConnectionError?.invoke(e)
                        is HttpException -> result.onError?.invoke(e.convertToWrapper())
                        else -> result.onError?.invoke(e)
                    }
                    result.onLoading?.invoke(false)
                }
            }
        }
    }

}


class ResultCallbacks<T>(
    val onSuccess: ((T) -> Unit)? = null,
    val onLoading: ((Boolean) -> Unit)? = null,
    val onError: ((Throwable) -> Unit)? = null,
    val onConnectionError: ((Throwable) -> Unit)? = null
)

interface UseCase<RESULT, PARAMS> {
    suspend fun remoteWork(params: PARAMS?): RESULT
}