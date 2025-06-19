package edu.ucne.joserivera_ap2_p2.presentation.respository

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto

@Composable
fun RepositoryListScreen(
    username: String = "enelramon",
    viewModel: RepositoryViewModel = hiltViewModel(),
    goToRepository: (RepositoryDto) -> Unit,
    onDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(username) {
        viewModel.fetchRepositories(username)
    }

    RepositoryListBodyScreen(
        uiState = uiState,
        username = username,
        goToRepository = goToRepository,
        onDrawer = onDrawer,
        onRefresh = { viewModel.fetchRepositories(username) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryListBodyScreen(
    uiState: RepositoryUiState,
    username: String,
    goToRepository: (RepositoryDto) -> Unit,
    onDrawer: () -> Unit,
    onRefresh: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                title = { Text("Listas De Repository") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onRefresh) {
                Icon(Icons.Default.Refresh, contentDescription = "Refrescar")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            uiState.errorMessage?.let { error ->
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp)
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.repositories) { repo ->
                    RepositoryRow(repo, goToRepository)
                }
            }
        }
    }
}

@Composable
private fun RepositoryRow(
    item: RepositoryDto,
    goToRepository: (RepositoryDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { goToRepository(item) }
            .padding(16.dp)
    ) {
        Text(text = "Nombre: ${item.name}")
        Text(text = "Descripción: ${item.description ?: "Sin descripción"}")
        Text(text = "URL: ${item.htmlUrl}")
    }
    Divider()
}
