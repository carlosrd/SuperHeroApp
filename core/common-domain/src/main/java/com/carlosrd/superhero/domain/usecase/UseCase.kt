package com.carlosrd.superhero.domain.usecase

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class UseCase<T, R>(
    private val coroutineDispatcher : CoroutineDispatcher = Dispatchers.IO
)  {

    abstract suspend fun run(params: T): R

    fun execute(input: T,
                scope: CoroutineScope,
                onResult: suspend (R) -> Unit = {} ) {

        scope.launch(Dispatchers.Main){

            val result = withContext(coroutineDispatcher) {
                run(input)
            }

            onResult(result)

        }

    }

}

fun <R> UseCase<Unit, R>.execute(scope: CoroutineScope,
                                 onResult: suspend (R) -> Unit = {}){
    execute(Unit, scope, onResult)
}