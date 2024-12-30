package com.example.praktikum12.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum12.model.Mahasiswa
import com.example.praktikum12.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val mahasiswaRepository: MahasiswaRepository) : ViewModel() {
    var uiState by mutableStateOf(DetailUiState())
        private set

    fun detailMhs(nim: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = mahasiswaRepository.getMahasiswaById(nim)
                uiState = mahasiswa.toUiStateDetail()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class DetailUiState(
    val nim: String = "",
    val nama: String = "",
    val alamat: String = "",
    val jenisKelamin: String = "",
    val kelas: String = "",
    val angkatan: String = "",
)

fun Mahasiswa.toUiStateDetail(): DetailUiState = DetailUiState(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan
)