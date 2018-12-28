package ro.dobrescuandrei.demonewlibs.router

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

object ShowDialog
{
    fun <T> withList(
        context : Context,
        title: Int,
        cancelable: Boolean = true,
        onClick: ((Int, T) -> (Unit))? = null,
        values: List<T>)
    {
        MaterialDialog.Builder(context)
            .title(title)
            .cancelable(cancelable)
            .items(values)
            .itemsCallback { dialog, itemView, position, text ->
                onClick?.invoke(position, values[position])
            }
            .show()
    }
}