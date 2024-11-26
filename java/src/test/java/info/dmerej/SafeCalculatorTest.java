package info.dmerej;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SafeCalculatorTest {
  @Test
  void should_not_throw_when_authorized() {
    /*
    SafeCalculator calculator = new SafeCalculator(() -> true);


    int result = calculator.add(1, 2);

    assert(result == 3);*/

    Authorizer mokitoAuthorizer = mock(Authorizer.class);
    when(mokitoAuthorizer.authorize()).thenReturn(true);

    SafeCalculator calculator = new SafeCalculator(mokitoAuthorizer);

    // Act: Perform addition
    int result = calculator.add(1, 2);

    // Assert: Verify the correct result
    assert(result == 3);

  }
}
