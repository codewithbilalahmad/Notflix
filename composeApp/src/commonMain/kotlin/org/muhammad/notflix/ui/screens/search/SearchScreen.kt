package org.muhammad.notflix.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.logo
import notflix.composeapp.generated.resources.title_search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.muhammad.notflix.domain.util.Result
import org.muhammad.notflix.ui.components.ErrorScreen
import org.muhammad.notflix.ui.components.LoadingSection
import org.muhammad.notflix.ui.components.MovieCardPortrait
import org.muhammad.notflix.ui.navigation.NavigationItem
import org.muhammad.notflix.ui.viewModel.MovieViewModel

@Composable
fun SearchScreen(
    navHostController: NavHostController,
    viewModel: MovieViewModel,
    isCompact: Boolean,
) {
    val searchMoviesState by viewModel.searchMovies.collectAsState()
    var searchQuery by remember { mutableStateOf("Programming") }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        SearchBar(query = searchQuery, onValueChange = { query ->
            searchQuery = query
        }, viewModel = viewModel)
    }) { innerPadding ->
        Column(Modifier.fillMaxSize().padding(innerPadding)) {
            Spacer(Modifier.height(4.dp))
            HorizontalDivider()
            Spacer(Modifier.height(4.dp))
            when (searchMoviesState) {
                is Result.Error -> {
                    ErrorScreen(modifier = Modifier.fillMaxSize(), onRetry = {
                        viewModel.getSearchMovies(searchQuery)
                    })
                }

                Result.Loading -> {
                    LoadingSection(modifier = Modifier.fillMaxSize())
                }

                is Result.Success -> {
                    val response =
                        (searchMoviesState as? Result.Success)?.response?.results?.toMutableList()
                            ?: emptyList()
                    val columns =
                        if (isCompact) GridCells.Fixed(2) else GridCells.Adaptive(minSize = 150.dp)
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        columns = columns,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        itemsIndexed(response) { index, item ->
                            MovieCardPortrait(
                                modifier = Modifier,
                                movie = item,
                                onItemClick = { movieId ->
                                    navHostController.navigate("${NavigationItem.Detail.route}/$movieId")
                                })
                            if (index == response.lastIndex) {
                                LaunchedEffect(Unit) {
                                    viewModel.loadMoreSearchMovies(searchQuery)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onValueChange: (String) -> Unit, viewModel: MovieViewModel,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = query,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        leadingIcon = {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(30.dp).clip(
                    CircleShape
                ),
            )
        },
        placeholder = {
            Text(
                text = stringResource(Res.string.title_search),
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 18.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    if (query.isNotEmpty()) viewModel.getSearchMovies(query)
                }) {
                    Icon(imageVector = Icons.Default.Search , contentDescription = null)
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ), keyboardActions = KeyboardActions(onSearch = {
            viewModel.getSearchMovies(query)
        }), maxLines = 1, singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}