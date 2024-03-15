package com.example.mycityapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mycityapp.data.DataSource
import com.example.mycityapp.data.Recommendation
import com.example.mycityapp.data.RoutOnCity
import com.example.mycityapp.ui.theme.MyCityAppTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCityAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    MyCity(
                        windowSize = windowSize.widthSizeClass,
                        onBackPressed = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun MyCity(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = RoutOnCity.Categories.route) {
        composable(RoutOnCity.Categories.route) {
            CityCategoriesScreen(navController = navController)
        }

        composable(
            RoutOnCity.Recommendations.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            CityRecommendationsScreen(backStackEntry = backStackEntry, navController = navController)
        }

        composable(
            RoutOnCity.RecommendationDetail.route,
            arguments = listOf(navArgument("recId") { type = NavType.StringType })
        ) { backStackEntry ->
            CityRecommendationDetailScreen(backStackEntry = backStackEntry, navController = navController)
        }
    }
}

@Composable
fun CityCategoriesScreen(navController: NavController) {
    TypeRoute(
        onCategorySelected = { category ->
            navController.navigate(RoutOnCity.Recommendations.route.replace("{categorys}", category))
        }
    )
}

@Composable
fun CityRecommendationsScreen(backStackEntry: NavBackStackEntry, navController: NavController) {
    val category = backStackEntry.arguments?.getString("category")
    if (category != null) {
        Recommend(
            category = category,
            onRecommendationSelected = { recommendationId ->
                navController.navigate(RoutOnCity.RecommendationDetail.route.replace("{recId}", recommendationId))
            }
        )
    }
}

@Composable
fun CityRecommendationDetailScreen(backStackEntry: NavBackStackEntry, navController: NavController) {
    val recommendationId = backStackEntry.arguments?.getString("recId")
    if (recommendationId != null) {
        RecommendScreen(
            recommendationId = recommendationId,
            onNavigateUp = { navController.popBackStack() }
        )
    }
}
@Composable
fun TypeRoute(onCategorySelected: (String) -> Unit) {
    val categories = DataSource.recommendations
        .map { it.category }
        .distinct().map { categoryId ->
            stringResource(categoryId)
        }


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        CategoryList(categories = categories, onCategorySelected = onCategorySelected)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoryList(categories: List<String>, onCategorySelected: (String) -> Unit) {
    Scaffold(
        topBar = @Composable {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.city), color = Color.Black)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 48.dp)
        ) {
            Card(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    text = categories[0],
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCategorySelected(categories[0]) }
                        .padding(30.dp),
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = categories[1],
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCategorySelected(categories[1]) }
                        .padding(30.dp),
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = categories[2],
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCategorySelected(categories[2]) }
                        .padding(30.dp),
                    fontSize = 25.sp
                )
            }
        }
    }
}

@Composable
fun Recommend(
    category: String,
    onRecommendationSelected: (String) -> Unit
) {
    val recommendations = DataSource.recommendations
        .filter { it.category.toString() == category }

    RecommendationList(recommendations = recommendations, onRecommendationSelected = onRecommendationSelected)
}

@Composable
fun RecommendationList(recommendations: List<Recommendation>, onRecommendationSelected: (String) -> Unit) {
    LazyColumn {
        itemsIndexed(recommendations) { _, recommendation ->
            RecommendationItem(recommendation = recommendation, onRecommendationSelected = onRecommendationSelected)
        }
    }
}

@Composable
fun RecommendationItem(recommendation: Recommendation, onRecommendationSelected: (String) -> Unit) {
    Text(
        text = stringResource(recommendation.id),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRecommendationSelected(recommendation.id.toString()) }
            .padding(20.dp),
        fontSize = 30.sp
    )
}
@Composable
fun RecommendScreen(
    recommendationId: String,
    onNavigateUp: () -> Unit
) {
    val recommendation = DataSource.recommendations
        .firstOrNull { it.id.toString() == recommendationId }

    if (recommendation != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            RecommendationHeader(recommendation = recommendation)
            Spacer(modifier = Modifier.height(20.dp))
            RecommendationTypeInfo(recommendation = recommendation)
            Spacer(modifier = Modifier.height(20.dp))
            RecommendationAdditionalInfo(recommendation = recommendation)
            Spacer(modifier = Modifier.height(20.dp))
            RecommendationImage(recommendation = recommendation)
        }
    } else {
        Text(text = stringResource(id = R.string.cinema))
    }
}

@Composable
fun RecommendationHeader(recommendation: Recommendation) {
    Text(
        text = stringResource(recommendation.category),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun RecommendationTypeInfo(recommendation: Recommendation) {
    Text(
        text = stringResource(recommendation.category),
        fontSize = 30.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun RecommendationAdditionalInfo(recommendation: Recommendation) {
    Text(
        text = stringResource(recommendation.title),
        fontSize = 20.sp,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = stringResource(recommendation.description),
        fontSize = 20.sp,
        color = Color.Black
    )
}

@Composable
fun RecommendationImage(recommendation: Recommendation) {
    Image(
        painter = painterResource(id = recommendation.image),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .border(width = 2.dp, color = Color.Blue)
            .padding(16.dp)
            .alpha(1f),
        contentScale = ContentScale.FillBounds
    )
}


