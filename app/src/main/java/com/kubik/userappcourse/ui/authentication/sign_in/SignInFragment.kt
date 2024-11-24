package com.kubik.userappcourse.ui.authentication.sign_in

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kubik.userappcourse.DependenciesDatabase
import com.kubik.userappcourse.R
import com.kubik.userappcourse.data.user_data.UserRepositoryImpl
import com.kubik.userappcourse.databinding.FragmentSignInBinding
import com.kubik.userappcourse.ui.auntification.models.SignInUserModel
import com.kubik.userappcourse.ui.authentication.AuthenticationActivity
import com.kubik.userappcourse.ui.checkNotEmptyEditText
import com.kubik.userappcourse.ui.hideKeyboard
import com.kubik.userappcourse.ui.showToast


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel by lazy { ViewModelProvider(this).get(SignInViewModel::class.java) }
    private val daoUser by lazy { DependenciesDatabase.daoUser }
    private val userRepository by lazy { UserRepositoryImpl() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initField()
        checkSignInUser()
    }

    private fun checkSignInUser() {
        setLoadingView()
        viewModel.checkSignInUser(daoUser, userRepository)
    }

    private fun setLoadingView() {
        binding.apply {
            textSignIn.visibility = View.INVISIBLE
            editTextEnterLogin.visibility = View.INVISIBLE
            editTextEnterPassword.visibility = View.INVISIBLE
            btnSignIn.visibility = View.INVISIBLE
            btnSignUp.visibility = View.INVISIBLE
            loading.visibility = View.VISIBLE
        }
    }

    private fun setViewNotLoading() {
        binding.apply {
            textSignIn.visibility = View.VISIBLE
            editTextEnterLogin.visibility = View.VISIBLE
            editTextEnterPassword.visibility = View.VISIBLE
            btnSignIn.visibility = View.VISIBLE
            btnSignUp.visibility = View.VISIBLE
            loading.visibility = View.GONE
        }
    }

    private fun initField() {
        viewModel.isSuccessfulSignIn.observeForever {
            binding.apply {
                loading.visibility = View.GONE
                if (it) goToMainAct()
                else {
                    showToast(root.context, getString(R.string.data_entered_incorrectly))
                    editTextEnterPassword.setText("")
                }
            }
        }
        viewModel.isSignIn.observeForever {
            if (it) {
                goToMainAct()
            } else setViewNotLoading()
        }
    }

    private fun initView() {
        binding.apply {
            btnSignIn.setOnClickListener {
                onClickBtnSignIn()
            }
            btnSignUp.setOnClickListener {
                onClickBtnSingOut()
            }
        }
    }

    private fun onClickBtnSignIn() {
        binding.apply {
            checkNotEmptyAllEdittext {
                val login = editTextEnterLogin.text.toString()
                val password = editTextEnterPassword.text.toString()
                viewModel.signIn(
                    SignInUserModel(login = login, password = password),
                    daoUser,
                    userRepository
                )
                hideKeyboard(activity as Activity)
                loading.visibility = View.VISIBLE
            }
        }
    }

    private fun goToMainAct() {
        (activity as AuthenticationActivity).goToMainAct()
    }

    private fun checkNotEmptyAllEdittext(successful: () -> Unit) {
        binding.apply {
            if (checkNotEmptyEditText(editTextEnterLogin)) {
                if (checkNotEmptyEditText(editTextEnterPassword)) {
                    successful()
                } else showToast(root.context, getString(R.string.please_enter_password))
            } else showToast(root.context, getString(R.string.please_enter_login))
        }
    }

    private fun onClickBtnSingOut() {
        findNavController().navigate(R.id.action_signInFragment_to_signOutFragment)
    }
}