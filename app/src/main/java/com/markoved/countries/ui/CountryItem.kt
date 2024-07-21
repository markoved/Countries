package com.markoved.countries.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.markoved.countries.R
import com.markoved.countries.ui.theme.CountriesTheme

@Composable
fun CountryElement(
    name: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(onClick = { onClick(name) }) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun CountryElementPreview() {
    CountriesTheme {
        CountryElement(name = stringResource(id = R.string.sweden), onClick = {})
    }
}
