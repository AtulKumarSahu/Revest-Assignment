package com.revest.assignment.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val comment: String,
    val date: String,
    val rating: Double,
    val reviewerEmail: String,
    val reviewerName: String
)
