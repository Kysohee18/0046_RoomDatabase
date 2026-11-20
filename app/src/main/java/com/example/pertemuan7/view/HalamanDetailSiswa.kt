package com.example.pertemuan7.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan7.R
import com.example.pertemuan7.uicontroller.DestinasiDetailSiswa
import com.example.pertemuan7.uicontroller.SiswaTopAppBar
import com.example.pertemuan7.viewmodel.DetailViewModel
import com.example.pertemuan7.viewmodel.DetailSiswa
import com.example.pertemuan7.viewmodel.provider.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    navigateBack: () -> Unit,
    navigateToEdit: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiDetailState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            if (uiState.detailSiswa.id != 0) {
                FloatingActionButton(
                    onClick = { navigateToEdit(uiState.detailSiswa.id) },
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_large))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.update)
                    )
                }
            }
        },
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiDetailSiswa.titleRes),
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        BodyDetailDataSiswa(
            detailSiswa = uiState.detailSiswa,
            onDelete = { deleteConfirmationRequired = true },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                coroutineScope.launch {
                    viewModel.deleteSiswa()
                    navigateBack()
                }
            },
            onDeleteCancel = { deleteConfirmationRequired = false }
        )
    }
}

@Composable
private fun BodyDetailDataSiswa(
    detailSiswa: DetailSiswa,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                BarisDetailData(R.string.nama1, detailSiswa.nama)
                BarisDetailData(R.string.alamat1, detailSiswa.alamat)
                BarisDetailData(R.string.telpon1, detailSiswa.telpon)
            }
        }
        OutlinedButton(
            onClick = onDelete,
            enabled = detailSiswa.id != 0,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.delete))
        }
    }
}

@Composable
private fun BarisDetailData(labelResId: Int, nilai: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(labelResId))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = nilai, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDeleteCancel,
        title = { Text(text = stringResource(R.string.attention)) },
        text = { Text(text = stringResource(R.string.tanya)) },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        }
    )
}
