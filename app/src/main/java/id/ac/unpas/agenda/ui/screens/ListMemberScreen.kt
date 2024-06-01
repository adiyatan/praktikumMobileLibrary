package id.ac.unpas.agenda.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import id.ac.unpas.agenda.models.Member
import id.ac.unpas.agenda.ui.composables.ConfirmationDialog
import id.ac.unpas.agenda.ui.composables.MemberCard
import id.ac.unpas.agenda.ui.theme.Blue40
import id.ac.unpas.agenda.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun ListMemberScreen(modifier: Modifier = Modifier, onDelete: () -> Unit, onClick: (String) -> Unit) {

    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<MemberViewModel>()
    val search = remember { mutableStateOf("") }

    var selectedMember by remember { mutableStateOf<Member?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }

    val list: List<Member> by viewModel.members.observeAsState(listOf())
    val title = remember { mutableStateOf("DAFTAR ANGGOTA") }
    val openDialog = remember { mutableStateOf(false) }
    val activeId = remember { mutableStateOf("") }
    val deleting = remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "DAFTAR ANGGOTA", fontSize = 24.sp,
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
                placeholder = { Text(text = "Cari Anggota", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(color = White),
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
                MemberCard(
                    name = item.name,
                    phone = item.phone,
                    address = item.address,
                ) {
                    // Set the selected member and show the edit dialog
                    selectedMember = item
                    showEditDialog = true
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
            title.value = "DAFTAR ANGGOTA"
        }
    }

    viewModel.isDeleted.observe(LocalLifecycleOwner.current) {
        if (deleting.value && it) {
            deleting.value = false
            onDelete()
        }
    }

    if (showEditDialog && selectedMember != null) {
        MemberEditDialog(item = selectedMember!!, onDismiss = { showEditDialog = false }) { updatedMember ->
            // Handle saving the updated member
            scope.launch {
                viewModel.update(
                    id = updatedMember.id,
                    name = updatedMember.name,
                    address = updatedMember.address,
                    phone = updatedMember.phone,
                    created_at = updatedMember.created_at,
                    update_at = updatedMember.updated_at
                )
                showEditDialog = false
            }
        }
    }
}