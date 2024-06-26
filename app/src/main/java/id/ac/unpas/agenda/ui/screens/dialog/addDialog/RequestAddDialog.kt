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

    fun capitalizeWords(input: String): String {
        return input.split(" ").joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
    }

    fun convertDateFormat(date: String): String {
        return try {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val parsedDate: Date? = sdf.parse(date)
            val outputSdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            outputSdf.format(parsedDate!!)
        } catch (e: Exception) {
            ""
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Form Peminjaman") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = fullName,
                    onValueChange = { fullName = capitalizeWords(sanitizeInput(it)) },
                    label = { Text("Nama Anggota") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = bookName,
                    onValueChange = { bookName = capitalizeWords(sanitizeInput(it)) },
                    label = { Text("Nama Buku") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = startDate,
                    onValueChange = { startDate = convertDateFormat(it) },
                    label = { Text("Tanggal Peminjaman") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = endDate,
                    onValueChange = { endDate = convertDateFormat(it) },
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
                            viewModel.insert(
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
                        } else {
                            errorMessage = "Book & Member not found. Please check the book and member name."
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


