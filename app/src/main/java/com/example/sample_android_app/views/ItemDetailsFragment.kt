package com.example.sample_android_app.views

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sample_android_app.R
import com.example.sample_android_app.databinding.FragmentItemDetailsBinding
import com.example.sample_android_app.viewmodels.ItemsSharedViewModel
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ItemDetailsFragment : Fragment() {

    private var _binding: FragmentItemDetailsBinding? = null
    private val viewModel: ItemsSharedViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewsSetups()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun viewsSetups() {

        binding.item = viewModel.selectedItem.value

        val url = viewModel.selectedItem.value?.imageUrls?.get(index = 0)

        url?.let { url ->
            Picasso.with(context)
                .load(url)
                .into(binding.detailsImage)
        }

        binding.executePendingBindings()
    }

}