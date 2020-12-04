/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.owl.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.owl.ui.MainDestinations.COURSE_DETAIL_ID_KEY
import com.example.owl.ui.course.CourseDetails
import com.example.owl.ui.courses.Courses
import com.example.owl.ui.onboarding.Onboarding
import com.example.owl.ui.utils.backHandler

/**
 * Destinations used in the ([OwlApp]).
 */
object MainDestinations {
    const val ONBOARDING_ROUTE = "onboarding"
    const val COURSES_ROUTE = "courses"
    const val COURSE_DETAIL_ROUTE = "course"
    const val COURSE_DETAIL_ID_KEY = "courseId"
}

@Composable
fun NavGraph(
    finishActivity: () -> Unit,
    startDestination: String = MainDestinations.COURSES_ROUTE,
    showOnboardingInitially: Boolean = true
) {
    val navController = rememberNavController()

    // Onboarding could be read from shared preferences.
    var onboardingComplete by remember { mutableStateOf(!showOnboardingInitially) }

    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.ONBOARDING_ROUTE) {
            backHandler {
                finishActivity()
            }

            Onboarding(
                onOnboardingComplete = {
                    // Set the flag so that onboarding is not shown next time.
                    onboardingComplete = true
                    actions.onboardingComplete()
                }
            )
        }
        composable(MainDestinations.COURSES_ROUTE) {
            // Show onboarding if this is
            LaunchedEffect(onboardingComplete) {
                if (!onboardingComplete) {
                    navController.navigate(MainDestinations.ONBOARDING_ROUTE)
                }
            }
            Courses(onCourseSelected = actions.selectCourse)
        }
        composable(
            "${MainDestinations.COURSE_DETAIL_ROUTE}/{$COURSE_DETAIL_ID_KEY}",
            arguments = listOf(navArgument(COURSE_DETAIL_ID_KEY) { type = NavType.LongType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            CourseDetails(
                courseId = arguments.getLong(COURSE_DETAIL_ID_KEY),
                selectCourse = actions.selectCourse,
                upPress = actions.upPress
            )
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val onboardingComplete: () -> Unit = {
        navController.popBackStack(navController.graph.startDestination, false)
    }
    val selectCourse: (Long) -> Unit = { courseId: Long ->
        navController.navigate("${MainDestinations.COURSE_DETAIL_ROUTE}/$courseId")
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}
