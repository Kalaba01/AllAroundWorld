package com.example.allaroundworld.screen

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.allaroundworld.R
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeApp(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel,
    navHostController: NavHostController
) {

    var showMenu by remember { mutableStateOf(false) }
    lateinit var textToSpeech: TextToSpeech
    val res = viewModel.res.value?.articles ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AllAroundWorld") },
                modifier = Modifier.height(48.dp),
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.Menu, contentDescription = "Category Menu")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        val countries = listOf(
                            "Korea" to "kr",
                            "Japan" to "jp",
                            "USA" to "us",
                            "Germany" to "de",
                            "India" to "in",
                            "Saudi" to "sa",
                            "Canada" to "ca",
                            "UAE" to "ae",
                            "NewZeaLand" to "nz",
                            "Austria" to "at",
                            "Ireland" to "ie",
                            "UK" to "gb",
                            "Italy" to "it",
                            "South Africa" to "za",
                            "Turkey" to "tr",
                            "Russia" to "ru"
                        )
                        countries.forEach { (name, code) ->
                            DropdownMenuItem(
                                text = { Text(name) },
                                onClick = {
                                    viewModel.updateCountry(code)
                                    showMenu = false
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(res.size) { index ->

                val article = res[index]
                textToSpeech = TextToSpeech(LocalContext.current) { status ->
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.language = Locale.UK
                    }
                }

                Card(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth()
                        .then(
                            if (index == 0) {
                                Modifier
                                    .height(265.dp)
                            } else {
                                Modifier
                                    .fillMaxWidth()
                                    .height(143.dp)
                            }
                        )
                ) {
                    if (index == 0) {
                        Column {

                            if (res[index]!!.urlToImage == null) {
                                Image(
                                    painter = painterResource(id = R.drawable.newslogobig),
                                    contentDescription = "NewsLogo", modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp),
                                    contentScale = ContentScale.FillBounds
                                )
                            } else {
                                AsyncImage(
                                    model = res[index]!!.urlToImage,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = res[index]!!.title!!,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2,
                                    textAlign = TextAlign.Start
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    if (res[index]!!.author != null && res[index]!!.publishedAt != null) {
                                        Text(
                                            text = "By ${res[index]!!.author!!}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                        Text(
                                            text = res[index]!!.publishedAt!!,
                                            fontSize = 12.sp,
                                            color = Color.Gray,
                                            maxLines = 1
                                        )
                                    } else if (res[index]!!.author == null) {
                                        Text(
                                            text = "Author Unknown",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    } else if (res[index]!!.publishedAt == null) {
                                        val currentTime = Date()
                                        val formatter =
                                            SimpleDateFormat("yyyy:M:d", Locale.getDefault())
                                        val formattedTime = formatter.format(currentTime)

                                        Text(
                                            text = formattedTime,
                                            modifier = Modifier.padding(
                                                end = 8.dp,
                                                top = 4.dp,
                                            ),
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }

                                    Row(modifier = Modifier
                                        .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween) {

                                        TextButton(
                                            onClick = {

                                                val articleJson = Gson().toJson(article)
                                                val encodedJson = URLEncoder.encode(
                                                    articleJson,
                                                    StandardCharsets.UTF_8.toString()
                                                )
                                                navHostController.navigate("newsDetail/$encodedJson")
                                            },
                                            modifier = Modifier
                                        ) {
                                            Text("Read More")
                                        }

                                        IconButton(onClick = {
                                            textToSpeech.speak(
                                                res[index]!!.description.toString(),
                                                TextToSpeech.QUEUE_FLUSH,
                                                null
                                            )
                                        })
                                        {
                                            Icon(
                                                painter = painterResource(id = R.drawable.volume_up_24px),
                                                contentDescription = "Description of the button action"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            if (res[index]!!.urlToImage == null) {
                                Image(
                                    painter = painterResource(id = R.drawable.newslogo),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(120.dp)
                                        .height(150.dp),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                AsyncImage(
                                    model = res[index]!!.urlToImage,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(120.dp)
                                        .width(150.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = res[index]!!.title!!,
                                    modifier = Modifier.padding(start = 8.dp),
                                    fontSize = 16.sp,
                                    maxLines = 3
                                )
                                if (res[index]!!.author != null && res[index]!!.publishedAt != null) {
                                    Row {
                                        Text(
                                            text = "By ${res[index]!!.author!!}",
                                            modifier = Modifier.padding(
                                                start = 8.dp,
                                                top = 4.dp
                                            ),
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = res[index]!!.publishedAt!!,
                                            modifier = Modifier.padding(end = 8.dp, top = 4.dp),
                                            fontSize = 12.sp,
                                            color = Color.Gray,
                                            maxLines = 1
                                        )
                                    }
                                } else if (res[index]!!.author == null) {
                                    Row {
                                        Text(
                                            text = "Author Unknown",
                                            modifier = Modifier.padding(
                                                start = 8.dp,
                                                top = 4.dp
                                            ),
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                } else if (res[index]!!.publishedAt == null) {

                                    val currentTime = Date()
                                    val formatter =
                                        SimpleDateFormat("yyyy:M:d", Locale.getDefault())
                                    val formattedTime = formatter.format(currentTime)

                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(
                                        text = formattedTime,
                                        modifier = Modifier.padding(end = 8.dp, top = 4.dp),
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    TextButton(
                                        onClick = {

                                            val articleJson = Gson().toJson(article)
                                            val encodedJson = URLEncoder.encode(
                                                articleJson,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navHostController.navigate("newsDetail/$encodedJson")
                                        },
                                        modifier = Modifier
                                    ) {
                                        Text("Read More")
                                    }
                                    IconButton(
                                        onClick = {
                                            textToSpeech.speak(
                                                res[index]!!.description.toString(),
                                                TextToSpeech.QUEUE_FLUSH,
                                                null
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.volume_up_24px),
                                            contentDescription = "Description of the button action"
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
