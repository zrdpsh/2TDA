1. Наследование с функциональной вариацией - без переопределения сигнатуры.
```java
class Person {
  public void logInto(String login) {
    System.out.println("Login is " + login);
  }
}

//
class StuffPerson extends Person {
  @Override
  public void logInto(String login) {
    System.out.println("Stuff member is logged in with " + login);
  }
}

//
class ClientPerson extends Person {
  public void logInto(String login, int clientId) {
    System.out.println("Client with login " + login + " and client ID " + clientId);
  }
}
```


2. Наследование с конкретизацией.
```java
abstract class Shape {
  public abstract double area();
}
```

3. Cтруктурное наследование - добавляем некоторое качественно новое свойство, семантически не привязанное к родительскому классу:
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
