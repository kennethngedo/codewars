package com.unken.codewars.challenges.di

import com.unken.codewars.challenges.data.repository.FakeCodeWarRepositoryCaseImpl
import com.unken.codewars.challenges.domain.repository.CodeWarRepository
import com.unken.codewars.challenges.domain.usecases.GetChallengeDetails
import com.unken.codewars.challenges.domain.usecases.GetCodeWarInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CodeWarsModule::class]
)
object TestNewsModule {

    @Provides
    fun providesCodeWarRepository(): CodeWarRepository {
        return FakeCodeWarRepositoryCaseImpl()
    }

    @Provides
    fun providesGetCodeWarInfoUseCase(repository: CodeWarRepository) : GetCodeWarInfo {
        return GetCodeWarInfo(repository)
    }

    @Provides
    fun providesGetChallengeDetailsUseCase(repository: CodeWarRepository) : GetChallengeDetails {
        return GetChallengeDetails(repository)
    }
}