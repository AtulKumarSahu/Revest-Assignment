package com.revest.assignment.domain.usecase

import com.revest.assignment.domain.model.ProductRes
import com.revest.assignment.domain.repository.ProductRepository

class GetProductsUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(): Result<ProductRes> {
        return repository.getProducts()
    }
}
