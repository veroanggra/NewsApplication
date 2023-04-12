package com.veroanggra.newsapplication.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veroanggra.newsapplication.databinding.FragmentDetailNewsBinding


class NewsDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailNewsBinding.inflate(layoutInflater)
        return binding.root
    }
}