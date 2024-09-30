package com.yu.piccircles.view.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.yu.piccircles.view.DestinationScreen
import com.yu.piccircles.PicViewModel
import com.yu.piccircles.view.CommonProgressSpinner
import com.yu.piccircles.view.navigateTo

@Composable
fun ProfileScreen(navController: NavController, vm: PicViewModel) {
    val isLoading = vm.inProgress.value
    if (isLoading) {
        CommonProgressSpinner()
    } else {
        val userData = vm.userData.value
        var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
        var username by rememberSaveable { mutableStateOf(userData?.username ?: "") }
        var bio by rememberSaveable { mutableStateOf(userData?.bio ?: "") }

        ProfileContent(
            vm = vm,
            name = name,
            username = username,
            bio = bio,
            onNameChange = { name = it },
            onUsernameChange = { username = it },
            onBioChange = { bio = it },
            onSave = { vm.updateProfileData(name, username, bio) },
            onBack = { navigateTo(navController = navController, DestinationScreen.MyPosts) },
            onLogout = {
                vm.onLogout()
                navigateTo(navController = navController, DestinationScreen.Login)
            }
        )
    }
}
