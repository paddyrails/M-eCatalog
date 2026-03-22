# Running CS Sales services on Minikube

## Prerequisites

- Minikube: `minikube start`
- kubectl
- Docker (or Minikube's Docker daemon: `eval $(minikube docker-env)`)

## 1. Start PostgreSQL (shared)

PostgreSQL is shared by all services; each service uses a different database name.

```bash
kubectl create namespace cs-sales
kubectl apply -n cs-sales -f M-eCatalog/k8s/postgres.yaml
```

Wait until postgres is ready, then create databases (optional if your app creates them via JPA):

```bash
kubectl exec -it deployment/postgres -n cs-sales -- psql -U cs_sales_user -d postgres -c "CREATE DATABASE \"cs-sales\";"
kubectl exec -it deployment/postgres -n cs-sales  -- psql -U cs_sales_user -d postgres -c "CREATE DATABASE \"cs-sales-offers\";"
kubectl exec -it deployment/postgres -n cs-sales  -- psql -U cs_sales_user -d postgres -c "CREATE DATABASE \"cs-sales-quotes\";"
kubectl exec -it deployment/postgres -n cs-sales  -- psql -U cs_sales_user -d postgres -c "CREATE DATABASE \"cs-sales-orders\";"
kubectl exec -it deployment/postgres -n cs-sales  -- psql -U cs_sales_user -d postgres -c "CREATE DATABASE \"cs-sales-order-fulfillment\";"
```

## 2. Build and load images (Minikube)

Use Minikube's Docker so images are available in the cluster:

```bash
eval $(minikube docker-env)
# From repo root (Dockerfile at root of each service folder):
docker build -t catalog-service:latest -f M-eCatalog/Dockerfile M-eCatalog
docker build -t offers-service:latest -f M-eOffers/Dockerfile M-eOffers
docker build -t quotes-service:latest -f M-eQuotes/Dockerfile M-eQuotes
docker build -t orders-service:latest -f M-eOrders/Dockerfile M-eOrders
docker build -t fulfillment-service:latest -f M-eFulfillment/Dockerfile M-eFulfillment
docker build -t api-gateway:latest -f M-eGateway/Dockerfile M-eGateway
```

## 3. Deploy each service

```bash
kubectl apply -n cs-sales -f M-eCatalog/k8s/catalog-service.yaml
kubectl apply -n cs-sales -f M-eOffers/k8s/offers-service.yaml
kubectl apply -n cs-sales -f M-eQuotes/k8s/quotes-service.yaml
kubectl apply -n cs-sales -f M-eOrders/k8s/orders-service.yaml
kubectl apply -n cs-sales -f M-eFulfillment/k8s/fulfillment-service.yaml
```

## 4. Optional: API Gateway

```bash
kubectl apply -n cs-sales -f M-eGateway/k8s/api-gateway.yaml
```

## Ports (ClusterIP)

| Service           | Port |
|-------------------|------|
| postgres          | 5432 |
| catalog-service   | 9080 |
| offers-service    | 9081 |
| quotes-service    | 9082 |
| orders-service    | 9083 |
| fulfillment-service | 9083 |
| api-gateway       | 80 (target 9000) |

To call a service from your machine: `kubectl port-forward svc/catalog-service 9080:9080`
