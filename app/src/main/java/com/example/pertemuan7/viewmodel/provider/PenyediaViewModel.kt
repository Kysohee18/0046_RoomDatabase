package com.example.pertemuan7.viewmodel.provider

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan7.AplikasiSiswa
import com.example.pertemuan7.viewmodel.DetailViewModel
import com.example.pertemuan7.viewmodel.EditViewModel
import com.example.pertemuan7.viewmodel.EntryViewModel
import com.example.pertemuan7.viewmodel.HomeViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(aplikasiSiswa().container.repositoriSiswa) }
        initializer { EntryViewModel(aplikasiSiswa().container.repositoriSiswa) }
        initializer {
            DetailViewModel(
                savedStateHandle = createSavedStateHandle(),
                repositoriSiswa = aplikasiSiswa().container.repositoriSiswa
            )
        }
        initializer {
            EditViewModel(
                savedStateHandle = createSavedStateHandle(),
                repositoriSiswa = aplikasiSiswa().container.repositoriSiswa
            )
        }
    }
}

private fun CreationExtras.aplikasiSiswa(): AplikasiSiswa =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AplikasiSiswa
