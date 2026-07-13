package com.revest.assignment.domain.usecase

import com.revest.assignment.domain.model.ProductRes
import com.revest.assignment.domain.repository.ProductRepository

class SearchProductsUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(query: String): Result<ProductRes> {
        return repository.searchProducts(query)
    }
}
