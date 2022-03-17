package sg.mirobotic.teminavigation.utils

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

object ScreenUtils {

    fun getScreenSize(context: Context): ScreenSize {
        var screenLayout = context.resources.configuration.screenLayout
        screenLayout = screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK

        return when (screenLayout) {
            Configuration.SCREENLAYOUT_SIZE_SMALL -> ScreenSize.SMALL
            Configuration.SCREENLAYOUT_SIZE_NORMAL -> ScreenSize.NORMAL
            Configuration.SCREENLAYOUT_SIZE_LARGE -> ScreenSize.LARGE
            Configuration.SCREENLAYOUT_SIZE_XLARGE -> ScreenSize.XLARGE
            else -> ScreenSize.UNDEFINED
        }
    }

    fun getScreenRotation(context: Context): ScreenOrientation {

        return when (context.resources.configuration.orientation) {
            1 -> ScreenOrientation.PORTRAIT
            2 -> ScreenOrientation.LANDSCAPE
            3 -> ScreenOrientation.SQUARE
            else -> ScreenOrientation.UNDEFINED
        }

    }

    fun applyLayoutManager(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val size = getScreenSize(context)
        val rotation = getScreenRotation(context)

        Log.e("Screen", "Size: $size | Rotation: $rotation")

        var spanCount = 1
        if (rotation == ScreenOrientation.PORTRAIT) {
            if (size == ScreenSize.LARGE) {
                spanCount = 2
            } else if (size == ScreenSize.XLARGE) {
                spanCount = 3
            }
        }else if (rotation == ScreenOrientation.LANDSCAPE) {
            if (size == ScreenSize.LARGE) {
                spanCount = 3
            } else if (size == ScreenSize.XLARGE) {
                spanCount = 4
            }
        }
        recyclerView.layoutManager = GridLayoutManager(context, spanCount)
    }

}

enum class ScreenOrientation{
    UNDEFINED, PORTRAIT, LANDSCAPE, SQUARE
}

enum class ScreenSize{
    SMALL, NORMAL, LARGE, XLARGE, UNDEFINED
}