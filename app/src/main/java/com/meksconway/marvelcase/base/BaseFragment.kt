package com.meksconway.marvelcase.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VM: ViewModel>(view: Int) : Fragment(view) {

    abstract val binding: ViewBinding
    abstract val viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDidLoad(savedInstanceState)
        observeViewModel(viewModel)
    }

    open fun observeViewModel(viewModel: VM) {

    }
    open fun viewDidLoad(savedInstanceState: Bundle?) {}

}