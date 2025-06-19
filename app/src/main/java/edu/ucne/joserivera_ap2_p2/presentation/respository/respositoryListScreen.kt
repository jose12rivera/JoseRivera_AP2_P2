package edu.ucne.joserivera_ap2_p2.presentation.respository

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryListScreen(
    username: String,
    viewModel: RepositoryViewModel = hiltViewModel(),
    goToRepository: (RepositoryDto) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(username) {
        viewModel.fetchRepositories(username)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repos de $username") },
                actions = {
                    IconButton(onClick = { viewModel.fetchRepositories(username) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refrescar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            uiState.errorMessage?.let { Text("Error: $it", color = MaterialTheme.colorScheme.error) }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(uiState.repositories) { repo ->
                    RepositoryItem(repository = repo, onClick = { goToRepository(repo) })
                }
            }
        }
    }
}

@Composable
fun RepositoryItem(repository: RepositoryDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = repository.name, style = MaterialTheme.typography.titleMedium)
            Text(text = repository.description ?: "Sin descripción")
        }
    }
}