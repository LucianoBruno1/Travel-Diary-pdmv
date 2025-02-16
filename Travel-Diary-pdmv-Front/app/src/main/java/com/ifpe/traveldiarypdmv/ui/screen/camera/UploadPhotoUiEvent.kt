package com.ifpe.traveldiarypdmv.ui.screen.camera

import android.content.Context
import android.net.Uri

sealed class UploadPhotoUiEvent {
    data class UploadPhoto(val uri: Uri, val userId: String, val token: String, val context: Context) : UploadPhotoUiEvent()
}

