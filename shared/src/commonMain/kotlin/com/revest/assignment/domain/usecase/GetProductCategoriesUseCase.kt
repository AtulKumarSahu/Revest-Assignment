package com.revest.assignment.domain.usecase

import com.revest.assignment.domain.model.FilterItem
import com.revest.assignment.domain.repository.ProductRepository

class GetProductCategoriesUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(): Result<List<FilterItem>> {
        return repository.getProductCategory()
    }
}
