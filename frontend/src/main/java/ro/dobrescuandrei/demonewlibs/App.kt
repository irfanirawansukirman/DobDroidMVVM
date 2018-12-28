package ro.dobrescuandrei.demonewlibs

import android.app.Application
import android.os.Handler
import com.chibatching.kotpref.Kotpref
import com.franmontiel.localechanger.LocaleChanger
import ro.dobrescuandrei.utils.Run
import java.util.*

class App : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        Run.handler=Handler(mainLooper)

        Kotpref.init(context = this)

        val locales = LinkedList<Locale>()
        locales.add(Locale(LANGUAGE_ENGLISH))
        locales.add(Locale(LANGUAGE_ROMANIAN))
        LocaleChanger.initialize(applicationContext, locales)
    }
}