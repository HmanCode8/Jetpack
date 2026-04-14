package com.example.jetcompose

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.jetcompose.services.Http
import com.example.jetcompose.untils.BaseResponse
import es.dmoral.toasty.Toasty


class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { LoginView() }
    }
}

fun doRegister(userName: String, email: String, password: String, currentContent: Context) {
    if (userName == "") {
        Toasty.info(currentContent, "请输入用户名").show()
        return
    }
    if (email == "") {
        Toasty.info(currentContent, "请输入邮箱").show()
        return
    }
    if (password == "") {
        Toasty.info(currentContent, "请输入密码").show()
        return
    }
    val params = mapOf(
        "name" to userName,
        "password" to password,
        "email" to email,
    )
    Http.post("/user/add", params, { isOk, data ->
        (currentContent as Activity).runOnUiThread {
            if (isOk) {
                Toasty.success(currentContent, "add success").show()
            } else {
                Toasty.warning(currentContent, "add Fail").show()
            }
        }

    })
}

fun doLogin(userName: String, password: String, currentContent: Context) {

    if (userName == "") {
        Toasty.info(currentContent, "请输入用户名").show()
        return
    }
    if (password == "") {
        Toasty.info(currentContent, "请输入密码").show()
        return
    }
    val loginParams = mapOf(
        "name" to userName,
        "password" to password
    )
    Http.post("/user/login", loginParams, { loginSuccess, data ->
        (currentContent as Activity).runOnUiThread {
            val d = Http.parseJson<BaseResponse<Any>>(data)
            if (loginSuccess && d?.code == 200) {
                Toasty.success(currentContent, "login success").show()
                val intent = Intent(currentContent, Home::class.java)
                currentContent.startActivity(intent)
                currentContent.finish()
            } else {
                Toasty.warning(currentContent, "login Fail").show()
            }
        }

    })
}
@Composable
fun Loader() {
    // ✅ 核心修复：加上 by 关键字！
    val compositionState = rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.login_logo)
    )
    val composition = compositionState.value

    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        modifier = Modifier.size(200.dp)
    )
}


@Composable
fun LoginView() {
    val currentContent = LocalContext.current
    val isRegister = remember { mutableStateOf(false) }
    val userName = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val avatar = remember { mutableStateOf("https://picsum.photos/200") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
//            .background(brush = Brush.verticalGradient(
//                colors = listOf(
//                    Color(0xFFADC3D5), // 顶部颜色
//                    Color(0xFF66686B),
//                    Color(0xFF353738)  // 底部颜色
//                )
//            ))
            .padding(10.dp)
    ) {
        Loader()
        Row() {
            OutlinedTextField(
                value = userName.value,
                onValueChange = { userName.value = it },
                label = { Text("username")},
            )
        }
        Spacer(modifier = Modifier.height(1.dp))
        Row() {
            OutlinedTextField(
                value = password.value,

                onValueChange = {
                    password.value = it
                },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("password") }
            )
        }
        if (isRegister.value) {
            Row() {
                OutlinedTextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    label = { Text("email") }
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                onClick = {
                    if (isRegister.value) doRegister(
                        userName.value,
                        email.value,
                        password.value,
                        currentContent
                    ) else
                        doLogin(userName.value, password.value, currentContent)
                },
            ) {
                Text(if (isRegister.value) "注册" else "登录")
            }
            Text(text = if (isRegister.value) "返回" else "去注册", modifier = Modifier.clickable {
                isRegister.value = !isRegister.value
            })
        }

    }
}

@Composable
@Preview
fun PreviewLogin() {
    LoginView()
}