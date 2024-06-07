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
import id.ac.unpas.agenda.models.Member
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MemberEditDialog(item: Member, onDismiss: () -> Unit, onSave: (Member) -> Unit, onDelete: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val viewModel: MemberViewModel = hiltViewModel()

    // Define state variables
    var name by remember { mutableStateOf(item.name) }
    var address by remember { mutableStateOf(item.address) }
    var phone by remember { mutableStateOf(item.phone) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun sanitizeInput(input: String): String {
        return input.replace(Regex("[^A-Za-z0-9\\s]"), "")
    }

    fun capitalizeWords(input: String): String {
        return input.split(" ").joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Anggota") },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = name,
                    onValueChange = { name = capitalizeWords(sanitizeInput(it)) },
                    label = { Text("Nama Anggota") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = address,
                    onValueChange = { address = sanitizeInput(it) },
                    label = { Text("Alamat") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = phone,
                    onValueChange = { phone = sanitizeInput(it) },
                    label = { Text("Nomor Telepon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                if (errorMessage != null) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isBlank() || address.isBlank() || phone.isBlank()) {
                    errorMessage = "Please fill all fields correctly."
                } else {
                    coroutineScope.launch {
                        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                        val updatedMember = item.copy(
                            name = name,
                            address = address,
                            phone = phone,
                            updated_at = sdf.format(Date())
                        )
                        viewModel.update(
                            id = updatedMember.id,
                            name = updatedMember.name,
                            address = updatedMember.address,
                            phone = updatedMember.phone,
                            created_at = updatedMember.created_at,
                            updated_at = updatedMember.updated_at
                        )
                        onSave(updatedMember)
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