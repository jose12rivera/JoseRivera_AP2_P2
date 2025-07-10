package edu.ucne.joserivera_ap2_p2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.joserivera_ap2_p2.data.remote.dto.RepositoryDto
import edu.ucne.joserivera_ap2_p2.presentation.HomeScreen
import edu.ucne.joserivera_ap2_p2.presentation.contribuidor.ContribuidorScreen
import edu.ucne.joserivera_ap2_p2.presentation.respository.RepositoryListScreen
import edu.ucne.joserivera_ap2_p2.presentation.respository.RepositoryScreen
import edu.ucne.joserivera_ap2_p2.presentation.respository.RepositoryViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    repositoryViewModel: RepositoryViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen
    ) {
        // Pantalla de inicio
        composable<Screen.HomeScreen> {
            HomeScreen(
                goToRepository = {
                    navController.navigate(
                        Screen.RepositoryList(username = "enelramon") // <-- cambiado aquí
                    )
                }
            )
        }

        // Pantalla de lista de repositorios
        composable<Screen.RepositoryList> {
            val args = it.toRoute<Screen.RepositoryList>()
            RepositoryListScreen(
                username = args.username,
                viewModel = repositoryViewModel,
                onRepositorySelected = { repo ->
                    val urlParts = repo.htmlUrl?.split("/") ?: listOf()
                    if (urlParts.size >= 5) {
                        val owner = urlParts[3]
                        val repoName = urlParts[4]
                        navController.navigate(
                            Screen.ContributorList(owner, repoName)
                        )
                    }
                },
                onDrawer = { }
            )
        }

        // Pantalla de detalle de repositorio
        composable<Screen.ViewRepository> {
            val args = it.toRoute<Screen.ViewRepository>()
            RepositoryScreen(
                repository = RepositoryDto(
                    name = args.name,
                    description = args.desc,
                    htmlUrl = args.url
                ),
                navController = navController,
            )
        }

        // Pantalla de lista de contribuidores
        composable<Screen.ContributorList> {
            val args = it.toRoute<Screen.ContributorList>()
            ContribuidorScreen(
                owner = args.owner,
                repoName = args.repoName,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
