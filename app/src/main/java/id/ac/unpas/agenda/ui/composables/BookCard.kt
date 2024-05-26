package id.ac.unpas.agenda.ui.composables

import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.ac.unpas.agenda.R
import id.ac.unpas.agenda.models.Todo
import id.ac.unpas.agenda.ui.composables.ConfirmationDialog
import id.ac.unpas.agenda.ui.theme.Gray40
import id.ac.unpas.agenda.ui.theme.White40
import id.ac.unpas.agenda.ui.theme.Yellow60
import kotlinx.coroutines.launch

@Composable
fun BookCard(name1: String, name2: String, qty: Int, date: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Yellow60),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onClick)
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text =  if (name1.length > 23) "${name1.take(23)}..." else name1,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text =  if (name2.length > 23) "${name2.take(23)}..." else name2,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Row(){
                    Text(
                        text = qty.toString(),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        text = "|",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Text(
                        text = SimpleDateFormat("yyyy", Locale.getDefault()).format(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource( id = R.drawable.settings), contentDescription = "Login Logo", modifier = Modifier
                        .width(40.dp),
                    colorFilter = ColorFilter.tint(Color.Black),

                    )
            }
        }
    }
}
