package com.example.dailybugleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow

class SearchNewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NewsAppScreen(dummyNewsList)
        }
    }
}

data class NewsDataItem(
    val title: String,
    val summary: String,
    val category: String,
    val language: String
)

val dummyNewsList = listOf(
    NewsDataItem("Election Results Announced", "Major changes in the political landscape.", "Politics", "English"),
    NewsDataItem("Star Player Wins Match", "A stunning victory in the finals.", "Sports", "English"),
    NewsDataItem("Robbery in Downtown", "Investigation underway after late-night robbery.", "Crime", "English"),
    NewsDataItem("New Environmental Policy", "The government launches new green initiative.", "Politics", "Hindi"),
    NewsDataItem("Olympics 2024 Updates", "Medal tally and highlights from day 3.", "Sports", "Hindi"),
    NewsDataItem("Cyber Crime Rises", "Authorities report increasing cyber threats.", "Crime", "Hindi"),
    NewsDataItem("Budget 2025 Released", "Finance minister reveals key economic decisions.", "Politics", "English"),
    NewsDataItem("Tennis Championship Begins", "Top seeds prepare for opening round.", "Sports", "English"),
    NewsDataItem("Crime Ring Busted", "Police arrest 12 in large criminal operation.", "Crime", "English"),
    NewsDataItem("Political Debate Turns Heated", "Tensions rise in national debate.", "Politics", "English"),
    NewsDataItem("Football League Kicks Off", "Fans excited as season begins.", "Sports", "Spanish"),
    NewsDataItem("New Crime Thriller Released", "Top author drops bestseller.", "Crime", "Spanish"),
    NewsDataItem("Presidential Speech Highlights", "Key takeaways from address.", "Politics", "Spanish"),
    NewsDataItem("Basketball World Cup", "Day 1 action recap.", "Sports", "French"),
    NewsDataItem("Bank Fraud Uncovered", "Major financial scandal breaks.", "Crime", "French")
)

@Composable
fun NewsAppScreen(newsList: List<NewsDataItem>) {
    var selectedLanguage by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    val filteredNews = newsList.filter {
        (selectedLanguage.isEmpty() || it.language.contains(selectedLanguage, ignoreCase = true)) &&
                (selectedCategory.isEmpty() || it.category.equals(selectedCategory, ignoreCase = true))
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_circle_left_24),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clickable {
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
            modifier = Modifier.fillMaxSize()
                .padding(12.dp)
        ) {


            Text("Search News", fontSize = 22.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = selectedLanguage,
                onValueChange = { selectedLanguage = it },
                label = { Text("Search by Language") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            CategoryChips(
                categories = listOf("Politics", "Sports", "Crime"),
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("Top News", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            NewsList(newsItems = filteredNews.take(5))

            Spacer(modifier = Modifier.height(20.dp))

            Text("Featured Stories", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            NewsList(newsItems = filteredNews.drop(5))
        }
    }
}

@Composable
fun CategoryChips(categories: List<String>, selectedCategory: String, onCategorySelected: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        categories.forEach { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(if (selectedCategory == category) "" else category) },
                label = { Text(category) }
            )
        }
    }
}

@Composable
fun NewsList(newsItems: List<NewsDataItem>) {
    LazyColumn {
        items(newsItems) { news ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = news.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = news.summary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Category: ${news.category} | Language: ${news.language}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsAppPreview() {
    NewsAppScreen(newsList = dummyNewsList)
}
