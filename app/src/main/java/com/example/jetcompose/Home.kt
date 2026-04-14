package com.example.jetcompose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.jetcompose.services.Http
import com.example.jetcompose.ui.theme.JetComposeTheme
import es.dmoral.toasty.Toasty


class Home : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            JetComposeTheme(false, false) {
                BottomNavPage()
//            }
        }
    }
}


// 底部导航条目数据类
data class NavItem(
    val title: String,
    val icon: ImageVector
)

data class CarouselItem(
    val id: Int,
    val imageResId: Int,
    val contentDescription: String
)

data class UserItem(
    val name: String,
    val id: Long,
    val email: String,
    val password: String,
    val age: Int
)

@Composable
fun BottomNavPage() {
    // 选中状态
    var selectedIndex = remember { mutableStateOf(0) }

    // 导航项
    val navItems = listOf(
        NavItem("首页", Icons.Default.Home),
        NavItem("我的", Icons.Default.Person),
        NavItem("设置", Icons.Default.Settings)
    )

    // 根布局 = 内容 + 底部导航
    Scaffold(
        bottomBar = {
            NavigationBar() {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = selectedIndex.value == index,
                        onClick = {
                            selectedIndex.value = index
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // 页面内容区域
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            // 根据索引切换页面
            when (selectedIndex.value) {
                0 -> HomePage()
                1 -> BaseMapView()
                2 -> CarouselExample_MultiBrowse()
            }
        }
    }
}


@Composable
fun HomePage() {
    val userList = remember { mutableStateListOf<UserItem>() }
    val avatarUrl = "https://picsum.photos/200" // 随机头像
    val c = LocalContext.current
    // ✅ 关键：放到副作用里
    LaunchedEffect(Unit) {
        Http.get("/user/list") { isOk, data ->
            if (isOk) {
                val newUsers = Http.parseJson<List<UserItem>>(data)
                userList.clear()
                userList.addAll(newUsers ?: emptyList())
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        userList.forEach { u ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .padding(5.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(5.dp)
                ) {
                    //                Text(text = u.id.toString())
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = avatarUrl,
                        ),
                        contentDescription = "用户头像",
                        modifier = Modifier
                            .size(20.dp)         // 大小
                            .clip(CircleShape)    // 圆形头像
                    )
                    Text(text = u.name)
                    Text(text = u.email, modifier = Modifier.padding(0.dp,5.dp))
                }

            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CarouselExample_MultiBrowse() {

    val items = remember {
        listOf(
            CarouselItem(0, R.drawable.carouse1, "cupcake"),
            CarouselItem(1, R.drawable.carouse2, "donut"),
            CarouselItem(2, R.drawable.carouse3, "eclair"),
            CarouselItem(3, R.drawable.carouse3, "eclair"),
            CarouselItem(4, R.drawable.carouse3, "eclair"),
            CarouselItem(5, R.drawable.carouse3, "eclair"),
        )
    }
    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { items.count() },
        modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth()
            .padding(top = 16.dp, bottom = 16.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { i ->
        val item = items[i]
        Image(
            modifier = Modifier
                .height(205.dp)
                .maskClip(MaterialTheme.shapes.extraLarge),
            painter = painterResource(id = item.imageResId),
            contentDescription = item.contentDescription,
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
@Preview(
    showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)
fun PreviewHome() {
    BottomNavPage()
}