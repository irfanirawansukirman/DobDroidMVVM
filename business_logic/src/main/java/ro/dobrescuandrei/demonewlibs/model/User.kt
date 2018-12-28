package ro.dobrescuandrei.demonewlibs.model

import ro.dobrescuandrei.mvvm.utils.Identifiable

class User
(
    id : ID,
    val name : String
) : Identifiable<ID>(id)