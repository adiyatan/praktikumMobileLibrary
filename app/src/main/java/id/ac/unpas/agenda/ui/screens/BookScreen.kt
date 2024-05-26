package id.ac.unpas.agenda.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.ac.unpas.agenda.R
import id.ac.unpas.agenda.models.Todo
import id.ac.unpas.agenda.ui.composables.ConfirmationDialog
import id.ac.unpas.agenda.ui.theme.Yellow60
import kotlinx.coroutines.launch
import androidx.navigation.NavController

@Composable
fun BookScreen(navController: NavController, modifier: Modifier = Modifier, onDelete: () -> Unit, onClick: (String) -> Unit) {

    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<TodoViewModel>()

    val list: List<Todo> by viewModel.todos.observeAsState(listOf())
    val title = remember { mutableStateOf("TODO") }
    val openDialog = remember {
        mutableStateOf(false)
    }
    val activeId = remember {
        mutableStateOf("")
    }
    val deleting = remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(20.dp).fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "PILIH MENU", fontSize = 24.sp,
                fontWeight = FontWeight.W800,
            )
        }
        MenuCard(
            title = "Daftar Buku",
            iconId = R.drawable.list_book,
            onClick = {navController.navigate(NavScreen.Book.route) }
        )
        MenuCard(
            title = "Peminjaman Buku",
            iconId = R.drawable.book,
            onClick = {navController.navigate(NavScreen.Loan.route) }
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(list.size) { index ->
                val item = list[index]
                TodoItem(item = item, onEditClick = { id ->
                    onClick(id)
                }, onDeleteClick = { id ->
                    deleting.value = true
                    activeId.value = id
                    openDialog.value = true
                })
            }
        }
    }

    if (openDialog.value) {
        ConfirmationDialog(onDismiss = {
            openDialog.value = false
        }) {
            scope.launch {
                viewModel.delete(activeId.value)
            }
            openDialog.value = false
        }
    }

    viewModel.isLoading.observe(LocalLifecycleOwner.current) {
        if (it) {
            title.value = "Loading..."
        } else {
            title.value = "TODO"
        }
    }

    viewModel.isDeleted.observe(LocalLifecycleOwner.current) {
        if (deleting.value && it) {
            deleting.value = false
            onDelete()
        }
    }
}

@Composable
fun MenuCard(title: String, iconId: Int, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Yellow60),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onClick)
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource( id = iconId), contentDescription = "Login Logo", modifier = Modifier
                        .width(40.dp),
                )
            }
        }
    }
}
