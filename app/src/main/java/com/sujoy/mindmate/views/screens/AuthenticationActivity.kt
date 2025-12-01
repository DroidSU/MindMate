package com.sujoy.mindmate.views.screens

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sujoy.mindmate.R
import com.sujoy.mindmate.ui.theme.MindMateTheme
import com.sujoy.mindmate.vm.AuthenticationViewModel
import com.sujoy.mindmate.vm.ViewModelFactory

class AuthenticationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: AuthenticationViewModel by viewModels { ViewModelFactory(application) }
        setContent {
            MindMateTheme {
                SignInPage(
                    viewModel = viewModel,
//                    navigateToHome = {
//                        // Navigation logic to Home
//                    },
//                    navigateToLogin = {
//                        // Navigation logic to Login
//                    }
                )
            }
        }
    }
}

@Composable
fun SignInPage(viewModel: AuthenticationViewModel? = viewModel()) {
    val tabTitles = listOf("Sign In", "Sign Up")
    val selectedTabIndex by viewModel?.selectedTabIndex?.collectAsState()
        ?: remember { mutableStateOf(0) }
    val isLoading by viewModel?.isLoading?.collectAsState() ?: remember { mutableStateOf(false) }
    val authError by viewModel?.authError?.collectAsState() ?: remember { mutableStateOf("") }
    val isAuthenticated by viewModel?.isAuthenticated?.collectAsState()
        ?: remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(authError) {
        authError?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    LaunchedEffect(isAuthenticated) {
        if (isAuthenticated) {
            // Navigate to your main activity/screen
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as Activity).finish()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                    Header()
                    Spacer(modifier = Modifier.height(40.dp))
                }

                item {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                                color = Color.White
                            )
                        },
                        modifier = Modifier.padding(horizontal = 25.dp, vertical = 10.dp)
                    ) {
                        tabTitles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { if (!isLoading) viewModel?.onTabSelected(index) },
                                text = {
                                    Text(
                                        title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                })
                        }
                    }
                }

                item {
                    Card(
                        elevation = CardDefaults.cardElevation(10.dp),
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    ) {
                        when (selectedTabIndex) {
                            0 -> SignInBox(viewModel)
                            1 -> SignUpBox(viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPagePreview() {
    MindMateTheme {
        SignInPage(null)
    }
}

@Composable
private fun Header() {
    Image(
        painter = painterResource(R.drawable.mental_health),
        contentDescription = "MindMate Logo",
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .size(80.dp)
    )
    Text(
        "MindMate",
        style = MaterialTheme.typography.bodyMedium,
        fontSize = (32.sp),
        color = MaterialTheme.colorScheme.tertiary,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        "Your calm space for reflection",
        style = MaterialTheme.typography.bodyMedium,
        fontSize = (16.sp)
    )
}

@Composable
fun SignInBox(viewModel: AuthenticationViewModel?) {
    val email by viewModel?.email?.collectAsState() ?: remember { mutableStateOf("") }
    val password by viewModel?.password?.collectAsState() ?: remember { mutableStateOf("") }
    val emailError by viewModel?.emailError?.collectAsState() ?: remember { mutableStateOf(null) }
    val passwordError by viewModel?.passwordError?.collectAsState() ?: remember {
        mutableStateOf(
            null
        )
    }
    val isLoading by viewModel?.isLoading?.collectAsState() ?: remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(vertical = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = email,
            onValueChange = { viewModel?.onEmailChanged(it) },
            placeholder = { Text("Email", fontSize = 16.sp, color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Color when focused
                unfocusedIndicatorColor = Color.Gray, // Color when not focused
                disabledIndicatorColor = Color.Transparent, // Optional: hide when disabled
                errorIndicatorColor = MaterialTheme.colorScheme.error, // Color for error
            )
        )
        TextField(
            value = password,
            onValueChange = { viewModel?.onPasswordChanged(it) },
            placeholder = { Text("Password", fontSize = 16.sp, color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Color when focused
                unfocusedIndicatorColor = Color.Gray, // Color when not focused
                disabledIndicatorColor = Color.Transparent, // Optional: hide when disabled
                errorIndicatorColor = MaterialTheme.colorScheme.error, // Color for error
            )
        )
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Button(
            onClick = { viewModel?.onSignInClick() },
            enabled = !isLoading,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp, bottom = 20.dp, top = 0.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text(
                    "Sign In",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SignUpBox(viewModel: AuthenticationViewModel? = viewModel()) {
    val email by viewModel?.email?.collectAsState() ?: remember { mutableStateOf("") }
    val password by viewModel?.password?.collectAsState() ?: remember { mutableStateOf("") }
    val confirmPassword by viewModel?.confirmPassword?.collectAsState()
        ?: remember { mutableStateOf("") }
    val emailError by viewModel?.emailError?.collectAsState() ?: remember { mutableStateOf(null) }
    val passwordError by viewModel?.passwordError?.collectAsState()
        ?: remember { mutableStateOf(null) }
    val confirmPasswordError by viewModel?.confirmPasswordError?.collectAsState()
        ?: remember { mutableStateOf(null) }
    val isLoading by viewModel?.isLoading?.collectAsState() ?: remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
    ) {
        TextField(
            value = email,
            onValueChange = { viewModel?.onEmailChanged(it) },
            placeholder = { Text("Email", fontSize = 16.sp, color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailError != null,
            supportingText = { emailError?.let { Text(it) } },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Color when focused
                unfocusedIndicatorColor = Color.Gray, // Color when not focused
                disabledIndicatorColor = Color.Transparent, // Optional: hide when disabled
                errorIndicatorColor = MaterialTheme.colorScheme.error, // Color for error
            )
        )
        TextField(
            value = password,
            onValueChange = { viewModel?.onPasswordChanged(it) },
            placeholder = { Text("Password", fontSize = 16.sp, color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            supportingText = { passwordError?.let { Text(it) } },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Color when focused
                unfocusedIndicatorColor = Color.Gray, // Color when not focused
                disabledIndicatorColor = Color.Transparent, // Optional: hide when disabled
                errorIndicatorColor = MaterialTheme.colorScheme.error, // Color for error
            )
        )
        TextField(
            value = confirmPassword,
            onValueChange = { viewModel?.onConfirmPasswordChanged(it) },
            label = { Text("Confirm Password", fontSize = 16.sp, color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            visualTransformation = VisualTransformation.None,
            isError = confirmPasswordError != null,
            supportingText = { confirmPasswordError?.let { Text(it) } },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Color when focused
                unfocusedIndicatorColor = Color.Gray, // Color when not focused
                disabledIndicatorColor = Color.Transparent, // Optional: hide when disabled
                errorIndicatorColor = MaterialTheme.colorScheme.error, // Color for error
            )
        )
        Spacer(modifier = Modifier.padding(top = 30.dp))
        Button(
            onClick = { viewModel?.onSignUpClick() },
            enabled = !isLoading,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 20.dp, bottom = 20.dp, top = 0.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text(
                    "Sign Up",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}
