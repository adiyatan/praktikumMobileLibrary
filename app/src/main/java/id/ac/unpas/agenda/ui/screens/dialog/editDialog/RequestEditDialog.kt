package id.ac.unpas.agenda.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.UUID
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import id.ac.unpas.agenda.models.Book
import id.ac.unpas.agenda.models.BookRequest
import id.ac.unpas.agenda.models.Member
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestEditDialog(
    item: BookRequest,
    onDismiss: () -> Unit,
    onSave: (BookRequest) -> Unit,
    onDelete: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: BookRequestViewModel = hiltViewModel()
    val bookViewModel: BookViewModel = hiltViewModel()
    val memberViewModel: MemberViewModel = hiltViewModel()

    val book = remember { mutableStateOf<Book?>(null) }
    val member = remember { mutableStateOf<Member?>(null) }

    LaunchedEffect(item.library_book_id) {
        book.value = bookViewModel.findId(item.library_book_id)
    }

    LaunchedEffect(item.library_member_id) {
        member.value = memberViewModel.findId(item.library_member_id)
    }

    var fullName by remember { mutableStateOf(book.value?.title ?: "Loading...") }
    var bookName by remember { mutableStateOf(member.value?.name ?: "Loading...") }
    var startDate by remember { mutableStateOf(item.start_date) }
    var endDate by remember { mutableStateOf(item.end_date) }
    var status by remember { mutableStateOf(item.status) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(book.value) {
        book.value?.let {
            fullName = it.title
        }
    }

    LaunchedEffect(member.value) {
        member.value?.let {
            bookName = it.name
        }
    }

    fun sanitizeInput(input: String): String {
        return input.replace(Regex("[^A-Za-z0-9\\s]"), "")
    }

    fun capitalizeWords(input: String): String {
        return input.split(" ").joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
    }

    var expanded by remember { mutableStateOf(false) }
    val statusOptions = listOf("on loan", "returned", "lost", "damaged")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Form Peminjaman") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = fullName,
                    onValueChange = { },
                    label = { Text("Nama Anggota") },
                    enabled = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = bookName,
                    onValueChange = { },
                    label = { Text("Nama Buku") },
                    enabled = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text("Tanggal Peminjaman") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("Tanggal Pengembalian") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = status,
                        onValueChange = { status = it },
                        label = { Text("Status") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        statusOptions.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    status = selectionOption
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                if (errorMessage != null) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (startDate.isBlank() || endDate.isBlank()) {
                    errorMessage = "Semua kolom harus diisi"
                } else {
                    coroutineScope.launch {
                            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                            val createdAt = sdf.format(Date())
                            val updatedAt = sdf.format(Date())
                            val bookRequest = BookRequest(
                                id = item.id,
                                library_book_id = item.library_book_id,
                                library_member_id = item.library_member_id,
                                start_date = startDate,
                                end_date = endDate,
                                status = status,
                                created_at = item.created_at,
                                updated_at = updatedAt
                            )
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
                            onSave(bookRequest)
                            onDismiss()
                    }
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = {
                coroutineScope.launch {
                    onDelete()
                    onDismiss()
                }
            }, colors = ButtonDefaults.buttonColors()) {
                Text("Delete")
            }
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
