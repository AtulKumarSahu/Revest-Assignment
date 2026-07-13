package com.revest.assignment.domain.usecase

import com.revest.assignment.domain.model.Product
import com.revest.assignment.domain.repository.ProductRepository

class GetProductDetailUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(id: Int): Result<Product> {
        return repository.getProductById(id)
    }
}
