package id.ac.unpas.agenda.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import id.ac.unpas.agenda.models.Book
import id.ac.unpas.agenda.models.BookRequest
import id.ac.unpas.agenda.ui.composables.BookCard
import id.ac.unpas.agenda.ui.composables.ConfirmationDialog
import id.ac.unpas.agenda.ui.theme.Blue40
import id.ac.unpas.agenda.ui.theme.White
import kotlinx.coroutines.launch
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import id.ac.unpas.agenda.models.Member
import id.ac.unpas.agenda.ui.composables.BookRequestCard

@Composable
fun ListBookRequestScreen(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onClick: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<BookRequestViewModel>()
    val search = remember { mutableStateOf("") }
    val bookViewModel: BookViewModel = hiltViewModel()
    val memberViewModel: MemberViewModel = hiltViewModel()
    val navController = rememberNavController()

    val list: List<BookRequest> by viewModel.requests.observeAsState(listOf())
    val title = remember { mutableStateOf("TODO") }
    val openDialog = remember { mutableStateOf(false) }
    val activeId = remember { mutableStateOf("") }
    val deleting = remember { mutableStateOf(false) }

    var selectedBookRequest by remember { mutableStateOf<BookRequest?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "PEMINJAMAN BUKU", fontSize = 24.sp,
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
                modifier = Modifier
                    .background(color = White)
                    .fillMaxWidth(0.8f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = White,
                    unfocusedBorderColor = White,
                ),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = Blue40, shape = RoundedCornerShape(12.dp))
                    .clickable { /* Perform search action */ },
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
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(list.size) { index ->
                val item = list[index]
                val book = remember { mutableStateOf<Book?>(null) }
                val member = remember { mutableStateOf<Member?>(null) }

                LaunchedEffect(item.library_book_id) {
                    book.value = bookViewModel.findId(item.library_book_id)
                }

                LaunchedEffect(item.library_member_id) {
                    member.value = memberViewModel.findId(item.library_member_id)
                }

                book.value?.let { book ->
                    BookRequestCard(
                        name1 = book.title,
                        name2 = member.value?.name ?: "Unknown",
                        status = item.status,
                        date = item.end_date
                    ) {
                        selectedBookRequest = item
                        showEditDialog = true
                    }
                }
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

    if (showEditDialog && selectedBookRequest != null) {
        RequestEditDialog(
            item = selectedBookRequest!!,
            onDismiss = {
                showEditDialog = false
            },
            onSave = { bookRequest ->
                viewModel.viewModelScope.launch {
                    viewModel.update(
                        id = bookRequest.id,
                        library_book_id = bookRequest.library_book_id,
                        library_member_id = bookRequest.library_member_id,
                        start_date = bookRequest.start_date,
                        end_date = bookRequest.end_date,
                        status = bookRequest.status,
                        created_at = bookRequest.created_at,
                        updated_at = bookRequest.updated_at
                    )
                    showEditDialog = false
                }
            },
            onDelete = {
                openDialog.value = true
                activeId.value = selectedBookRequest!!.id
                deleting.value = true
            }
        )
    }
}