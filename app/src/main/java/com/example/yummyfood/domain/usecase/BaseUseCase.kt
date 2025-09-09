// path: app/src/main/java/com/example/yummyfood/domain/usecase/BaseUseCase.kt
package com.example.yummyfood.domain.usecase

import com.example.yummyfood.data.common.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Lớp UseCase cơ sở.
 * P: Kiểu dữ liệu của tham số đầu vào.
 * R: Kiểu dữ liệu của kết quả trả về.
 */
abstract class BaseUseCase<in P, out R : Any> {

    // Hàm thực thi chính, trả về một Flow của Result.
    operator fun invoke(params: P): Flow<Result<R>> = flow {
        // Phát ra trạng thái Loading trước khi thực hiện công việc.
        emit(Result.Loading)
        // Gọi hàm executeImpl để thực hiện logic chính.
        val result = executeImpl(params)
        emit(Result.Success(result))
    }.catch { e ->
        // Bắt lỗi và phát ra trạng thái Error.
        emit(Result.Error(e.message ?: "Unknown Error", e as? Exception))
    }

    // Các lớp con phải implement logic nghiệp vụ cụ thể ở đây.
    protected abstract suspend fun executeImpl(params: P): R
}