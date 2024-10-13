package com.example.fetchtakehomeproject.domain

import com.example.fetchtakehomeproject.data.repository.ItemsRepository
import com.example.fetchtakehomeproject.domain.model.DomainItem
import com.example.fetchtakehomeproject.domain.model.toDomainItem
import javax.inject.Inject

class GetAllSortedItemsUseCase @Inject constructor(private val repository: ItemsRepository) {
    suspend operator fun invoke(): List<DomainItem> {
        return repository.getAllItems().filter { !it.name.isNullOrBlank() }.sortedWith(
            compareBy(
                { it.listId },
                {
                    /*
                     This is so "Item 8" is displayed before "Item 794" (this sorting behavior wasn't defined
                     in the requirements document but it makes sense logically to show "Item 8" before "Item 794")

                     This sorting could've also been done using id since all the names use the id number
                     but the requirements said to sort by name so I split the string and sorted using the integer in the name.
                      */
                    it.name?.split("\\s+".toRegex())?.get(1)?.toInt()
                }
            )
        ).map { it.toDomainItem() }
    }
}