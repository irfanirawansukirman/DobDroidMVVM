package ro.dobrescuandrei.demonewlibs.model.utils

import com.chibatching.kotpref.KotprefModel
import ro.dobrescuandrei.utils.cached

object Preferences : KotprefModel()
{
    var userId by cached(intPref())
    var username by cached(stringPref())
}