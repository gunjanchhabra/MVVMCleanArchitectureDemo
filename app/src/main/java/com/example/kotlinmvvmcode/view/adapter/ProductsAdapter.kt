package com.example.kotlinmvvmcode

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvmcode.databinding.AdapterProductsBinding
import com.example.kotlinmvvmcode.view.adapter.ProductViewHolder
import com.example.kotlinmvvmcode.view.model.ProductListUiModel

class ProductsAdapter : RecyclerView.Adapter<ProductViewHolder>(), Filterable {

    private var productItemList = mutableListOf<ProductListUiModel>()
    private var filterdProductItemList = mutableListOf<ProductListUiModel>()
    var onItemClicked: ((productItem: ProductListUiModel) -> Unit)? = null

    fun setProductList(productItem: MutableList<ProductListUiModel>) {
        this.productItemList = productItem
        this.filterdProductItemList = productItemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterProductsBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(filterdProductItemList[position])
    }

    override fun getItemCount(): Int {
        return filterdProductItemList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) filterdProductItemList = productItemList else {
                    val resultList = mutableListOf<ProductListUiModel>()
                    productItemList
                        .filter {
                            (it.name?.lowercase()?.contains(charString.lowercase())!!)
                        }
                        .forEach { resultList.add(it) }
                    filterdProductItemList = resultList
                }
                return FilterResults().apply { values = filterdProductItemList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                filterdProductItemList = results?.values as MutableList<ProductListUiModel>
                notifyDataSetChanged()
            }
        }
    }
}
