package com.revest.assignment.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FilterItem(
    val name: String,
    val slug: String,
    val url: String
)