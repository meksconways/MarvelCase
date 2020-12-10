package com.meksconway.marvelcase.di

import com.meksconway.marvelcase.data.repository.CharacterDetailRepository
import com.meksconway.marvelcase.data.repository.CharacterDetailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCharacterDetailRepository(
        repository: CharacterDetailRepositoryImpl
    ): CharacterDetailRepository

}