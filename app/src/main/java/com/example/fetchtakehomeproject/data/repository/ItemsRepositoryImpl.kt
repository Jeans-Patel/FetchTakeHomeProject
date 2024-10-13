package com.example.fetchtakehomeproject.data.repository

import com.example.fetchtakehomeproject.data.datasource.RemoteItemDataSource
import com.example.fetchtakehomeproject.data.model.DataItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemsRepositoryImpl @Inject constructor(
    private val dataSource: RemoteItemDataSource
) : ItemsRepository {
    override suspend fun getAllItems(): List<DataItem> {
        val response = dataSource.getRemoteItems()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }
}