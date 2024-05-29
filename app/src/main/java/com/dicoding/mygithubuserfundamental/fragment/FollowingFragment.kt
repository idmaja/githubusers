package com.dicoding.mygithubuserfundamental.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubuserfundamental.adapter.UsersAdapter
import com.dicoding.mygithubuserfundamental.data.response.ItemList
import com.dicoding.mygithubuserfundamental.databinding.FragmentFollowingBinding
import com.dicoding.mygithubuserfundamental.viewmodel.UsersViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FollowingFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UsersViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        startViewModel()
        return binding.root
    }

    private fun startViewModel() {
        userViewModel.listFollowing.observe(viewLifecycleOwner) {listfollowing ->
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.listFollowing.layoutManager = layoutManager
            setUserData(listfollowing)
        }

        userViewModel.isLoadingFrag.observe(viewLifecycleOwner) {isLoadingFragment ->
            binding.apply {
                progressBar.visibility = if (isLoadingFragment) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setUserData(listFollowing: List<ItemList>) {
        val adapter = UsersAdapter(listFollowing)
        if (adapter.itemCount == 0){
            binding.status.visibility = View.VISIBLE
        }
        binding.listFollowing.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}