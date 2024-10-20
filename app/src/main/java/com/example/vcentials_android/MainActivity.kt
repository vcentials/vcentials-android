package com.example.vcentials_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.vcentials_android.ui.theme.TextGrey
import com.example.vcentials_android.ui.theme.ValenciaRed
import com.example.vcentials_android.ui.theme.VcentialsandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VcentialsandroidTheme {
                LoginScreen(modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val username = remember { mutableStateOf(" ") }
    val password = remember { mutableStateOf(" ") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ValenciaRed)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Sign In", style = MaterialTheme.typography.headlineLarge, color = TextGrey)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle login logic here */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = TextGrey
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In", color = TextGrey)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Forgot Password?",
            color = TextGrey,
            modifier = Modifier
                .clickable { /* TODO Password Recovery Action */ }
                .padding(top = 8.dp),
            style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline)
        )


        Spacer(modifier = Modifier.height(120.dp))

        // Sign up for up for users that don't have a login
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {

                Text(
                    text = "Don't have an account? ",
                    color = TextGrey
                )

                Text(
                    text = "Sign Up",
                    color = TextGrey,
                    modifier = Modifier
                        .clickable {/* TODO Sign Up Action */ },
                    style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline)

                )

            }
        }
    }
}
/*
    @Preview(showBackground = true)
    @Composable
    fun LoginScreenPreview() {
        VcentialsandroidTheme {
            LoginScreen()
        }
    }
*/