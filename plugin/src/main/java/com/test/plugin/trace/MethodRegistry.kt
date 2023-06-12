package com.test.plugin.trace

import com.test.plugin.trace.internal.bytecode.MethodDesc
import java.io.Serializable

class MethodRegistry : Serializable {

    companion object {

        private val map = hashMapOf<String, MethodRegistry>()

        fun getMethodRegistry(variant: String): MethodRegistry {
            return map[variant] ?: run {
                MethodRegistry().also {
                    map[variant] = it
                }
            }
        }

    }

    private val map: MutableMap<String, Int> = hashMapOf()

    private var count = 0

    fun generateId(method: MethodDesc): Int {
        val key = method.key
        return if (map.containsKey(key)) {
            map[key]!!
        } else {
            count++
            map[key] = count
            count
        }
    }

    fun getMap(): List<Pair<String, Int>> {
        return map
            .asSequence()
            .map { it.key to it.value }
            .sortedBy { it.second }
            .toList()
    }

}