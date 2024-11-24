package com.kubik.userappcourse.ui.base.profile_base.edit_profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kubik.userappcourse.DependenciesDatabase
import com.kubik.userappcourse.R
import com.kubik.userappcourse.databinding.FragmentEditProfileBinding
import com.kubik.userappcourse.ui.authentication.models.UserModel
import com.kubik.userappcourse.ui.checkNotEmptyEditText
import com.kubik.userappcourse.ui.showToast

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel by lazy { ViewModelProvider(this).get(EditProfileViewModel::class.java) }
    private var user = UserModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initField()
        initView()
    }

    private fun initView() {
        binding.apply {
            btnSave.setOnClickListener {
                checkNotEmptyEnterData {
                    setUserNewData()
                    loadingVisible()
                    viewModel.editDataUser(user, DependenciesDatabase.daoUser)
                }
            }
            btnBack.setOnClickListener {
                goToBackFragment()
            }
        }
    }

    private fun loadingVisible() {
        binding.apply {
            containerText.visibility = View.GONE
            btnSave.visibility = View.GONE
            loading.visibility = View.VISIBLE
        }
    }

    private fun visibleBaseView() {
        binding.apply {
            containerText.visibility = View.VISIBLE
            btnSave.visibility = View.VISIBLE
            loading.visibility = View.GONE
        }
    }

    private fun setUserNewData() {
        binding.apply {
            user.password = passwordNew.text.toString()
            user.first_name = firstName.text.toString()
            user.last_name = lastName.text.toString()
        }
    }

    private fun checkNotEmptyEnterData(successful: () -> Unit) {
        binding.apply {
            if (checkNotEmptyEditText(firstName)) {
                if (checkNotEmptyEditText(lastName)) {
                    if (checkNotEmptyEditText(passwordNew)) {
                        successful()
                    } else showToast(root.context, getString(R.string.please_enter_password))
                } else showToast(root.context, getString(R.string.please_enter_last_name))
            } else showToast(root.context, getString(R.string.please_enter_first_name))
        }
    }

    private fun initField() {
        val getData =
            arguments?.getParcelable<UserModel>(KEY_RECEPTION_DATA)
        if (getData != null) {
            user = getData
            Log.d("MyLof", "data = $user")
            setData()
        }
        viewModel.resultEditDataUser.observeForever {
            if (it) {
                showToast(
                    binding.root.context, getString(R.string.toast_user_data_successful_update)
                )
                goToBackFragment()
            } else {
                showToast(binding.root.context, getString(R.string.error_sending_data_to_server))
                visibleBaseView()
            }
        }
    }

    private fun setData() {
        binding.apply {
            firstName.setText(user.first_name)
            lastName.setText(user.last_name)
            passwordNew.setText(user.password)
        }
    }

    private fun goToBackFragment() {
        try {
            findNavController().popBackStack()
        } catch (e: Exception) {
            Log.e("MyLog", e.message.toString())
        }
    }

    companion object {
        const val KEY_RECEPTION_DATA = "KEY_RECEPTION_DATA_EDIT_PROFILE"
    }
}