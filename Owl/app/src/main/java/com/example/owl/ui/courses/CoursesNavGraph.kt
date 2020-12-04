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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.owl.model.courses
import com.example.owl.model.topics

@Composable
fun CoursesNavGraph(
    modifier: Modifier = Modifier,
    onCourseSelected: (Long) -> Unit,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = CourseTabs.FEATURED.route
    ) {
        composable(CourseTabs.FEATURED.route) {
            FeaturedCourses(courses, onCourseSelected, modifier)
        }
        composable(CourseTabs.MY_COURSES.route) {
            MyCourses(courses, onCourseSelected, modifier)
        }
        composable(CourseTabs.SEARCH.route) {
            SearchCourses(topics, modifier)
        }
    }
}
