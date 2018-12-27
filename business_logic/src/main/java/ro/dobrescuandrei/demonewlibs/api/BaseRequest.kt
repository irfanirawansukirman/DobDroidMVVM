package ro.dobrescuandrei.demonewlibs.api

abstract class BaseRequest<T>
{
    abstract fun execute() : T
}