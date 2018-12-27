package ro.dobrescuandrei.demonewlibs.model

class Restaurant
(
    val id : ID,
    val name : String,
    val rating : Int,
    val type : Int
)
{
    companion object
    {
        const val TYPE_NORMAL = 1
        const val TYPE_FAST_FOOD = 2
    }
}