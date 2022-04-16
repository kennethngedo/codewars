package com.unken.codewars.challenges.presentation.challenge_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.unken.codewars.R
import com.unken.codewars.challenges.presentation.destinations.ChallengeDetailScreenDestination
import com.unken.codewars.common.utils.UIEvent
import kotlinx.coroutines.flow.collectLatest


@Destination(start = true)
@Composable
fun ChallengesListScreen(
    navigator: DestinationsNavigator,
    viewModel: ChallengeListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val listSate = rememberLazyListState()
    val context =  LocalContext.current
    val state = viewModel.state.value
    val challengeInfo = state.completedChallenges
    val challenges = challengeInfo?.data
    val page = viewModel.page

    LaunchedEffect(key1 = true) {
        viewModel.startPage()
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

    Scaffold(scaffoldState = scaffoldState) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
        ) {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = page.value.toString(),
                onValueChange = {
                    viewModel.gotoPage(it.toInt())
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { viewModel.startPage() }) {
                    Text(text = stringResource(id = R.string.start, 1))
                }
                Button(onClick = { viewModel.previousPage() }) {
                    Text(text = stringResource(id = R.string.previous))
                }

                Button(onClick = { viewModel.nextPage() }) {
                    Text(text = stringResource(id = R.string.next))
                }
                Button(onClick = { viewModel.endPage() }) {
                    val lastPage = challengeInfo?.totalPages ?: 1
                    Text(text = stringResource(
                        id = R.string.end, lastPage)
                    )
                }
            }

            Box(modifier = Modifier
                .fillMaxSize()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    challenges?.let {
                        LazyColumn(state = listSate, modifier = Modifier.fillMaxSize()) {
                            items(challenges.size){ i ->
                                val challenge = challenges[i]
                                if (i > 0) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                                ChallengesListItem(challenge) {
                                    navigator.navigate(ChallengeDetailScreenDestination(challengeSummary = it))
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                if(i < challenges.size - 1) {
                                    Divider()
                                }
                            }
                        }
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
}



