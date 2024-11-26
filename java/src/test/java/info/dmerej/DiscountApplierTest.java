package info.dmerej;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DiscountApplierTest {

  User user1 = new User("toto", "toto@gmail.com");

  User user2 = new User("Marie", "marie@gmail.com");

  List<User> userList = new ArrayList<>();

  //TestNotifier countingNotifier = new TestNotifier();

  //DiscountApplier discountApplier = new DiscountApplier(countingNotifier);

  static class TestNotifier implements Notifier {
    private final HashMap<User, Integer> notifications = new HashMap<>();

    @Override
    public void notify(User user, String message) {
      notifications.compute(user, (key, value) -> (value == null) ? 1 : value + 1);
    }

    public HashMap<User, Integer> getNotifications() {
      return notifications;
    }
  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v1() {
    userList.add(user1);
    userList.add(user2);
    /* PARTIE 1*/
    /*
    discountApplier.applyV1(10, userList);

    assertEquals(1, countingNotifier.getNotifications().get(user1));
    assertEquals(1, countingNotifier.getNotifications().get(user2));
    */

    /* PARTIE 2 */
    Notifier notifier = mock(Notifier.class);
    DiscountApplier discountApplier = new DiscountApplier(notifier);
    discountApplier.applyV1(10, userList);
    verify(notifier, times(1)).notify(eq(user1), eq("You've got a new discount of 10%"));
    verify(notifier, times(1)).notify(eq(user2), eq("You've got a new discount of 10%"));
  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v2() {
    userList.add(user1);
    userList.add(user2);
    /* PARTIE 1*/
    /*
    discountApplier.applyV2(10, userList);

    assertEquals(1, countingNotifier.getNotifications().get(user1));
    assertEquals(1, countingNotifier.getNotifications().get(user2));
    */

    /* PARTIE 2 */
    Notifier notifier = mock(Notifier.class);
    DiscountApplier discountApplier = new DiscountApplier(notifier);
    discountApplier.applyV2(10, userList);

    verify(notifier, times(1)).notify(eq(user1), eq("You've got a new discount of 10%"));
    verify(notifier, times(1)).notify(eq(user2), eq("You've got a new discount of 10%"));
  }
}

