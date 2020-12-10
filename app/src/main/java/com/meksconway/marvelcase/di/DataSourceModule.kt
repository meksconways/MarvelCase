package com.meksconway.marvelcase.di

import com.meksconway.marvelcase.data.datasource.CharacterDetailDataSource
import com.meksconway.marvelcase.data.datasource.CharacterDetailDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindCharacterDetailDataSource(
        source: CharacterDetailDataSourceImpl
    ): CharacterDetailDataSource


}