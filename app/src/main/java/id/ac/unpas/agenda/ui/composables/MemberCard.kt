package id.ac.unpas.agenda.ui.composables

import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.ac.unpas.agenda.R
import id.ac.unpas.agenda.ui.theme.Gray40
import id.ac.unpas.agenda.ui.theme.White40
import id.ac.unpas.agenda.ui.theme.Yellow60
import kotlinx.coroutines.launch

@Composable
fun MemberCard(name: String, address: String, phone: String, onClick: () -> Unit) {
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
                    text =  if (name.length > 18) "${name.take(18)}..." else name,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W800,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text =  if (address.length > 18) "${address.take(18)}..." else address,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text =  phone,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600,
                    modifier = Modifier.padding(end = 16.dp)
                )
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
