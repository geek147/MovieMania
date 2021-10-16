package com.envious.domain.usecase

import com.envious.domain.util.Result

abstract class BaseUseCase<ResultType : Any, in Params> {
    protected abstract suspend fun build(params: Params?): ResultType

    open suspend operator fun invoke(params: Params? = null): ResultType {
        return build(params)
    }
}

abstract class BaseCaseWrapper<SuccessType : Any, in Params> :
    BaseUseCase<Result<SuccessType>, Params>() {

    suspend fun execute(params: Params? = null): Result<SuccessType> {
        return try {
            build(params)
        } catch (error: Exception) {
            Result.Error(error)
        }
    }

    override suspend operator fun invoke(params: Params?): Result<SuccessType> {
        return execute(params)
    }
}
