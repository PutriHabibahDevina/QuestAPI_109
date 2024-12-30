import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikum12.navigasi.DestinasiUpdate
import com.example.praktikum12.ui.customwidgwt.CostumeTopAppBar
import com.example.praktikum12.viewmodel.PenyediaViewModel
import com.example.praktikum12.viewmodel.UpdateUiEvent
import com.example.praktikum12.viewmodel.UpdateUiState
import com.example.praktikum12.viewmodel.UpdateViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(
    nim: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(nim) {
        updateViewModel.loadMahasiswa(nim)
    }

    val updateUiState = updateViewModel.uiState

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        when (updateUiState.updateUiEvent) {
            is UpdateUiEvent -> {
                UpdateBody(
                    updateUiState = updateUiState,
                    onSiswaValueChange = { event -> updateViewModel.updateMhsState(event) },
                    onUpdateClick = {
                        coroutineScope.launch {
                            try {
                                updateViewModel.updateMhs() // Update data mahasiswa
                                navigateBack()
                            } catch (e: Exception) {
                                updateViewModel.updateMhsState(UpdateUiEvent())
                                println("Error updating: ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun UpdateBody(
    updateUiState: UpdateUiState,
    onSiswaValueChange: (UpdateUiEvent) -> Unit,
    onUpdateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Input untuk Nama
        OutlinedTextField(
            value = updateUiState.updateUiEvent.nama,
            onValueChange = { onSiswaValueChange(updateUiState.updateUiEvent.copy(nama = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input untuk NIM
        OutlinedTextField(
            value = updateUiState.updateUiEvent.nim,
            onValueChange = { onSiswaValueChange(updateUiState.updateUiEvent.copy(nim = it)) },
            label = { Text("NIM") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Input untuk Alamat
        OutlinedTextField(
            value = updateUiState.updateUiEvent.alamat,
            onValueChange = { onSiswaValueChange(updateUiState.updateUiEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input untuk Jenis Kelamin
        OutlinedTextField(
            value = updateUiState.updateUiEvent.jenisKelamin,
            onValueChange = { onSiswaValueChange(updateUiState.updateUiEvent.copy(jenisKelamin = it)) },
            label = { Text("Jenis Kelamin") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input untuk Kelas
        OutlinedTextField(
            value = updateUiState.updateUiEvent.kelas,
            onValueChange = { onSiswaValueChange(updateUiState.updateUiEvent.copy(kelas = it)) },
            label = { Text("Kelas") },
            modifier = Modifier.fillMaxWidth()
        )

        // Input untuk Angkatan
        OutlinedTextField(
            value = updateUiState.updateUiEvent.angkatan,
            onValueChange = { onSiswaValueChange(updateUiState.updateUiEvent.copy(angkatan = it)) },
            label = { Text("Angkatan") },
            modifier = Modifier.fillMaxWidth()
        )

        // Tombol untuk Update
        Button(
            onClick = onUpdateClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update")
        }
    }
}