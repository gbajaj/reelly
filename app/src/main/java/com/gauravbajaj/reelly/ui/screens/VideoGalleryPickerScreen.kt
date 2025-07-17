@file:OptIn(ExperimentalMaterial3Api::class)

package com.gauravbajaj.reelly.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.gauravbajaj.reelly.data.model.Subtitle
import com.gauravbajaj.reelly.ui.base.ScreenContent
import com.gauravbajaj.reelly.ui.base.UIState
import com.gauravbajaj.reelly.ui.components.SubtitleOverlay
import com.gauravbajaj.reelly.ui.viewmodel.VideoGalleryPickerViewModel
import kotlinx.coroutines.delay

@Composable
fun VideoGalleryPickerScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<VideoGalleryPickerViewModel>()
    val uiSubtitlesState by viewModel.uiSubtitlesState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.loadSubtitles()
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Reelly") }
            )
        }
    ) { paddingValues ->
        ScreenContent(
            uiState = uiSubtitlesState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onRetry = {
                viewModel.loadSubtitles()
            }
        ) {
            var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }
            var isPlaying by remember { mutableStateOf(false) }
            var currentPosition by remember { mutableStateOf(0L) }
            var duration by remember { mutableStateOf(0L) }
            var bufferPosition by remember { mutableStateOf(0L) }
            val context = LocalContext.current

            // ExoPlayer instance
            val exoPlayer = remember {
                ExoPlayer.Builder(context).build()
            }


            // Add these state variables to your existing VideoGalleryPickerScreen()
            var currentSubtitle by remember { mutableStateOf<Subtitle?>(null) }
            var subtitles by remember { mutableStateOf(emptyList<Subtitle>()) }

            subtitles = (uiSubtitlesState as? UIState.Success)?.data ?: emptyList()

            // Add this effect alongside your existing LaunchedEffect for position updates
            LaunchedEffect(currentPosition) {
                // Find current subtitle based on timeline
                currentSubtitle = subtitles.find { subtitle ->
                    currentPosition >= subtitle.startTime && currentPosition <= subtitle.endTime
                }
            }
            // Player listener for timeline changes
            val playerListener = remember {
                object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        when (playbackState) {
                            Player.STATE_READY -> {
                                duration = exoPlayer.duration
                            }

                            Player.STATE_ENDED -> {
                                isPlaying = false
                                currentPosition = 0L
                            }
                        }
                    }

                    override fun onIsPlayingChanged(playing: Boolean) {
                        isPlaying = playing
                    }

                    override fun onPositionDiscontinuity(
                        oldPosition: Player.PositionInfo,
                        newPosition: Player.PositionInfo,
                        reason: Int
                    ) {
                        currentPosition = newPosition.positionMs
                    }
                }
            }

            // Add listener to ExoPlayer
            LaunchedEffect(exoPlayer) {
                exoPlayer.addListener(playerListener)
            }

            // Update position periodically while playing
            LaunchedEffect(isPlaying) {
                while (isPlaying) {
                    currentPosition = exoPlayer.currentPosition
                    bufferPosition = exoPlayer.bufferedPosition
                    delay(100) // Update every 100ms
                }
            }

            // Gallery launcher for video selection
            val videoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                uri?.let {
                    selectedVideoUri = it
                    // Prepare the media item
                    val mediaItem = MediaItem.fromUri(it)
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                    isPlaying = false
                }
            }

            // Clean up ExoPlayer when composable is disposed
            DisposableEffect(Unit) {
                onDispose {
                    exoPlayer.removeListener(playerListener)
                    exoPlayer.release()
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top App Bar
                CenterAlignedTopAppBar(
                    title = { Text("Video Gallery Player") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Pick Video Button
                Button(
                    onClick = {
                        videoPickerLauncher.launch("video/*")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Pick Video from Gallery")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Video Player Section
                selectedVideoUri?.let { uri ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Box {
                            AndroidView(
                                factory = { context ->
                                    PlayerView(context).apply {
                                        player = exoPlayer
                                        useController = true
                                        controllerShowTimeoutMs = 3000
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )

                            // Subtitle overlay
                            SubtitleOverlay(
                                subtitle = currentSubtitle,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Progress Bar and Timeline Info
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Playback Timeline",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Progress bar
                            LinearProgressIndicator(
                                progress = if (duration > 0) currentPosition.toFloat() / duration.toFloat() else 0f,
                                modifier = Modifier.fillMaxWidth(),
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // Buffer progress bar
                            LinearProgressIndicator(
                                progress = if (duration > 0) bufferPosition.toFloat() / duration.toFloat() else 0f,
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Time display
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = formatTime(currentPosition),
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = formatTime(duration),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Additional timeline info
                            Column {
                                Text(
                                    text = "Current Position: ${formatTime(currentPosition)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Buffer Position: ${formatTime(bufferPosition)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Duration: ${formatTime(duration)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Playing: $isPlaying",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Seek Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                val newPosition = (currentPosition - 10000).coerceAtLeast(0)
                                exoPlayer.seekTo(newPosition)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("-10s")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                val newPosition =
                                    (currentPosition + 10000).coerceAtMost(duration)
                                exoPlayer.seekTo(newPosition)
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("+10s")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Play/Pause Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                if (isPlaying) {
                                    exoPlayer.pause()
                                } else {
                                    exoPlayer.play()
                                }
                                isPlaying = !isPlaying
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (isPlaying) "Pause" else "Play")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                                exoPlayer.seekTo(0)
                                exoPlayer.pause()
                                isPlaying = false
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Restart")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Video Info
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Selected Video",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uri.lastPathSegment ?: "Unknown",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } ?: run {
                    // Placeholder when no video is selected
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = android.R.drawable.ic_media_play),
                                    contentDescription = "No video selected",
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "No video selected",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Tap the button above to pick a video",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Helper function to format time
fun formatTime(timeMs: Long): String {
    if (timeMs <= 0) return "00:00"

    val seconds = (timeMs / 1000) % 60
    val minutes = (timeMs / (1000 * 60)) % 60
    val hours = (timeMs / (1000 * 60 * 60))

    return if (hours > 0) {
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}



