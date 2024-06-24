package com.example.allaroundworld.screen

import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.allaroundworld.R
import com.example.allaroundworld.network.NewsModel
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.util.Locale

@Composable
fun NewsDetailScreen(article: String, navHostController: NavHostController) {
    val article = remember(article) {
        article.let {
            val decodedJson = URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            Gson().fromJson(decodedJson, NewsModel.Article::class.java)
        }
    }
    val context = LocalContext.current
    lateinit var textToSpeech: TextToSpeech

    if (article != null) {
        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                TopAppBar(
                    title = { Text("All Around World") },
                    modifier = Modifier.height(48.dp),
                    navigationIcon = {
                        IconButton(onClick = {
                            navHostController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    textToSpeech = TextToSpeech(LocalContext.current) { status ->
                        if (status != TextToSpeech.ERROR) {
                            textToSpeech.language = Locale.UK
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    if (article.title == null) {
                        Text(
                            text = "No Title Found",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif,
                            maxLines = 3
                        )
                    } else {
                        Text(
                            text = article.title,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 26.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif,
                            maxLines = 3
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Card {
                        Row(modifier = Modifier.fillMaxWidth().height(265.dp)) {
                            if (article.urlToImage == null) {
                                Image(
                                    painter = painterResource(id = R.drawable.newslogobig),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(265.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                            } else {
                                AsyncImage(
                                    model = article.urlToImage,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(265.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    if (article.content == null) {
                        Text(
                            text = "No Content Available",
                            fontSize = 16.sp
                        )
                    } else {
                        Text(
                            text = article.content,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                textToSpeech.speak(
                                    article.content.toString(),
                                    TextToSpeech.QUEUE_FLUSH,
                                    null
                                )
                            },
                            modifier = Modifier
                                .padding(2.dp)

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.volume_up_24px),
                                contentDescription = "VolumeButton"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (article.author == null) {
                            Text(text = "Author Unknown")
                        } else {
                            Text(text = article.author)
                        }

                        if (article.publishedAt == null) {
                            Text(text = "No Date")
                        } else {
                            Text(text = article.publishedAt)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, article.content)
                                type = "text/plain"
                            }
                            context.startActivity(
                                Intent.createChooser(shareIntent, "Share news article")
                            )
                        }) {
                            Text("Share")
                        }
                    }
                }
            }
        }
    } else {

        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                TopAppBar(
                    title = { Text("All Around World") },
                    modifier = Modifier.height(48.dp),
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = "No Title",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Card {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(id = R.drawable.newslogobig),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(225.dp),
                                contentScale = ContentScale.FillBounds
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "No Data",
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                            },
                            modifier = Modifier
                                .padding(2.dp)

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.volume_up_24px),
                                contentDescription = "VolumeButton"
                            )
                        }
                    }
                }
            }
        }
    }
}
