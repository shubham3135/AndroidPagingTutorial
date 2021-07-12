package com.shubhamkumarwinner.pagingpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubhamkumarwinner.pagingpractice.adapter.PhotoAdapter
import com.shubhamkumarwinner.pagingpractice.adapter.PhotoLoadStateAdapter
import com.shubhamkumarwinner.pagingpractice.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding
    private val adapter = PhotoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(layoutInflater, container, false)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getPhoto("cats").collectLatest {
                binding.recyclerView.adapter = adapter
                .withLoadStateHeaderAndFooter(
                    header = PhotoLoadStateAdapter { adapter::retry },
                    footer = PhotoLoadStateAdapter { adapter::retry },
                )
                adapter.submitData(it)
            }
        }

        binding.buttonRetry.setOnClickListener {
            adapter.retry()
        }

        adapter.addLoadStateListener { loadState->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached
                    && adapter.itemCount<1){
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                }else{
                    textViewEmpty.isVisible = false
                }
            }

        }

        return binding.root
    }
}