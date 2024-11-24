package com.kubik.userappcourse.ui.base.base_home.home_item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kubik.userappcourse.DependenciesDatabase
import com.kubik.userappcourse.R
import com.kubik.userappcourse.databinding.FragmentHomeItemBinding
import com.kubik.userappcourse.ui.base.models.GetProductModel
import com.kubik.userappcourse.ui.showToast
import com.squareup.picasso.Picasso

class HomeItemFragment : Fragment() {

    private lateinit var binding: FragmentHomeItemBinding
    private val viewModel by lazy { ViewModelProvider(this).get(HomeItemViewModel::class.java) }
    private var data = GetProductModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        initField()
    }

    private fun initObserver() {
        viewModel.newResultSuccessful.observeForever {
            if (it != 0) {
                showToast(binding.root.context, getString(R.string.successful_buy_product))
                goToBackFragment()
                binding.loading.visibility = View.GONE
                binding.baseContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun initField() {
        val getData = arguments?.getParcelable<GetProductModel>(KEY_ARGUMENTS)
        if (getData != null) {
            Log.d("MyLof", "data = $data")
            data = getData
            setData()
        }
    }

    private fun setData() {
        binding.apply {
            titleProductEditText.setText(data.title)
            descriptionProductEditText.setText(data.description)
            price.setText(data.price.toString() + "$")
            count.setText(data.count.toString())
            Picasso.get().load(data.imageUri).into(imageView)
        }
    }

    private fun initView() {
        binding.apply {
            btnBack.setOnClickListener {
                goToBackFragment()
            }
            btnBuy.setOnClickListener {
                if (count.text.toString().toInt() > 0) {
                    loading.visibility = View.VISIBLE
                    baseContainer.visibility = View.GONE
                    viewModel.buyProduct(
                        binding.root.context.applicationContext,
                        data.id,
                        DependenciesDatabase.daoUser
                    )
                } else showToast(root.context, getString(R.string.not_available))
            }
        }
    }

    private fun goToBackFragment() {
        try {
            findNavController().navigate(R.id.action_homeItemFragment_to_homeListsFragment)
        } catch (e: Exception) {
            Log.e("MyLog", "HomeItemFragment navigate: ${e.message}")
        }
    }

    companion object {
        const val KEY_ARGUMENTS = "KEY_ARGUMENTS_HOME_ITEM"
    }

}