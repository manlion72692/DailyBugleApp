package dailybugle.by.lakshminarasimhas3375885

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json

class NewsByCountryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsSearchScreen(::onBookMarkClicked)
        }
    }

    private fun onBookMarkClicked(article: Article) {
        val dbHelper = ArticleDatabase(this)
        dbHelper.addBookmark(article)
        Toast.makeText(this, "BookMark Successfull", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun NewsSearchScreen(onBookmarkClick: (Article) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    var newsList by remember { mutableStateOf<List<Article>>(emptyList()) }
    var loading by remember { mutableStateOf(false) }

    var isBookmarked by remember { mutableStateOf(false) }

    val context = LocalContext.current


    // Perform the search when the user submits the search query
    LaunchedEffect(searchQuery) {
        if (searchQuery.isNotEmpty()) {
            loading = true
            newsList = fetchNewsByKeyword(searchQuery)
            loading = false
        }
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
                        (context as Activity).finish()
                    }
            )

            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = "Search News",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {


            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search for news") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (loading && searchQuery.isNotEmpty()) {
                CircularProgressIndicator()
            } else {
                if(newsList.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(newsList) { article ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(text = article.title, fontWeight = FontWeight.Bold)
                                    article.description?.let {
                                        Text(text = it, style = MaterialTheme.typography.bodySmall)
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Text(
                                            text = "Published on: ${article.publishedAt}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.weight(1f))

                                        IconButton(
                                            onClick = {
                                                isBookmarked = !isBookmarked
                                                onBookmarkClick(article) // Trigger the bookmark saving
                                            },
                                            modifier = Modifier
                                                .padding(top = 8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.FavoriteBorder,
                                                contentDescription = if (isBookmarked) "Remove from bookmarks" else "Add to bookmarks"
                                            )
                                        }

                                    }
                                }
                            }
                        }
                    }
                }else{
                    Text(text = "Start Searching for news...")
                }
            }
        }
    }
}


suspend fun fetchNewsByKeyword(keyword: String): List<Article> {
    val apiKey = "00abacfdd786437b8c22413a603fd137"
    val url =
        "https://newsapi.org/v2/everything?q=$keyword&from=2025-04-01&sortBy=popularity&apiKey=$apiKey"

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    val response: NewsResponse = client.get(url).body()
    client.close()

    return response.articles
}
