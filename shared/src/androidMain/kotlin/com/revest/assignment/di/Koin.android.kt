package com.revest.assignment.di

import com.revest.assignment.data.local.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { DatabaseDriverFactory(get()) }
}
