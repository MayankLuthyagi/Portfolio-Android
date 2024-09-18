package com.example.jetbizcard_portfolio

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetbizcard_portfolio.ui.theme.JetBizCardPortfolioTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetBizCardPortfolioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateBizCard()
                }
            }
        }
    }
}
@Composable
fun LinkedInLink(link:String, name:String) {
    val context = LocalContext.current  // Ensure this is within a composable function

    val annotatedString = buildAnnotatedString {

        pushStringAnnotation(tag = "URL", annotation = link)
        withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
            append(name)
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { stringAnnotation ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(stringAnnotation.item))
                    context.startActivity(intent)  // Using the context to start the activity
                }
        },
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun CreateBizCard(){
    val buttonClickedState = remember {
        mutableStateOf(false)
    }
    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()){
        Card(modifier = Modifier
            .width(200.dp)
            .height(390.dp)
            .padding(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp))
        ){
            Column(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CreateImageProfile(imageRecource = R.drawable.profile_pic)
                Divider()
                CreateInfo()
                Button(onClick = {
                    buttonClickedState.value = !buttonClickedState.value

                }) {
                    Text(text = "Portfolio")
                    
                }
                if(buttonClickedState.value){
                    Content()
                }else{
                    Box(){

                    }
                }

            }
        }
    }
}
//@Preview
@Composable
private fun Content(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(5.dp)){
        Surface(modifier = Modifier
            .padding(3.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(6.dp)),
            border = BorderStroke(width = 2.dp, color = Color.LightGray)
            ){
            Portfolio(data = listOf("Project 1", "Project 2"), data2 = listOf("JustChat- A Messaging App", "SocialPulse-Sentiment_Analysis")
            ,data3= listOf("https://github.com/MayankLuthyagi/JustChat-Messaging_App", "https://github.com/MayankLuthyagi/SocialPulse-Sentiment_Analysis"))
        }
    }
}

@Composable
fun Portfolio(data: List<String>, data2: List<String>, data3: List<String>) {
    LazyColumn {
        // Correct destructuring with Triple
        items(data.zip(data2).zip(data3) { firstPair, thirdItem ->
            Triple(firstPair.first, firstPair.second, thirdItem)
        }) { item ->
            val (item1, item2, item3) = item  // Destructure the Triple

            Card(
                modifier = Modifier
                    .padding(13.dp)
                    .fillMaxWidth(),
                shape = RectangleShape,
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(7.dp)
                ) {
                    CreateImageProfile(
                        modifier = Modifier.size(100.dp),
                        imageRecource = R.drawable.github_pic
                    )
                    Column {
                        Text(
                            text = item1,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(5.dp)
                        )
                        LinkedInLink(item3,item2) // Correct the LinkedInLink usage
                    }
                }
            }
        }
    }
}


@Composable
private fun CreateInfo() {
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mayank Singh",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Android Developer",
            modifier = Modifier.padding(5.dp)
        )
        Row {
            Text(text = "connect with me", modifier = Modifier.padding(7.dp))
            LinkedInLink("https://www.linkedin.com/in/mayank012/", "LinkedIn")
        }
    }
}

@Composable
private fun CreateImageProfile(modifier: Modifier=Modifier, imageRecource : Int)
    {
        Surface(
            modifier = modifier
                .size(150.dp)
                .padding(16.dp),
            shape = CircleShape,
            border = BorderStroke(0.5.dp, Color.LightGray),
            tonalElevation = 4.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ) {
            Image(
                painter = painterResource(id = imageRecource),
                contentDescription = "Profile Image",
                modifier = modifier.size(135.dp),
                contentScale = ContentScale.Crop
            )
        }
    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetBizCardPortfolioTheme {
        CreateBizCard()
    }
}