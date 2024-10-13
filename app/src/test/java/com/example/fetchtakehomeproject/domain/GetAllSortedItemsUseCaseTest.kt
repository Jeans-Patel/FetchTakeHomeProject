package com.example.fetchtakehomeproject.domain

import com.example.fetchtakehomeproject.data.model.DataItem
import com.example.fetchtakehomeproject.data.repository.ItemsRepository
import com.example.fetchtakehomeproject.domain.model.DomainItem
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class GetAllSortedItemsUseCaseTest : BehaviorSpec({
    val repository = mockk<ItemsRepository>()

    val feature = GetAllSortedItemsUseCase(repository)

    Given("use case is invoked") {
        forAll(
            row(
                listOf(DataItem(1, 1, null), DataItem(2, 2, ""), DataItem(3, 3, "item 3")),
                listOf(DomainItem(3, 3, "item 3"))
            ),
            row(
                listOf(
                    DataItem(3, 3, "item 3"),
                    DataItem(23, 3, "item 23"),
                    DataItem(1, 1, "item 1")
                ),
                listOf(
                    DomainItem(1, 1, "item 1"),
                    DomainItem(3, 3, "item 3"),
                    DomainItem(23, 3, "item 23")
                )
            )
        ) {repositoryReturn, expectedResult ->
            coEvery { repository.getAllItems() } returns repositoryReturn

            val result = feature()

            Then("getAllItems is called") {
                coVerify { repository.getAllItems() }
            }

            Then("expected result is a sorted and filtered list of items") {
                result shouldBe expectedResult
            }
        }
    }
})
