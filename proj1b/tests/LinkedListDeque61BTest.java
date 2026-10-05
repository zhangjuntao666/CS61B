import deque.ArrayDeque61B;
import deque.LinkedListDeque61B;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class LinkedListDeque61BTest {
    // test for iterator, equals, toString
    // test for iterator
    @Test
    public void testIteratorA() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(7);
        a.addFirst(6);
        a.addFirst(5);

        StringBuilder sb = new StringBuilder();
        for (Integer item : a) {
            sb.append(item).append(", ");
        }

        String expected = "5, 6, 7, ";
        assertThat(sb.toString()).isEqualTo(expected);

        ArrayDeque61B<Integer> b = new ArrayDeque61B<>();

        StringBuilder sb1 = new StringBuilder();
        for (Integer item : b) {
            sb1.append(item).append(", ");
        }
        assertThat(sb1.toString()).isEqualTo("");
    }

    @Test
    public void testIteratorD() {
        LinkedListDeque61B<Integer> a = new LinkedListDeque61B<>();
        a.addFirst(7);
        a.addFirst(6);
        a.addFirst(5);

        StringBuilder sb = new StringBuilder();
        for (Integer item : a) {
            sb.append(item).append(", ");
        }

        String expected = "5, 6, 7, ";
        assertThat(sb.toString()).isEqualTo(expected);
    }

    // tests for toString
    @Test
    public void TestToStringA() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(8);

        String result1 = a.toString();

        ArrayDeque61B<Integer> b = new ArrayDeque61B<>();
        String result2 = b.toString();

        assertThat(result1).isEqualTo("[1, 2, 3, 4, 5, 6, 7, 8]");
        assertThat(result2).isEqualTo("[]");
    }

    @Test
    public void TestToStringD() {
        LinkedListDeque61B<Integer> a = new LinkedListDeque61B<>();
        a.addFirst(7);
        a.addFirst(6);
        a.addFirst(5);

        String result1 = a.toString();

        LinkedListDeque61B<Integer> b = new LinkedListDeque61B<>();
        String result2 = b.toString();

        assertThat(result1).isEqualTo("[5, 6, 7]");
        assertThat(result2).isEqualTo("[]");
    }

    // tests for equals
    @Test
    public void TestEqualsA() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(7);
        a.addFirst(6);
        a.addFirst(5);

        ArrayDeque61B<Integer> b = new ArrayDeque61B<>();
        b.addFirst(7);
        b.addFirst(6);
        b.addFirst(5);

        ArrayDeque61B<Integer> c = new ArrayDeque61B<>();

        assertThat(a.equals(b)).isTrue();
        assertThat(a.equals(c)).isFalse();
    }

    @Test
    public void TestEqualsD() {
        LinkedListDeque61B<Integer> a = new LinkedListDeque61B<>();
        a.addFirst(7);
        a.addFirst(6);
        a.addFirst(5);

        LinkedListDeque61B<Integer> b = new LinkedListDeque61B<>();
        b.addFirst(7);
        b.addFirst(6);
        b.addFirst(5);

        LinkedListDeque61B<Integer> c = new LinkedListDeque61B<>();

        assertThat(a.equals(b)).isTrue();
        assertThat(a.equals(c)).isFalse();
    }

}
