1. Наследование реализации - оригинальная логика сохраняется, но поверх неё добавляется возможность делать безопасный запрос изнутри обёртки с шифрованием.
```java
class HttpRequest {
    void doRequest(String body, Map<String, String> headers, String url) {
        System.out.println("Sending plain HTTP request to: " + url);
    }
}
class HttpsRequest extends HttpRequest {
    private String secretKey;

    @Override
    void doRequest(String body, Map<String, String> headers, String url) {
        handshake(url); 
        String encrypted = encrypt(body, secretKey);
        super.doRequest(encrypted, headers, url); 
    }

    private void handshake(String url) {
        System.out.println("Performing handshake with " + url);
        // заглушка
        this.secretKey = "sessionKey123";
    }

    private String encrypt(String body, String key) {
        return "[encrypted:" + body + "]";
    }
}
```

2. Льготное наследование - в классы-потомки перекочует вся логика родительского класса, включая технический метод generateID

```java
class BaseEntity {
    protected final UUID id = "id_" + generateUserId();
    protected final Instant createdAt = Instant.now();

    public UUID getId() {
        return id;
    }

    private static String generateUserId() {
        return UUID.randomUUID().toString();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void printAuditInfo() {
        System.out.println("ID: " + id + ", created at: " + createdAt);
    }
}

class User extends BaseEntity {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public void greet() {
        System.out.println("Hola, " + name + "!");
    }
}

class Admin extends User {
    public Admin(String name) {
        super(name);
    }

    public void ban(User user) {
        System.out.println("User " + user.getId() + " was banned.");
    }
}

```
