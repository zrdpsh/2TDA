Если использовать в качестве основного инструмента контроля видимости protected, то:

1. Метод публичен в родителе и публичен в потомке - базовая механика, можно:
```java
class Logger {
  public void log(String msg) {
    System.out.println("Log: " + msg);
  }
}
class FileLogger extends Logger {
  @Override
  public void log(String msg) {
    System.out.println("File log: " + msg);
  }
}
```
2. Метод публичен в родителе и скрыт в потомке - уменьшать видлимость в Java нельзя.
3. Метод скрыт в родителе и публичен в потомке - фактически нельзя, но Java позволяет определять метод с таким же именем, как и в родителе, если родителький метод private:
```java
class Logger {
  private void log(String msg) {
    System.out.println("Log: " + msg);
  }
}
class FileLogger extends Logger {
  public void log(String msg) {
    System.out.println("File log: " + msg); // ошибки не будет, Logger.log() как бы вообще недоступен извне
  }
}
```
4. Метод скрыт в родителе и скрыт в потомке - поведение возможно, но это - снова скорее просто не запрещённое, нежели чем прямо предлагаемое поведени. Если нет @Override, мы просто переписываем метод родителя.
```java
class Logger {
  private void log(String msg) {
    System.out.println("Log: " + msg);
  }
}
class FileLogger extends Logger {
  private void log(String msg) {
    System.out.println("File log: " + msg); 
  }

  public void privateMethodCall() {
    log("Log method is called"); // перезапись сработает, ошибки не будет
  }
}
```
