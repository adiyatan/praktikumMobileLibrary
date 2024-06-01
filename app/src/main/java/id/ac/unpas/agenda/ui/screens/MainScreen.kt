package id.ac.unpas.agenda.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.ac.unpas.agenda.R
import id.ac.unpas.agenda.persistences.AppDatabase
import id.ac.unpas.agenda.ui.theme.Gray40
import id.ac.unpas.agenda.ui.theme.Blue40
import id.ac.unpas.agenda.ui.theme.White
import id.ac.unpas.agenda.ui.theme.White40
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onExitClick: () -> Unit) {
    val navController = rememberNavController()
    val currentRoute = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val snackBarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
            drawerContent = {
                Column(
                        modifier = Modifier
                                .background(color = White)
                                .fillMaxHeight()
                                .width(300.dp)
                ) {
                    Text(
                            text = "PERPUS UNPAS",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W700,
                            modifier = Modifier.padding(16.dp)
                    )
                    // Add more items here
                    Text(
                            text = "Pengkuh agamana, luhung élmuna, jembar budayana",
                            modifier = Modifier
                                    .padding(16.dp)
                                    .clickable {
                                        navController.navigate(NavScreen.Home.route)
                                        scope.launch { drawerState.close() }
                                    }
                    )
                    Text(
                            text = "Copyright 2024.",
                            modifier = Modifier
                                    .padding(16.dp)
                                    .clickable {
                                        navController.navigate(NavScreen.Home.route)
                                        scope.launch { drawerState.close() }
                                    }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope.launch {
                                            drawerState.close()
                                            onExitClick()
                                        }
                                    }
                                    .padding(16.dp)
                    ) {
                        Text(
                                text = "Exit",
                                modifier = Modifier.weight(1f)
                        )
                        Image(
                                painter = painterResource(id = R.drawable.baseline_exit_to_app_24),
                                contentDescription = "Exit",
                                colorFilter = ColorFilter.tint(Color.Black)
                        )
                    }
                }
            },
            drawerState = drawerState
    ) {
        Scaffold(
                topBar = {
                    TopAppBar(
                            title = { Text(text = "PERPUS UNPAS", fontWeight = FontWeight.W500) },
                            navigationIcon = {
                                Image(
                                        painterResource(id = R.drawable.baseline_hamburger_24),
                                        contentDescription = "Menu",
                                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                                        modifier = Modifier
                                                .padding(16.dp)
                                                .clickable {
                                                    scope.launch {
                                                        drawerState.open()
                                                    }
                                                }
                                )
                            },
                            colors = topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                    )
                },
                bottomBar = {
                    if (currentRoute.value != NavScreen.Login.route) {
                        BottomAppBar(
                                containerColor = White40,
                                contentColor = Gray40,
                        ) {
                            Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                        painterResource(id = R.drawable.baseline_home_24),
                                        contentDescription = "Home",
                                        colorFilter = if (currentRoute.value == NavScreen.Home.route) ColorFilter.tint(Blue40) else ColorFilter.tint(Gray40),
                                        modifier = Modifier
                                                .clickable {
                                                    navController.navigate(NavScreen.Home.route)
                                                }
                                                .weight(0.5f)
                                )
                                Image(
                                        painter = painterResource(id = R.drawable.baseline_book_24),
                                        contentDescription = "Tambah",
                                        colorFilter = if (currentRoute.value == NavScreen.Menu.route || currentRoute.value == NavScreen.Book.route || currentRoute.value == NavScreen.Request.route) ColorFilter.tint(Blue40) else ColorFilter.tint(Gray40),
                                        modifier = Modifier
                                                .clickable {
                                                    navController.navigate(NavScreen.Menu.route)
                                                }
                                                .weight(0.5f)
                                )
                                Image(
                                        painterResource(id = R.drawable.baseline_card_membership_24),
                                        contentDescription = "Lihat",
                                        colorFilter = if (currentRoute.value == NavScreen.Member.route) ColorFilter.tint(Blue40) else ColorFilter.tint(Gray40),
                                        modifier = Modifier
                                                .clickable {
                                                    navController.navigate(NavScreen.Member.route)
                                                }
                                                .weight(0.5f)
                                )
                            }
                        }
                    }
                },
                floatingActionButton = {
                    if (currentRoute.value == NavScreen.Book.route ) {
                        FloatingActionButton(
                                onClick = { navController.navigate(NavScreen.AddBook.route) },
                                containerColor = Blue40,
                                shape = CircleShape // or CircleShape for fully round
                        ) {
                            Image(
                                    painterResource(id = R.drawable.baseline_add_24),
                                    contentDescription = "AddBook",
                                    colorFilter = ColorFilter.tint(White),
                            )
                        }
                    }
                    if (currentRoute.value == NavScreen.Member.route ) {
                        FloatingActionButton(
                                onClick = { navController.navigate(NavScreen.AddMember.route) },
                                containerColor = Blue40,
                                shape = CircleShape // or CircleShape for fully round
                        ) {
                            Image(
                                    painterResource(id = R.drawable.baseline_add_24),
                                    contentDescription = "AddMember",
                                    colorFilter = ColorFilter.tint(White),
                            )
                        }
                    }
                    if (currentRoute.value == NavScreen.Request.route ) {
                        FloatingActionButton(
                                onClick = { navController.navigate(NavScreen.AddRequest.route) },
                                containerColor = Blue40,
                                shape = CircleShape // or CircleShape for fully round
                        ) {
                            Image(
                                    painterResource(id = R.drawable.baseline_add_24),
                                    contentDescription = "AddRequest",
                                    colorFilter = ColorFilter.tint(White),
                            )
                        }
                    }
                },
                floatingActionButtonPosition = FabPosition.End,
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState)
                }
        ) { innerPadding ->
            NavHost(navController = navController, startDestination = NavScreen.Login.route) {
                composable(NavScreen.Login.route) {
                    currentRoute.value = NavScreen.Login.route
                    LoginScreen(modifier = Modifier.padding(innerPadding)) {
                        navController.navigate(NavScreen.Home.route)
                    }
                }
                composable(NavScreen.Home.route) {
                    currentRoute.value = NavScreen.Home.route
                    HomeScreen(navController = navController, modifier = Modifier.padding(innerPadding)) {
                        navController.navigate(NavScreen.Menu.route)
                    }
                }
                composable(NavScreen.Menu.route) {
                    currentRoute.value = NavScreen.Menu.route
                    BookScreen(navController = navController, modifier = Modifier.padding(innerPadding))
                }
                composable(NavScreen.Book.route) {
                    currentRoute.value = NavScreen.Book.route
                    ListBookScreen(modifier = Modifier.padding(innerPadding), onDelete = {
                        scope.launch {
                            snackBarHostState.showSnackbar("Data telah dihapus", "OK")
                        }
                    }) { id ->
                        navController.navigate("${NavScreen.Edit.route}/$id")
                    }
                }
                composable(NavScreen.Request.route) {
                    currentRoute.value = NavScreen.Request.route
                    BookRequestScreen(modifier = Modifier.padding(innerPadding), onDelete = {
                        scope.launch {
                            snackBarHostState.showSnackbar("Data telah dihapus", "OK")
                        }
                    }) { id ->
                        navController.navigate("${NavScreen.Edit.route}/$id")
                    }
                }
                composable(NavScreen.Member.route) {
                    currentRoute.value = NavScreen.Member.route
                    ListMemberScreen(modifier = Modifier.padding(innerPadding), onDelete = {
                        scope.launch {
                            snackBarHostState.showSnackbar("Data telah dihapus", "OK")
                        }
                    }) { id ->
                        navController.navigate("${NavScreen.Edit.route}/$id")
                    }
                }
                composable(NavScreen.AddBook.route) {
                    //munculkan bookAddDialog
                    currentRoute.value = NavScreen.AddBook.route
                    BookAddDialog(onDismiss = {
                        navController.popBackStack()
                    }, onSave = { book ->
                        scope.launch {
                            navController.navigate(NavScreen.Book.route)
                            snackBarHostState.showSnackbar("Data has been saved", "OK")
                        }
                    })
                }
                composable(NavScreen.AddMember.route) {
                    //munculkan memberAddDialog
                    currentRoute.value = NavScreen.AddMember.route
                    MemberAddDialog(onDismiss = {
                        navController.popBackStack()
                    }, onSave = { member ->
                        scope.launch {
                            navController.navigate(NavScreen.Member.route)
                            snackBarHostState.showSnackbar("Data has been saved", "OK")
                        }
                    })
                }
                composable(NavScreen.AddRequest.route) {
                    //munculkan requestAddDialog
                    currentRoute.value = NavScreen.AddRequest.route
                    RequestAddDialog(onDismiss = {
                        navController.popBackStack()
                    }, onSave = { request ->
                        scope.launch {
                            snackBarHostState.showSnackbar("Data has been saved", "OK")
                        }
                    })
                }
            }
        }
    }
}