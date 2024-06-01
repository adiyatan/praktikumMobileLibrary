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
import id.ac.unpas.agenda.models.Book
import id.ac.unpas.agenda.models.BookRequest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RequestAddDialog(onDismiss: () -> Unit, onSave: (BookRequest) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: BookRequestViewModel = hiltViewModel()
    val bookViewModel: BookViewModel = hiltViewModel()
    val memberViewModel: MemberViewModel = hiltViewModel()

    // Define state variables
    var fullName by remember { mutableStateOf("") }
    var bookName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun sanitizeInput(input: String): String {
        return input.replace(Regex("[^A-Za-z0-9\\s]"), "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Form Peminjaman") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = fullName,
                    onValueChange = { fullName = sanitizeInput(it) },
                    label = { Text("Nama Anggota") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = bookName,
                    onValueChange = { bookName = sanitizeInput(it) },
                    label = { Text("Nama Buku") }
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
                if (errorMessage != null) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (fullName.isBlank() || bookName.isBlank() || startDate.isBlank() || endDate.isBlank()) {
                    errorMessage = "Please fill all fields correctly."
                } else {
                    coroutineScope.launch {
                        val book = bookViewModel.findByTitle(bookName)
                        val member = memberViewModel.findByName(fullName)
                        if (book != null && member != null) {
                            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                            val createdAt = sdf.format(Date())
                            val updatedAt = sdf.format(Date())
                            val status = "on loan"
                            val bookRequest = BookRequest(
                                id = UUID.randomUUID().toString(),
                                library_book_id = book.id,
                                library_member_id = member.id,
                                start_date = startDate,
                                end_date = endDate,
                                status = status,
                                created_at = createdAt,
                                updated_at = updatedAt
                            )
                            onSave(bookRequest)
                            onDismiss()
                        } else {
                            errorMessage = "Book not found. Please check the book name."
                        }
                    }
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


