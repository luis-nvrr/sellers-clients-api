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
- [X] Posts endpoint
- [X] Seller's endpoint documentation
- [X] Post's endpoint documentation
- [ ] Refactor Post sorting strategy
- [X] Refactor Post & Promotional post

## Endpoints Overview

This API has 4 endpoints:
- ```
  /buyers
  ```
- ```
  /sellers
  ```

- ```
  /posts
  ```

- ```
  /promotionalPosts
  ```

Each following detailed explanation consists of:
1. Parameters required
2. Request model
3. Request example
4. Response example

## Endpoints Details

### Buyers 

#### Get all sellers followed by a buyer

- Path params: 
  - buyerUsername: a buyer's username


- Model:
```shell
curl --request GET \
  --url http://localhost:8080/buyers/{buyerUsername}/following
```

- Working example:
```shell
curl --request GET \
  --url http://localhost:8080/buyers/buyer/following
```

- Response:
```json
{
  "buyerUsername": "buyer",
  "followingSellers": [
    {
      "username": "seller",
      "email": "seller@meli.com",
      "creationDate": "2022-02-24T03:00:00.000+00:00",
      "followersUsernames": [
        "buyer"
      ],
      "posts": [
        {
          "description": "this is a new post",
          "creationDate": "2022-02-24T03:12:49.513+00:00"
        },
        {
          "description": "new promotional post",
          "creationDate": "2022-02-24T03:12:51.999+00:00"
        },
        {
          "description": "new promotional post",
          "creationDate": "2022-02-24T03:13:17.768+00:00"
        },
        {
          "description": "this is a new post",
          "creationDate": "2022-02-24T03:13:24.751+00:00"
        }
      ],
      "promotionalPosts": [
        {
          "description": "new promotional post",
          "creationDate": "2022-02-24T03:12:51.999+00:00"
        },
        {
          "description": "new promotional post",
          "creationDate": "2022-02-24T03:13:17.768+00:00"
        }
      ]
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

### Sellers

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

### Posts

#### Create new post

- Request body
  - description: the post content;
  - sellerUsername: the username of the seller owner of this post;


- Model:
```shell
curl --request POST \
  --url http://localhost:8080/posts/ \
  --header 'Content-Type: application/json' \
  --data '{
	  "description": "{description}",
		"sellerUsername": "{sellerUsername}"
}'
```

- Working example:
```shell
curl --request POST \
  --url http://localhost:8080/posts/ \
  --header 'Content-Type: application/json' \
  --data '{
	  "description": "this is a new post",
		"sellerUsername": "seller"
}'
```

- Response
```json
{
  "post": {
    "description": "this is a new post",
    "creationDate": "2022-02-24T03:21:36.490+00:00"
  }
}
```

#### Get posts from buyers followed by a user, from the last two weeks
The posts are sorted by most recent to older

- Query params
  - buyerUsername: the buyer's username from which to search the sellers;

- Model:
```shell
curl --request GET \
  --url 'http://localhost:8080/posts?buyerUsername={buyerUsername}'
```

- Working example:
```shell
curl --request GET \
  --url 'http://localhost:8080/posts?buyerUsername=buyer'
```

- Response
```json
{
  "buyerUsername": "buyer",
  "followingSellersPosts": [
    {
      "description": "this is a new post",
      "creationDate": "2022-02-24T03:21:36.490+00:00"
    },
    {
      "description": "this is a new post",
      "creationDate": "2022-02-24T03:21:17.550+00:00"
    },
    {
      "description": "this is a new post",
      "creationDate": "2022-02-24T03:13:24.751+00:00"
    },
    {
      "description": "new promotional post",
      "creationDate": "2022-02-24T03:13:17.768+00:00"
    },
    {
      "description": "new promotional post",
      "creationDate": "2022-02-24T03:12:51.999+00:00"
    },
    {
      "description": "this is a new post",
      "creationDate": "2022-02-24T03:12:49.513+00:00"
    }
  ],
  "postsCount": 6
}
```

### Promotional posts

#### Create new promotional post

- Request body
  - description: the post content;
  - sellerUsername: the username of the seller owner of this post;


- Model:
```shell
curl --request POST \
  --url http://localhost:8080/promotionalPosts \
  --header 'Content-Type: application/json' \
  --data '{
	"description" : "{description}",
	"sellerUsername" : "{sellerUsername}"
}'
```

- Working example:
```shell
curl --request POST \
  --url http://localhost:8080/promotionalPosts \
  --header 'Content-Type: application/json' \
  --data '{
	"description" : "new promotional post",
	"sellerUsername" : "seller"
}'
```

- Response
```json
{
  "post": {
    "description": "new promotional post",
    "creationDate": "2022-02-24T03:13:17.768+00:00"
  }
}
```

#### Get all promotional posts from a seller

- Request params:
  - sellerUsername: the username of the seller from which to search the promotional posts.


- Model:
```shell
curl --request GET \
  --url 'http://localhost:8080/promotionalPosts?sellerUsername={sellerUsername}'
```

- Working example:
```shell
curl --request GET \
  --url 'http://localhost:8080/promotionalPosts?sellerUsername=seller'
```

- Response
```json
{
  "promotionalPosts": [
    {
      "description": "new promotional post",
      "creationDate": "2022-02-24T03:12:51.999+00:00"
    },
    {
      "description": "new promotional post",
      "creationDate": "2022-02-24T03:13:17.768+00:00"
    }
  ]
}
```


#### Count all promotional posts from a seller

- Request params:
  - sellerUsername: the username of the seller from which to search the promotional posts.
  - shouldCount: if true, tells the api to return the count of promotional posts.

- Model:
```shell
curl --request GET \
  --url 'http://localhost:8080/promotionalPosts?sellerUsername=seller&shouldCount=true'
```

- Working example:
```shell
curl --request GET \
  --url 'http://localhost:8080/promotionalPosts?sellerUsername=seller&shouldCount=true'
```

- Response
```json
{
  "sellerUsername": "seller",
  "promotionalPostsCount": 2
}
```