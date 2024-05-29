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



@Composable
fun BookAddDialog(onDismiss: () -> Unit, onSave: (BookViewModel) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    data class BookViewModel(
    val id: String,
    val title: String,
    val author: String,
    val releasedDate: Int,
    val stock: Int
)

    // Define state variables
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var releasedDate by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Buku") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul Buku") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Penerbit") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = releasedDate,
                    onValueChange = { releasedDate = it },
                    label = { Text("Tahun Terbit") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = stock,
                    onValueChange = { stock = it },
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
                if (title.isBlank() || author.isBlank() || releasedDate.isBlank() || stock.isBlank() || stock.toIntOrNull() == null) {
                    errorMessage = "Please fill all fields correctly."
                } else {
                    val book = BookViewModel(
                        id = UUID.randomUUID().toString(),
                        title = title,
                        author = author,
                        releasedDate = releasedDate.toInt(),
                        stock = stock.toInt()
                    )
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
