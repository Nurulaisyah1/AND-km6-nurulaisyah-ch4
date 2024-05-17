package com.foodapps.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapps.data.model.Profile

class ProfileViewModel : ViewModel() {
    val profileData =
        MutableLiveData(
            Profile(
                name = "NURUL AISYAH",
                username = "Nrlaisyh",
                email = "nurul.aisyah@gmail.com",
                profileImg = "https://github.com/Nurulaisyah1/Food_Asset/blob/main/img_nurulaisyah.jpg",
            ),
        )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}
