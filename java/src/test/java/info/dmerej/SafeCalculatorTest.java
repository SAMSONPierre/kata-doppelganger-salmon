package info.dmerej;

import org.junit.jupiter.api.Test;

public class SafeCalculatorTest {
  @Test
  void should_not_throw_when_authorized() {
    SafeCalculator calculator = new SafeCalculator(() -> true);

    int result = calculator.add(1, 2);

    assert(result == 3);
    // TODO: write a test to demonstrate the bug in SafeCalculator.add()
  }
}
