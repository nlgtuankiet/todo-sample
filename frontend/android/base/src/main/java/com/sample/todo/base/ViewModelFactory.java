package com.sample.todo.base;

import androidx.lifecycle.ViewModel;

import com.airbnb.mvrx.MvRxState;

public interface ViewModelFactory <VM extends ViewModel, S extends MvRxState> {
    VM create(S initialState);
}

/**
 interface ViewModelFactory<VM : ViewModel, S : MvRxState> {
 fun create(initialState: S): VM
 }
 **/