import deque.ArrayDeque61B;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }
    @Test
    public void TestAddFirst() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(7);
        a.addFirst(6);
        a.addFirst(5);
        a.addFirst(4);
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.addLast(8);
        assertThat(a.toList()).containsExactly(1,2,3,4,5,6,7,8).inOrder();
    }

    @Test
    public void TestAddLast() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(8);
        assertThat(a.toList()).containsExactly(1,2,3,4,5,6,7,8).inOrder();
    }

    @Test
    public void TestIsEmpty1() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        assertThat(a.isEmpty()).isTrue();
    }

    @Test
    public void TestIsEmpty2() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addLast(8);
        assertThat(a.isEmpty()).isFalse();
    }

}
