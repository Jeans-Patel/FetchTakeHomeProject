package com.example.fetchtakehomeproject.domain.model

import com.example.fetchtakehomeproject.data.model.DataItem

data class DomainItem(
    val id: Int,
    val listId: Int,
    val name: String
)

fun DataItem.toDomainItem() = DomainItem(this.id, this.listId, this.name.orEmpty())