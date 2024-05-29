package com.example.beverageapp.ui.rates;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RatesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RatesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Your Rates");
    }

    public LiveData<String> getText() {
        return mText;
    }
}