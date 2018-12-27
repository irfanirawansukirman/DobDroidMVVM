package ro.dobrescuandrei.demonewlibs.api

import ro.dobrescuandrei.demonewlibs.model.User

class LoginRequest
(
    val username : String,
    val password : String
) : BaseRequest<User>()
{
    override fun execute() : User =
        User(1, "asdf")
}