package com.gregjandl.raytracer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UtilsTest {
  @Test
  @DisplayName("floats may be compared for approximate equality")
  void testFloatAboutEquals() {
    assertTrue(Utils.aboutEqual(1.0f, 1.0f));
    assertFalse(Utils.aboutEqual(1.0f, 100.0f));
    assertTrue(Utils.aboutEqual(0, 0));
    assertTrue(Utils.aboutEqual(1.1f, 1.1f));
    assertFalse(Utils.aboutEqual(1.1f + Utils.EPSILON, 1.1f));
  }
}
