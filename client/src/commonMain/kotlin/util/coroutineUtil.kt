package util

import globalCoroutineContext
import korlibs.io.async.launchImmediately
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlin.coroutines.CoroutineContext

@DelicateCoroutinesApi
fun launchNow(context: CoroutineContext = GlobalScope.coroutineContext, callback: suspend () -> Unit) = CoroutineScope(context).launchImmediately(callback)
