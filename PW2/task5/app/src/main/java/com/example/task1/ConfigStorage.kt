package com.example.task1

import android.content.Context

data class SavedEnergyConfig(
    val capacity: String = "",
    val power: String = "",
    val timeDelta: String = "",
    val energyPercentage: Float = 100f,
    val scenario: Scenarios = Scenarios.STORE_ENERGY
)

class ConfigStorage(context: Context) {
    private val prefs = context.getSharedPreferences("energy_system_config", Context.MODE_PRIVATE)

    fun loadConfig(): SavedEnergyConfig {
        val capacity = prefs.getString(KEY_CAPACITY, "") ?: ""
        val power = prefs.getString(KEY_POWER, "") ?: ""
        val timeDelta = prefs.getString(KEY_TIME_DELTA, "") ?: ""
        val energyPercentage = prefs.getFloat(KEY_ENERGY_PERCENTAGE, 100f)
        val scenarioName = prefs.getString(KEY_SCENARIO, Scenarios.STORE_ENERGY.name)
        val scenario = runCatching { Scenarios.valueOf(scenarioName ?: "") }
            .getOrDefault(Scenarios.STORE_ENERGY)

        return SavedEnergyConfig(
            capacity = capacity,
            power = power,
            timeDelta = timeDelta,
            energyPercentage = energyPercentage,
            scenario = scenario
        )
    }

    fun saveConfig(
        capacity: String,
        power: String,
        timeDelta: String,
        energyPercentage: Float,
        scenario: Scenarios
    ) {
        prefs.edit()
            .putString(KEY_CAPACITY, capacity)
            .putString(KEY_POWER, power)
            .putString(KEY_TIME_DELTA, timeDelta)
            .putFloat(KEY_ENERGY_PERCENTAGE, energyPercentage)
            .putString(KEY_SCENARIO, scenario.name)
            .apply()
    }

    private companion object {
        const val KEY_CAPACITY = "capacity"
        const val KEY_POWER = "power"
        const val KEY_TIME_DELTA = "time_delta"
        const val KEY_ENERGY_PERCENTAGE = "energy_percentage"
        const val KEY_SCENARIO = "scenario"
    }
}