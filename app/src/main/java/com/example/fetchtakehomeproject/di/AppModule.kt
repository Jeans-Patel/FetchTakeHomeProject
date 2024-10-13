package com.example.fetchtakehomeproject.di

import com.example.fetchtakehomeproject.data.datasource.BASE_URL
import com.example.fetchtakehomeproject.data.datasource.RemoteItemDataSource
import com.example.fetchtakehomeproject.data.repository.ItemsRepository
import com.example.fetchtakehomeproject.data.repository.ItemsRepositoryImpl
import com.example.fetchtakehomeproject.domain.GetAllSortedItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides @Singleton
    fun provideItemRemoteDataSource(): RemoteItemDataSource = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RemoteItemDataSource::class.java)

    @Provides @Singleton
    fun provideItemRepository(dataSource: RemoteItemDataSource): ItemsRepository = ItemsRepositoryImpl(dataSource)

    @Provides @Singleton
    fun provideUseCase(repository: ItemsRepository): GetAllSortedItemsUseCase = GetAllSortedItemsUseCase(repository)
}