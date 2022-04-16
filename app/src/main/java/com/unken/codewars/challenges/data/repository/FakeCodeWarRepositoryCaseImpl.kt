package com.unken.codewars.challenges.data.repository

import com.unken.codewars.challenges.data.remote.dto.ChallengeSummaryDto
import com.unken.codewars.challenges.domain.model.Challenge
import com.unken.codewars.challenges.domain.model.Rank
import com.unken.codewars.challenges.domain.model.CompletedChallenges
import com.unken.codewars.challenges.domain.repository.CodeWarRepository
import com.unken.codewars.common.utils.Resource
import com.unken.codewars.common.utils.UIText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class FakeCodeWarRepositoryCaseImpl : CodeWarRepository {

    private val challengesPageOne = listOf(
        ChallengeSummaryDto(
            completedAt = Calendar.getInstance().time.toString(),
            name = "Living in bondage",
            id = "page_one_item_1",
            slug = "page_one_item_1_slug",
            completedLanguages = listOf("pascal", "vbase")
        ),
        ChallengeSummaryDto(
            completedAt = Calendar.getInstance().time.toString(),
            name = "New year's eve",
            id = "page_one_item_2",
            slug = "page_one_item_2_slug",
            completedLanguages = listOf("java", "python")
        ),
        ChallengeSummaryDto(
            completedAt = Calendar.getInstance().time.toString(),
            name = "Palindrome",
            id = "page_one_item_3",
            slug = "page_one_item_3_slug",
            completedLanguages = listOf("c++")
        )
    )
    private val challengesPageTwo = listOf(
        ChallengeSummaryDto(
            completedAt = Calendar.getInstance().time.toString(),
            name = "Graph depth",
            id = "page_two_item_1",
            slug = "page_two_item_1_slug",
            completedLanguages = listOf("pascal", "vbase")
        ),
        ChallengeSummaryDto(
            completedAt = Calendar.getInstance().time.toString(),
            name = "Living in bondage",
            id = "page_two_item_2",
            slug = "page_two_item_2_slug",
            completedLanguages = listOf("java", "fortran")
        ),
        ChallengeSummaryDto(
            completedAt = Calendar.getInstance().time.toString(),
            name = "Joker Mania",
            id = "page_two_item_3",
            slug = "page_two_item_3_slug",
            completedLanguages = listOf("java", "fortran")
        )
    )
    private val challengesPageThree = listOf<ChallengeSummaryDto>(
        ChallengeSummaryDto(
            completedAt = Calendar.getInstance().time.toString(),
            name = "Pontus Arielus",
            id = "page_three_item_1",
            slug = "page_three_item_1_slug",
            completedLanguages = listOf("pascal", "vbase")
        ),
        ChallengeSummaryDto(
            completedAt = Calendar.getInstance().time.toString(),
            name = "Marc Anthony's Quiz",
            id = "page_three_item_2",
            slug = "page_three_item_2_slug",
            completedLanguages = listOf("java", "fortran")
        ),
        ChallengeSummaryDto(
            completedAt = Calendar.getInstance().time.toString(),
            name = "Lara Croft's Tombstone",
            id = "page_three_item_3",
            slug = "page_three_item_3_slug",
            completedLanguages = listOf("java", "fortran")
        )
    )

    override fun getCompletedChallenges(pageNumber: Int): Flow<Resource<CompletedChallenges>> = flow {
        val page = when(pageNumber) {
            0 -> challengesPageOne
            1 -> challengesPageTwo
            2 -> challengesPageThree
            else -> arrayListOf()
        }
        emit(
            Resource.Success(
                CompletedChallenges(
                    totalItems = challengesPageOne.size + challengesPageTwo.size + challengesPageThree.size,
                    totalPages = 3,
                    data = page.map { it.toChallenge() }
                )
            )
        )
    }

    override fun getChallengeById(id: String): Flow<Resource<Challenge>> = flow {
        val item = arrayListOf<ChallengeSummaryDto>().apply {
            addAll(challengesPageOne)
            addAll(challengesPageTwo)
            addAll(challengesPageThree)
        }.find { it.id == id }

        if (item == null) {
            // item does not exist
            emit(Resource.Error(message = UIText.DynamicString("Item does not exist")))
        } else {
            val challengeDetails = Challenge(
                name = "Living in bondage",
                id = "page_one_item_1",
                slug = "page_one_item_1_slug",
                languages = listOf("pascal", "vbase"),
                description = "This is a sample problem to test your problem solving skills",
                totalStars = 2,
                totalCompleted = 200,
                totalAttempts = 400,
            )
            emit(Resource.Success(challengeDetails))
        }
    }
}