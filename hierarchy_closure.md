1. Наследуемся от Any:
```java
public final class None extends Any {
  public static final None INSTANCE = new None();

  private None() {}

  @Override
  public void deepCopyTo(General target) {
    // ничего не копируем
  }

  @Override
  public General deepClone() {
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    return this == obj;
  }

  @Override
  public String serialize() {
    return "None";
  }

  public static None deserialize(String data) {
    if ("None".equals(data)) return INSTANCE;
    throw new IllegalArgumentException("Do not deserialize");
  }

  @Override
  public String toString() {
    return "None";
  }

  @Override
  public boolean isInstanceOf(Class<?> cls) {
    return cls == None.class || super.isInstanceOf(cls);
  }

  @Override
  public Class<?> getRealType() {
    return None.class;
  }
}
```

2. Пример использования
```java
public class Main {
    public static void main(String[] args) {
        General result = computeSomething(false);

        System.out.println("Clone: " + result.deepClone());        // печатает None
        System.out.println("Serialize: " + result.serialize());    // печатает "None"
        System.out.println("Equals: " + result.equals(result)); 
        
        General target = new Any();                                
        result.deepCopyTo(target);

        System.out.println("Target after copyFrom None: " + target); 
    }

    static General computeSomething(boolean ok) {
        if (ok) {
            Any real = new Any();
            // какой-то код
            return real;
        } else {
            return None.INSTANCE;  
        }
    }
}

```
