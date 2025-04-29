package dailybugle.by.lakshminarasimhas3375885

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class ContactUsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactUsScreen()
        }
    }
}

@Composable
fun ContactUsScreen() {
    val context = LocalContext.current as Activity
    var content by remember { mutableStateOf("") }

    val emailAddress = "alnuk0709@gmail.com"


    val makeCall = {
        val phoneNumber = "tel:067575365363"
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse(phoneNumber)
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize().padding(WindowInsets.systemBars.asPaddingValues()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_circle_left_24),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clickable {
                        context.finish()
                    }
            )

            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = "Contact Us",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.iv_gmail),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(96.dp)
            )

            // Email input field
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Write us an email") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )

            // Button to send email
            Button(
                onClick = {
                    sendEmail(context, emailAddress, "Support", content)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send Email")
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.iv_call),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(96.dp)
                )

                Text("Our Phone Service is available\nRing us for any important queries")

                // Button to make a call
                Button(
                    onClick = makeCall,
                    modifier = Modifier
                ) {
                    Text("Call Us")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

        }
    }
}


fun sendEmail(context: Context, to: String, subject: String, body: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:") // Ensures only email apps handle this
        putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    try {
        context.startActivity(Intent.createChooser(intent, "Send Email"))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
    }
}