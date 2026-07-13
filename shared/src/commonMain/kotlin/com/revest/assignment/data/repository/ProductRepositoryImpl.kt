package com.revest.assignment.data.repository

import com.revest.assignment.data.local.ProductLocalDataSource
import com.revest.assignment.data.remote.Client
import com.revest.assignment.domain.model.FilterItem
import com.revest.assignment.domain.model.Product
import com.revest.assignment.domain.model.ProductRes
import com.revest.assignment.domain.repository.ProductRepository
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProductRepositoryImpl(
    private val localDataSource: ProductLocalDataSource
) : ProductRepository {
    override suspend fun getProducts(): Result<ProductRes> {
        return runCatching {
            val response = Client.client.get("products")
            val productRes = response.body<ProductRes>()
            runCatching { localDataSource.insertProducts(productRes.products) }
            productRes
        }.recoverCatching {
            val localProducts = localDataSource.getAllProducts()
            if (localProducts.isNotEmpty()) {
                ProductRes(
                    products = localProducts,
                    total = localProducts.size,
                    skip = 0,
                    limit = localProducts.size
                )
            } else {
                throw it
            }
        }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return runCatching {
            val response = Client.client.get("products/$id")
            val product = response.body<Product>()
            runCatching { localDataSource.insertProducts(listOf(product)) }
            product
        }.recoverCatching {
            localDataSource.getProductById(id) ?: throw it
        }
    }

    override suspend fun searchProducts(query: String): Result<ProductRes> {
        return runCatching {
            val response = Client.client.get("products/search") {
                url {
                    parameters.append("q", query)
                }
            }
            val productRes = response.body<ProductRes>()
            runCatching { localDataSource.insertProducts(productRes.products) }
            productRes
        }.recoverCatching {
            val localProducts = localDataSource.searchProducts(query)
            if (localProducts.isNotEmpty()) {
                ProductRes(
                    products = localProducts,
                    total = localProducts.size,
                    skip = 0,
                    limit = localProducts.size
                )
            } else {
                throw it
            }
        }
    }

    override suspend fun getProductCategory(): Result<List<FilterItem>> {
        return runCatching {
            val remoteCategories = Client.client.get("products/categories").body<List<FilterItem>>()
            runCatching { localDataSource.insertCategories(remoteCategories) }
            remoteCategories
        }.recoverCatching {
            val localCategories = localDataSource.getAllCategories()
            if (localCategories.isNotEmpty()) {
                localCategories
            } else {
                throw it
            }
        }
    }

    override suspend fun getProductsByCategory(category: String): Result<ProductRes> {
        return runCatching {
            val response = Client.client.get("products/category/$category")
            val productRes = response.body<ProductRes>()
            runCatching { localDataSource.insertProducts(productRes.products) }
            productRes
        }.recoverCatching {
            val localProducts = localDataSource.getProductsByCategory(category)
            if (localProducts.isNotEmpty()) {
                ProductRes(
                    products = localProducts,
                    total = localProducts.size,
                    skip = 0,
                    limit = localProducts.size
                )
            } else {
                throw it
            }
        }
    }

}
