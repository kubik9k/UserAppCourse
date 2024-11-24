package com.kubik.userappcourse.ui.base.base_home.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kubik.userappcourse.R
import com.kubik.userappcourse.data.category.CategoryRepositoryImpl
import com.kubik.userappcourse.databinding.FragmentHomeBinding
import com.kubik.userappcourse.ui.base.base_home.home.adapter.HomeAdapter
import com.kubik.userappcourse.ui.base.base_home.home.adapter.HomeAdapterListener

class HomeFragment : Fragment(), HomeAdapterListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private val adapter = HomeAdapter(this)
    private val categoryRepository = CategoryRepositoryImpl()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
        initField()
    }

    private fun initObservers() {
        viewModel.apply {
            listAdapter.observeForever {
                adapter.setList(it)
            }
            isSaveNumCategory.observeForever {
                if (it != 0) {
                    Log.d("MyLog", "Save num category")
                    goToHomeListFragment()
                }
            }
        }
    }

    private fun goToHomeListFragment() {
        try {
            findNavController().navigate(R.id.action_homeFragment_to_homeListsFragment)
        } catch (e: Exception) {
            Log.e("MyLog", "HomeFragment error navigate: ${e.message}")
        }
    }

    private fun initField() {
        viewModel.apply {
            initViewModel(categoryRepository)
            loadListAdapter()
        }
    }

    private fun initView() {
        binding.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter

        }
    }

    override fun onClickAdapter(numItem: Int) {
        Log.d("MyLog", "click adapter: $numItem")
        viewModel.saveNumCategory(binding.root.context.applicationContext, numItem)
    }

}