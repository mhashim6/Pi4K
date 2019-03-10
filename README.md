# Pi4K
[Pi4J](https://www.pi4j.com) Kotlin bindings.

## Demo
Make sure to check [Examples.kt](https://github.com/mhashim6/Pi4K/blob/master/src/main/kotlin/mhashim6/pi4k/Examples.kt).

```kotlin
fun blink() {
    digitalOutput(GPIO_01) {
        toggle()
        delay(500)
    }
}
```
- Pulse:
```kotlin
digitalOutput(GPIO_01).pulse(600)
```

- Triggers:
```kotlin
val input = digitalInput(GPIO_04)
val output = digitalOutput(GPIO_05)
input.trigger(output) //output is HIGH when input is HIGH.
// input.trigger(output, whenState = PinState.LOW, targetState = PinState.HIGH) //output is HIGH when input is LOW.
```

- Pulse triggers:
```kotlin
val input = digitalInput(GPIO_04)
val output = digitalOutput(GPIO_05)
input.triggerPulse(output, PinState.HIGH, millis = 1000) //when input is HIGH, output is HIGH for 1 second
```

- State change listeners:
```kotlin
digitalInput(GPIO_06).onStateChange { event, edge, state -> println(state) }
```

- PWM
```kotlin
pwm(clockDivisor = 500, mode = PWM_MODE_MS, range = 1000)
pwmOutput(GPIO_01, 400)
```
- Software PWM
```kotlin
softPwmOutput(GPIO_01, 400)
```
- Analog counterparts are also available.

## Testing
Tests were simulated on my machine using the ` PI4J_PLATFORM=Simulated` Environment variable.  
However, I was not able to mock/test `AnalogPins` bindings. Contributions are always welcome.

## Dependency
- Gradle

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency:
```groovy
dependencies {
	implementation 'com.github.mhashim6:Pi4K:Tag'
}
```
---
- Maven
```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
```
Add the dependency:
```xml
<dependency>
	<groupId>com.github.mhashim6</groupId>
	<artifactId>Pi4K</artifactId>
	<version>Tag</version>
</dependency>
```
