// edu.ucne.joserivera_ap2_p2.presentation.navigation.AppNavigation.kt
package edu.ucne.joserivera_ap2_p2.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.joserivera_ap2_p2.presentation.HomeScreen
import edu.ucne.joserivera_ap2_p2.presentation.respository.RepositoryListScreen
import edu.ucne.joserivera_ap2_p2.presentation.respository.RepositoryScreen
import edu.ucne.joserivera_ap2_p2.presentation.respository.RepositoryViewModel

@Composable
fun AppNavigation(navController: NavHostController, viewModel: RepositoryViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(goToRepository = {
                navController.navigate("repositoryList")
            })
        }

    }
}

