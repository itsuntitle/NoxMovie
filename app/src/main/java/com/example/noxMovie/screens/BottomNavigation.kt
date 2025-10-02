import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.noxMovie.screens.MainScreen
import com.example.noxMovie.screens.tabs.HomeItem
import com.example.noxMovie.screens.tabs.SearchItem


class TabsScreen : Screen {
    @Composable
    override fun Content() {
        HomeItem()
    }
}


// Home tab
object HomeTab : Tab {
    @Composable
    override fun Content() {
        MainScreen().Content()
    }

    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 0u,
            title = "Home",
            icon = rememberVectorPainter(Icons.Outlined.Home)
        )
}

//Search tab
object SearchTab : Tab {
    @Composable
    override fun Content() {
        SearchItem()
    }
    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 1u,
            title = "Search",
            icon = rememberVectorPainter(Icons.Outlined.Search)
        )


}
