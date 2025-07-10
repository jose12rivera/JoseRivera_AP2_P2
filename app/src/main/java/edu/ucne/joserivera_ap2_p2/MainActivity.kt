package edu.ucne.joserivera_ap2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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

                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        navController = navController,
                        repositoryViewModel = viewModel
                    )
                }
            }
        }
    }
}
