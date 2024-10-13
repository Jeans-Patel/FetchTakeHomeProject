package com.example.fetchtakehomeproject.ui.screens

import com.example.fetchtakehomeproject.domain.GetAllSortedItemsUseCase
import com.example.fetchtakehomeproject.domain.model.DomainItem
import io.kotest.core.coroutines.backgroundScope
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class MainScreenViewModelTest : BehaviorSpec({
    val useCase = mockk<GetAllSortedItemsUseCase>()
    val testData = listOf(DomainItem(1, 1, "item 1"), DomainItem(2, 2, "item 2"))
    val dispatcher = UnconfinedTestDispatcher()

    coroutineTestScope = true

    Given("view model is initiated") {
        Dispatchers.setMain(dispatcher)

        val viewModel = MainScreenViewModel(useCase)

        When("data is being retrieved") {

            Then("ui state should be loading") {
                viewModel.uiState.value.isLoading.shouldBeTrue()
            }

            Then("ui state should have no errors") {
                viewModel.uiState.value.isError.shouldBeFalse()
            }
        }

        When("data is retrieved and no errors") {
            coEvery { useCase() } returns testData

            backgroundScope.launch(dispatcher) { viewModel.uiState.collect() }

            Then("data is updated") {
                viewModel.uiState.value.data shouldBe testData
            }

            Then("ui state should not be loading") {
                viewModel.uiState.value.isLoading.shouldBeFalse()
            }

            Then("ui state should have no errors") {
                viewModel.uiState.value.isError.shouldBeFalse()
            }
        }

        When("data is retrieved and is empty") {
            coEvery { useCase() } returns emptyList()

            backgroundScope.launch(dispatcher) { viewModel.uiState.collect() }

            Then("data is updated") {
                viewModel.uiState.value.data shouldBe emptyList()
            }

            Then("ui state should not be loading") {
                viewModel.uiState.value.isLoading.shouldBeFalse()
            }

            Then("ui state should have no errors") {
                viewModel.uiState.value.isError.shouldBeTrue()
            }
        }

        Dispatchers.resetMain()
    }
})
