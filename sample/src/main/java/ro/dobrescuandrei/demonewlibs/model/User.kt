package ro.dobrescuandrei.demonewlibs.model

import ro.dobrescuandrei.demonewlibs.model.utils.ID
import ro.dobrescuandrei.mvvm.utils.Identifiable

class User
(
    id : ID,
    val name : String
) : Identifiable<ID>(id)