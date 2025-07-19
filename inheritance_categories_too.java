//1. Наследование с функциональной вариацией - без переопределения сигнатуры.
```java
class Person {
  public void logInto(String login) {
    System.out.println("Login is " + login);
  }
}

// здесь сигнатура метода полностью совпадает с родительской
class StuffPerson extends Person {
  @Override
  public void logInto(String login) {
    System.out.println("Stuff member is logged in with " + login);
  }
}

// здесь - новый аргумент с для метода с тем же именем, в Java для такого Override не нужен
class ClientPerson extends Person {
  public void logInto(String login, int clientId) {
    System.out.println("Client with login " + login + " and client ID " + clientId);
  }
}
```


// 2. Наследование с конкретизацией.
```java
abstract class Shape {
  public abstract double area();
}

class Circle extends Shape {
  private double radius;

  public Circle(double radius) {
    this.radius = radius;
  }

  @Override
  public double area() {
    return Math.PI * radius * radius;
  }
}

class Rectrangle extends Shape {
  private double width, height;

  public Rectangle(double width, double height) {
    this.width = width;
    this.height = height;
}

  @Override
  public double area() {
    return widht * height;
  }
}
```

// 3. Cтруктурное наследование - добавляем некоторое качественно новое свойство, семантически не привязанное к родительскому классу:
```java
interface Printable {
  void print();
}

// Когда наследовать имеет смысл именно абстрактное свойство - здесь обоим классам нужно *что-то* печатать,
// но это *что-то* в каждом случае принципиально своё

class TextDocument implements Printable {
  private String documentContent;

  public TextDocument(String contents) {
    this.documentContent = contents;
  }

  @Override
  public void print() {
    System.out.println("Document contents are: " + documentContent);
  }
}

class Image implements Printable {
  private String fileName;

  public Image(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public void print() {
    System.out.println("This is the image named " + fileName);
  }
}
```
