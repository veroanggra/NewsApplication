package com.veroanggra.newsapplication.feature.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veroanggra.newsapplication.databinding.FragmentBookmarkNewsBinding


class BookmarkNewsFragment : Fragment() {
    private lateinit var binding: FragmentBookmarkNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookmarkNewsBinding.inflate(layoutInflater)
        return binding.root
    }
}