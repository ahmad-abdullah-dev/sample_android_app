package com.example.sample_android_app.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample_android_app.R
import com.example.sample_android_app.databinding.FragmentItemsListBinding
import com.example.sample_android_app.databinding.ListItemBinding
import com.example.sample_android_app.models.data.Item
import com.example.sample_android_app.viewmodels.ItemsSharedViewModel
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ItemsListFragment : Fragment() {

    private var _binding: FragmentItemsListBinding? = null
    private val viewModel: ItemsSharedViewModel by activityViewModels()

    private val adapter by lazy { ItemsListAdapter() }

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemsListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewsSetups()

        bindAdapter()

        bindViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun bindViewModel() {

        activity?.let {

            binding.isEmpty = true

            viewModel.items.observe(viewLifecycleOwner) { items ->
                // Update the UI
                items?.let { items ->
                    Log.i("TAG", "Getting Items ${items.size}...")

                    binding.isEmpty = items.isEmpty()
                    adapter?.items = items
                }

            }

            viewModel?.updateProgressState = { visible ->
                if (visible) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }

            /*viewModel?.onError = { error ->
                ViewUtils.handleError(activity, error)
            }*/
        }
    }

    private fun viewsSetups() {

        val linearLayoutManager = LinearLayoutManager(this.activity)

        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.setHasFixedSize(true)

        // SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener { viewModel?.loadItems() }

        /*binding.recyclerView.addOnScrollListener(object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore( page: Int, totalItemsCount: Int, view: androidx.recyclerview.widget.RecyclerView) {
                viewModel?.loadNext()
            }
        })*/
    }

    private fun bindAdapter() {

        activity?.applicationContext?.let {

            binding.recyclerView.adapter = adapter

            adapter?.onClickItem = { view, item ->

                viewModel.selectedItem.value = item

                val args = Bundle()
                args.putString("title_action", item.name)

                //args.putParcelable(kParam_appointment, appointment)
                findNavController().navigate(R.id.action_ItemsListFragment_to_ItemDetailsFragment, args)
            }

        }
    }


    inner class ItemsListAdapter : RecyclerView.Adapter<ItemsListAdapter.ItemViewHolder>() {

        var onClickItem: ( (view: View, item: Item) -> Unit )? = null

        var items: List<Item>? = null
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

        override fun getItemCount() = items?.size ?: 0

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

            val item = items?.get(position)

            item?.let { item ->

                holder.binding.item     = item
                holder.binding.callback = OnClickCallback()

                val url = item.imageUrlsThumbnails.get(index = 0)

                url?.let { url ->
                    Picasso.with(context)
                        .load(url)
                        .into(holder.binding.iconImage)
                }
            }
        }

        inner class ItemViewHolder(val binding: ListItemBinding) :
            RecyclerView.ViewHolder(binding.root)

        inner class OnClickCallback {
            fun onClick(view: View, item: Item) {
                onClickItem?.invoke(view, item)
            }
        }
    }
}
