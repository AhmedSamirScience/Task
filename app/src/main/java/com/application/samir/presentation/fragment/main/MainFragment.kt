package com.application.samir.presentation.fragment.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.application.samir.R
import com.application.samir.common.base.BaseFragment
import com.application.samir.common.network.Resource
import com.application.samir.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {
    private var builderAlert: AlertDialog? = null

    override fun getContentView(): Int {
        return R.layout.fragment_main
    }
    override fun initializeViewModel() {
        val viewModel: MainViewModel by viewModels()
        baseViewModel = viewModel
    }
    override fun subscribeLiveData() {
        observeGetPost()
    }

    override fun initView() {
        baseViewBinding.morePostsBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v){
            baseViewBinding.morePostsBtn-> {
                findNavController().navigate(R.id.action_mainFragment_to_postsListFragment)
            }
        }
    }

    private fun observeGetPost(){
        lifecycleScope.launch {
            baseViewModel?.postLiveDate?.collect {
                when (it) {
                    is Resource.Success -> {
                        Log.i("getPostApi", "Success")
                        baseViewBinding.bodyTv.text = it.data?.body.toString()
                        baseViewBinding.titleTv.text = it.data?.title.toString()
                        builderAlert?.dismiss()
                    }
                    is Resource.Error -> {
                         Log.e("getPostApi", "Error"+ it.message)
                        builderAlert?.dismiss()
                    }
                    is Resource.Loading -> {
                         Log.i("getPostApi", "Loading")
                         initAndShowAlertDialog(getString(R.string.dialog_loading_text_message))
                    }
                    is Resource.Empty -> {
                         Log.i("getPostApi", "Empty")
                    }
                }
            }
        }

    }
    private fun initAndShowAlertDialog(messageAlert: String) {
        builderAlert = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog).create()
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_loading, null)
        val textMessage = view.findViewById<TextView>(R.id.textView)
        textMessage.text = messageAlert
        builderAlert?.setView(view)
        builderAlert?.setCanceledOnTouchOutside(false)
        builderAlert?.setCancelable(false)
        if (builderAlert?.isShowing == false) {
            builderAlert?.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        builderAlert?.dismiss()
    }

}