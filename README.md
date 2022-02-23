#Sellers & Buyers API

## Development

### Description

The project uses a 3-layer folder structure:
1) Domain
2) Application
3) Infrastructure

The goal was to develop the core part of the application (Domain, Application) with ease and agility.
For that, I tried to minimize the dependencies of the inner layers from the outer ones.

While trying to get things done relatively fast, I made the decision to couple the Entities of the Domain layer to the Persistance layer.
Ideally, those entities shouldn't be the same, because they're different abstractions.

Another observation worth taking a look is the Adapter Pattern used to avoid coupling the "Repository" Interfaces to the naming of JPA. Semantically, makes sense to follow DDD practices and use our own terms and naming conventions.

### Technologies

- Springboot 3.6
- Maven 3.8
- IntelliJ IDEA
- Java 11 LTS
- JPA

### Environment

- Ubuntu 21.04 LTS

### Instructions

1. Clone the repository

```
git clone https://github.com/luis-nvrr/sellers-clients-api
```

2. Start the application 

```
./mvnw spring-boot:run
```

### Data

The file ***./src/main/resources/data.sql*** is used to populate the database at start, after the schema is created by hibernate.
Feel free to add any changes to manually test the api.

### Configuration

All application configuration is in the ***./src/main/resources/application.properties*** file.


### Todo

- [ ] Create Dockerfile
- [ ] Replace H2 database
- [ ] Create Docker Compose
- [ ] Add integration tests
- [X] Add Unit Tests
- [X] Add JPA
- [X] Buyer service
- [X] Seller service
- [X] Posts service
- [X] Buyer endpoint
- [X] Seller endpoint
- [ ] Posts endpoint
- [X] Seller's endpoint documentation
- [ ] Post's endpoint documentation

## Endpoints

### Buyers 

#### Get all sellers followed by a buyer

- Path params: 
  - buyerUsername: a buyer's username


- Model:
```shell
curl -X GET http://localhost:8080/buyers/{buyerUsername}/following
```

- Working example:
```shell
curl -X GET http://localhost:8080/buyers/buyer/following
```

- Response:
```json
{
	"buyerUsername": "buyer",
	"followingSellers": [
		{
			"username": "seller",
			"email": "seller@meli.com",
			"creationDate": "2022-02-23T03:00:00.000+00:00",
			"followersUsernames": [
				"buyer"
			],
			"posts": [],
			"promotionalPosts": []
		}
	],
	"followingCount": 1
}
```

#### Follow a seller

- Path params:
    - buyerUsername: a buyer's username.
- Request body:
  - sellerUsername: the username of the seller to follow. 


- Model:
```shell
curl --request POST \
  --url http://localhost:8080/buyers/{buyerUsername}/following \
  --header 'Content-Type: application/json' \
  --data '{
	"sellerUsername": "{sellerUsername}"
}'
```

- Working example:
```shell
curl --request POST \
  --url http://localhost:8080/buyers/buyer/following \
  --header 'Content-Type: application/json' \
  --data '{
	"sellerUsername": "seller"
}'
```

- Response
```json
{
	"buyerUsername": "buyer",
	"followingSeller": "seller",
	"newFollowingCount": 1
}
```

#### Stop following a seller

- Path params:
    - buyerUsername: a buyer's username.
    - sellerUsername: the username of the seller to unfollow.


- Model:
```shell
curl --request DELETE \
  --url http://localhost:8080/buyers/{buyerUsername}/following/{sellerUsername}
```

- Working example:
```shell
curl --request DELETE \
  --url http://localhost:8080/buyers/buyer/following/seller
```

- Response
```json
{
  "unfollowedSeller": "seller",
  "buyerUsername": "buyer",
  "buyerNewFollowingCount": 0,
  "sellerNewFollowersCount": 0
}
```

### Buyers 

#### Get followers of a seller

- Path params:
  - sellerUsername: a seller's username.


- Model:
```shell
curl --request GET \
  --url http://localhost:8080/sellers/{sellerUsername}/followers
```

- Working example:
```shell
curl --request GET \
  --url http://localhost:8080/sellers/seller/followers
```

- Response
```json
{
  "sellerUsername": "seller",
  "followers": [
    {
      "username": "buyer",
      "email": "buyer@meli.com",
      "creationDate": "2022-02-23T03:00:00.000+00:00",
      "followingSellersUsernames": [
        "seller"
      ]
    }
  ]
}
```

#### Get followers count of a seller

- Path params:
  - sellerUsername: a seller's username.
- Query param:
  - shouldCount=true: tells the api it should return the count of followers.


- Model:
```shell
curl --request GET \
  --url 'http://localhost:8080/sellers/{sellerUsername}/followers?shouldCount=true'
```

- Working example:
```shell
curl --request GET \
  --url 'http://localhost:8080/sellers/seller/followers?shouldCount=true'
```

- Response
```json
{
  "sellerUsername": "seller",
  "followersCount": 1
}
```