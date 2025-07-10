package edu.ucne.joserivera_ap2_p2.presentation.respository

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto

@Composable
fun RepositoryListScreen(
    username: String = "enelramon",
    viewModel: RepositoryViewModel = hiltViewModel(),
    onRepositorySelected: (RepositoryDto) -> Unit,
    onDrawer: () -> Unit
)
{
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(username) {
        viewModel.fetchRepositories(username)
    }


    RepositoryListBodyScreen(
        uiState = uiState,
        username = username,
        onRepositorySelected = onRepositorySelected,
        onDrawer = onDrawer,
        onRefresh = { viewModel.fetchRepositories(username) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryListBodyScreen(
    uiState: RepositoryUiState,
    username: String,
    onRepositorySelected: (RepositoryDto) -> Unit,
    onDrawer: () -> Unit,
    onRefresh: () -> Unit,
) {
    var query by remember { mutableStateOf("") }

    val filteredRepositories = uiState.repositories.filter { repo ->
        query.isBlank() || repo.name.contains(query, ignoreCase = true)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                title = { Text("Listas De Repository", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Abrir menú", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF0D47A1)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onRefresh,
                containerColor = Color(0xFFCCC2DC)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Refrescar")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0D47A1))
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Barra de búsqueda
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar repositorio") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color(0xFF1565C0),
                    unfocusedContainerColor = Color(0xFF1565C0),
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

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

            LazyColumn(modifier = Modifier.fillMaxSize().background(Color(0xFF0D47A1))) {
                items(filteredRepositories) { repo ->
                    RepositoryRow(repo) {
                        onRepositorySelected(repo)
                    }
                }
            }
        }
    }
}


@Composable
private fun RepositoryRow(
    item: RepositoryDto,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = "Nombre: ${item.name}", color = Color.White)
        Text(text = "Descripción: ${item.description ?: "Sin descripción"}", color = Color.White)
        Text(text = "URL: ${item.htmlUrl}", color = Color.White)
    }
    Divider(color = Color.White.copy(alpha = 0.3f))
}

