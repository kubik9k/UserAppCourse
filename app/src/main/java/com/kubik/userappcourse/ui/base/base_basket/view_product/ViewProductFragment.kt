package com.kubik.userappcourse.ui.base.base_basket.view_product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kubik.userappcourse.databinding.FragmentViewProductBinding
import com.kubik.userappcourse.ui.base.models.GetBasketProductModel
import com.kubik.userappcourse.ui.getStatusProduct
import com.squareup.picasso.Picasso

class ViewProductFragment : Fragment() {

    private lateinit var binding: FragmentViewProductBinding
    private var data = GetBasketProductModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentViewProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initField()
    }

    private fun initView() {
        binding.btnBack.setOnClickListener {
            try {
                findNavController().popBackStack()
            } catch (e: Exception) {
                Log.e("MyLog", "ViewProductFragment navigate: ${e.message}")
            }
        }
    }

    private fun initField() {
        val getData = arguments?.getParcelable<GetBasketProductModel>(KEY_ARGUMENTS)
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
            status.text = getStatusProduct(data.status, root.context)
            Picasso.get().load(data.imageUri).into(imageView)
        }
    }

    companion object {
        const val KEY_ARGUMENTS = "KEY_ARGUMENTS_VIEW_PRODUCT"
    }
}