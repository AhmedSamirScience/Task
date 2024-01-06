package com.application.samir.presentation.fragment.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.samir.R
import com.application.samir.common.base.BaseFragment
import com.application.samir.common.network.Resource
import com.application.samir.data.remote.dto.PostDto
import com.application.samir.databinding.FragmentPostsListBinding
import com.application.samir.presentation.fragment.list.adapter.PostsListAdapter
import com.application.samir.presentation.fragment.list.clickEvent.PostListClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsListFragment : BaseFragment<PostsListViewModel, FragmentPostsListBinding>(),
    PostListClickListener {
    private var builderAlert: AlertDialog? = null

    override fun getContentView(): Int {
        return R.layout.fragment_posts_list
    }
    override fun initializeViewModel() {
        val viewModel: PostsListViewModel by viewModels()
        baseViewModel = viewModel
    }
    override fun subscribeLiveData() {
        observeGetPostsList()
    }

    override fun initView() {}
    override fun onClick(v: View?) {}

    private fun observeGetPostsList(){
        lifecycleScope.launch {
            baseViewModel?.postLiveDate?.collect { it ->

                when (it) {
                    is Resource.Success -> {
                        Log.i("getPostApi", "Success")
                        it.data?.let { it1 -> initPostsListAdapterRecycler(it1, baseViewBinding.recyclerView) }
                        builderAlert?.dismiss()
                    }
                    is Resource.Error -> {
                        Log.e("getPostApi", "Error"+ it.message)
                        Toast.makeText(requireContext(), it.message,Toast.LENGTH_SHORT).show()
                        builderAlert?.dismiss()
                        findNavController().apply {
                            popBackStack()
                            navigateUp()
                        }
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

    private fun initPostsListAdapterRecycler(myList: List<PostDto>, recyclerView: RecyclerView){
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        val paymentHistoryAdapter = PostsListAdapter().apply {
            submitMyList(myList, this@PostsListFragment)
        }
        recyclerView.visibility = View.VISIBLE
        recyclerView.adapter = paymentHistoryAdapter
    }
    override fun onItemClicked(itemSelected: PostDto) {
        Toast.makeText(requireContext(), itemSelected.toString() , Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        builderAlert?.dismiss()
    }
}