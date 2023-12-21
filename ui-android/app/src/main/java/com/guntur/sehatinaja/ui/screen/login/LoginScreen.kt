package com.guntur.sehatinaja.ui.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.guntur.sehatinaja.R
import com.guntur.sehatinaja.data.pref.repository.Injection
import com.guntur.sehatinaja.data.remote.response.LoginResponse
import com.guntur.sehatinaja.data.remote.retrofit.ApiType
import com.guntur.sehatinaja.ui.ViewModelFactory
import com.guntur.sehatinaja.ui.component.PasswordTextField
import com.guntur.sehatinaja.ui.component.UsernameTextField
import com.guntur.sehatinaja.ui.navigation.Screen
import com.guntur.sehatinaja.ui.state.PasswordState
import com.guntur.sehatinaja.ui.state.TextFieldState
import com.guntur.sehatinaja.ui.theme.Poppins

@Composable
fun LoginScreen(
    context: Context,
    navController : NavHostController,
    modifier : Modifier = Modifier,
    apiType: ApiType = ApiType.LOGIN,
    loginViewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(context, apiType))
    )
) {
    val dialogAlert = remember {
        mutableStateOf(false)
    }
    val userLoginState = loginViewModel.registerUser.observeAsState()

    LoginContent(
        navController = navController,
        onSignInSubmitted = { username, password ->
            loginViewModel.login(username, password)
        },
        userLoginState = userLoginState,
        dialogAlert = dialogAlert,
        modifier = modifier
            .padding(top = 108.dp)
    )
    if (userLoginState.value?.error == true){
        dialogAlert.value = true
    }
    Log.d("LoginScreen", "dialogAlert ${dialogAlert.value}")
    Log.d("LoginScreen", "login ${userLoginState.value?.error}")
}
@Composable
fun LoginContent(
    navController: NavHostController,
    onSignInSubmitted: (String, String) -> Unit,
    userLoginState: State<LoginResponse?>,
    dialogAlert: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Start
    ) {
        val focusRequester = remember { FocusRequester() }
        Text(
            text = stringResource(id = R.string.welcome),
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = stringResource(id = R.string.login_text)
        )
        Text(
            text = stringResource(id = R.string.username),
            modifier = Modifier
                .padding(top = 36.dp)
        )
        val usernameState = remember {
            TextFieldState()
        }
        UsernameTextField(username = usernameState, onImeAction = {focusRequester.requestFocus()})
        Text(
            text = stringResource(id = R.string.password),
            modifier = Modifier
                .padding(top = 16.dp)
        )

        val passwordState = remember { PasswordState() }

        val onSubmit = {
            if (passwordState.isValid) {
                onSignInSubmitted(
                    usernameState.text,
                    passwordState.text,
                )
            }
        }
        if (userLoginState.value?.error == true){
            if (dialogAlert.value) {
                AlertDialog(
                    onDismissRequest = {
                        userLoginState.value!!.error = null
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                dialogAlert.value = false
                                userLoginState.value!!.error = null
                                Log.d("LoginScreen", "dialogAlert ${dialogAlert.value}")
                                Log.d("LoginScreen", "login ${userLoginState.value!!.error}")
                            }) {
                            Text(
                                text = "Ok",
                                fontFamily = Poppins
                            )
                        }
                    },
                    title = {
                        Text(
                            text = "Alert",
                            fontFamily = Poppins
                        )
                    },
                    text = {
                        userLoginState.value!!.message?.let {
                            Text(
                                text = it,
                                fontFamily = Poppins,
                                fontSize = 14.sp
                            )
                        }
                    },
                    icon = {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "")
                    }
                )
            }
        }else if (userLoginState.value?.error == false){
            LaunchedEffect(userLoginState){
                navController.navigate(Screen.Home.route)
            }
        }

        PasswordTextField(
            passwordState = passwordState,
            onImeAction = { onSubmit() },
            placeholder = stringResource(id = R.string.password),
            imeAction = ImeAction.Done
        )
        Button(
            onClick = {
                onSubmit()
            },
            elevation = ButtonDefaults.filledTonalButtonElevation(),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                fontFamily = Poppins,
                modifier = Modifier
                    .align(CenterVertically)
            )

        }
        Row {
            Text(
                text = stringResource(id = R.string.login_validate),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.signup),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable(
                        onClick = { navController.navigate(Screen.Register.route) }
                    )
                    .padding(top = 8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

}