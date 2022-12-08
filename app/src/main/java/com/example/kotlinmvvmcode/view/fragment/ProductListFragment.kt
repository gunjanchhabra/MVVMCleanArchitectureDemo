package com.example.kotlinmvvmcode.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.kotlinmvvmcode.MyApplication
import com.example.kotlinmvvmcode.ProductsAdapter
import com.example.kotlinmvvmcode.R
import com.example.kotlinmvvmcode.databinding.FragmentProductListBinding
import com.example.kotlinmvvmcode.datamodel.model.ProductItemModel
import com.example.kotlinmvvmcode.utils.Status
import com.example.kotlinmvvmcode.view.viewmodel.ProductListViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListFragment : Fragment() {
    lateinit var binding : FragmentProductListBinding

    @Inject
    lateinit var productListViewModel: ProductListViewModel

    @Inject
    lateinit var productsAdapter : ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productListViewModel.getProductsList()
        productsAdapter.onItemClicked = {
            navigateToNextScreen(it)
        }

        lifecycleScope.launch {
            productListViewModel._productListStateFlow.collect{
                when(it.status){
                    Status.LOADING -> {
                        binding.progressDialog.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressDialog.visibility = View.GONE
                        it.data?.let { products ->
                            binding.rvProducts.apply {
                                productsAdapter.setProductList(products)
                                this.adapter = productsAdapter
                            }
                        }
                    }
                    Status.ERROR -> {
                        binding.progressDialog.visibility = View.GONE
                        Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun navigateToNextScreen(it: ProductItemModel) {
        val bundle = Bundle()
        bundle.putInt("ID", it.id)
        binding.root.findNavController().navigate(R.id.product_list_fragment_to_product_detail_fragment, bundle)
    }
}