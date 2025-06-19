package edu.ucne.joserivera_ap2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.joserivera_ap2_p2.presentation.navigation.AppNavigation
import edu.ucne.joserivera_ap2_p2.presentation.respository.RepositoryViewModel
import edu.ucne.joserivera_ap2_p2.ui.theme.JoseRivera_AP2_P2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JoseRivera_AP2_P2Theme {
                val navController = rememberNavController()
                val viewModel: RepositoryViewModel = hiltViewModel()

                AppNavigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}

