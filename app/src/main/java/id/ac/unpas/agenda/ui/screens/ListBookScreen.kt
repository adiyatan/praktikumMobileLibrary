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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.ac.unpas.agenda.R
import id.ac.unpas.agenda.models.Todo
import id.ac.unpas.agenda.ui.composables.BookCard
import id.ac.unpas.agenda.ui.composables.ConfirmationDialog
import id.ac.unpas.agenda.ui.theme.Blue40
import id.ac.unpas.agenda.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun ListBookScreen( modifier: Modifier = Modifier, onDelete: () -> Unit, onClick: (String) -> Unit) {

    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<TodoViewModel>()
    val search = remember { mutableStateOf("") }

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
                .padding(10.dp).fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "DAFTAR BUKU", fontSize = 24.sp,
                fontWeight = FontWeight.W800,
            )
        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(12.dp)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = search.value,
                onValueChange = { search.value = it },
                placeholder = { Text(text = "Cari Buku", color = Color.Gray) },
                modifier = roundedOutlinedTextFieldModifier(12.dp).background(color = White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = White ,
                    unfocusedBorderColor = White,
                ),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.width(36.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = Blue40, shape = RoundedCornerShape(12.dp))
                    .clickable { /* Perform search action */ }
                    ,
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search Icon",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        BookCard(
            name1 = "Algoritma dan Pemograman 2",
            name2 = "Falahah, S.Si., M.T.",
            qty = 10,
            date = "2014-06-26 04:07:31",
            onClick = { }
        )
        BookCard(
            name1 = "Detektif Conan 93",
            name2 = "Aoyama Gosho",
            qty = 10,
            date = "2014-06-26 04:07:31",
            onClick = { /* Navigate to Peminjaman Buku */ }
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