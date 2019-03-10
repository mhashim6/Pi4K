package mhashim6.pi4k

import com.pi4j.io.gpio.PinState
import com.pi4j.io.gpio.RaspiPin.*
import com.pi4j.wiringpi.Gpio.PWM_MODE_MS
import com.pi4j.wiringpi.Gpio.delay

/**
 *@author mhashim6 on 10/03/19
 */


fun blink() {
    digitalOutput(GPIO_01) {
        while (true) {
            toggle()
            delay(500)
        }
    }
}

fun triggerExample() {
    val input = digitalInput(GPIO_04)
    val output = digitalOutput(GPIO_05)
    input.trigger(output) //output is HIGH when input is HIGH.
//    input.trigger(output, whenState = PinState.LOW, targetState = PinState.HIGH) //output is HIGH when input is LOW.
}

fun pulseExample() {
    val input = digitalInput(GPIO_04)
    val output = digitalOutput(GPIO_05)
    input.triggerPulse(output, PinState.HIGH, millis = 1000) //when input is HIGH, output is HIGH for 1 second
}

fun stateChangeExample() {
    digitalInput(GPIO_06).onStateChange { event, edge, state -> println(state) }
}

fun pwmExamples() {
    pwm(clockDivisor = 500, mode = PWM_MODE_MS, range = 1000)
    pwmOutput(GPIO_01, 400)
}