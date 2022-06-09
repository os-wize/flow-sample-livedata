package com.example.myprept.ui.main


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myprept.core.data.source.ReposRepository
import com.example.myprept.core.exception.Failure
import com.example.myprept.core.functional.Either
import com.example.myprept.utils.TestCoroutineRule
import com.example.myprept.utils.UiStateManager
import com.example.myprept.utils.observeForTestingObserver
import com.example.myprept.utils.observeForTestingResult
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val reposRepository: ReposRepository = mock()

    private lateinit var viewModel: ReposViewModel

    @get:Rule
    val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()


    @Before
    fun setUp() {
        viewModel = ReposViewModel(reposRepository)

    }

    @Test
    fun init() {
        testCoroutineRule.runBlockingTest {
            val uiState = viewModel.uiState.observeForTestingObserver()
            verify(uiState).onChanged(UiStateManager.UiState.INIT_EMPTY)
        }
    }

    @Test
    fun whenRequestOfReposesUnsuccessfully_shouldSetErrorStare() {
        testCoroutineRule.runBlockingTest {
            val uiState = viewModel.uiState.observeForTestingObserver()
            whenever(reposRepository.getRepos(any())).doReturn(Either.Left(Failure.ServerError))
            viewModel.getRepos("flutter")
            verify(uiState).onChanged(UiStateManager.UiState.ERROR)
        }
    }
}