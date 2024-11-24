package com.kubik.userappcourse.ui.base.base_basket.basket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kubik.userappcourse.DependenciesDatabase
import com.kubik.userappcourse.R
import com.kubik.userappcourse.databinding.FragmentBasketBinding
import com.kubik.userappcourse.ui.base.base_basket.basket.adapter.BasketAdapter
import com.kubik.userappcourse.ui.base.base_basket.basket.adapter.BasketAdapterListener
import com.kubik.userappcourse.ui.base.base_basket.view_product.ViewProductFragment
import com.kubik.userappcourse.ui.base.models.GetBasketProductModel

class BasketFragment : Fragment(), BasketAdapterListener {

    private lateinit var binding: FragmentBasketBinding
    private val viewModel by lazy { ViewModelProvider(this).get(BasketViewModel::class.java) }
    private val adapter = BasketAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserve()
        initField()
    }

    private fun initObserve() {
        viewModel.listBasket.observeForever {
            adapter.updateList(it)
            binding.apply {
                recyclerView.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }
        }
    }

    private fun initField() {
        DependenciesDatabase.apply {
            viewModel.getList(daoUser, daoProduct)
        }
    }

    private fun initView() {
        binding.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
            btnHistory.setOnClickListener {
                try {
                    findNavController().navigate(R.id.action_basketFragment_to_historyFragment)
                } catch (e: Exception) {
                    Log.e("MyLog", "BasketFragment navigate: ${e.message}")
                }
            }
        }
    }

    override fun onClick(product: GetBasketProductModel) {
        try {
            val bundle = Bundle()
            bundle.putParcelable(ViewProductFragment.KEY_ARGUMENTS, product)
            findNavController().navigate(
                R.id.action_basketFragment_to_viewProductFragment,
                bundle
            )
        } catch (e: Exception) {
            Log.e("MyLog", "BasketFragment navigate: ${e.message}")
        }
    }
}