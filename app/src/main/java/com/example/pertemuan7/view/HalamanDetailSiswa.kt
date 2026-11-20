package com.example.pertemuan7.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState by viewModel.uiDetailState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
private fun BodyDetailDataSiswa(
    detailSiswa: DetailSiswa,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
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
}

@Composable
private fun BarisDetailData(labelResId: Int, nilai: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(labelResId))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = nilai, fontWeight = FontWeight.Bold)
    }
}
