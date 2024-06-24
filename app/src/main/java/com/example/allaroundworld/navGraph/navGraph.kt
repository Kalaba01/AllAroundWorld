package com.example.allaroundworld.navGraph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.allaroundworld.screen.HomeApp
import com.example.allaroundworld.screen.NewsDetailScreen
import com.example.allaroundworld.screen.NewsViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val viewModel: NewsViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeApp(viewModel = viewModel, navHostController = navController)
        }
        composable("newsDetail/{article}") { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("article")
            NewsDetailScreen(articleJson!!, navController)
        }
    }
}
