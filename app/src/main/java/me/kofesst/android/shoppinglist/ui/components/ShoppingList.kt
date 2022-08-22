@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package me.kofesst.android.shoppinglist.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.kofesst.android.shoppinglist.domain.models.ShoppingItem
import me.kofesst.android.shoppinglist.domain.models.ShoppingList
import me.kofesst.android.shoppinglist.presentation.utils.AppText
import me.kofesst.android.shoppinglist.presentation.utils.formatDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShoppingListItem(
    list: ShoppingList,
    modifier: Modifier = Modifier,
    elevation: Dp = 10.dp,
    padding: Dp = 20.dp,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        elevation = elevation,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Text(
                text = if (list.done) {
                    AppText.doneListText()
                } else {
                    AppText.activeListText()
                },
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = buildAnnotatedString {
                    append(AppText.listItemsText())
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("${list.items.size} шт.")
                    }
                },
                style = MaterialTheme.typography.body2
            )
            if (list.done) {
                Text(
                    text = buildAnnotatedString {
                        append(AppText.listDoneByText())
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(list.doneBy)
                        }
                    },
                    style = MaterialTheme.typography.body2
                )
                Text(
                    text = buildAnnotatedString {
                        append(AppText.listDoneAtText())
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(
                                list.doneAt.formatDate(
                                    showTime = true
                                )
                            )
                        }
                    },
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun EditingShoppingListColumn(
    items: List<ShoppingItem>,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 10.dp,
    verticalSpace: Dp = 10.dp
) {
    ShoppingListContent(
        items = items.map { item ->
            { modifier ->
                EditingShoppingItem(
                    item = item,
                    modifier = modifier
                )
            }
        },
        horizontalPadding = horizontalPadding,
        verticalSpace = verticalSpace,
        modifier = modifier
    )
}

@Composable
fun ShoppingListColumn(
    items: List<ShoppingItem>,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 10.dp,
    verticalSpace: Dp = 10.dp,
    onItemClick: (Int) -> Unit = {}
) {
    ShoppingListContent(
        items = items.mapIndexed { index, item ->
            { modifier ->
                ShoppingItem(
                    item = item,
                    modifier = modifier,
                    onClick = { onItemClick(index) }
                )
            }
        },
        horizontalPadding = horizontalPadding,
        verticalSpace = verticalSpace,
        modifier = modifier
    )
}

@Composable
fun ShoppingListContent(
    items: List<@Composable (Modifier) -> Unit>,
    horizontalPadding: Dp,
    verticalSpace: Dp,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(verticalSpace),
        modifier = modifier
    ) {
        itemsIndexed(items) { index, item ->
            item.invoke(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding)
                    .then(
                        if (index == 0) Modifier.padding(top = verticalSpace)
                        else Modifier
                    )
                    .then(
                        if (index == items.lastIndex) Modifier.padding(bottom = verticalSpace)
                        else Modifier
                    )
            )
        }
    }
}

@Composable
private fun EditingShoppingItem(
    item: ShoppingItem,
    modifier: Modifier = Modifier
) {
    var checked by remember {
        mutableStateOf(item.checked)
    }
    Card(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            ShoppingItemContent(
                name = item.name,
                amount = item.amount,
                modifier = Modifier.weight(1.0f)
            )
            Checkbox(
                enabled = !item.done,
                checked = checked,
                onCheckedChange = {
                    checked = it
                    item.checked = it
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ShoppingItem(
    item: ShoppingItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
    ) {
        ShoppingItemContent(
            name = item.name,
            amount = item.amount,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        )
    }
}

@Composable
private fun ShoppingItemContent(
    name: String,
    amount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$amount шт.",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Light
        )
    }
}