package edu.ucne.joserivera_ap2_p2.presentation.respository

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto


@Composable
fun RepositoryScreen(
    repository: RepositoryDto,
    navController: NavHostController,
    viewModel: RepositoryViewModel = hiltViewModel()
) {
    // Carga inicial con los datos recibidos
    LaunchedEffect(Unit) {
        viewModel.setName(repository.name)
        viewModel.setDescription(repository.description)
        viewModel.setHtmlUrl(repository.htmlUrl)
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    RepositoryBodyScreen(
        uiState = uiState.value,
        onNameChange = { viewModel.setName(it) },
        onDescriptionChange = { viewModel.setDescription(it) },
        onHtmlUrlChange = { viewModel.setHtmlUrl(it) },
        goBack = { navController.popBackStack() },
        openGitHub = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uiState.value.htmlUrl))
            LocalContext.current.startActivity(intent)
        },
        limpiarCampos = { viewModel.limpiarCampos() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryBodyScreen(
    uiState: RepositoryUiState,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String?) -> Unit,
    onHtmlUrlChange: (String) -> Unit,
    goBack: () -> Unit,
    openGitHub: @Composable () -> Unit,
    limpiarCampos: () -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF0D47A1), Color(0xFF1976D2))
                    )
                )
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Detalles del Repositorio",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = goBack) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Mostrar errores de validación
            uiState.inputError?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nombre") },
                value = uiState.name ?: "",
                onValueChange = onNameChange,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Descripción") },
                value = uiState.description ?: "",
                onValueChange = { onDescriptionChange(it) },
                singleLine = false
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text("URL de GitHub") },
                value = uiState.htmlUrl ?: "",
                onValueChange = onHtmlUrlChange,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = limpiarCampos
                ) {
                    Text("Nuevo")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Filled.Refresh, contentDescription = "Limpiar")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = openGitHub as () -> Unit
                ) {
                    Icon(Icons.Filled.Check, contentDescription = "Ir")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ver en GitHub")
                }
            }
        }
    }
}
