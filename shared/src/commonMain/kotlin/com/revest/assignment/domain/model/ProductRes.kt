package com.revest.assignment.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductRes(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)
