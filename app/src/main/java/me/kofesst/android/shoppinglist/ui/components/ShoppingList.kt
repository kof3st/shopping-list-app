@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package me.kofesst.android.shoppinglist.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.kofesst.android.shoppinglist.domain.models.ShoppingItem

@Composable
fun ShoppingListColumn(
    list: List<ShoppingItem>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit = {},
    horizontalPadding: Dp = 10.dp,
    verticalSpace: Dp = 10.dp
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(verticalSpace),
        modifier = modifier
    ) {
        itemsIndexed(list) { index, item ->
            ShoppingListItem(
                item = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .then(
                        if (index == 0) Modifier.padding(top = verticalSpace)
                        else Modifier
                    )
                    .then(
                        if (index == list.lastIndex) Modifier.padding(bottom = verticalSpace)
                        else Modifier
                    ),
                onClick = { onItemClick(index) }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ShoppingListItem(
    item: ShoppingItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${item.amount} шт.",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Light
            )
        }
    }
}