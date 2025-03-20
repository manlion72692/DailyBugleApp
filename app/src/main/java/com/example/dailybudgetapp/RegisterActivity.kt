package com.example.dailybudgetapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen()
        }
    }
}

@Composable
fun RegisterScreen() {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    val genderOptions = listOf("Male", "Female", "Other")

    var selectedGender by remember { mutableStateOf("Male") }


    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Hey,\nRegister Now!",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Enter Full Name") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle, // Replace with desired icon
                    contentDescription = "Email Icon"
                )
            },
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(text = "Select Gender", style = MaterialTheme.typography.bodyMedium)

        // Gender options as radio buttons

        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            genderOptions.forEach { gender ->
                RadioButton(
                    selected = (gender == selectedGender),
                    onClick = { selectedGender = gender }
                )
                Text(
                    text = gender,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(end = 6.dp)
                )
            }
        }


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter E-Mail") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email, // Replace with desired icon
                    contentDescription = "Email Icon"
                )
            },
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock, // Replace with desired icon
                    contentDescription = "Password Icon"
                )
            },
        )


        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock, // Replace with desired icon
                    contentDescription = "Password Icon"
                )
            },
        )

        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = {

                if (fullName.isEmpty()) {
                    Toast.makeText(context, "Enter FullName", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (email.isEmpty()) {
                    Toast.makeText(context, "Enter Mail", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (password.isEmpty()) {
                    Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (password != confirmPassword) {
                    Toast.makeText(context, "Passwords doesn't match", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                Toast.makeText(context, "All Fields are filled", Toast.LENGTH_SHORT).show()

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 0.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(12.dp))


        Row(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(
                text = "I have an account / ",
                style = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = "Login Now",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black),
                modifier = Modifier.clickable {
                    // Intent to open RegisterActivity
                    context.startActivity(Intent(context, LoginActivity::class.java))
                    context.finish()
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

    }
}