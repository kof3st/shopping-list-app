package me.kofesst.android.shoppinglist.presentation.screen

sealed class ScreenConstants {
    class Auth private constructor() {
        companion object {
            const val ROUTE_NAME = "auth"
        }
    }

    class Home private constructor() {
        companion object {
            const val ROUTE_NAME = "home"
        }
    }

    class ListDetails private constructor() {
        companion object {
            const val ROUTE_NAME = "list"
            const val LIST_ID_ARG_NAME = "id"
        }
    }

    class NewList private constructor() {
        companion object {
            const val ROUTE_NAME = "list/new"
        }
    }

    class NewListItem private constructor() {
        companion object {
            const val ROUTE_NAME = "list/new/item"
            const val ITEM_INDEX_ARG_NAME = "index"
        }
    }

    class NewListResult private constructor() {
        companion object {
            const val ROUTE_NAME = "list/new/result"
            const val LIST_ID_ARG_NAME = "id"
        }
    }

    class Lists private constructor() {
        companion object {
            const val ROUTE_NAME = "lists"
        }
    }
}