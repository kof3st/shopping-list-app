package me.kofesst.android.shoppinglist.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.kofesst.android.shoppinglist.domain.models.ShoppingItem

@Composable
fun ShoppingListColumn(
    list: List<ShoppingItem>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(list) { index, item ->
            ShoppingListItem(
                item = item,
                modifier = Modifier.fillMaxWidth(),
                onClick = { onItemClick(index) }
            )

            if (index != list.lastIndex) {
                Divider()
            }
        }
    }
}

@Composable
private fun ShoppingListItem(
    item: ShoppingItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(modifier = modifier.clickable(onClick = onClick)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
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
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Light
            )
        }
    }
}