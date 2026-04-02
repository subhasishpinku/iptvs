/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bacbpl.iptv.jetStram.presentation.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ListItem
import androidx.tv.material3.ListItemDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Switch
import androidx.tv.material3.SwitchDefaults
import androidx.tv.material3.Text
import androidx.tv.material3.surfaceColorAtElevation
import com.bacbpl.iptv.jetStram.data.util.StringConstants
import com.bacbpl.iptv.jetStram.presentation.theme.JetStreamCardShape

@Composable
fun SubtitlesSection(
    isSubtitlesChecked: Boolean,
    onSubtitleCheckChange: (isChecked: Boolean) -> Unit
) {
    // Get string resources
    val subtitlesSectionTitle = stringResource(id = StringConstants.Composable.Placeholders.SubtitlesSectionTitle)
    val subtitlesSectionSubtitlesItem = stringResource(id = StringConstants.Composable.Placeholders.SubtitlesSectionSubtitlesItem)
    val subtitlesSectionLanguageValue = stringResource(id = StringConstants.Composable.Placeholders.SubtitlesSectionLanguageValue)
    val subtitlesSectionLanguageItem = stringResource(id = StringConstants.Composable.Placeholders.SubtitlesSectionLanguageItem)

    Column(modifier = Modifier.padding(horizontal = 72.dp)) {
        Text(
            text = subtitlesSectionTitle,
            style = MaterialTheme.typography.headlineSmall
        )
        ListItem(
            modifier = Modifier.padding(top = 16.dp),
            selected = false,
            onClick = { onSubtitleCheckChange(!isSubtitlesChecked) },
            trailingContent = {
                Switch(
                    checked = isSubtitlesChecked,
                    onCheckedChange = onSubtitleCheckChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primaryContainer,
                        checkedTrackColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            headlineContent = {
                Text(
                    text = subtitlesSectionSubtitlesItem,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
            ),
            shape = ListItemDefaults.shape(shape = JetStreamCardShape)
        )
        ListItem(
            modifier = Modifier.padding(top = 16.dp),
            selected = false,
            onClick = {},
            trailingContent = {
                Text(
                    text = subtitlesSectionLanguageValue,
                    style = MaterialTheme.typography.labelLarge
                )
            },
            headlineContent = {
                Text(
                    text = subtitlesSectionLanguageItem,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
            ),
            shape = ListItemDefaults.shape(shape = JetStreamCardShape)
        )
    }
}