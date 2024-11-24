package com.kubik.userappcourse.ui.base.base_basket.history

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
import com.kubik.userappcourse.databinding.FragmentHistoryBinding
import com.kubik.userappcourse.ui.base.base_basket.history.adapter.HistoryAdapter
import com.kubik.userappcourse.ui.base.base_basket.history.adapter.HistoryAdapterListener
import com.kubik.userappcourse.ui.base.base_basket.view_product.ViewProductFragment
import com.kubik.userappcourse.ui.base.models.GetBasketProductModel

class HistoryFragment : Fragment(), HistoryAdapterListener {

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by lazy { ViewModelProvider(this).get(HistoryViewModel::class.java) }
    private val adapter = HistoryAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initField()
    }

    private fun initField() {
        viewModel.getListHistory(DependenciesDatabase.daoProduct)
        viewModel.listHistory.observeForever {
            adapter.updateList(it)
            binding.apply {
                recyclerView.visibility = View.VISIBLE
                loading.visibility = View.GONE
            }
        }
    }

    private fun initView() {
        binding.apply {
            recyclerView.layoutManager =
                LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter
            btnBack.setOnClickListener {
                try {
                    findNavController().navigate(R.id.action_historyFragment_to_basketFragment)
                } catch (e: Exception) {
                    Log.e("MyLog", "HistoryFragment navigate: ${e.message}")
                }
            }
            btnClear.setOnClickListener {
                viewModel.clearData(DependenciesDatabase.daoProduct)
            }
        }
    }

    override fun onClick(product: GetBasketProductModel) {
        try {
            val bundle = Bundle()
            bundle.putParcelable(ViewProductFragment.KEY_ARGUMENTS, product)
            findNavController().navigate(
                R.id.action_historyFragment_to_viewProductFragment,
                bundle
            )
        } catch (e: Exception) {
            Log.e("MyLog", "HistoryFragment navigate: ${e.message}")
        }
    }
}