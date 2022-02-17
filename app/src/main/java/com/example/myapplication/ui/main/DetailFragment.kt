package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.DetailFragmentBinding
import com.example.myapplication.ui.AbilityItemAdapter
import com.example.myapplication.util.load

class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvName.text = args.item.name
            args.item.icon?.let { ivIcon.load(it) }
            tvSpeedValue.text = args.item.getStatValue("speed").toString()
            tvAttackValue.text = args.item.getStatValue("attack").toString()
            tvDefenseValue.text = args.item.getStatValue("defense").toString()
            rvAbility.adapter = AbilityItemAdapter()
            (rvAbility.adapter as AbilityItemAdapter).submitList(
                args.item.ability.map { ability -> ability.ability.name })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}