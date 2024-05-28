package id.ac.unpas.agenda.ui.screens

sealed class NavScreen(val route: String) {
    object Home : NavScreen("home")
    object Add : NavScreen("add")
    object Edit : NavScreen("edit") {
        const val routeWithArgument: String = "edit/{id}"
        const val argument0 : String = "id"
    }
    object Menu : NavScreen("menu")
    object Book : NavScreen("book")
    object Request : NavScreen("request")
    object Member : NavScreen("member")
    object Login : NavScreen("login")
}