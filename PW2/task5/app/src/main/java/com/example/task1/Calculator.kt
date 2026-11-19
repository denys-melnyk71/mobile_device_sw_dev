package com.example.task1

fun calc_energy(
    capacity: Double,
    power: Double,
    time_delta: Double,
    energyPercentage: Int,
    k: Int
): Pair<Double, Int> {
    // обмежуємо значення діапазоном [0, capacity]
    val limiter = {
        value: Double ->  when (k) {
            1 -> minOf(value, capacity)
            -1 -> maxOf(value, 0.0)
            else -> value
        }
    }

    // рахуємо початкову енергію
    val initialEnergy = capacity * energyPercentage / 100.0
    // рахуємо результат по формулі E(t+delta_t) = E(t) +/- P * delta_t
    val energy = limiter( initialEnergy  + k * power * time_delta )

    // рахуємо відсоток
    val percentage = if (capacity > 0) (energy * 100 / capacity).toInt() else 0

    return energy to percentage
}