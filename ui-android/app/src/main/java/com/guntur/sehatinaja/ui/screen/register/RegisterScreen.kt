package com.guntur.sehatinaja.ui.screen.register

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.guntur.sehatinaja.data.remote.response.RegisterResponse
import com.guntur.sehatinaja.data.remote.retrofit.ApiType
import com.guntur.sehatinaja.ui.ViewModelFactory
import com.guntur.sehatinaja.ui.component.EmailTextField
import com.guntur.sehatinaja.ui.component.PasswordTextField
import com.guntur.sehatinaja.ui.component.UsernameTextField
import com.guntur.sehatinaja.ui.navigation.Screen
import com.guntur.sehatinaja.ui.state.ConfirmPasswordState
import com.guntur.sehatinaja.ui.state.EmailState
import com.guntur.sehatinaja.ui.state.PasswordState
import com.guntur.sehatinaja.ui.state.TextFieldState
import com.guntur.sehatinaja.ui.theme.Poppins

@Composable
fun RegisterScreen(
    context: Context,
    navController: NavHostController,
    apiType: ApiType = ApiType.LOGIN,
    viewModel: RegisterViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(context, apiType))
    )
) {
    val registerUserState by viewModel.registerUser.observeAsState()
    val dialogAlert = remember {
        mutableStateOf(true)
    }
    RegisterContent(
        navController = navController,
        dialogAlert = dialogAlert,
        userSignUpState = registerUserState,
        onSignUpSubmitted = {
            viewModel.register(it.username,it.email,it.password,it.confirmPassword)
        }
    )
    if (registerUserState?.error == true){
        dialogAlert.value = true
    }
}

@Composable
fun RegisterContent(
    navController: NavHostController,
    userSignUpState: RegisterResponse?,
    dialogAlert: MutableState<Boolean>,
    onSignUpSubmitted: (RegisterData) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(30.dp)
    ) {
        Text(
            text = stringResource(id = R.string.welcome),
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = R.string.login_text),
            modifier = Modifier
                .padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.username),
        )
        val focusRequester = remember { FocusRequester() }
        val usernameState = remember {
            TextFieldState()
        }
        UsernameTextField(
            username = usernameState,
            imeAction = ImeAction.Next,
            onImeAction = {focusRequester.requestFocus()}
        )
        Text(
            text = stringResource(id = R.string.email),
            modifier = Modifier
                .padding(top = 16.dp)
        )
        val emailState = remember {
            EmailState()
        }
        EmailTextField(
            emailState = emailState,
            imeAction = ImeAction.Next,
            onImeAction = {focusRequester.requestFocus()}
        )
        Text(
            text = stringResource(id = R.string.password),
            modifier = Modifier
                .padding(top = 16.dp)
        )
        val passwordState = remember { PasswordState() }
        PasswordTextField(
            passwordState = passwordState,
            imeAction = ImeAction.Next,
            onImeAction = {focusRequester.requestFocus()},
            placeholder = stringResource(id = R.string.password)
        )
        Text(
            text = stringResource(id = R.string.confirm_password),
            modifier = Modifier
                .padding(top = 16.dp)
        )
        val confirmPasswordState = remember {
            ConfirmPasswordState(passwordState = passwordState)
        }

        val onSubmit = {
            if (passwordState.isValid && emailState.isValid) {
                onSignUpSubmitted(
                    RegisterData(
                        usernameState.text,
                        emailState.text,
                        passwordState.text,
                        confirmPasswordState.text,
                    )
                )
            }
        }
        if (userSignUpState?.error == true){
            if (dialogAlert.value){
                Log.d("LoginScreen","dialogAlert ${dialogAlert.value}")
                AlertDialog(
                    onDismissRequest = {
                        dialogAlert.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                dialogAlert.value = false
                                userSignUpState.error = null
                                Log.d("LoginScreen","dialogAlert ${userSignUpState.error}")
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
                            fontFamily = Poppins)
                    },
                    text = {
                        userSignUpState.message?.let {
                            Text(
                                text = it,
                                fontFamily = Poppins,
                                fontSize = 14.sp
                            ) }
                    },
                    icon = {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "")
                    }
                )
            }
        }else if (userSignUpState?.error == false){
            LaunchedEffect(userSignUpState){
                navController.navigate(Screen.Login.route)
            }
        }
        PasswordTextField(
            passwordState = confirmPasswordState,
            onImeAction = {onSubmit()},
            imeAction = ImeAction.Done,
            placeholder = stringResource(id = R.string.confirm_password)
        )
        Button(
            onClick = { onSubmit() },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.signup),
                fontFamily = Poppins
            )
        }
        Row {
            Text(
                text = stringResource(id = R.string.signup_validate),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.login),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable { navController.navigate(Screen.Login.route) }
                    .padding(top = 8.dp)
            )
        }
    }
}
data class RegisterData(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {

}