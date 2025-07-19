//1. Наследование с функциональной вариацией - без переопределения сигнатуры.
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
