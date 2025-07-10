package edu.ucne.joserivera_ap2_p2.presentation.contribuidor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import edu.ucne.joserivera_ap2_p2.data.remote.dto.ContribuidoreDto
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContribuidorScreen(
    owner: String,
    repoName: String,
    onBack: () -> Unit,
    viewModel: ContribuidorViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(owner, repoName) {
        viewModel.getContributors(owner, repoName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Contribuidores de $repoName",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D47A1)
                )
            )
        },
        containerColor = Color(0xFF0D47A1)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0D47A1))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp, vertical = 18.dp)
            ) {
                when {
                    state.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }
                    state.errorMessage != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.errorMessage ?: "",
                                color = Color.Red,
                                fontSize = 18.sp
                            )
                        }
                    }
                    state.contributors.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay contribuidores.",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.contributors) { contributor ->
                                ContributorRow(
                                    contributor = contributor,
                                    onImageClick = { imageUrl ->
                                        selectedImageUrl = imageUrl
                                    }
                                )
                            }
                        }
                    }
                }
            }

            if (selectedImageUrl != null) {
                Dialog(onDismissRequest = { selectedImageUrl = null }) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.85f))
                            .clickable { selectedImageUrl = null },
                        contentAlignment = Alignment.Center
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
fun ContributorRow(
    contributor: ContribuidoreDto,
    onImageClick: (String) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contributor.login,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    color = Color(0xFF2E7D32)
                )
                Text(
                    text = "Contribuciones: ${contributor.contributions}",
                    fontSize = 15.sp,
                    color = Color(0xFF66BB6A)
                )
            }
        }
    }
}
