# __About:__
This project implemented via `Spring Boot`.

Database crates via Flyway.
 
___
# __Project setup steps__
___
* ```git clone <username>/gift-certification.git ```
* go to the folder ```cd 'your project folder'```
* paste project url from the first step
* open the project in your IDE ```File->Open->'your project folder'```

# __To ```run``` application you need:__

* open folder with project in the terminal ```cd 'your project folder'```
* checkout to the ```feature-1-spring-boot-application``` branch
* enter ```gradle clean build```
* get the database up in Docker by command ```docker compose up -d --build```
___
# __Steps for work with application:__

__Work with certificates:__
* For getting all certificates from database with sorting and pagination you should go to ```GET http://localhost:8083/gifts/all?sort=<param for sorting>&keyword=<search keyword>```
```
keywords from: name, description, tagname
sorting by: name, description, price, duration, createDate, lastUpdateDate
```
* For add certificate to the database you should go to ```POST http://localhost:8083/gifts``` 
and pass the following parameters in the body of the request:
   ```
   {
       "name": "name",
       "description": "description",
       "price": <price>,
       "duration": <duration>,
       "tags": [
                   {
                   "name":"Gifts"
                   }
               ]
   } 
  ```
 
* For update information about certificates you should go to ```PUT http://localhost:8083/gifts/update?id=<id>```
and pass the following parameters in the body of the request:
  ```
  {
        "id": <id>
        "name": "name",
        "description": "description",
        "price": <price>,
        "duration": <duration>,
        "tags": [
                   {
                   "name":"Gifts"
                   }
                [
  } 
   ```
* For get certificate from the database using product's id you should go to ```GET.../gifts/<id>```
* For delete certificate from the database you should go to  ```DELETE.../gifts/delete/<id>```
___
__Work with tags:__
* For getting all tags from database you should go to ```GET http://localhost:8083/tags/```
* For add tag to the database you should go to ```POST http://localhost:8083/tags/add``` 
and pass the following parameters in the body of the request:
   ```
   {
       "name":"Somthing"
   } 
  ```
* For update information about tags you should go to ```PUT http://localhost:8083/tags/update?id=<id>```
and pass the following parameters in the body of the request:
   ```
   {      
       "name":"Somthing"
   } 
  ```
* For get tag from the database using tag's id you should go to ```GET.../tags/<id>```
* For delete tag from the database you should go to  ```DELETE.../tags/delete/<id>```

___
__Work with users:__
* For getting all users from the database you should go to ```GET http://localhost:8083/user/```
* For add user to the database you should go to ```POST http://localhost:8083/user/``` 
  and pass the following parameters in the body of the request:
  ```
  {
      "name": "Username"
  }
  ```
* For searching the most widely used tag for user ```GET http://localhost:8083/user/tag?userId=<id>```
* For delete user from the database you should go to  ```DELETE.../user/delete/<id>```

___
__Work with orders:__

* For getting order by id from the database you should go to ```GET http://localhost:8083/order/find/<id>```
* For add order to the database you should go to ```POST http://localhost:8083/order/add?userId=<user id>&certId=<gift certificate id>``` 
