package com.guntur.sehatinaja.ui.screen.detail

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.guntur.sehatinaja.data.pref.repository.Injection
import com.guntur.sehatinaja.data.remote.response.MlResponse
import com.guntur.sehatinaja.data.remote.retrofit.ApiType
import com.guntur.sehatinaja.ui.ViewModelFactory

@Composable
fun DetailScreen(
    context: Context,
    age: String,
    height: String,
    weight: String,
    apiType: ApiType = ApiType.BASE,
    detailViewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(context, apiType))
    )
) {
    val prediction = detailViewModel.predict.observeAsState().value
    Column {
        Text(text = "age: $age")
        Text(text = "height: $height")
        Text(text = "weight: $weight")
        detailViewModel.getPredict(age = age.toFloat(), weight = weight.toFloat(), height = height.toFloat())
        Text(text = prediction?.predict.toString())
        Text(text = prediction?.message.toString())
    }
}

@Composable
fun DetailContent(
    data: MlResponse?
) {
    Column {
        Text(text = data?.predict.toString())
    }
}