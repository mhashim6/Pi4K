package mhashim6.pi4k

import com.pi4j.io.gpio.*
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.BeforeClass

/**
 *@author mhashim6 on 10/03/19
 */
class Tests {

    companion object Initializer {
        lateinit var inputPin: GpioPinDigitalInput
        lateinit var outputPins: Array<GpioPinDigitalOutput>

        /*
        // register inputPin listener
        pin.addListener(GpioPinListenerDigital { event ->
            // set pin state
            if (event.pin === inputPin) {
                pinMonitoredState = event.state
            }
        })
        */


        @BeforeClass
        @JvmStatic
        fun setUp() {
            inputPin = digitalInput(RaspiPin.GPIO_00)
            outputPins = arrayOf(
                digitalOutput(RaspiPin.GPIO_01),
                digitalOutput(RaspiPin.GPIO_04),
                digitalOutput(RaspiPin.GPIO_05)
            )

        }
    }

    @Test
    fun testPinHiState() {
        outputPins[0].high()
        assertEquals(PinState.HIGH, outputPins[0].state)
    }

    @Test
    fun testPinLowState() {
        outputPins[0].low()
        assertEquals(PinState.LOW, outputPins[0].state)
    }

    @Test
    fun testPinToggleState() {
        val prev = outputPins[2].state
        outputPins[2].toggle()
        assertEquals(PinState.getInverseState(prev), outputPins[2].state)
    }
}