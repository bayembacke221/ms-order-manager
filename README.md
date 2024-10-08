# Architecture Microservices pour Site E-commerce

Ce document décrit l'architecture microservices pour un site e-commerce, basée sur Spring Boot avec JDK 17, utilisant OpenFeign pour la communication inter-services et PostgreSQL comme base de données.

## Table des matières

1. [Vue d'ensemble de l'architecture](#vue-densemble-de-larchitecture)
2. [Service de Gestion des Produits](#service-de-gestion-des-produits)
3. [Service de Gestion des Ventes aux Clients](#service-de-gestion-des-ventes-aux-clients)
4. [Service de Paiements](#service-de-paiements)
5. [Service de Statistiques](#service-de-statistiques)
6. [Communication inter-services avec OpenFeign](#communication-inter-services-avec-openfeign)
7. [Exemple de cas d'utilisation avec OpenFeign](#exemple-de-cas-dutilisation-avec-openfeign)

## Vue d'ensemble de l'architecture

L'architecture se compose de quatre microservices principaux :

1. Service de Gestion des Produits
2. Service de Gestion des Ventes aux Clients
3. Service de Paiements
4. Service de Statistiques

Chaque service a sa propre base de données PostgreSQL et communique avec les autres services via OpenFeign.

## Service de Gestion des Produits

### Fonctionnalités
- Créer un produit
- Lister tous les produits
- Récupérer un produit par son ID
- Mettre à jour le stock d'un produit après une commande

### Structure du projet
```
product-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ecommerce/product/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── ProductServiceApplication.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
```

### Exemple de code : ProductController.java
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // Autres méthodes...
}
```

## Service de Gestion des Ventes aux Clients

### Fonctionnalités
- Créer un client
- Lister tous les clients
- Retrouver un client par son ID
- Créer une commande
- Créer les détails d'une commande

### Structure du projet
```
sales-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ecommerce/sales/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── SalesServiceApplication.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
```

### Exemple de code : OrderService.java
```java
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductServiceClient productServiceClient;

    public Order createOrder(Order order) {
        // Logique pour créer une commande
        // Utilisation de OpenFeign pour mettre à jour le stock des produits
        productServiceClient.updateProductStock(order.getProductId(), order.getQuantity());
        return orderRepository.save(order);
    }

    // Autres méthodes...
}
```

## Service de Paiements

### Fonctionnalités
- Créer un paiement pour une commande
- Mettre à jour l'état de la commande une fois le paiement enregistré

### Structure du projet
```
payment-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ecommerce/payment/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── PaymentServiceApplication.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
```

### Exemple de code : PaymentService.java
```java
@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private OrderServiceClient orderServiceClient;

    public Payment processPayment(Payment payment) {
        Payment savedPayment = paymentRepository.save(payment);
        // Mise à jour de l'état de la commande via OpenFeign
        orderServiceClient.updateOrderStatus(payment.getOrderId(), "PAID");
        return savedPayment;
    }

    // Autres méthodes...
}
```

## Service de Statistiques

### Fonctionnalités
- Calculer les montants des ventes
- Identifier les produits les plus vendus
- Identifier les meilleurs clients

### Structure du projet
```
stats-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ecommerce/stats/
│   │   │       ├── controller/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── StatsServiceApplication.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
```

### Exemple de code : StatsService.java
```java
@Service
public class StatsService {
    @Autowired
    private OrderServiceClient orderServiceClient;
    
    @Autowired
    private ProductServiceClient productServiceClient;

    public List<ProductSalesStats> getTopSellingProducts() {
        List<Order> orders = orderServiceClient.getAllOrders();
        // Logique pour calculer les produits les plus vendus
        // ...
        return topSellingProducts;
    }

    // Autres méthodes...
}
```

## Communication inter-services avec OpenFeign

Pour utiliser OpenFeign, ajoutez la dépendance suivante dans votre fichier `pom.xml` :

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

Ensuite, activez OpenFeign dans votre application principale :

```java
@SpringBootApplication
@EnableFeignClients
public class YourServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourServiceApplication.class, args);
    }
}
```

## Exemple de cas d'utilisation avec OpenFeign

Voici un exemple de cas d'utilisation avec OpenFeign pour la communication entre le Service de Gestion des Ventes et le Service de Gestion des Produits.

### 1. Définir l'interface client OpenFeign

Dans le Service de Gestion des Ventes, créez une interface pour communiquer avec le Service de Gestion des Produits :

```java
@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductServiceClient {
    @PutMapping("/api/products/{id}/stock")
    void updateProductStock(@PathVariable("id") Long productId, @RequestParam("quantity") int quantity);
}
```

### 2. Utiliser le client dans le service

Dans le `OrderService` du Service de Gestion des Ventes :

```java
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductServiceClient productServiceClient;

    @Transactional
    public Order createOrder(Order order) {
        // Sauvegarder la commande
        Order savedOrder = orderRepository.save(order);
        
        // Mettre à jour le stock du produit via OpenFeign
        productServiceClient.updateProductStock(order.getProductId(), order.getQuantity());
        
        return savedOrder;
    }
}
```

### 3. Implémenter l'endpoint correspondant

Dans le `ProductController` du Service de Gestion des Produits :

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> updateProductStock(@PathVariable Long id, @RequestParam int quantity) {
        productService.updateStock(id, quantity);
        return ResponseEntity.ok().build();
    }
}
```

Avec cette configuration, lorsqu'une nouvelle commande est créée dans le Service de Gestion des Ventes, il appellera automatiquement le Service de Gestion des Produits pour mettre à jour le stock du produit correspondant.

Ce cas d'utilisation démontre comment OpenFeign facilite la communication entre microservices de manière déclarative et type-safe.
