package com.example.kotlinmvvmcode.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.kotlinmvvmcode.MyApplication
import com.example.kotlinmvvmcode.databinding.FragmentProductDetailBinding
import com.example.kotlinmvvmcode.di.viewmodel.ViewModelFactory
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.view.viewmodel.ProductDetailViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var binding: FragmentProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetailOfProduct(arguments?.getInt("ID") ?: 0)
        fetchProductDetail()
    }

    private fun fetchProductDetail() {
        lifecycleScope.launch {
            viewModel.productDetailStateFlow.collect { result ->
                when (result) {
                    is ApiResponse.Loading -> {
                        binding.progressDialog.visibility = View.VISIBLE
                    }
                    is ApiResponse.Success -> {
                        binding.progressDialog.visibility = View.GONE
                        Glide.with(requireContext()).load(result.data?.apiFeaturedImage)
                            .into(binding.imageview)
                        binding.name.text = result.data?.name
                        binding.description.text = result.data?.description
                        binding.price.text = result.data?.price
                    }
                    is ApiResponse.Error -> {
                        binding.progressDialog.visibility = View.GONE
                        Toast.makeText(requireActivity(), result.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
