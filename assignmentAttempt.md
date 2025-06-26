1. Внутри готового класса General добавляем новый метод:
```java
public abstract class General implements Serializable {

  // методы, которые уже есть

  public static <T extends General> T assignmentAttempt(Class<T> targetType, General source) {
    if (source != null && targetType.isInstance(source)) {
      return (T) source;
    }
    return (T) None.INSTANCE;
  }
}
```



2. Теперь проверка на соответствие типу упакована внутрь одного общего метода:
```java
public class Main {
  public static void main(String[] args) {
    General someObject = new Any(); // образец
    General anotherObject = new Cat("Barsik"); // предполагаем, что Cat наследуется от Any, и находится ниже в иерархии - здесь сработает вторая ветка if

    Any firstAttempt = General.assignmentAttempt(Any.class, someObject);
    Any secondAttempt = General.assignmentAttempt(Any.class, anotherObject);

    // если присваивать в переменную подходящего типа - то всё в порядке
    System.out.println(firstAttempt == someObject);
    System.out.println(secondAttmept == wrongObject);

    Cat someCat = General.assignmentAttempt(Cat.class, someObject);
    Cat anotherCat = General.assignmentAttempt(Cat.class, anotherObject);

    // здесь первое из присваиваний уже не сработает, и вместо Cat в переменной лежит единственный экземпляр None, обе печати выведут true
    System.out.println(someCat == None.INSTANCE);
    System.out.println(someCat == anotherObject);
  }
}
```
