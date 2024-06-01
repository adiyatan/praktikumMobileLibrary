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
import id.ac.unpas.agenda.persistences.BookDao
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BookEditDialog(item: Book, onDismiss: () -> Unit, onSave: (Book) -> Unit, onDelete: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: BookViewModel = hiltViewModel()

    // Define state variables
    val (title, setTitle) = remember { mutableStateOf(item.title) }
    val (author, setAuthor) = remember { mutableStateOf(item.author) }
    val (releasedDate, setReleasedDate) = remember { mutableStateOf(item.released_date) }
    val (stock, setStock) = remember { mutableStateOf(item.stock.toString()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Function to sanitize input
    fun sanitizeInput(input: String): String {
        return input.replace(Regex("[^A-Za-z0-9\\s]"), "")
    }

    // Function to capitalize each word
    fun formatTitle(input: String): String {
        return input.split(" ").joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Buku") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = title,
                    onValueChange = { setTitle(sanitizeInput(it)) },
                    label = { Text("Judul Buku") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = author,
                    onValueChange = { setAuthor(sanitizeInput(it)) },
                    label = { Text("Penulis") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = releasedDate,
                    onValueChange = { setReleasedDate(sanitizeInput(it)) },
                    label = { Text("Tahun Terbit") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = stock,
                    onValueChange = { setStock(sanitizeInput(it)) },
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
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    coroutineScope.launch {
                        if (title.isBlank() || author.isBlank() || releasedDate.isBlank() || stock.isBlank() || stock.toIntOrNull() == null) {
                            errorMessage = "Please fill all fields correctly."
                        } else {
                            val formattedTitle = formatTitle(title)
                            if (formattedTitle != item.title && viewModel.existsByTitle(formattedTitle)) {
                                errorMessage = "Nama buku sudah ada."
                            } else {
                                val updatedBook = item.copy(
                                    title = formattedTitle,
                                    author = author,
                                    released_date = releasedDate,
                                    stock = stock.toInt()
                                )
                                onSave(updatedBook)
                                onDismiss()
                            }
                        }
                    }
                }) {
                    Text("Save")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        onDelete()
                        onDismiss()
                    }
                }, colors = ButtonDefaults.buttonColors()) {
                    Text("Delete")
                }
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
