package edu.ucne.joserivera_ap2_p2.presentation.respository

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryScreen(repository: RepositoryDto) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(repository.name) })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text(text = "Descripción:", style = MaterialTheme.typography.labelLarge)
            Text(text = repository.description ?: "Sin descripción")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repository.htmlUrl))
                context.startActivity(intent)
            }) {
                Text("Ver en GitHub")
            }
        }
    }
}