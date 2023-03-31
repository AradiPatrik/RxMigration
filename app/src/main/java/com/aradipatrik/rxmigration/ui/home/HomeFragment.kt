package com.aradipatrik.rxmigration.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.aradipatrik.rxmigration.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import logcat.LogPriority
import logcat.LogPriority.WARN
import logcat.logcat
import kotlin.concurrent.thread

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.reposRv.layoutManager = LinearLayoutManager(context)

        val adapter = RepoAdapter()
        binding.reposRv.adapter = adapter

        val errorHandler = CoroutineExceptionHandler { _, throwable ->
            logcat(WARN) { "Error: $throwable" }
            Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_LONG).show()
        }

        adapter.itemClicks
            .onEach { homeViewModel.onItemClick(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope + errorHandler)

        homeViewModel.repoItems
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.CREATED)
            .onEach(adapter::submitList)
            .launchIn(viewLifecycleOwner.lifecycleScope + errorHandler)

        binding.searchEt.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewLifecycleOwner.lifecycleScope.launch(errorHandler) {
                    homeViewModel.queryRepos(v.text.toString())
                }
                true
            } else {
                false
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}