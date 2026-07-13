package com.revest.assignment.data.local

import com.revest.assignment.db.AppDatabase
import com.revest.assignment.domain.model.Product
import com.revest.assignment.domain.model.Dimensions
import com.revest.assignment.domain.model.Meta
import com.revest.assignment.domain.model.Review
import com.revest.assignment.domain.model.FilterItem

class ProductLocalDataSource(database: AppDatabase) {
    private val queries = database.appDatabaseQueries

    fun insertProducts(products: List<Product>) {
        queries.transaction {
            products.forEach { product ->
                queries.insertProduct(
                    id = product.id.toLong(),
                    title = product.title,
                    price = product.price,
                    thumbnail = product.thumbnail,
                    category = product.category,
                    brand = product.brand,
                    description = product.description,
                    rating = product.rating,
                    discountPercentage = product.discountPercentage,
                    stock = product.stock.toLong()
                )
            }
        }
    }

    fun getAllProducts(): List<Product> {
        return queries.getAllProducts().executeAsList().map { entity ->
            mapEntityToProduct(entity)
        }
    }

    fun getProductById(id: Int): Product? {
        return queries.getProductById(id.toLong()).executeAsOneOrNull()?.let { entity ->
            mapEntityToProduct(entity)
        }
    }

    fun searchProducts(query: String): List<Product> {
        return queries.searchProducts(query, query).executeAsList().map { entity ->
            mapEntityToProduct(entity)
        }
    }

    fun getProductsByCategory(category: String): List<Product> {
        return queries.getProductsByCategory(category).executeAsList().map { entity ->
            mapEntityToProduct(entity)
        }
    }

    private fun mapEntityToProduct(entity: com.revest.assignment.db.ProductEntity): Product {
        return Product(
            id = entity.id.toInt(),
            title = entity.title,
            price = entity.price,
            thumbnail = entity.thumbnail,
            category = entity.category,
            brand = entity.brand,
            description = entity.description,
            rating = entity.rating,
            discountPercentage = entity.discountPercentage,
            stock = entity.stock.toInt(),
            availabilityStatus = "",
            dimensions = Dimensions(0.0, 0.0, 0.0),
            images = emptyList(),
            meta = Meta("", "", "", ""),
            minimumOrderQuantity = 0,
            returnPolicy = "",
            reviews = emptyList(),
            shippingInformation = "",
            sku = "",
            tags = emptyList(),
            warrantyInformation = "",
            weight = 0
        )
    }

    fun insertCategories(categories: List<FilterItem>) {
        queries.transaction {
            categories.forEach { category ->
                queries.insertCategory(
                    slug = category.slug,
                    name = category.name,
                    url = category.url
                )
            }
        }
    }

    fun getAllCategories(): List<FilterItem> {
        return queries.getAllCategories().executeAsList().map { entity ->
            FilterItem(
                name = entity.name,
                slug = entity.slug,
                url = entity.url
            )
        }
    }

    fun clearDatabase() {
        queries.clearProducts()
        queries.clearCategories()
    }
}
