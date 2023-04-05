package com.example.searchgifs.ui.components

import android.os.Build.VERSION.SDK_INT
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.searchgifs.network.responses.GifsItem
//import kotlinx.coroutines.FlowPreview
//import kotlinx.coroutines.flow.MutableStateFlow

//@OptIn(FlowPreview::class)   Maybe rebuild this all to flow
@Preview
@Composable
fun MainContent(viewModel: MainViewModel = hiltViewModel()) {

    val query: MutableState<String> = remember { mutableStateOf("") }
    val result = viewModel.list.value


    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(8.dp)) {

            OutlinedTextField(value = query.value, onValueChange = {
                query.value = it
                viewModel.getImageList(query.value)


            }, enabled = true,
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                label = { Text(text = "Search here...") },
                modifier=Modifier.fillMaxWidth()

            )



            if (result.isLoading) {
                Log.d("TAG", "MainContent: in the loading")
             //   Log.d("GifURL", ": ${viewModel.list.value.data}")
                Box(modifier = Modifier
                    .fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }


            if (result.error.isNotBlank()) {
              //  Log.d("TAG", "MainContent: ${result.error}")
                Box(modifier = Modifier
                    .fillMaxSize()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = viewModel.list.value.error

                    )
                }
            }


            if (result.data.isNotEmpty()) {

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(300.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(10.dp))
                    {

                    Log.d("TAG", "MainContent: Your Token")
                        viewModel.list.value.data?.let {
                            items(it) {
                                MainContentItem(it)
                            }
                    }


                }

            }


        }
    }


}

@Composable
fun MainContentItem(gifsItem: GifsItem) {

   // val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (SDK_INT >= 28) {  //maybe upper sdk version in build.gradle
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Column(
        modifier = Modifier

            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = rememberAsyncImagePainter(gifsItem.images.original.url, imageLoader),
            contentDescription = gifsItem.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(gifsItem.images.original.height.toInt().dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxSize()
        )

    }

}