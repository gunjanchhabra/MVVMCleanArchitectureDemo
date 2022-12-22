package com.example.kotlinmvvmcode.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.kotlinmvvmcode.MyApplication
import com.example.kotlinmvvmcode.databinding.FragmentProductDetailBinding
import com.example.kotlinmvvmcode.di.viewmodel.ViewModelFactory
import com.example.kotlinmvvmcode.utils.Status
import com.example.kotlinmvvmcode.view.viewmodel.ProductDetailViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailFragment : Fragment() {
    lateinit var binding : FragmentProductDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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

        lifecycleScope.launch {
            viewModel.productDetailStateFlow.collect{
                when(it.status){
                    Status.LOADING -> {
                        binding.progressDialog.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressDialog.visibility = View.GONE
                        val imageUrl = viewModel.getProductImageUrl(it.data?.apiFeaturedImage)
                        Glide.with(requireContext()).load(imageUrl).into(binding.imageview)
                        binding.name.text = it.data?.name
                        binding.description.text = it.data?.description
                        binding.price.text = "Price- ${it.data?.priceSign}${it.data?.price}"
                    }
                    Status.ERROR -> {
                        binding.progressDialog.visibility = View.GONE
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}