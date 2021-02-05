package com.example.rssclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.rssclient.databinding.Tab2SavedBinding

class LaterRSSFragment : Fragment() {
    private lateinit var binding: Tab2SavedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.tab_2_saved, container, false)
        return binding.root
    }

}
