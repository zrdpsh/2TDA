1. В старых версиях Java (до 17-ой) контролировать наследование от классов/методов нужно было с помощью ключевого слова final:
```java
// Запретить можно наследование всего класса
public final class BaseLogger {
  public void logMessage() {
    System.out.println("logged message");
  }
}

// Либо отдельного метода
public class BaseLogger {
  public final void logMessage() {
    System.out.println("logged message");
  }
}

public class DataBaseLogger extends BaseLogger{ // это - разрешённая конструкция
  @Override                                     // а здесь уже будет ошибка
  public void logMessage() {
    System.out.println("logged message");
  }
}

```
При этом, вызывая один метод из другого, можно переопределять поведение, но трогая API
```java
public class BaseLogger {
  public final void logMessage() {
    innerOperation();
    System.out.println("logged message");
  }
  
  protected void innerOperation() {
    System.out.println("Inner operation invoked");
  }
}

public class DataBaseLogger extends BaseLogger{ // это - разрешённая конструкция
  @Override                                     // и это тоже - разрешённая конструкция
  public void innerOperation() {
    System.out.println("Overridden inner operation invoked");
  }
}

```
2. Начиная с 17 версии, теперь есть не только "все"/"никакие", но и "некоторые" потомки - контролировать наследование можно выборочно:
```java
// только Any/Special/Other могут наследоваться от General
public sealed class General permits Any, SpecialGeneral, OtherGeneral {
    public final void copyTo(General other) { /*…*/ }
}

// здесь будет ошибка - Entity нет после слова permits
public non-sealed class Entity extends General {
    // компилятор не даст
}

// а здесь уже вполне наследуемся от родительского класса, но по-прежнему не можем переопределить copyTo()
public non-sealed class Any extends General {
  @Override
  public void copyTo(General other) {
    // компилятор не даст  
  }
}

```
