package com.example.dailybugleapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MyBookMarksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookmarkedArticlesScreen()
        }
    }
}

@Composable
fun BookmarkedArticlesScreen() {
    val context = LocalContext.current as Activity
    val articleDatabase = remember { ArticleDatabase(context) }
    var bookmarkedArticles by remember { mutableStateOf(articleDatabase.getAllBookmarks()) }

    // Delete bookmark handler
    val deleteBookmark: (String) -> Unit = { url ->
        articleDatabase.deleteBookmark(url)
        bookmarkedArticles = articleDatabase.getAllBookmarks() // Update the list
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                text = "BookMarked Articles",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {


            Spacer(modifier = Modifier.height(8.dp))

            if (bookmarkedArticles.isEmpty()) {
                Text("No bookmarked articles.")
            } else {
                LazyColumn {
                    items(bookmarkedArticles) { article ->
                        Card(modifier = Modifier.padding(vertical = 4.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = article.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = article.description ?: "",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row {
                                    IconButton(onClick = { deleteBookmark(article.url) }) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete Bookmark"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
