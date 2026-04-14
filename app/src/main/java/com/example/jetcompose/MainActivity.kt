package com.example.jetcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


class MainActivity : ComponentActivity() {
    private val arr = arrayOf("d", "d")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { LayoutBasic() }
    }
}

data class User(
    val name: String,
    val id: Int,
    val age: Int,
    val post: String
)

val Students: List<User> = listOf(
    User("jack", 1, 12, "前端"),
    User("David", 11, 12, "后端"),
)
@Composable
fun LayoutBasic() {
    val padding = 20.dp
    Column () {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.background(Color.Green).fillMaxWidth()
        ) {

            Text(text = stringResource(R.string.app_name), fontWeight = FontWeight.Bold,color= Color.Red)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()

        ) {
            StudentList()
            Box() {
                Image(painterResource(R.drawable.img), contentDescription = "image")
                Icon(Icons.Filled.Check, contentDescription = "Check mark")
            }
        }
    }

}

@Composable
fun StudentList() {
    val c = LocalContext.current
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(Students) { s ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clickable() {
                    Toast.makeText(c, s.name, Toast.LENGTH_SHORT).show()
                }.padding(5.dp)

            ) {

                Text(
                    text = s.id.toString(),
                    Modifier.size(10.dp, 10.dp),
                    color = Color.Green,
                    fontWeight = FontWeight.Bold
                )
                Text(text = s.post)
                Text(text = s.name)
            }
        }
    }
}



@Preview
@Composable
fun preview(){
    LayoutBasic()
}