package com.example.dailybugleapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyBugleHomeActivity()
        }
    }
}

@Composable
fun DailyBugleHomeActivity() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Welcome to Daily Bugle",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF5D3FD3),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 12.dp)
        )
        Text(
            text = "Hi, User !",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF5D3FD3),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // First row of cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CardWithImageAndText(imageRes = R.drawable.daily_budget, title = "Create New Article",1)
//            CardWithImageAndText(imageRes = R.drawable.daily_budget, title = "Add Contributors",2)
            CardWithImageAndText(imageRes = R.drawable.daily_budget, title = "Search News",2)

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Second row of cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CardWithImageAndText(imageRes = R.drawable.daily_budget, title = "Manage Article",3)
            CardWithImageAndText(imageRes = R.drawable.daily_budget, title = "Delete Article", 4)

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CardWithImageAndText(imageRes = R.drawable.daily_budget, title = "Access Profile",5)
            CardWithImageAndText(imageRes = R.drawable.daily_budget, title = "Logout", 6)

        }
//        CardWithImageAndText(imageRes = R.drawable.daily_budget, title = "Logout", 5)

    }
}



@Composable
fun CardWithImageAndText(imageRes: Int, title: String,cardId: Int) {

    val context = LocalContext.current as Activity


    Card(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .clickable {
//                onCardClicked(cardId)

                when(cardId)
                {
                    1 -> {
                        context.startActivity(Intent(context, CreateNewsFeed::class.java))
                    }

                    2->{
                        context.startActivity(Intent(context, SearchNewsActivity::class.java))

                    }
                }


            },
        shape = RoundedCornerShape(16.dp)
    )

    {
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(64.dp)
                    .width(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DailyBugleHomeActivity()
}