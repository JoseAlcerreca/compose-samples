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

package com.example.owl.ui.courses

import android.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.owl.model.courses
import com.example.owl.model.topics
import com.example.owl.ui.MainDestinations.COURSE_DETAIL_ID_KEY

/**
 * Destinations used in the ([OwlApp]).
 */
object CoursesDestinations {
    const val FEATURED_ROUTE = "courses/featured"
    const val MY_COURSES_ROUTE = "courses/my"
    const val SEARCH_COURSES_ROUTE = "courses/search"
}

@Composable
fun CoursesNavGraph(
    modifier: Modifier = Modifier,
    onCourseSelected: (Long) -> Unit,
    navController: NavHostController
) {

    navigation(
        navController = navController,
        startDestination = CoursesDestinations.FEATURED_ROUTE
    ) {
        composable(CoursesDestinations.FEATURED_ROUTE) {
            FeaturedCourses(courses, onCourseSelected, modifier)
        }
        composable(CoursesDestinations.MY_COURSES_ROUTE) {
            MyCourses(courses, onCourseSelected, modifier)
        }
        composable(CoursesDestinations.SEARCH_COURSES_ROUTE) {
            SearchCourses(topics, modifier)
        }
    }
}