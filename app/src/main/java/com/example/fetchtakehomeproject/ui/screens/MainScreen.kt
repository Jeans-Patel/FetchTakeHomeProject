package com.example.fetchtakehomeproject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fetchtakehomeproject.domain.model.DomainItem
import com.example.fetchtakehomeproject.ui.theme.FetchTakeHomeProjectTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    MainScreen(isLoading = uiState.isLoading, data = uiState.data, isError = uiState.isError, modifier = modifier)
}

@Composable
private fun MainScreen(isLoading: Boolean, data: List<DomainItem>, isError: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Horizontal))
    ) {
        when {
            isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            isError -> Text(text = "Error loading data", textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data) { item ->
                    Item(item)
                }
            }
        }
//        if (isLoading) {
//            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//        } else {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(top = 8.dp),
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(data) { item ->
//                    Item(item)
//                }
//            }
//        }
    }
}

@Composable
private fun Item(data: DomainItem) {
    with(data) {
        Card {
            Column(Modifier.padding(4.dp)) {
                Text("Name: $name")
                Row {
                    Text("id: $id", modifier = Modifier.weight(1f))
                    Text("list id: $listId")
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMainScreen() {
    FetchTakeHomeProjectTheme {
        MainScreen(
            isLoading = false, data = listOf(
                DomainItem(1, 1, "name 1"),
                DomainItem(2, 1, "name 2"),
                DomainItem(3, 1, "name 3"),
                DomainItem(4, 1, "name 4"),
                DomainItem(1, 2, "name 1"),
            ),
             isError = true
        )
    }
}