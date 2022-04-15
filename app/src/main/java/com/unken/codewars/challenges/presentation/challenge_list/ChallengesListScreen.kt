package com.unken.codewars.challenges.presentation.challenge_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
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
    val context =  LocalContext.current
    val state = viewModel.state.value
    val challenges = state.userChallengesInfo?.data

    LaunchedEffect(key1 = true) {
        viewModel.fetchChallenges()
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
        Box(modifier = Modifier
            .fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                challenges?.let {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
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



