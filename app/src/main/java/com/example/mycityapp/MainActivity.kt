@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mycityapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycityapp.data.DataSource
import com.example.mycityapp.data.Recommendation
import com.example.mycityapp.data.SelectedScr
import com.example.mycityapp.ui.theme.MyCityAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCityAppTheme {
                setContent {
                    navController = rememberNavController()
                    MainScreen(navController)
                }


            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "s1") {
        composable("s1") {
            CategoryList(navController = navController)
        }
        composable("s2") {
            CityRecommendationsScreen(navController = navController)
        }
        composable("s3") {
            CityRecommendationDetailScreen(navController)
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityRecommendationsScreen(navController: NavController) {
    Scaffold(
        topBar = @Composable {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {

                            Image(
                                painter = painterResource(R.drawable.backnew_image),
                                contentDescription = stringResource(
                                    R.string.back
                                ), modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        navController.navigate("s1")
                                    }
                            )
                        Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                stringResource(SelectedScr.scr),
                                color = Color.Black,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            )
                    }
                }
            )
        }
    ) {
        var rec: MutableList<Int> = mutableListOf()
        for (i in DataSource.recommendations) {
            if (i.category == SelectedScr.scr) {
                rec.add(i.title)
            }
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 80.dp)

        ) {
            itemsIndexed(
                rec
            ) { _, item ->
                Card(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = item),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                SelectedScr.selectedRec = item
                                navController.navigate("s3")
                            }
                            .padding(16.dp),
                        fontSize = 24.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityRecommendationDetailScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Image(
                            painter = painterResource(R.drawable.backnew_image),
                            contentDescription = stringResource(R.string.back),
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    navController.navigate("s2")
                                }
                        )


                        Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                stringResource(SelectedScr.selectedRec),
                                color = Color.Black,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth(),
                                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                            )


                    }
                }
            )
        }
    ) {
        var rec: Recommendation = DataSource.recommendations[0]
        for (i in DataSource.recommendations) {
            if (i.title == SelectedScr.selectedRec) {
                rec = i
            }
        }
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 80.dp) // Добавлен отступ от верхней плашки
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(painter = painterResource(rec.image), contentDescription = stringResource(rec.title),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(rec.description),

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                fontSize = 16.sp
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoryList(navController: NavController) {
    var rec: MutableList<Int> = mutableListOf()
    for (i in DataSource.recommendations) {
        if (rec.size == 0)
            rec.add(i.category)
        else {
            val index = rec.indexOf(i.category)
            if (index == -1)
                rec.add(i.category)
        }
    }
    Scaffold(
        topBar = @Composable {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.city), color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 80.dp)
        ) {
            Card(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(id = rec[0]),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("s2")
                            SelectedScr.scr = (rec[0])
                        }
                        .padding(30.dp),
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(id = rec[1]),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("s2")
                            SelectedScr.scr = (rec[1])
                        }
                        .padding(30.dp),
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(id = rec[2]),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("s2")
                            SelectedScr.scr = (rec[2])
                        }
                        .padding(30.dp),
                    fontSize = 25.sp
                )
            }
        }
    }
}