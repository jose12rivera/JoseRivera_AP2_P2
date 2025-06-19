package edu.ucne.joserivera_ap2_p2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto
import edu.ucne.joserivera_ap2_p2.presentation.HomeScreen
import edu.ucne.joserivera_ap2_p2.presentation.Navigation.Screen
import edu.ucne.joserivera_ap2_p2.presentation.respository.RepositoryListScreen
import edu.ucne.joserivera_ap2_p2.presentation.respository.RepositoryScreen
import edu.ucne.joserivera_ap2_p2.presentation.respository.RepositoryViewModel

@Composable
fun AppNavigation(navController: NavHostController, viewModel: RepositoryViewModel) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen) {
        composable<Screen.HomeScreen> {
            HomeScreen(
                goToRepository = {
                    navController.navigate(Screen.RepositoryList("enelramon"))
                }
            )
        }

        composable<Screen.RepositoryList> {
            val args = it.toRoute<Screen.RepositoryList>()
            RepositoryListScreen(username = args.username, viewModel = viewModel, goToRepository = { repo ->
                    navController.navigate(
                        Screen.RepositoryDetail(
                            name = repo.name,
                            desc = repo.description,
                            url = repo.htmlUrl
                        )
                    )
                }, onDrawer = {
                    // puedes añadir lógica de Drawer si es necesario
                })
        }

        composable<Screen.RepositoryDetail> {
            val args = it.toRoute<Screen.RepositoryDetail>()
            RepositoryScreen(
                repository = RepositoryDto(
                    name = args.name,
                    description = args.desc,
                    htmlUrl = args.url
                ),
                navController = navController
            )
        }
    }
}
