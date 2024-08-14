package com.markoved.countries.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.markoved.countries.R
import com.markoved.countries.domain.entity.Country
import com.markoved.countries.ui.theme.CountriesTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun CountryListScreen(
    onCountryItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CountriesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState is CountriesUIState.Loading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.loading),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        return
    }

    val readyState = uiState as CountriesUIState.Ready

    Column(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        SearchBar(
            readyState.searchText,
            { viewModel.onSearchTextChanged(it) },
            Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(16.dp))
        CountryList(
            data = readyState.countries,
            onItemClick = onCountryItemClick
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun CountryList(
    data: List<Country>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.no_results),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        return
    }
    LazyColumn(
        modifier = modifier
    ) {
        items(data) { item ->
            CountryItem(
                name = item.commonName,
                onClick = onItemClick
            )
        }
    }
}

@Composable
fun SearchBar(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = text,
        singleLine = true,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun CountryItem(
    name: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = { onClick(name) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun CountryElementPreview() {
    CountriesTheme {
        CountryItem(name = stringResource(id = R.string.sweden), onClick = {})
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun SearchBarPreview() {
    CountriesTheme {
        SearchBar(
            "Belarus",
            {},
            Modifier.padding(8.dp)
        )
    }
}
