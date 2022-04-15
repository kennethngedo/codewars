package com.unken.codewars.challenges.presentation.challenge_list

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.unken.codewars.challenges.domain.model.ChallengeSummary
import com.unken.codewars.common.theme.Shapes

@Composable
fun ChallengesListItem(challenge: ChallengeSummary, onClick: (id: ChallengeSummary) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().clickable { onClick(challenge) }) {
        Text(text = challenge.name, style = TextStyle(fontSize = 20.sp))
        Spacer(modifier = Modifier.height(5.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(challenge.completedLanguages.size) { i ->
                val language = challenge.completedLanguages[i]
                if (i != 0) Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = language,
                    modifier = Modifier.background(color = Color.Gray, shape = Shapes.medium)
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                )

            }
        }
    }
}