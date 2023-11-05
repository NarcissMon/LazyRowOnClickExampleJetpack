package com.mkrdeveloper.lazyrowonclickexamplejetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mkrdeveloper.lazyrowonclickexamplejetpack.screens.DetailScreen
import com.mkrdeveloper.lazyrowonclickexamplejetpack.screens.MainScreen
import com.mkrdeveloper.lazyrowonclickexamplejetpack.ui.theme.LazyRowOnClickExampleJetpackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyRowOnClickExampleJetpackTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val imageId = List(600) {
                        when (it % 6) {
                            0 -> R.drawable.p1
                            1 -> R.drawable.p2
                            2 -> R.drawable.p3
                            3 -> R.drawable.p4
                            4 -> R.drawable.p5
                            5 -> R.drawable.p6
                            else -> R.drawable.p1
                        }
                    }/*(
                        R.drawable.p1,
                        R.drawable.p2,
                        R.drawable.p3,
                        R.drawable.p4,
                        R.drawable.p5,
                        R.drawable.p6
                    )*/

                    val names = List(600) {
                        when (it % 6){
                            0 -> "Peperoni"
                            1 -> "Vegan"
                            2 -> "FourCheese"
                            3 -> "Margaritta"
                            4 -> "American"
                            5 -> "Mexican"
                            else -> "Peperoni"
                        }
                    } /*(
                        "Peperoni",
                        "Vegan",
                        "FourCheese",
                        "Margaritta",
                        "American",
                        "Mexican"
                    )*/

                    val ingredients = List(600) {
                        when (it % 6) {
                            0 -> "Tomato sos, cheese, oregano, peperoni"
                            1 -> "Tomato sos, cheese, oregano, spinach, green paprika, rukola"
                            2 -> "Tomato sos, oregano, mozzarella, gouda, parmesan, cheddar"
                            3 -> "Tomato sos, cheese, oregano, basil"
                            4 -> "Tomato sos, cheese, oregano, green paprika, red beans"
                            5 -> "Tomato sos, cheese, oregano, corn, jalapeno, chicken"
                            else -> "Tomato sos, cheese, oregano, peperoni" // Здесь также можно добавить обработку
                        }
                    }
                        /*(
                        "Tomato sos, cheese, oregano, peperoni",
                        "Tomato sos, cheese, oregano, spinach, green paprika, rukola",
                        "Tomato sos, oregano, mozzarella, goda, parmesan, cheddar",
                        "Tomato sos, cheese, oregano, bazillion",
                        "Tomato sos, cheese, oregano, green paprika, red beans",
                        "Tomato sos, cheese, oregano, corn, jalapeno, chicken",
                    )*/

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
}

