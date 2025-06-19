import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import notflix.composeapp.generated.resources.Res
import notflix.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.muhammad.notflix.App
import org.muhammad.notflix.util.Context

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Notflix", icon = painterResource(Res.drawable.logo)
    ) {
        App(Context())
    }
}