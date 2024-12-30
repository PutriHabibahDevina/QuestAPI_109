package com.example.praktikum12.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikum12.navigasi.DestinasiDetail
import com.example.praktikum12.ui.customwidgwt.CostumeTopAppBar
import com.example.praktikum12.viewmodel.DetailUiState
import com.example.praktikum12.viewmodel.DetailViewModel
import com.example.praktikum12.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMultiplatform::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    nim: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(nim) {
        viewModel.detailMhs(nim)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        DetailBody(
            detailUiState = viewModel.uiState,
            onRetryClick = {
                coroutineScope.launch { viewModel.detailMhs(nim) }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBody(
    detailUiState: DetailUiState,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        detailUiState.nim.isEmpty() -> Text(
            text = "Loading...",
            modifier = modifier.padding(16.dp)
        )
        detailUiState.nama.isEmpty() -> Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Text(text = "Error: ${detailUiState.alamat}") // Menampilkan pesan error
            Button(onClick = onRetryClick) {
                Text(text = "Retry")
            }
        }
        else -> detailUiState.let { detailUi ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.padding(16.dp)
            ) {
                Text(text = "Nama: ${detailUi.nama}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "NIM: ${detailUi.nim}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Jenis Kelamin: ${detailUi.jenisKelamin}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Alamat: ${detailUi.alamat}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Kelas: ${detailUi.kelas}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Angkatan: ${detailUi.angkatan}", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}