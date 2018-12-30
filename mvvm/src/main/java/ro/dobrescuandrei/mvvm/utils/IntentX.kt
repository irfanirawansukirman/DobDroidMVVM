package ro.dobrescuandrei.mvvm.utils

import android.content.Intent
import android.os.Bundle

fun <MODEL : Identifiable<*>> Intent.setModel(model : MODEL)
{
    val bundle=Bundle()
    bundle.putSerializable(ARG_MODEL, model)
    putExtras(bundle)
}

fun Intent.setChooseMode()
{
    val bundle=Bundle()
    bundle.putBoolean(ARG_CHOOSE_MODE, true)
    putExtras(bundle)
}
