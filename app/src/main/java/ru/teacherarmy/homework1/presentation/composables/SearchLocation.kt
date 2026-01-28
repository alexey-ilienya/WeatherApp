package ru.teacherarmy.homework1.presentation.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.teacherarmy.homework1.R
import ru.teacherarmy.homework1.presentation.mapper.toDomainModel
import ru.teacherarmy.homework1.presentation.navigation.NavScreen
import ru.teacherarmy.homework1.presentation.viewmodels.SearchCityViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocation(navController: NavHostController, viewModel: SearchCityViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchTopAppBar(navController = navController, viewModel = viewModel, scrollBehavior = scrollBehavior)
        },
    ) {
        if (!state.isLoading) {
            state.data?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Spacer(modifier = Modifier.height(60.dp))

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 16.dp, start = 30.dp)
                    ) {
                        items(it) {

                            Text(text = it.name ?: "",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clickable {
                                        viewModel.insertCity(it.toDomainModel())
                                        navController.popBackStack()
                                        navController.clearBackStack(NavScreen.Search.route)
                                    }
                            )
                        }
                    }
                }
            }
        }

        if (state.error != null) {
            Text(
                text = "Error: ${state.error}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                color = Color.Red
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(navController: NavHostController,
                    viewModel: SearchCityViewModel,
                    scrollBehavior: TopAppBarScrollBehavior
) {
    val searchText by viewModel.searchText.collectAsState()

    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")

            }
        },
        scrollBehavior = scrollBehavior,
        actions = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextField(
                    value = searchText,
                    onValueChange = {
                        viewModel.onSearchTextChange(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = {
                        Text(stringResource(R.string.search_hint))
                    },
                    singleLine = true,
                    maxLines = 1,
                )
                IconButton(
                    onClick = {
                        viewModel.onSearchTextChange("")
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        tint = Color.Gray
                    )
                }
            }
        }
    )
}
