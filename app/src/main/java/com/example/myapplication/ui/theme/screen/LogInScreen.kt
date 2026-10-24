package com.example.myapplication.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Champagne
import com.example.myapplication.ui.theme.Onyx

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Image(
            painter = painterResource(id = R.drawable.bg_bank_building),
            contentDescription = "Bank Building Background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "SAVEIT BANK",
                color = Champagne,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "SAVE SMART. LIVE BETTER.",
                color = Champagne.copy(alpha = 0.8f),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(56.dp))


            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = {
                    Text(
                        "Account Number / Username",
                        color = Champagne.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Champagne),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Champagne,
                    unfocusedBorderColor = Champagne.copy(alpha = 0.6f),
                    focusedLabelColor = Champagne,
                    cursorColor = Champagne,
                    focusedTextColor = Champagne,
                    unfocusedTextColor = Champagne
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        "Mobile PIN / Password",
                        color = Champagne.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Champagne),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Champagne,
                    unfocusedBorderColor = Champagne.copy(alpha = 0.6f),
                    focusedLabelColor = Champagne,
                    cursorColor = Champagne,
                    focusedTextColor = Champagne,
                    unfocusedTextColor = Champagne
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = { onLoginSuccess() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Champagne,
                    contentColor = Onyx
                )
            ) {
                Text(
                    text = "Log In Securely",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}