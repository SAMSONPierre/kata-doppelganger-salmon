package info.dmerej;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountApplierTest {

  User user1 = new User("toto", "toto@gmail.com");

  User user2 = new User("Marie", "marie@gmail.com");

  List<User> userList = new ArrayList<>();


  CountingNotifier countingNotifier = new CountingNotifier();

  DiscountApplier discountApplier = new DiscountApplier(countingNotifier);

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v1() {
    userList.add(user1);
    userList.add(user2);
    // TODO: write a test to demonstrate the bug in DiscountApplier.applyV1()
    //NotifierClass mockNotifierClass = Mockito.mock(NotifierClass.class);

    discountApplier.applyV1(10, userList);

    assertEquals(2, countingNotifier.getNotificationCount());

    //Mockito.verify(mockNotifierClass, times(2)).notify(Mockito.any(User.class), Mockito.anyString());

  }


  // Custom Notifier implementation
  static class CountingNotifier implements Notifier {
    private int notificationCount = 0;

    //HashMap<User, Integer> userMessageMap = new HashMap<>();

    @Override
    public void notify(User user, String message) {
      notificationCount++;

      //userMessageMap.put(user, );
    }

    public int getNotificationCount() {
      return notificationCount;
    }

  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v2() {
    // TODO: write a test to demonstrate the bug in DiscountApplier.applyV2()
    userList.add(user1);
    userList.add(user2);
    discountApplier.applyV2(10, userList);

  }

}
