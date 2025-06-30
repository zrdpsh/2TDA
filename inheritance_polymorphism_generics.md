1. Определяем интерфейс, вводящий в систему типов операцию сложения.
```java
public interface Addable<T> {
  T add(T other);
}
```
2. Вводим простой тип, которым будем параметризовать векторы - целое число, IntValue
```java
public class IntValue extends General implements Addable<IntValue> {
  // поле для храниения собственно числа
  private final int value;

  public IntValue(int value) {
    this.value = value;
  }

  // после переопределения обобщённая операция "сумма" определена для нашего целого числа
  @Override
  public IntValue add(IntValue other) {
    return new IntValue(this.value + other.value);
  }

  @Override String toString() {
    return Integer.toString(value);
  }
}
```
3. Вводим сам класс Vector, наследующий от General с операцией сложения
```java
public class Vector<T extends General & Addable<T>> extends General implements Addable<Vector<T>> {
  // поле для хранения значений вектора
  private final List<T> elems;

  public Vector(List<T> elems) {
    this.element = List.copyOf(elems);
  }

  public int size() {
    return elems.size();
  }

  public T get(int i) {
    return elems.get(i);
  }

  @Override
  public Vector<T> add(Vector<T> other) {
    // ветка для случаев, когда векторы нескладываемы
    if (this.size() != other.size()) {
      return None.INSTANCE;
    }

    // создаём хранилище для результата сложения и проходимся по всем соответствующим элементам слагаемых
    List<T> result = new ArrayList<>(size());
    for (int i = 0; i < size(); i++) {
      T sum = this.get(i).add(other.get(i));
      result.add(sum);
    }
    return new Vector<>(result);
  }

  @Override
  public String toString() {
    return elements.toString();
  }
}
```
4. И пробуем вызвать:
```java
public class Main {
  public static void main(String[] args) {
    Vector<IntValue> v1 = new Vector<>(List.of(new IntValue(1), new IntValue(2), new IntValue(3)));
    Vector<IntValue> v2 = new Vector<>(List.of(new IntValue(100), new IntValue(99), new IntValue(98)));

    // должно вывести 101 101 101
    System.out.println(v1.add(v2)); 

    // здесь должна сработать ветка с null
    System.out.println(v1.add(new Vector<>(List.of(
      new IntValue(1), new IntValue(2))))); 

    // Вложенные векторы (Vector<Vector<IntValue>>)
    Vector<Vector<IntValue>> vv1 = new Vector<>(List.of(v1, v2));
    Vector<Vector<IntValue>> vv2 = new Vector<>(List.of(
      new Vector<>(List.of(new IntValue(1), new IntValue(2), new IntValue(3))),
      new Vector<>(List.of(new IntValue(100) new IntValue(100), new IntValue(100)))
      ));

    // должно получиться [2, 4, 6],[200, 199, 198]
    System.out.println(vv1.add(vv2));

    // [[[2, 4, 6],[200, 199, 198]],[[2, 4, 6],[200, 199, 198]]]
    var vvv1 = new Vector<>(List.of(vv1, vv1));
    var vvv2 = new Vector<>(List.of(vv2, vv2));
    System.out.println(vvv1.add(vvv2));
    }
}

```
