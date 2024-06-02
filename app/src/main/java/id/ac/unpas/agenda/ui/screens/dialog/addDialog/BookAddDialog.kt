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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BookAddDialog(onDismiss: () -> Unit, onSave: (Book) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: BookViewModel = hiltViewModel()

    // Define state variables
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var releasedDate by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Function to sanitize input
    fun sanitizeInput(input: String): String {
        return input.replace(Regex("[^A-Za-z0-9\\s]"), "")
    }

    // Function to capitalize each word
    fun capitalizeWords(input: String): String {
        return input.split(" ").joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Buku") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = title,
                    onValueChange = { title = capitalizeWords(sanitizeInput(it)) },
                    label = { Text("Judul Buku") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = author,
                    onValueChange = { author = capitalizeWords(sanitizeInput(it)) },
                    label = { Text("Penulis") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = releasedDate,
                    onValueChange = { releasedDate = sanitizeInput(it) },
                    label = { Text("Tahun Terbit") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = stock,
                    onValueChange = { stock = sanitizeInput(it) },
                    label = { Text("Stok") },
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
                coroutineScope.launch {
                    if (title.isBlank() || author.isBlank() || releasedDate.isBlank() || stock.isBlank() || stock.toIntOrNull() == null) {
                        errorMessage = "Please fill all fields correctly."
                    } else {
                        if (viewModel.existsByTitle(title)) {
                            errorMessage = "Nama buku sudah ada."
                        } else {
                            val id = UUID.randomUUID().toString()
                            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                            val createdAt = sdf.format(Date())
                            val updatedAt = sdf.format(Date())
                            val book = Book(
                                id = id,
                                title = title,
                                author = author,
                                released_date = releasedDate,
                                stock = stock.toInt(),
                                created_at = createdAt,
                                updated_at = updatedAt
                            )
                            viewModel.insert(
                                id = id,
                                title = title,
                                author = author,
                                released_date = releasedDate,
                                stock = stock.toInt(),
                                created_at = createdAt,
                                update_at = updatedAt
                            )
                            onSave(book)
                            onDismiss()
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
