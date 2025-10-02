package com.example.noxMovie.screens.tabs

import MovieScreen
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import com.example.noxMovie.model.Search
import com.example.noxMovie.network.apiMainMovieCall
import com.example.noxMovie.screens.SearchBar
import com.orhanobut.hawk.Hawk

@Composable
fun SearchItem() {
    val context = LocalContext.current
    var moviesListStates by remember { mutableStateOf<List<Search>?>(null) }
    val naviagtor = LocalNavigator.current
    var textFiled by remember { mutableStateOf("") }
    var updateList by remember { mutableStateOf<List<Search>?>(emptyList()) }
    val movieActors = listOf(
        listOf("Christian Bale", "Michael Caine", "Liam Neeson"), // Batman Begins
        listOf("Robert Pattinson", "Zoë Kravitz", "Paul Dano"),   // The Batman
        listOf("Ben Affleck", "Henry Cavill", "Gal Gadot"),       // Batman v Superman
        listOf("Michael Keaton", "Jack Nicholson", "Kim Basinger"), // Batman (1989)
        listOf("Michael Keaton", "Danny DeVito", "Michelle Pfeiffer"), // Batman Returns
        listOf("George Clooney", "Arnold Schwarzenegger", "Uma Thurman"), // Batman & Robin
        listOf("Val Kilmer", "Jim Carrey", "Nicole Kidman"),      // Batman Forever
        listOf("Will Arnett", "Michael Cera", "Rosario Dawson")   // The Lego Batman Movie
    )

    val movieDes: Array<Pair<String, String>> = arrayOf(
        "Batman Begins" to "بروس وین پس از قتل والدینش به سراسر جهان سفر می‌کند تا مهارت‌های مبارزه با جرم و جنایت را یاد بگیرد. او با لیگ سایه‌ها تمرین می‌کند و سپس به گاتهام بازمی‌گردد تا به عنوان بتمن جلوی جنایتکاران بایستد.",
        "The Batman" to "بتمن در دومین سال مبارزه‌اش با جنایت، فساد گسترده در گاتهام را کشف می‌کند و باید با ریدلر، قاتلی مرموز که رهبران شهر را هدف قرار داده روبرو شود.",
        "Batman v Superman: Dawn of Justice" to "پس از نبردهای ویرانگر سوپرمن، بتمن او را تهدیدی برای بشریت می‌بیند. نبرد میان دو قهرمان شکل می‌گیرد، اما در همین حال تهدیدی بزرگ‌تر در کمین است.",
        "Batman (1989)" to "بتمن باید با جوکر، خلافکار دیوانه‌ای که قصد دارد گاتهام را به هرج و مرج بکشاند، مبارزه کند.",
        "Batman Returns" to "بتمن در برابر دو دشمن جدید قرار می‌گیرد: پنگوئن که می‌خواهد کنترل گاتهام را به دست بگیرد و زن گربه‌ای که اهداف شخصی خود را دنبال می‌کند.",
        "Batman & Robin" to "بتمن و رابین به همراه بت‌گرل در برابر آقای فریز و پویزن آیوی قرار می‌گیرند تا جلوی نابودی شهر را بگیرند.",
        "Batman Forever" to "بتمن به همراه رابین تازه‌کارش باید با ریدلر و تو فیس مبارزه کند که قصد نابودی گاتهام را دارند.",
        "The Lego Batman Movie" to "بتمن لگویی باید با جوکر و دشمنان قدیمی‌اش مبارزه کند، در حالی که یاد می‌گیرد خانواده و همکاری را بپذیرد.",
        "Batman: Mask of the Phantasm" to "بتمن درگیر پرونده‌ای می‌شود که با گذشته بروس وین و عشق قدیمی‌اش مرتبط است، در حالی که دشمنی مرموز به نام فانتاسم ظاهر می‌شود.",
        "Batman: The Dark Knight Returns" to "بروس وین ۵۵ ساله از بازنشستگی خارج می‌شود تا دوباره بتمن شود و با افزایش جرم و جنایت و دشمنان قدیمی مانند جوکر روبرو گردد.",
        "Batman: Under the Red Hood" to "بتمن با جنایتکاری جدید به نام رد هود روبرو می‌شود که شیوه‌های خشن‌تری نسبت به خودش دارد و گذشته بروس را به چالش می‌کشد.",
        "Batman: Year One" to "این داستان آغازین بتمن است؛ بروس وین پس از سال‌ها به گاتهام بازمی‌گردد تا با فساد و جنایت مبارزه کند، در حالی که جیم گوردون تازه به شهر آمده است.",
        "Batman: Hush" to "بتمن درگیر معمایی می‌شود که تمام دشمنانش را به هم پیوند می‌زند و ردپایی از فردی مرموز به نام هاش را نشان می‌دهد.",
        "Batman: Soul of the Dragon" to "بتمن و متحدان رزمی‌کارش با تهدیدی از گذشته روبرو می‌شوند که جهان را در معرض خطر قرار داده است."
    )


    val movieUrls = listOf(
        "https://dls4.iran-gamecenter-host.com/DonyayeSerial/movies/2005/tt0372784/Dubbed/Batman.Begins.2005.1080p.BluRay.Dubbed.Unknown.DonyayeSerial.mkv",
        "https://dls7.iran-gamecenter-host.com/DonyayeSerial/movies/2022/tt1877830/Dubbed/The.Batman.2022.720p.BluRay.Dubbed.Pahe.DonyayeSerial.mkv",
        "https://dls4.iran-gamecenter-host.com/DonyayeSerial/movies/2016/tt2975590/Dubbed/Batman.v.Superman.Dawn.of.Justice.2016.1080p.BluRay.Dubbed.YTS.DonyayeSerial.mkv",
        "https://dls4.iran-gamecenter-host.com/DonyayeSerial/movies/2016/tt2975590/Dubbed/Batman.v.Superman.Dawn.of.Justice.2016.1080p.BluRay.Dubbed.YTS.DonyayeSerial.mkv",
        "https://dls4.iran-gamecenter-host.com/DonyayeSerial/movie/1989/Batman/Soft.Sub/Batman.1989.1080p.BluRay.SoftSub.DonyayeSerial.mkv",
        "https://modernfilm.upera.tv/2862087-11-1080.mp4?ref=gG6g",
        "https://s1.irdanlod.ir/files/Movie/1997/B/Batman.and.Robin.1997.1080p.Farsi.Dubbed.mkv",
        "https://s1.irdanlod.ir/files/Movie/1995/B/Batman.Forever.1995.1080p.Farsi.Dubbed.mkv",
        "https://s1.irdanlod.ir/files/Animations/2017/T/The.LEGO.Batman.Movie.2017.1080p.Farsi.Dubbed.mkv"
    )


    LaunchedEffect(Unit) {
        moviesListStates = apiMainMovieCall()
        updateList = moviesListStates
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        var savedWordsState by remember {
            mutableStateOf(
                Hawk.get(
                    "savedwords",
                    emptyList<String>()
                )
            )
        }

        SearchBar(
            text = textFiled,
            onTextValueChange = {
                textFiled = it
                updateList = moviesListStates?.filter {
                    it.Title?.contains(textFiled, ignoreCase = true) == true
                } ?: emptyList()
            },
            onSavedItemClicked = {
                textFiled = it
                updateList = moviesListStates?.filter {
                    it.Title?.contains(textFiled, ignoreCase = true) == true
                } ?: emptyList()
            },
            savedWords = savedWordsState
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (moviesListStates == null) {
                CircularProgressIndicator()
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(items = updateList ?: emptyList()) { index, search ->
                        val url = movieUrls.getOrNull(index) ?: ""

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(0.7f)
                                .clickable {
                                    if (url.isNotEmpty()) {
                                        val desc = movieDes.getOrNull(index)?.second
                                            ?: "Description not found"
                                        val actors =
                                            movieActors.getOrNull(index)?.joinToString(", ")
                                                ?: "Actors not found"

                                        naviagtor?.push(
                                            MovieScreen(
                                                url,
                                                search.Poster ?: "",
                                                search.Title ?: "",
                                                desc,
                                                actors
                                            )
                                        )
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "URL not found",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                },
                            shape = RoundedCornerShape(8.dp),
                            elevation = 4.dp
                        ) {
                            Column {
                                AsyncImage(
                                    model = search.Poster,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = search.Title ?: "",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }

                }
            }
        }

    }

}
