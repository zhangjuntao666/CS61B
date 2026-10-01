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
    public void TestToList1() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        assertThat(a.toList()).containsExactly(1,2,3).inOrder();
    }

    @Test
    public void TestToList2() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        assertThat(a.toList()).containsExactly(null, null, null, null, null, null, null, null).inOrder();
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

    @Test
    public void TestSize() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        ArrayDeque61B<Integer> b = new ArrayDeque61B<>();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(8);
        assertThat(a.size()).isEqualTo(8);
        assertThat(b.size()).isEqualTo(0);
    }

    @Test
    public void TestRemoveFirst() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        ArrayDeque61B<Integer> b = new ArrayDeque61B<>();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(8);
        assertThat(a.removeFirst()).isEqualTo(1);
        assertThat(a.size()).isEqualTo(7);
        assertThat(b.removeFirst()).isEqualTo(null);
    }

    @Test
    public void TestRemoveLast() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        ArrayDeque61B<Integer> b = new ArrayDeque61B<>();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(8);
        assertThat(a.removeLast()).isEqualTo(8);
        assertThat(a.size()).isEqualTo(7);
        assertThat(b.removeFirst()).isEqualTo(null);
    }

    @Test
    public void TestRemove() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(8);
        assertThat(a.removeFirst()).isEqualTo(1);
        assertThat(a.size()).isEqualTo(7);
        assertThat(a.removeFirst()).isEqualTo(2);
        assertThat(a.size()).isEqualTo(6);
        assertThat(a.removeFirst()).isEqualTo(3);
        assertThat(a.size()).isEqualTo(5);
        assertThat(a.removeFirst()).isEqualTo(4);
        assertThat(a.size()).isEqualTo(4);
    }

    @Test
    public void TestRemove2() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.addLast(7);
        a.addLast(8);
        assertThat(a.removeLast()).isEqualTo(8);
        assertThat(a.size()).isEqualTo(7);
        assertThat(a.removeLast()).isEqualTo(7);
        assertThat(a.size()).isEqualTo(6);
        assertThat(a.removeLast()).isEqualTo(6);
        assertThat(a.size()).isEqualTo(5);
        assertThat(a.removeLast()).isEqualTo(5);
        assertThat(a.size()).isEqualTo(4);
        assertThat(a.removeLast()).isEqualTo(4);
        assertThat(a.size()).isEqualTo(3);
        assertThat(a.removeLast()).isEqualTo(3);
        assertThat(a.size()).isEqualTo(2);
        assertThat(a.removeLast()).isEqualTo(2);
        assertThat(a.size()).isEqualTo(1);
        assertThat(a.removeLast()).isEqualTo(1);
        assertThat(a.size()).isEqualTo(0);
        assertThat(a.removeLast()).isEqualTo(null);
        assertThat(a.size()).isEqualTo(0);
    }

    @Test
    public void TestGet1() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        assertThat(a.get(0)).isEqualTo(null);
    }

    @Test
    public void TestGet2() {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        assertThat(a.get(7)).isEqualTo(null);
        assertThat(a.get(0)).isEqualTo(1);
        assertThat(a.get(2)).isEqualTo(3);
    }
}
