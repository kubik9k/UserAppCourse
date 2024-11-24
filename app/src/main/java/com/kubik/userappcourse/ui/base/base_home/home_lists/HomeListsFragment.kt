package com.kubik.userappcourse.ui.base.base_home.home_lists

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kubik.userappcourse.R
import com.kubik.userappcourse.databinding.FragmentHomeListsBinding
import com.kubik.userappcourse.ui.base.base_home.home_item.HomeItemFragment
import com.kubik.userappcourse.ui.base.base_home.home_lists.adapters.AdapterCartProduct
import com.kubik.userappcourse.ui.base.base_home.home_lists.adapters.AdapterSubcategory
import com.kubik.userappcourse.ui.base.base_home.home_lists.adapters.CartProductListener
import com.kubik.userappcourse.ui.base.base_home.home_lists.adapters.SubcategoryAdapterListener
import com.kubik.userappcourse.ui.base.models.GetProductModel

class HomeListsFragment : Fragment(), SubcategoryAdapterListener, CartProductListener {

    private lateinit var binding: FragmentHomeListsBinding
    private val viewModel by lazy { ViewModelProvider(this).get(HomeListViewModel::class.java) }
    private val adapterSubcategory = AdapterSubcategory(this)
    private val adapterProducts = AdapterCartProduct(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initField()
    }

    private fun initObserver() {
        viewModel.apply {
            listSubcategory.observeForever {
                adapterSubcategory.setList(it, binding.subcategoryRecyclerView)
            }
            listProduct.observeForever {
                adapterProducts.updateList(it)
                binding.loading.visibility = View.GONE
            }
        }
    }

    private fun initField() {
        viewModel.initViewModel(binding.root.context)
    }

    private fun initView() {
        initRecyclerView()
        binding.apply {
            loading.visibility = View.VISIBLE
            btnBack.setOnClickListener {
                try {
                    findNavController().navigate(R.id.action_homeListsFragment_to_homeFragment)
                } catch (e: Exception) {
                    Log.e("MyLog", "HomeListFragment navigate: ${e.message}")
                }
            }
            searchView.setOnClickListener {
                searchView.isIconified = false
                searchView.onActionViewExpanded()
                searchView.requestFocus()
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        viewModel.searchText(newText)
                    }
                    return true
                }
            })
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            baseRecycleView.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            baseRecycleView.adapter = adapterProducts
            subcategoryRecyclerView.layoutManager =
                GridLayoutManager(root.context, 2, GridLayoutManager.HORIZONTAL, false)
            subcategoryRecyclerView.adapter = adapterSubcategory
        }
    }

    override fun onClickItem(position: Int) {
        binding.loading.visibility = View.VISIBLE
        viewModel.clickItemSubcategory(position)
    }

    override fun onClickProduct(item: GetProductModel) {
        viewModel.clickProduct(binding.root.context.applicationContext) {
            try {
                val bundle = Bundle()
                bundle.putParcelable(HomeItemFragment.KEY_ARGUMENTS, item)
                findNavController().navigate(
                    R.id.action_homeListsFragment_to_homeItemFragment,
                    bundle
                )
            } catch (e: Exception) {
                Log.e("MyLog", "HomeListFragment navigate: ${e.message}")
            }
        }
    }
}
