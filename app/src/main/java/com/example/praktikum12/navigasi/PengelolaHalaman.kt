package com.example.praktikum12.navigasi

import UpdateScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.praktikum12.ui.view.DestinasiEntry
import com.example.praktikum12.ui.view.DestinasiHome
import com.example.praktikum12.ui.view.DetailScreen
import com.example.praktikum12.ui.view.EntryMhsScreen
import com.example.praktikum12.ui.view.HomeScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ){
        composable(route = DestinasiHome.route)
        {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim -> navController.navigate(DestinasiDetail.createRoute(nim)) }
            )
        }
        composable(DestinasiEntry.route)
        {
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){ inclusive = true }
                }
            })
        }
        composable(
            route = DestinasiDetail.route,
            arguments = listOf(navArgument("nim") { type = NavType.StringType })
        ) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: ""
            DetailScreen(
                nim = nim,
                navigateBack = { navController.popBackStack() }
            )
        }
        composable(
            route = DestinasiUpdate.route,
            arguments = listOf(navArgument("nim") { type = NavType.StringType })
        ) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: ""
            UpdateScreen(
                nim = nim,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}