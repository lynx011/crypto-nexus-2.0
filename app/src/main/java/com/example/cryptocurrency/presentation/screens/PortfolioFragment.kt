package com.example.cryptocurrency.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.R
import com.example.cryptocurrency.adapter.TransactionRoomAdapter
import com.example.cryptocurrency.databinding.FragmentPortfolioBinding
import com.example.cryptocurrency.utils.SwipeToDeleteCallback
import com.example.cryptocurrency.utils.cutOffPoint
import com.example.cryptocurrency.utils.navigateTo
import com.example.cryptocurrency.utils.navigateWithBundle
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class PortfolioFragment : Fragment() {
    private lateinit var binding: FragmentPortfolioBinding
    private lateinit var transactionRoomAdapter: TransactionRoomAdapter
//    private val portfolioViewModel: PortfolioViewModel by activityViewModels()
    private lateinit var swipeToDeleteCallback: SwipeToDeleteCallback
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var remoteConfigSettings: FirebaseRemoteConfigSettings
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_portfolio, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.recView.apply {
//            layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
//            transactionRoomAdapter = TransactionRoomAdapter {
//                try {
//                    val bundle = bundleOf("portfolio_name" to it.name, "portfolio_symbol" to it.symbol)
//                    navigateWithBundle(R.id.action_portfolio_to_chart_details, bundle)
//                }catch (e: IllegalArgumentException){
//                    Log.d("exc",e.message.toString())
//                }
//            }
//            adapter = transactionRoomAdapter
//        }
//        swipeToDeleteCallback =
//            SwipeToDeleteCallback(requireContext(),portfolioViewModel, transactionRoomAdapter)
//        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
//        itemTouchHelper.attachToRecyclerView(binding.recView)
//
//        portfolioViewModel.roomTransaction()
//            .observe(viewLifecycleOwner) { transaction ->
//                transactionRoomAdapter.differ.submitList(transaction.sortedBy { it.usd_amount })
//                var totalTrans = 0.0
//                for (i in transaction) {
//                    portfolioViewModel.totalTrans.value += i.usd_amount
//                }
//                portfolioViewModel.totalHoldingValue.value = "$${requireContext().cutOffPoint(portfolioViewModel.totalTrans.value)}"
//
//            }
//
//        portfolioViewModel.totalHoldingValue.observe(viewLifecycleOwner) { value ->
//            binding.totalValue.text = value
//        }
//
//        binding.addAsset.setOnClickListener {
//            navigateTo(R.id.action_portfolio_to_add_trans)
//        }

        remoteConfig = FirebaseRemoteConfig.getInstance()
        remoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()

        remoteConfig.setConfigSettingsAsync(remoteConfigSettings)
        remoteConfig.setDefaultsAsync(R.xml.firebase_remote_config_defaults)
        fetchRemoteParams()

    }

    @SuppressLint("SetTextI18n")
    private fun fetchRemoteParams() {
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
//                binding.balance.text = "$" + remoteConfig.getString("current_balance")
//                binding.lottie.setAnimationFromJson(remoteConfig.getString("lottie_anim"), "lottie")
                binding.lottie.setFailureListener { throwable ->
                    Log.d("config", throwable.toString())
                }
                val updated = task.result
                Log.d("config", "updated $updated")
            } else {
                Log.d("config", "failed")
            }
        }
    }


}