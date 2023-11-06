package com.mkrdeveloper.lazyrowonclickexamplejetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mkrdeveloper.lazyrowonclickexamplejetpack.screens.DetailScreen
import com.mkrdeveloper.lazyrowonclickexamplejetpack.screens.MainScreen
import com.mkrdeveloper.lazyrowonclickexamplejetpack.ui.theme.LazyRowOnClickExampleJetpackTheme
import kotlin.math.min


class MainActivity : ComponentActivity() {
    private val pageSize = 20 // Количество элементов для подгрузки

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialImageList = List(1000) { R.drawable.p1 }
        val initialNamesList = List(1000) { "Курс $it" }
        val initialIngredientsList = List(1000) { "Описание курса для элемента $it" }

        fun getNextPageImages(currentSize: Int): List<Int> {
            val nextPageImages = List(pageSize) { R.drawable.p1 }
            return nextPageImages
        }

        fun getNextPageNames(currentSize: Int): List<String> {
            val nextPageNames = List(pageSize) { "Новый курс" }
            return nextPageNames
        }


        fun getNextPageIngredients(currentSize: Int): List<String> {
            val nextPageIngredients = List(pageSize) { "Новые описания" }
            return nextPageIngredients
        }

        setContent {
            val (imageList, setImageList) = remember { mutableStateOf(initialImageList) }
            val (namesList, setNamesList) = remember { mutableStateOf(initialNamesList) }
            val (ingredientsList, setIngredientsList) = remember { mutableStateOf(initialIngredientsList) }
            val (isLoading, setIsLoading) = remember { mutableStateOf(false) }

            val scrollState = rememberLazyListState()

            LazyRowOnClickExampleJetpackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "MainScreen") {
                        composable(route = "MainScreen") {
                            MainScreen(
                                imageList.subList(0, imageList.size),
                                namesList.subList(0, imageList.size),
                                ingredientsList.subList(0, imageList.size),
                                navController
                            )

                            // Проверяем, если мы приближаемся к концу списка, то подгружаем новые данные
                            LaunchedEffect(isLoading) {
                                if (!isLoading) {
                                    val totalItems = imageList.size
                                    val currentFirstVisibleItem = scrollState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0
                                    val currentLastVisibleItem = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                                    if (currentLastVisibleItem >= totalItems - 5) {
                                        setIsLoading(true)

                                        val newImages = getNextPageImages(imageList.size)
                                        val newNames = getNextPageNames(namesList.size)
                                        val newIngredients = getNextPageIngredients(ingredientsList.size)

                                        setImageList(imageList + newImages)
                                        setNamesList(namesList + newNames)
                                        setIngredientsList(ingredientsList + newIngredients)

                                        setIsLoading(false)
                                    }
                                }
                            }

                            if (isLoading) {
                                LinearProgressIndicator(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        composable(route = "DetailScreen/{index}",
                            arguments = listOf(
                                navArgument(name = "index") {
                                    type = NavType.IntType
                                }
                            )
                        ) { index ->
                            DetailScreen(
                                photos = imageList,
                                names = namesList,
                                ingredients = ingredientsList,
                                itemIndex = index.arguments?.getInt("index")
                            )
                        }
                    }
                }
            }
        }
    }
}




