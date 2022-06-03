package com.example.myprept.ui.main


import org.junit.Before
import org.junit.Rule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myprept.core.data.source.ReposRepository
import com.example.myprept.core.exception.Failure
import com.example.myprept.core.functional.Either
import com.example.myprept.features.repos.model.GetReposUseCase
import com.example.myprept.utils.CurrentThreadExecutor
import com.example.myprept.utils.TestCoroutineRule
import com.example.myprept.utils.UiStateManager
import com.example.myprept.utils.observeForTestingResult
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import org.junit.Test


class ReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val getReposUseCase: GetReposUseCase = mock()
    private val reposRepository: ReposRepository = mock()

    private lateinit var viewModel: ReposViewModel

    @get:Rule
    val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()


    @Before
    fun setUp() {
        viewModel = ReposViewModel(getReposUseCase)
    }

    @Test
    fun init() {
        val uiState = viewModel.uiState.observeForTestingResult()
        assertThat(uiState).isEqualTo(UiStateManager.UiState.INIT_EMPTY)

    }

    @Test
    fun whenRequestOfReposisUnsucesfull_shouldSetErrorStare() {
        val uiState = viewModel.uiState.observeForTestingResult()
        testCoroutineRule.runBlockingTest {
            whenever(getReposUseCase.run(any())).thenReturn(Either.Left(Failure.ServerError))
            whenever(reposRepository.getRepos(any(), any(), any())).thenReturn(Either.Left(Failure.ServerError))
            viewModel.getRepos("flutter")
            assertThat(uiState).isEqualTo(UiStateManager.UiState.ERROR)
        }

    }
}