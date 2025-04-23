package com.example.dailybugleapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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


//00abacfdd786437b8c22413a603fd137

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

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            painter = painterResource(id = R.drawable.ic_dailybugle),
            contentDescription = "Lakshminarasimha Anipakula App",
        )

        Spacer(modifier = Modifier.height(16.dp))

        // First row of cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CardWithImageAndText(
                imageRes = R.drawable.iv_breakingnews,
                title = "Breaking News",
                1
            )
//            CardWithImageAndText(imageRes = R.drawable.daily_budget, title = "Add Contributors",2)
            CardWithImageAndText(imageRes = R.drawable.iv_search_news, title = "Search News", 2)

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Second row of cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CardWithImageAndText(imageRes = R.drawable.iv_bookmarked, title = "BookMarked Article", 3)
            CardWithImageAndText(imageRes = R.drawable.iv_contactus, title = "Contact Us", 4)

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CardWithImageAndText(imageRes = R.drawable.iv_profile, title = "Access Profile", 5)
            CardWithImageAndText(imageRes = R.drawable.iv_logout, title = "Logout", 6)

        }
//        CardWithImageAndText(imageRes = R.drawable.daily_budget, title = "Logout", 5)

    }
}


@Composable
fun CardWithImageAndText(imageRes: Int, title: String, cardId: Int) {

    val context = LocalContext.current as Activity


    Card(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .clickable {
//                onCardClicked(cardId)

                when (cardId) {
                    1 -> {
                        context.startActivity(Intent(context, BreakingNewsActivity::class.java))
                    }

                    2 -> {
                        context.startActivity(Intent(context, NewsByCountryActivity::class.java))

                    }

                    3 -> {
                        context.startActivity(Intent(context, MyBookMarksActivity::class.java))

                    }

                    4 -> {
                        context.startActivity(Intent(context, ContactUsActivity::class.java))

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