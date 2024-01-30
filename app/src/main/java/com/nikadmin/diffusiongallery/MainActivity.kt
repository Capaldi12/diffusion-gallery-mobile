package com.nikadmin.diffusiongallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.nikadmin.diffusiongallery.ui.theme.DiffusionGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiffusionGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentTab by remember { mutableIntStateOf(0) }
                    val tabs = listOf("Images", "Prompts")

                    Column (modifier = Modifier.fillMaxSize()) {
                        TabRow(selectedTabIndex = currentTab) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(title) },
                                    selected = currentTab == index,
                                    onClick = { currentTab = index },
                                )
                            }
                        }

                        when (currentTab) {
                            0 -> ImageList(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            )
                            1 -> PromptList(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize()
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ImageList(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsState()

    Column (
        modifier = modifier,
    ) {
        when (val status = state.value.status) {
            is Ready -> {
                var imageSize by remember { mutableStateOf(IntSize.Zero) }

                val gradient = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black),
                    startY = imageSize.height.toFloat()/3,  // 1/3
                    endY = imageSize.height.toFloat()
                )

                LazyColumn (
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ){
                    items(state.value.images) {image ->
                        Box (
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned {
                                    imageSize = it.size
                                },
                        ) {
                            AsyncImage(
                                model = image.src,
                                contentDescription = image.name,
                                modifier = Modifier.fillMaxWidth(),
                                error = painterResource(R.drawable.error),
                                contentScale = ContentScale.FillWidth,
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(gradient)
                            ) {
                                Text(
                                    text = image.name,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(bottom = 8.dp),
                                    style = MaterialTheme.typography.displaySmall,
                                    color = Color.White,
                                )
                            }
                        }
                    }
                }
            }
            is Loading -> {
                LoadingCard(
                    modifier = Modifier.fillMaxSize(),
                )
            }
            is Error -> {
                ErrorCard(
                    message = status.message,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun PromptList(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsState()

    Column (
        modifier = modifier,
    ) {

        when (val status = state.value.status) {
            is Ready -> {
                LazyColumn (
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.value.prompts) {prompt ->
                        Card (
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                            ) {
                                Text(
                                    text = prompt.name,
                                    style = MaterialTheme.typography.titleLarge,
                                )
                                Text(prompt.text)
                            }
                        }
                    }
                }
            }
            is Loading -> {
                LoadingCard(
                    modifier = Modifier.fillMaxSize(),
                )
            }
            is Error -> {
                ErrorCard(
                    message = status.message,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Composable
fun LoadingCard(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
        )
    }
}


@Composable
fun ErrorCard(
    message: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text("An error has occurred: $message")
    }
}