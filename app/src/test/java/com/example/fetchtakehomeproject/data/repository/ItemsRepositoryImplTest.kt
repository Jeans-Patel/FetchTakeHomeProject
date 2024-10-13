package com.example.fetchtakehomeproject.data.repository

import com.example.fetchtakehomeproject.data.datasource.RemoteItemDataSource
import com.example.fetchtakehomeproject.data.model.DataItem
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import okhttp3.ResponseBody.Companion.toResponseBody

import retrofit2.Response

class ItemsRepositoryImplTest : BehaviorSpec({
    val dataSource = mockk<RemoteItemDataSource>()

    val feature = ItemsRepositoryImpl(dataSource)
    coEvery { dataSource.getRemoteItems() } returns Response.error(400, "".toResponseBody())

    val testData = listOf(
        DataItem(1, 1, "1"),
        DataItem(2, 2, "2"),
        DataItem(3, 3, "3"),
    )

    Given("getAllItems is called") {
        feature.getAllItems()

        Then("data source get remote items is called") {
            coVerify { dataSource.getRemoteItems() }
        }

        When("Data source returns failure") {
            coEvery { dataSource.getRemoteItems() } returns Response.error(400, "".toResponseBody())
            val data = feature.getAllItems()

            Then("empty list is returned") {
                data shouldBe emptyList()
            }
        }

        When("Data source returns success") {

            And("data is null") {
                coEvery { dataSource.getRemoteItems() } returns Response.success(null)
                val data = feature.getAllItems()

                Then("empty list is returned") {
                    data shouldBe emptyList()
                }
            }

            And("data is not null") {
                coEvery { dataSource.getRemoteItems() } returns Response.success(testData)
                val data = feature.getAllItems()

                Then("data should be returned") {
                    data shouldBe testData
                }
            }
        }
    }
})
