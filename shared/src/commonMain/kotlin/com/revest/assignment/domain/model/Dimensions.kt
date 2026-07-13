package com.revest.assignment.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Dimensions(
    val depth: Double,
    val height: Double,
    val width: Double
)
