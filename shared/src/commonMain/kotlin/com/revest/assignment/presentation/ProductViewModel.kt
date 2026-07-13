package com.revest.assignment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revest.assignment.domain.model.FilterItem
import com.revest.assignment.domain.usecase.GetProductCategoriesUseCase
import com.revest.assignment.domain.usecase.GetProductDetailUseCase
import com.revest.assignment.domain.usecase.GetProductsByCategoryUseCase
import com.revest.assignment.domain.usecase.GetProductsUseCase
import com.revest.assignment.domain.usecase.SearchProductsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase,
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,

) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _categories = MutableStateFlow<List<FilterItem>>(emptyList())
    val categories: StateFlow<List<FilterItem>> = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    init {
        fetchProducts()
        getCategories()
    }





    fun filterByCategory(category: String?) {
        _selectedCategory.value = category
        _searchQuery.value = "" // Clear search when filtering by category
        if (category == null) {
            fetchProducts()
        } else {
            fetchProductsByCategory(category)
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            getProductsUseCase()
                .onSuccess { _uiState.value = ProductUiState.Success(it) }
                .onFailure { _uiState.value = ProductUiState.Error(it.message ?: "Unknown error") }
        }
    }

    private fun fetchProductsByCategory(category: String) {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            getProductsByCategoryUseCase(category)
                .onSuccess { _uiState.value = ProductUiState.Success(it) }
                .onFailure { _uiState.value = ProductUiState.Error(it.message ?: "Unknown error") }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun performSearch(query: String) {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            searchProductsUseCase(query)
                .onSuccess { _uiState.value = ProductUiState.Success(it) }
                .onFailure { _uiState.value = ProductUiState.Error(it.message ?: "Unknown error") }
        }
    }

    fun getProductDetail(id: Int) {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            getProductDetailUseCase(id)
                .onSuccess { _uiState.value = ProductUiState.DetailSuccess(it) }
                .onFailure { _uiState.value = ProductUiState.Error(it.message ?: "Unknown error") }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            getProductCategoriesUseCase()
                .onSuccess { _categories.value = it }
                .onFailure { /* Optionally handle failure */ }
        }
    }

    fun clearDetail() {
        if (_searchQuery.value.isBlank()) {
            fetchProducts()
        } else {
            _searchQuery.value = _searchQuery.value
        }
    }
}
