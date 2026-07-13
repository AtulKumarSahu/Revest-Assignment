package com.revest.assignment.di

import com.revest.assignment.data.local.ProductLocalDataSource
import com.revest.assignment.data.repository.ProductRepositoryImpl
import com.revest.assignment.db.AppDatabase
import com.revest.assignment.domain.repository.ProductRepository
import com.revest.assignment.domain.usecase.GetProductCategoriesUseCase
import com.revest.assignment.domain.usecase.GetProductDetailUseCase
import com.revest.assignment.domain.usecase.GetProductsByCategoryUseCase
import com.revest.assignment.domain.usecase.GetProductsUseCase
import com.revest.assignment.domain.usecase.SearchProductsUseCase
import com.revest.assignment.presentation.ProductViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {

    single { 
        val driver = get<com.revest.assignment.data.local.DatabaseDriverFactory>().createDriver()
        AppDatabase(driver)
    }
    
    singleOf(::ProductLocalDataSource)
    
    factoryOf(::GetProductsUseCase)
    factoryOf(::SearchProductsUseCase)
    factoryOf(::GetProductDetailUseCase)
    factoryOf(::GetProductCategoriesUseCase)
    factoryOf(::GetProductsByCategoryUseCase)
    viewModelOf(::ProductViewModel)
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class

}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule, platformModule)
    }
