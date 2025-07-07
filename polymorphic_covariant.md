1. Пример полиморфного вызова:
```java
interface Logger {
    void log(String msg);
}

class ConsoleLogger implements Logger {
    public void log(String msg) {
        System.out.println("Console: " + msg);
    }
}

class FileLogger implements Logger {
    public void log(String msg) {
        System.out.println("File: " + msg);
    }
}


public class Demo {
    public static void main(String[] args) {
        Logger logger = new ConsoleLogger(); 
        logger.log("one message");

        logger = new FileLogger();
        logger.log("other message");
    }
}

```

2. Пример ковариантного вызова:
```java
class Animal {
    Animal clone() {
        return new Animal();
    }
}

class Dog extends Animal {
    @Override
    //здесь мы возвращаем более узкий тип
    Dog clone() { 
        return new Dog();
    }
}

```
