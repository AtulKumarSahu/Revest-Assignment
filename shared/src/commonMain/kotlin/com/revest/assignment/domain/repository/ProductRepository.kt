package com.revest.assignment.domain.repository

import com.revest.assignment.domain.model.FilterItem
import com.revest.assignment.domain.model.Product
import com.revest.assignment.domain.model.ProductRes

interface ProductRepository {
    suspend fun getProducts(): Result<ProductRes>
    suspend fun getProductById(id: Int): Result<Product>
    suspend fun searchProducts(query: String): Result<ProductRes>

    suspend fun getProductCategory(): Result<List<FilterItem>>
    suspend fun getProductsByCategory(category: String): Result<ProductRes>
}
