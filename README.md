
# GetMyGraphicsCard
Graphics card comparison site which sends email notifications.


## 📝  Tech Stack

Frontend: React Typescript (still under construction)

Backend: Java 17, Spring Boot v3, Spring Cloud, Spring Security, JPA (Hibernate), MySQL, MongoDB, Kafka

## 🧱 Server Architecture Diagram

![Backend-Diagram](https://user-images.githubusercontent.com/31177070/219319133-67edebc7-bfd9-4a9d-8afd-d0d4c8989caf.png)

## 🧱 Database Schema Diagram

For product-service,
![ItemSchema](https://user-images.githubusercontent.com/31177070/219332017-b1b6e89d-5870-480f-836d-f28ead74e256.png)

For subscription-service,
![Screenshot from 2023-02-16 18-47-04](https://user-images.githubusercontent.com/31177070/219332020-ecb00564-22fe-49e6-80c5-0bcc6a23ae79.png)

## 📝 How It Works

Users access to the app via Spring Cloud Gateway, running on port 8888.


The product service sends HTTP requests to https://shopping.naver.com, every 5 minutes, retrieving graphics card price information and saving the information to  MongoDB. It compares the item price information with previously saved data when saving the data to MongoDB. If the lowest price of a product becomes available when retrieving price information from Naver.com, the app publishes alert messages to the Kafka cluster. And users can look up the price information via /items/** endpoints.

The subscription service is another spring boot app, which deals with user management and email notification service. Multiple instances of the subscription service can be deployed in case of heavy user traffics. Users must register to use the subscription service. The user information and their subscription information are saved to the connected MySQL database. Users can add or delete items to their wishlist. The subscription service listens (subscribes) to the Kafka clusters, and saves the messages to the MySQL database. The saved messages are used as price information alert emails. The service sends the price information alert email to the users when the items on their wishlist become the lowest price available. After sending emails to the users, the subscription service deletes the messages in the MySQL database to prevent users from receiving emails regarding the same item information.

Users can have either role USER or ADMIN.
ADMIN users can perform CRUD operations on all resources of registered users.
Users with the role of USER can only perform CRUD operations on their resources.
The endpoint /subscriptions/** is secured with Spring Security. JWT is used for authentication and authorization to enhance the scalability of the app. When the user signs in to the app, the subscription service returns a JWT token which lasts 1 hour. Users can access their resources with the returned token. 

Users will receive emails like below when their items on the wishlist become the lowest price.

![Email](https://user-images.githubusercontent.com/31177070/219342151-e9abacee-2a3e-4382-830f-2e517a3a7f88.png)





# 📝 API Documentation

## Product Service

###  Items

**Description**: Returns 20 saved items information. 

**URL** : `/api/items/`

**Method** : `GET`

**Auth required** : NO

**Params** : pageNo (default = 0),  size (default 20)

#### Success Response

**Code** : `200 OK`

**Content example**

```json
{"content": [{"title": "INNO3D 지포스 GTX 1630 OC D6 4GB TWIN X2","link": "[https://search.shopping.naver.com/gate.nhn?id=35480507621](https://search.shopping.naver.com/gate.nhn?id=35480507621)","image": "[https://shopping-phinf.pstatic.net/main_3548050/35480507621.20221027163645.jpg](https://shopping-phinf.pstatic.net/main_3548050/35480507621.20221027163645.jpg)","lprice": 198000},{"title": "이엠텍 지포스 GTX 1630 STORM X Dual MINI D6 4GB","link": "[https://search.shopping.naver.com/gate.nhn?id=33802526621](https://search.shopping.naver.com/gate.nhn?id=33802526621)","image": "[https://shopping-phinf.pstatic.net/main_3380252/33802526621.20220801171627.jpg]], ..., "pageable": {"sort": {"empty": true,"sorted": false,"unsorted": true},"offset": 0,"pageNumber": 0,"pageSize": 20,"unpaged": false,"paged": true},"totalPages": 201,"totalElements": 4007,"last": false,"size": 20,"number": 0,"sort": {"empty": true,"sorted": false,"unsorted": true},"first": true,"numberOfElements": 20,"empty": false}
```

##

###  Items/{id}

**Description**: Returns information of item with id. 

**URL** : `/api/items/{id}`

**Method** : `GET`

**Auth required** : NO

**Params** : id (required), pageNo (default = 0),  size (default 20)

#### Success Response

**Code** : `200 OK`

**Content example**
{

	"title":  "이엠텍 지포스 GTX 1630 STORM X Dual MINI D6 4GB",

	"link":  "https://search.shopping.naver.com/gate.nhn?id=33802526621",

	"image":  "https://shopping-phinf.pstatic.net/main_3380252/33802526621.20220801171627.jpg",

	"lprice":  202920

}

##

### Items/Search

**URL** : `/api/items/search`

**Description**: Returns 20 saved items with the item name containing the title param. 

**Method** : `GET`

**Auth required** : NO

**Params** : title (required), pageNo (default = 0),  size (default 20)

#### Success Response

**Code** : `200 OK`

**Content example**
![ItemSearch](https://user-images.githubusercontent.com/31177070/219348568-eed8d390-ff43-4e93-bb47-f50906f589de.png)

### Items/Price

**URL** : `/api/items/price`

**Description**: Returns 20 saved items with their prices ranging from the lowest parameter to the highest parameter. 

**Method** : `GET`

**Auth required** : NO

**Params** : lowest (required), highest(required), pageNo (default = 0), size (default 20)

### Success Response

**Code** : `200 OK`

**Content example**
![ItemPriceSearch](https://user-images.githubusercontent.com/31177070/219348562-862e9d8c-fc94-4e76-b98a-e3758882c755.png)

## Subscription Service

### Login 

Used to collect a Token for a registered User.

**URL** : `/api/auth/login`

**Method** : `POST`

**Auth required** : NO

**Data constraints**

```json
{
    "email": "[valid email address]",
    "password": "[password in plain text]"
}
```

**Data example**

```json
{
    "email": "iloveauth@example.com",
    "password": "test1234"
}
```

### Success Response

**Code** : `200 OK`

**Content example**

String
``` 
eyJraWQiOiIwNGY4YThhOS1mZjljLTQ3ZDctOGIxNC0xYmJlNTExNzllMDkiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYXRoYW5hc2lhOTQxMEBnbWFpbC5jb20iLCJleHAiOjE2NzY1NTExNjYsImlhdCI6MTY3NjU0NzU2Niwic2NvcGUiOiJBRE1JTiJ9.gMvD-dJlFN8m0yxlErl2dnbpJw6AePTV4jZQqKuew3Nz_dkFR6PfyNQ5Guyr3A1o4COJYLr3Qq9ng1JpNOzScROTTIUyviOlGL9ZDWXxKdn23tdeCJBknUHcxeAushr0zZPJ_oaGgUtBd25yb2adT_2DfMNrfvTCUHYvunKrbp3zxfaU1k1PlmGTjwwf_k-2jWS3Y78YgNg5zsR1y8V9y7sXMfUwKh6x9-a2Dp5uigzpDyrilObro8rmBtzff7cKIYvXU_Hrp5rownZfmm44sxVSvvplQCdhtRi8vcCluV7KzuAOmVRRTOQET-2r455YIMDgAWhV_-suhn-knMcatg
```

### Error Response

**Condition** : If 'username' and 'password' combination is wrong.

**Code** : `401 Unauthorized`

##

### SignUp

Send POST request to sign up to the app

**URL** : `/api/auth/signup`

**Method** : `POST`

**Auth required** : NO

**Data constraints**

```json
{
    "email": "[valid email address]",
    "password": "[password in plain text]"
}
```

**Data example**

```json
{
    "email": "iloveauth@example.com",
    "password": "abcd1234"
}
```

### Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "email": "iloveauth@example.com",
    "password": "abcd1234"
}
```

### Error Response

**Condition** : If the email is already registered.

**Code** : `401 Unauthorized`

### Delete User 

Delete a user from the database.
It can be performed by an authenticated user with id same with the parameter or by ADMIN user.

**URL** : `/api/subscriptions/{id}`

**Method** : `DELETE`

**Auth required** : YES

### Success Response

**Code** : `200 OK`

**Content example**

Subscription deleted


### Error Response

**Condition** : If user with {id} param doesn't exist in the database.

**Code** : `403 Forbidden`

##

### Get User's Subscribed Items 

Retrieve the user's items wishlist who is specified with {id}.  
It can be performed by an authenticated user with id same with the parameter or by ADMIN user.

**URL** : `/api/subscriptions/{id}`

**Method** : `GET`

**Auth required** : YES

**Params** : {id} (required)

### Success Response

**Code** : `200 OK`

**Content example**

```
[
	
	{

	"title":  "COLORFUL RTX 2060 토마호크 D6 6GB 무상AS 24년",

	"link":  "https://search.shopping.naver.com/gate.nhn?id=36362481720",

	"image":  "https://shopping-phinf.pstatic.net/main_3636248/36362481720.jpg",

	"lprice":  168330

	},

	{

	"title":  "MSI 지포스 RTX 2070 벤투스 D6 8GB 중고 AS6개월",

	"link":  "https://search.shopping.naver.com/gate.nhn?id=37433169835",

	"image":  "https://shopping-phinf.pstatic.net/main_3743316/37433169835.jpg",

	"lprice":  756960

	},

	{

	"title":  "ASUS RTX2060 GTX1660ti 1660 1650 TUF 그래픽 냉각팬",

	"link":  "https://search.shopping.naver.com/gate.nhn?id=29542489777",

	"image":  "https://shopping-phinf.pstatic.net/main_2954248/29542489777.jpg",

	"lprice":  152900

	}

]
```


### Error Response

**Condition** : If user with {id} param doesn't exist in the database.

**Code** : `403 Forbidden`

##

### Add item to the subscription

Add the item to the user with id {id}'s wishlist.
It can be performed by an authenticated user with id same with the parameter or by ADMIN user.

**URL** : `/api/subscriptions/{id}`

**Method** : `POST`

**Auth required** : YES

**Params** : {id} (required)

**Data constraints**

```
{productId}
```

**Data example**
```
33816585619
```


### Success Response

**Code** : `200 OK`

**Content example**

```
{

	"title":  "컬러풀 지포스 GTX 1630 토마호크 D6 4GB",

	"link":  "https://search.shopping.naver.com/gate.nhn?id=33816585619",

	"image":  "https://shopping-phinf.pstatic.net/main_3381658/33816585619.20220802114706.jpg",

	"lprice":  187500

}
```


### Error Response

**Condition** : If the item with the specified productId doesn't exist in the database.

**Code** : `403 Forbidden`

##

### Remove item from the subscription

Delete the item with index from the user with id {id}'s wishlist.
It can be performed by an authenticated user with id same with the parameter or by ADMIN user.

**URL** : `/api/subscriptions/{id}/{index}`

**Method** : `DELETE`

**Auth required** : YES

**Params** : {id} (required), {index} (required)

### Success Response

**Code** : `200 OK`

**Content example**

```
Item deleted successfully.
```


### Error Response

**Condition** : If the item with the specified index doesn't exist in the database.

**Code** : `403 Forbidden`
