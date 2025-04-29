package dailybugle.by.lakshminarasimhas3375885

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserStoriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewStoriesScreen()
        }
    }
}

@Composable
fun ViewStoriesScreen() {
    val stories = remember { mutableStateListOf<NewsStory>() }

    val context = LocalContext.current as Activity

    LaunchedEffect(Unit) {
        val ref = FirebaseDatabase.getInstance().getReference("NewsStories")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                stories.clear()
                for (child in snapshot.children) {
                    val story = child.getValue(NewsStory::class.java)
                    story?.let { stories.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    Column(modifier = Modifier.fillMaxSize()) {

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
                text = "User Stories",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {

            if (stories.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    items(stories) { story ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = story.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = story.content,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        modifier = Modifier.size(24.dp),
                                        painter = painterResource(id = R.drawable.writer),
                                        contentDescription = ""
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = story.author,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Box(modifier = Modifier.weight(1f))
                {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        text = "No User Stories Found",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }

            Text(
                modifier = Modifier
                    .clickable {

                        context.startActivity(Intent(context, AddStoryActivity::class.java))
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(
                            10.dp
                        )
                    )
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(
                            10.dp
                        )
                    )
                    .padding(vertical = 12.dp, horizontal = 12.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Add Story",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = colorResource(id = R.color.white),
                )
            )
        }
    }
}

data class NewsStory(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val author: String = "App Owner",
)
