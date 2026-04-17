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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import androidx.tv.material3.surfaceColorAtElevation
import androidx.compose.ui.graphics.Color
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun AccountsSelectionItem(
    modifier: Modifier = Modifier,
    itemKey: Any?,  // Renamed from 'key' to 'itemKey' to avoid conflict with Kotlin keyword
    accountsSectionData: AccountsSectionData,
    index: Int,
    onFocusChanged: (Boolean) -> Unit = {}
) {
    key(itemKey) {  // Use the renamed parameter here
        Surface(
            onClick = accountsSectionData.onClick,
            modifier = modifier
                .padding(4.dp)
                .fillMaxWidth()
                .aspectRatio(3.5f),
            colors = ClickableSurfaceDefaults.colors(
                containerColor = Color(0xFF1A1A1A),
                focusedContainerColor = Color(0xFFD3CCCC).copy(alpha = 0.8f),
                pressedContainerColor = Color(0xFFFFFFFF),
            ),
            shape = ClickableSurfaceDefaults.shape(shape = MaterialTheme.shapes.extraSmall),
            scale = ClickableSurfaceDefaults.scale(focusedScale = 1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    accountsSectionData.icon?.let { icon ->
                        Icon(
                            imageVector = icon,
                            contentDescription = accountsSectionData.title,
                            tint = Color(0xFFE50914),
                            modifier = Modifier.size(18.dp)
                        )
                    } ?: Spacer(modifier = Modifier.size(18.dp))

                    Text(
                        text = accountsSectionData.title,
                        color = Color(0xFFFFFFFF),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }

                accountsSectionData.value?.let { nnValue ->
                    Text(
                        text = nnValue,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFFFFFFFF),
                        modifier = Modifier
                            .alpha(0.75f)
                            .padding(top = 4.dp),
                        maxLines = 1
                    )
                }
            }
        }
    }
}