package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

  @Test
  @DisplayName("floats may be clamped to the range [0, 1], inclusive")
  void testClamp01() {
    assertEquals(0, Utils.clamp(-1f));
    assertEquals(0, Utils.clamp(0f));
    assertEquals(1, Utils.clamp(1.1f));
    assertEquals(1, Utils.clamp(1f));
    assertTrue(Utils.aboutEqual(.1f, Utils.clamp(.1f)));
    assertTrue(Utils.aboutEqual(.9f, Utils.clamp(.9f)));
  }
}
