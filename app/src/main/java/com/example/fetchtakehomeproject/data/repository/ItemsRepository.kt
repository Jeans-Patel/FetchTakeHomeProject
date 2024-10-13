package com.example.fetchtakehomeproject.data.repository

import com.example.fetchtakehomeproject.data.model.DataItem

interface ItemsRepository {
    suspend fun getAllItems(): List<DataItem>
}