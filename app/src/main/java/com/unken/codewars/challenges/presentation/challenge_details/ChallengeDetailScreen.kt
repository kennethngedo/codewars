package com.unken.codewars.challenges.presentation.challenge_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.unken.codewars.R
import com.unken.codewars.challenges.domain.model.ChallengeSummary
import com.unken.codewars.common.theme.Shapes
import com.unken.codewars.common.utils.UIEvent
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun ChallengeDetailScreen(
    navigator: DestinationsNavigator,
    challengeSummary: ChallengeSummary,
    viewModel: ChallengeDetailsViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val state = viewModel.state.value

    LaunchedEffect(key1 = true) {
        viewModel.getChallengeBySlug(challengeSummary.slug)
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = challengeSummary.name)
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable {
                                navigator.popBackStack()
                            }
                    )
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(state = scrollState, enabled = true)
            ) {
                state.challengeDetails?.let { details ->
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = stringResource(id = R.string.total_attempts).uppercase(), style = TextStyle(fontSize = 16.sp, color = Color.Gray))
                        Text(text = details.totalAttempts.toString(), style = TextStyle(fontSize = 16.sp))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = stringResource(id = R.string.total_completed).uppercase(), style = TextStyle(fontSize = 16.sp, color = Color.Gray))
                        Text(text = details.totalCompleted.toString(), style = TextStyle(fontSize = 16.sp))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = stringResource(id = R.string.total_stars).uppercase(), style = TextStyle(fontSize = 16.sp, color = Color.Gray))
                        Text(text = details.totalStars.toString(), style = TextStyle(fontSize = 16.sp))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = stringResource(id = R.string.description).uppercase(), style = TextStyle(fontSize = 16.sp, color = Color.Gray))
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = details.description, style = TextStyle(fontSize = 16.sp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = stringResource(id = R.string.languages).uppercase(), style = TextStyle(fontSize = 16.sp, color = Color.Gray))
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth().height(250.dp),
                        cells = GridCells.Fixed(3),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        items(details.languages.size) { i ->
                            val language = details.languages[i]
                            Text(
                                text = language,
                                modifier = Modifier
                                    .padding(horizontal = 5.dp, vertical = 5.dp)
                                    .background(color = Color.Gray, shape = Shapes.medium)
                                    .padding(horizontal = 10.dp, vertical = 3.dp)
                            )

                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

            }

            if (state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }

    }
}