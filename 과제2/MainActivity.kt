package com.example.week7

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
class HomeFragment : Fragment(R.layout.home_fragment) {
    private lateinit var viewModel: MyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        viewModel.countLiveData.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.textView)?.text = "${it}"
        }

        view.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.increaseCount()
            findNavController().navigate(R.id.action_homeFragment_to_nav1Fragment)
        }
    }
}

class Nav1Fragment : Fragment(R.layout.nav1_fragment) {
    private lateinit var viewModel: MyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        viewModel.countLiveData.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.textView)?.text = "${it}"
        }

        view.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.increaseCount()
            findNavController().navigate(R.id.action_nav1Fragment_to_nav2Fragment)
        }
    }
}

class Nav2Fragment : Fragment(R.layout.nav2_fragment) {
    private lateinit var viewModel: MyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        viewModel.countLiveData.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.textView)?.text = "${it}"
        }

        view.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.increaseCount()
            findNavController().navigate(R.id.action_nav2Fragment_to_homeFragment)
        }
    }
}
