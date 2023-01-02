package com.example.kotlinmvvmcode.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.kotlinmvvmcode.MyApplication
import com.example.kotlinmvvmcode.ProductsAdapter
import com.example.kotlinmvvmcode.R
import com.example.kotlinmvvmcode.databinding.FragmentProductListBinding
import com.example.kotlinmvvmcode.di.viewmodel.ViewModelFactory
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.view.model.ProductListUiModel
import com.example.kotlinmvvmcode.view.viewmodel.ProductListViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListFragment : Fragment(), SearchView.OnQueryTextListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: FragmentProductListBinding
    private lateinit var productsAdapter: ProductsAdapter
    private val productListViewModel: ProductListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productsAdapter = ProductsAdapter()
        fetchProductList()
        productsAdapter.onItemClicked = {
            navigateToDetailScreen(it)
        }
    }

    private fun fetchProductList() {
        productListViewModel.viewModelScope.launch {
            productListViewModel.productListStateFlow.collect { result ->
                when (result) {
                    is ApiResponse.Loading -> {
                        binding.progressDialog.visibility = View.VISIBLE
                    }
                    is ApiResponse.Success -> {
                        binding.progressDialog.visibility = View.GONE
                        result.data?.let { products ->
                            binding.rvProducts.apply {
                                productsAdapter.setProductList(products)
                                this.adapter = productsAdapter
                                binding.svProducts.setOnQueryTextListener(this@ProductListFragment)
                            }
                        }
                    }
                    is ApiResponse.Error -> {
                        binding.progressDialog.visibility = View.GONE
                        Toast.makeText(requireActivity(), result.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun navigateToDetailScreen(it: ProductListUiModel) {
        binding.root.findNavController()
            .navigate(
                R.id.product_list_fragment_to_product_detail_fragment,
                bundleOf("ID" to it.id)
            )
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        productsAdapter.filter.filter(query)
        binding.svProducts.clearFocus()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        productsAdapter.filter.filter(newText)
        return false
    }

}
