@file:OptIn(UnstableApi::class)

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import cafe.adriel.voyager.core.screen.Screen
import com.example.noxmoview.R

data class MovieScreen(val url: String = "" , val image: String? = "" , val title: String , val des: String , val actors:String): Screen{
    @Composable
    override fun Content() {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                FullScreenVideoPlayer(url)

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "نام اصلی: $title",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF333333)
                                )
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "نام بازیگران: $actors",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF555555)
                                )
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "خلاصه داستان: $des",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 20.sp,
                                    color = Color(0xFF444444)
                                )
                            )
                        }
                    }
                }

            }
    }

}



private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun FullScreenVideoPlayer(
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }

    // Persist across rotation
    var isFullscreen by rememberSaveable { mutableStateOf(false) }

    var playbackPosition by rememberSaveable { mutableStateOf(0L) }
    var currentWindowIndex by rememberSaveable { mutableStateOf(0) }
    var autoPlay by rememberSaveable { mutableStateOf(true) }

    val exoPlayer = remember(url) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            seekTo(currentWindowIndex, playbackPosition)
            playWhenReady = autoPlay

        }
    }

    // Save state on pause & release on dispose
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_PAUSE) {
                playbackPosition = exoPlayer.currentPosition
                currentWindowIndex = exoPlayer.currentMediaItemIndex
                autoPlay = exoPlayer.playWhenReady
                exoPlayer.pause()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }

    // Apply / restore “real fullscreen” side-effects
    DisposableEffect(isFullscreen) {
        val window = activity?.window
        if (window != null) {
            WindowCompat.setDecorFitsSystemWindows(window, !isFullscreen)
            val controller = WindowInsetsControllerCompat(window, window.decorView)

            if (isFullscreen) {
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                activity.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            } else {
                controller.show(WindowInsetsCompat.Type.systemBars())
                activity.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
        onDispose {
            // Safety net when leaving this screen while fullscreen
            activity?.let { act ->
                WindowCompat.setDecorFitsSystemWindows(act.window, true)
                WindowInsetsControllerCompat(act.window, act.window.decorView)
                    .show(WindowInsetsCompat.Type.systemBars())
                act.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
    }

    Box(
        modifier = modifier.then(
            if (isFullscreen) Modifier.fillMaxSize()
            else Modifier.height(220.dp).fillMaxWidth()
        )
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx ).apply {
                    player = exoPlayer
                    useController = true
                    resizeMode = if (isFullscreen)
                        AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    else
                        AspectRatioFrameLayout.RESIZE_MODE_FIT
                    keepScreenOn = true
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Move the button to the bottom-right
        IconButton(
            onClick = { isFullscreen = !isFullscreen },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp , end = 50.dp , bottom = 12.dp)
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.full),
                tint = Color.White,
                contentDescription = if (isFullscreen) "Exit fullscreen" else "Fullscreen"
            )
        }
    }
}
