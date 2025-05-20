package com.arise.network.screens.help

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelpCenter(navController = rememberNavController())
        }
    }
}

@Composable
fun HelpCenter(navController: NavHostController) {
    var messages by remember {
        mutableStateOf(
            listOf("Support: Hello! How can we assist you today?")
        )
    }
    var currentMessage by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message = message)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            BasicTextField(
                value = currentMessage,
                onValueChange = { currentMessage = it },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFF0F0F0), RoundedCornerShape(24.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (currentMessage.text.isNotBlank()) {
                        messages = messages + "You: ${currentMessage.text}"
                        messages = messages + "Support: Thank you for reaching out! We'll assist you shortly."
                        currentMessage = TextFieldValue("")
                    }
                },
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun ChatBubble(message: String) {
    val isUser = message.startsWith("You:")

    Row(
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (isUser) MaterialTheme.colorScheme.primary else Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.removePrefix("You:").removePrefix("Support:").trim(),
                color = if (isUser) Color.White else Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHelpCenter() {
    HelpCenter(rememberNavController())
}
