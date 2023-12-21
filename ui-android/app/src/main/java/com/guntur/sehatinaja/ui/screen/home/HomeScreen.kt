package com.guntur.sehatinaja.ui.screen.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.guntur.sehatinaja.R
import com.guntur.sehatinaja.data.pref.repository.Injection
import com.guntur.sehatinaja.data.remote.retrofit.ApiType
import com.guntur.sehatinaja.ui.ViewModelFactory
import com.guntur.sehatinaja.ui.component.AgeTextField
import com.guntur.sehatinaja.ui.component.GenderTextField
import com.guntur.sehatinaja.ui.component.UsernameTextField
import com.guntur.sehatinaja.ui.navigation.Screen
import com.guntur.sehatinaja.ui.state.TextFieldState
import com.guntur.sehatinaja.ui.theme.Poppins

@Composable
fun HomeScreen(
    context : Context,
    navHostController: NavHostController,
    navController : (String, String, String) -> Unit,
    apiType: ApiType = ApiType.BASE,
    homeViewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(context, apiType))
    )
) {
    homeViewModel.getSession().observeAsState().value.let { user ->
        if (user?.isLogin == false){
            navHostController.navigate(Screen.Welcome.route)
        }else{
            HomeContent(
                onSignInSubmitted = { age, height, weight->
                    navController(age, height, weight)
                },
            )
        }
    }
}


@Composable
fun HomeContent(
    onSignInSubmitted: (age: String, height: String, weight: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(30.dp)
    ) {
        Text(
            text = stringResource(id = R.string.hello),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(
            text = stringResource(id = R.string.home_text),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = stringResource(id = R.string.age))
        val ageState = remember {
            TextFieldState()
        }
        AgeTextField(
            int = ageState,
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = stringResource(id = R.string.gender))
        GenderTextField()
        Spacer(modifier = Modifier.padding(8.dp))
        val heightState = remember {
            TextFieldState()
        }
        val weightState = remember {
            TextFieldState()
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .width(160.dp)
                    .padding(end = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.height))

                AgeTextField(
                    int = heightState,
                    placeholder = stringResource(id = R.string.cm),
                    imeAction = ImeAction.Next
                )
            }
            Column {
                Text(text = stringResource(id = R.string.weight))
                AgeTextField(
                    int = weightState,
                    placeholder = stringResource(id = R.string.kg),
                    imeAction = ImeAction.Next
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = stringResource(id = R.string.history_of_illness))
        val onSubmit = {
            onSignInSubmitted(
                ageState.text,
                heightState.text,
                weightState.text
            )
        }
        val historyOfIllnessState = remember {
            TextFieldState()
        }
        UsernameTextField(
            username = historyOfIllnessState,
            onImeAction = {
                onSubmit()
                          },
            imeAction = ImeAction.Done
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                onSubmit()
                      },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .align(Alignment.End)
                .width(150.dp)
        ) {
            Text(
                text = stringResource(id = R.string.find),
                fontFamily = Poppins,
                fontSize = 16.sp

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
}