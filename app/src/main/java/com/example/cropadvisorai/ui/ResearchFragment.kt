package com.example.cropadvisorai.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cropadvisorai.R

class ResearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_research, container, false)

        // Find sample link TextViews (similar to the old Explore)
        val tvLink1: TextView = view.findViewById(R.id.tvLink1)
        val tvLink2: TextView = view.findViewById(R.id.tvLink2)

        tvLink1.setOnClickListener {
            openUrl("https://www.fao.org/home/en/")
        }
        tvLink2.setOnClickListener {
            openUrl("https://www.agri.gov.in/")
        }

        return view
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
