package com.revest.assignment.presentation

import com.revest.assignment.domain.model.Product
import com.revest.assignment.domain.model.ProductRes

sealed class ProductUiState {
    data object Loading : ProductUiState()
    data class Success(val data: ProductRes) : ProductUiState()
    data class Error(val message: String) : ProductUiState()
    data class DetailSuccess(val product: Product) : ProductUiState()
}
