package com.project.jufood.presentation.main.screens.profile.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.jufood.domain.util.ProfilePage

@Composable
fun ActionChips(selected: ProfilePage, onSelected: (ProfilePage) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 50.dp)
    ) {
        items(ProfilePage.entries.toTypedArray()) { page ->
            Chip(
                title = stringResource(id = page.title),
                selected = (selected == page).toString(),
                onSelected = {
                    onSelected(page)
                }
            )
        }
    }
}
