package com.example.myh

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.DetailScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Vaibhav"
                    nullable = true
                }
            )) { entry ->
            DetailScreen(navController = navController, name = entry.arguments?.getString("name"))
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    ScaffoldLayout {
        var text by remember {
            mutableStateOf("")
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(text = "Enter you name and hit that button!")

            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = text, onValueChange = {
                text = it
            }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                navController.navigate(route = Screen.DetailScreen.withArgs(text)) {
                    popUpTo(Screen.MainScreen.route) {
                        inclusive = true
                    }
                }
            }, modifier = Modifier.align(Alignment.End)) {
                Text(text = "To DetailScreen")
            }
        }
    }
}


@Composable
fun DetailScreen(navController: NavController, name: String?) {
    BasicLayout {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Hello, $name")

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            Text(text = "Hit that button and see the magic!")

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                navController.navigate(route = Screen.MainScreen.route)
            }) {
                Text(text = "To MainScreen")
            }


        }
    }
}

@Composable
fun ScaffoldLayout(content: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(text = "Jetpack Compose Navigation", modifier = Modifier.padding(16.dp))
            }
        },

        bottomBar = {
            BottomAppBar { /* Bottom app bar content */ }
        },

        isFloatingActionButtonDocked = true,

        floatingActionButton = {
            FloatingActionButton(onClick = { /* ... */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_24),
                    contentDescription = "notif"
                )
            }
        },

        floatingActionButtonPosition = FabPosition.Center,

        drawerContent = {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            Divider()
            // Drawer items
        }
    ) {
        content()
    }
}

@Composable
fun BasicLayout(content: @Composable () -> Unit) {
    content()
}