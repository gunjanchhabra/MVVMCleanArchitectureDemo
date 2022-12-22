package com.example.kotlinmvvmcode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmvvmcode.databinding.AdapterProuctsBinding
import com.example.kotlinmvvmcode.view.model.ProductListUiModel

class ProductsAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    private var productItemList = mutableListOf<ProductListUiModel>()

    var onItemClicked : ((productItem : ProductListUiModel) -> Unit) ?= null

    fun setProductList(productItem: MutableList<ProductListUiModel>) {
        this.productItemList = productItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterProuctsBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productItemList[position]
        holder.binding.name.text = product.name
        holder.binding.price.text = "${product.priceSign}${product.price}"
        val imageUrl = "https:${product.apiFeaturedImage}"
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.binding.imageview)

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return productItemList.size
    }
}

class ProductViewHolder(val binding: AdapterProuctsBinding) : RecyclerView.ViewHolder(binding.root)