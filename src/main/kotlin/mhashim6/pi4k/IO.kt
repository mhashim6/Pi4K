package mhashim6.pi4k

/**
 *@author mhashim6 on 10/03/19
 */

import com.pi4j.io.gpio.*
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent
import com.pi4j.io.gpio.event.GpioPinListenerDigital
import com.pi4j.io.gpio.trigger.GpioPulseStateTrigger
import com.pi4j.io.gpio.trigger.GpioSetStateTrigger
import com.pi4j.wiringpi.Gpio
import com.pi4j.wiringpi.Gpio.PWM_MODE_BAL


inline fun digitalInput(
    pin: Pin,
    name: String = pin.name,
    provider: GpioProvider = GpioFactory.getDefaultProvider(),
    resistance: PinPullResistance? = null,
    controller: GpioController = GpioFactory.getInstance(),
    initializer: GpioPinDigitalInput.() -> Unit = { }
): GpioPinDigitalInput = controller.provisionDigitalInputPin(provider, pin, name, resistance).apply(initializer)

/**
 * Triggers output state based on input state.
 *
 * Default `whenState` = `HIGH`.
 *
 * Default `targetState` = `whenState`.
 *
 * When default parameters are used, it behaves similar to `GpioSyncStateTrigger`.
 */
fun GpioPinDigitalInput.trigger(
    target: GpioPinDigitalOutput,
    whenState: PinState = PinState.HIGH,
    targetState: PinState = whenState
) {
    this.addTrigger(GpioSetStateTrigger(whenState, target, targetState))
}

/**
 * When the `this.state` matches `whenState`, pulse `target` to the HIGH state for `millis` time.
 *
 * Default `whenState` = `HIGH`.
 */
fun GpioPinDigitalInput.triggerPulse(
    target: GpioPinDigitalOutput,
    whenState: PinState = PinState.HIGH,
    millis: Long
) {
    this.addTrigger(GpioPulseStateTrigger(whenState, target, millis))
}

inline fun digitalOutput(
    pin: Pin,
    name: String = pin.name,
    provider: GpioProvider = GpioFactory.getDefaultProvider(),
    state: PinState = PinState.LOW,
    controller: GpioController = GpioFactory.getInstance(),
    initializer: GpioPinDigitalOutput.() -> Unit = { }
): GpioPinDigitalOutput = controller.provisionDigitalOutputPin(provider, pin, name, state).apply(initializer)


fun GpioPinDigitalOutput.pulse(millis: Long, controller: GpioController = GpioFactory.getInstance()) =
    controller.pulse(millis, this)

fun GpioPinDigital.onStateChange(action: (event: GpioPinDigitalStateChangeEvent, edge: PinEdge, state: PinState) -> Unit) {
    this.addListener(GpioPinListenerDigital { action(it, it.edge, it.state) })
}

inline fun analogInput(
    pin: Pin,
    name: String = pin.name,
    provider: GpioProvider = GpioFactory.getDefaultProvider(),
    controller: GpioController = GpioFactory.getInstance(),
    initializer: GpioPinAnalogInput .() -> Unit = { }
): GpioPinAnalogInput = controller.provisionAnalogInputPin(provider, pin, name).apply(initializer)

/**
 *Default `defaultValue` = 0.0
 */
inline fun analogOutput(
    pin: Pin,
    defaultValue: Double = 0.0,
    name: String = pin.name,
    provider: GpioProvider = GpioFactory.getDefaultProvider(),
    controller: GpioController = GpioFactory.getInstance(),
    initializer: GpioPinAnalogOutput .() -> Unit = { }
): GpioPinAnalogOutput = controller.provisionAnalogOutputPin(provider, pin, name, defaultValue).apply(initializer)

/**
 * @param clockDivisor This sets the divisor for the PWM clock. The default is 1.
 * @param mode The PWM generator can run in 2 modes – balanced and mark:space. The mark:space mode is traditional, however the default mode in the Pi is balanced. You can switch modes by supplying the parameter: PWM_MODE_BAL or PWM_MODE_MS.
 * @param range This sets the range register in the PWM generator. The default is 1024.
 */
fun pwm(clockDivisor: Int = 1, mode: Int = PWM_MODE_BAL, range: Int = 1024) {
    Gpio.pwmSetClock(clockDivisor)
    Gpio.pwmSetMode(mode)
    Gpio.pwmSetRange(range)
}

/**
 *Default `defaultValue` = 0
 */
inline fun pwmOutput(
    pin: Pin,
    defaultValue: Int = 0,
    name: String = pin.name,
    provider: GpioProvider = GpioFactory.getDefaultProvider(),
    controller: GpioController = GpioFactory.getInstance(),
    initializer: GpioPinPwmOutput.() -> Unit = { }
): GpioPinPwmOutput = controller.provisionPwmOutputPin(provider, pin, name, defaultValue).apply(initializer)

/**
 *Default `defaultValue` = 0
 */
inline fun softPwmOutput(
    pin: Pin,
    defaultValue: Int = 0,
    name: String = pin.name,
    provider: GpioProvider = GpioFactory.getDefaultProvider(),
    controller: GpioController = GpioFactory.getInstance(),
    initializer: GpioPinPwmOutput.() -> Unit = { }
): GpioPinPwmOutput = controller.provisionSoftPwmOutputPin(provider, pin, name, defaultValue).apply(initializer)

/**
 *  Shuts the gpio controller instance down, make sure to eventually call this
 * in a cleanup point in your program.
 */
fun gpioShutdown() = GpioFactory.getInstance().shutdown()