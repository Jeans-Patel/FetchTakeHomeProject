package com.example.fetchtakehomeproject.di


import com.example.fetchtakehomeproject.data.datasource.RemoteItemDataSource
import com.example.fetchtakehomeproject.data.model.DataItem
import com.example.fetchtakehomeproject.data.repository.ItemsRepository
import com.example.fetchtakehomeproject.data.repository.ItemsRepositoryImpl
import com.example.fetchtakehomeproject.domain.GetAllSortedItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.mockk.coEvery
import io.mockk.mockk
import retrofit2.Response
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TestAppModule {

    @Provides
    @Singleton
    fun testDataSource(): RemoteItemDataSource {
        val dataSource = mockk<RemoteItemDataSource>()

        coEvery { dataSource.getRemoteItems() } returns Response.success(
            listOf(
                DataItem(1, 1, "item 1"),
                DataItem(2, 2, "item 2"),
                DataItem(3, 3, "item 3"),
            )
        )

        return dataSource
    }

    @Provides
    @Singleton
    fun provideItemRepository(dataSource: RemoteItemDataSource): ItemsRepository =
        ItemsRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideUseCase(repository: ItemsRepository): GetAllSortedItemsUseCase =
        GetAllSortedItemsUseCase(repository)
}