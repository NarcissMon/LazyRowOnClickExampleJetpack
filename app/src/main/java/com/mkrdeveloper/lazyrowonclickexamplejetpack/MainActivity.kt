package com.mkrdeveloper.lazyrowonclickexamplejetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
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

/*class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyRowOnClickExampleJetpackTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val imageId = List(1000) {
                        when (it % 6) {
                            0 -> R.drawable.p1
                            1 -> R.drawable.p2
                            2 -> R.drawable.p3
                            3 -> R.drawable.p4
                            4 -> R.drawable.p5
                            5 -> R.drawable.p6
                            else -> R.drawable.p1
                        }
                    }

                    val names = List(600) {
                        when (it % 6){
                            0 -> "PRO Go. Основы программирования"
                            1 -> "Основы PHP: Структура и Синтаксис"
                            2 -> "Тестировщик мобильных приложений: Android и iOS"
                            3 -> "Пакет SQL курсов"
                            4 -> "Профессия Python-разработчик"
                            5 -> "Продвинутый Django 4"
                            else -> "PRO Go. Основы программирования"
                        }
                    }

                    val ingredients = List(600) {
                        when (it % 6) {
                            0 -> "Начальный уровень"
                            1 -> "Начальный уровень"
                            2 -> "Средний уровень"
                            3 -> "Программа"
                            4 -> "Программа"
                            5 -> "Средний уровень"
                            else -> "Начальный уровень" // Здесь также можно добавить обработку
                        }
                    }

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "MainScreen") {
                        composable(route = "MainScreen") {
                            MainScreen(imageId, names, ingredients, navController)
                        }
                        composable(route = "DetailScreen/{index}",
                            arguments = listOf(
                                navArgument(name = "index") {
                                    type = NavType.IntType
                                }
                            )
                        ) { index->
                            DetailScreen(
                                photos = imageId,
                                names = names,
                                ingredients = ingredients,
                                itemIndex = index.arguments?.getInt("index")
                            )
                        }
                    }
                }
            }
        }
    }
}*/

class MainActivity : ComponentActivity() {
    private val pageSize = 20 // Количество элементов для подгрузки

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val initialImageList = List(100) { R.drawable.p1 }
        val initialNamesList = List(100) { "Элемент $it" }
        val initialIngredientsList = List(100) { "Описание курса для элемента $it" }

        fun getNextPageImages(currentSize: Int): List<Int> {
            val nextPageImages = List(pageSize) { R.drawable.p1 } // Замените на реальные ресурсы
            return nextPageImages
        }

        fun getNextPageNames(currentSize: Int): List<String> {
            val nextPageNames = List(pageSize) { "Новый элемент" } // Замените на реальные названия
            return nextPageNames
        }

        // Функция для загрузки следующей порции ингредиентов
        fun getNextPageIngredients(currentSize: Int): List<String> {
            val nextPageIngredients = List(pageSize) { "Новые ингредиенты" } // Замените на реальные ингредиенты
            return nextPageIngredients
        }

        setContent {
            val (imageList, setImageList) = remember { mutableStateOf(initialImageList) }
            val (namesList, setNamesList) = remember { mutableStateOf(initialNamesList) }
            val (ingredientsList, setIngredientsList) = remember { mutableStateOf(initialIngredientsList) }

            val scrollState = rememberLazyListState()
            val visibleItemIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

            LazyRowOnClickExampleJetpackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "MainScreen") {
                        composable(route = "MainScreen") {
                            MainScreen(
                                imageList.subList(0, visibleItemIndex + pageSize),
                                namesList.subList(0, visibleItemIndex + pageSize),
                                ingredientsList.subList(0, visibleItemIndex + pageSize),
                                navController
                            )

                            // Проверяем, если мы приближаемся к концу списка, то подгружаем новые данные
                            if (visibleItemIndex + pageSize >= imageList.size) {
                                setImageList(imageList + getNextPageImages(imageList.size))
                                setNamesList(namesList + getNextPageNames(namesList.size))
                                setIngredientsList(ingredientsList + getNextPageIngredients(ingredientsList.size))
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



