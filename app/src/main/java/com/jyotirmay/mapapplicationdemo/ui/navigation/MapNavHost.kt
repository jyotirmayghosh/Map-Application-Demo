package com.jyotirmay.mapapplicationdemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jyotirmay.mapapplicationdemo.domain.model.PointKey
import com.jyotirmay.mapapplicationdemo.ui.booking.BookingSummaryScreen
import com.jyotirmay.mapapplicationdemo.ui.detail.LocationDetailScreen
import com.jyotirmay.mapapplicationdemo.ui.history.UsageHistoryScreen
import com.jyotirmay.mapapplicationdemo.ui.history.UsageHistoryViewModel
import com.jyotirmay.mapapplicationdemo.ui.map.MapScreen
import com.jyotirmay.mapapplicationdemo.ui.map.MapViewModel

@Composable
fun MapNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.MAP_GRAPH,
        modifier = modifier,
    ) {
        navigation(
            startDestination = NavRoutes.MAP,
            route = NavRoutes.MAP_GRAPH,
        ) {
            composable(NavRoutes.MAP) {
                val parentEntry = navController.getBackStackEntry(NavRoutes.MAP_GRAPH)
                val viewModel: MapViewModel = hiltViewModel(parentEntry)
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                LaunchedEffect(uiState.bookingResult?.id) {
                    uiState.bookingResult?.let {
                        if (navController.currentBackStackEntry?.destination?.route == NavRoutes.MAP) {
                            navController.navigate(NavRoutes.BOOK_SUMMARY)
                        }
                    }
                }

                MapScreen(
                    uiState = uiState,
                    onLocationPermissionGranted = viewModel::onLocationPermissionGranted,
                    onMapCenterChanged = viewModel::onMapCenterChanged,
                    onVButtonClick = viewModel::onVButtonClick,
                    onLabelClick = { pointKey ->
                        navController.navigate(NavRoutes.detailRoute(pointKey.name))
                    },
                    onErrorDismissed = viewModel::clearError,
                    onRecenterToCurrentLocation = viewModel::onRecenterToCurrentLocation,
                    onRecenterHandled = viewModel::onRecenterHandled,
                    onOpenHistory = { navController.navigate(NavRoutes.USAGE_HISTORY) },
                )
            }

            composable(
                route = NavRoutes.DETAIL,
                arguments = listOf(
                    navArgument("pointKey") { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val parentEntry = navController.getBackStackEntry(NavRoutes.MAP_GRAPH)
                val viewModel: MapViewModel = hiltViewModel(parentEntry)
                val pointKeyName = backStackEntry.arguments?.getString("pointKey") ?: return@composable
                val pointKey = PointKey.valueOf(pointKeyName)
                val locationPoint = viewModel.getPointForKey(pointKey)

                LocationDetailScreen(
                    pointKey = pointKey,
                    locationPoint = locationPoint,
                    onConfirm = { nickname ->
                        viewModel.onNicknameConfirmed(pointKey, nickname)
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() },
                )
            }

            composable(NavRoutes.BOOK_SUMMARY) {
                val parentEntry = navController.getBackStackEntry(NavRoutes.MAP_GRAPH)
                val viewModel: MapViewModel = hiltViewModel(parentEntry)
                val bookingResult = viewModel.getBookingResult()

                if (bookingResult == null) {
                    LaunchedEffect(Unit) {
                        navController.popBackStack()
                    }
                    return@composable
                }

                BookingSummaryScreen(
                    bookingResult = bookingResult,
                    onContinue = { navController.navigate(NavRoutes.USAGE_HISTORY) },
                    onBack = {
                        viewModel.resetBooking()
                        navController.popBackStack(NavRoutes.MAP, inclusive = false)
                    },
                )
            }

            composable(NavRoutes.USAGE_HISTORY) {
                val viewModel: UsageHistoryViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                UsageHistoryScreen(
                    uiState = uiState,
                    onBack = { navController.popBackStack() },
                    onRetry = viewModel::loadHistory,
                )
            }
        }
    }
}
