package com.revest.assignment.domain.usecase

import com.revest.assignment.domain.model.ProductRes
import com.revest.assignment.domain.repository.ProductRepository

class GetProductsByCategoryUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(category: String): Result<ProductRes> {
        return repository.getProductsByCategory(category)
    }
}
