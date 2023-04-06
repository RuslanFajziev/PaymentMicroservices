## Сервис обработки платежей

### Технологии проекта
![badge](https://img.shields.io/badge/Spring-Boot/Security/Data-green)
![badge](https://img.shields.io/badge/Apache-Kafka-red)
![badge](https://img.shields.io/badge/PostgreSQL-14-blue)

Сервис состоит из двух микросервисов:
- RestApiPaymentService - принимает платежи через Rest Api и отправляет их на обработку в PaymentProcessing
- PaymentProcessing - принимает платежи, добавляет их в очередь, после обработки отправляет обновленный статус платежей в RestApiPaymentService

Обмен между сервисами - Kafka.

Хранение данных сервисов - PostgreSQL.

Трехслойная архитектура сервисов - `Controller` `>` `Service` `>` `Repository`.

Валидация параметров моделей при получении по Rest API.

Аутификация загрузки платежей, добавления пользователей - JWT.

Аудита работоспособности сервиса RestApiPaymentService - Healthcheck Edpoint.

Функциональное тестирование сервиса - Junit+Postman query collection.
### Описание функционала сервиса RestApiPaymentService

#### 1. Получение JWT для сервиса RestApiPaymentService

Запрос `POST https://localhost:7176/api/v1/jwt`

Тело
`
{
"login" : "admin",
"password" : "admin"
}
`

Ответ JSON - успех
`
{
"login": "admin",
"access_token": "eyJhbGciOiJIUzI1NiIsInR5cC.i_xdvKRyOGhOiYfj6iHhOyyOg_rgEYeDhIlMfHXPnvo"
}
`

#### 2. Добавление пользователей сервиса RestApiPaymentService

Запрос `POST https://localhost:7176/api/v1/adduser`

Заголовок `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cC.i_xdvKRyOGhOiYfj6iHhOyyOg_rgEYeDhIlMfHXPnvo`

Тело
`
{
"login" : "vadim",
"passwordHash" : "p@$$word",
"role" : "user_full",
"fioUser" : "Tsoy Vadim Batkovich"
}
`

Ответ JSON - успех
`
{
"result": "user successfully added"
}
`
#### 3. Запрос Healthcheck сервиса RestApiPaymentService

Запрос `GET https://localhost:7176/api/v1/healthcheck`

Заголовок `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cC.i_xdvKRyOGhOiYfj6iHhOyyOg_rgEYeDhIlMfHXPnvo`

Ответ - успех
`Healthy`
#### 4. Запрос списка всех платежей сервиса RestApiPaymentService

Запрос `GET https://localhost:7176/api/v1/payment/get`

Заголовок `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cC.i_xdvKRyOGhOiYfj6iHhOyyOg_rgEYeDhIlMfHXPnvo`

Ответ JSON - успех
`
[
{
"id": 1,
"nameService": "flystep",
"amount": 5555,
"metadate": "[{\"coefficient\":9999,\"sity\":\"Moscow\"},{\"coefficient\":4555,\"sity\":\"Piter\"}]",
"correlationId": "9f3bf96d-b7b0-4601-9471-14eb8d54f0a1",
"datePayment": "2022-11-28T14:19:33.926873Z",
"statusPayment": "complete",
"userСreator": "pupkin"
},
{
"id": 2,
"nameService": "flystep",
"amount": 5555,
"metadate": "[{\"coefficient\":9999,\"sity\":\"Moscow\"},{\"coefficient\":4555,\"sity\":\"Piter\"}]",
"correlationId": "d7e1911c-46bb-4834-804e-0087998d9023",
"datePayment": "2022-11-28T14:23:00.750166Z",
"statusPayment": "complete",
"userСreator": "pupkin"
}
]
`
#### 5. Запрос платежа по id сервиса RestApiPaymentService

Запрос `GET https://localhost:7176/api/v1/payment/get/4`, где 4 это id платежа

Заголовок `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cC.i_xdvKRyOGhOiYfj6iHhOyyOg_rgEYeDhIlMfHXPnvo`

Ответ JSON - успех
`
{
"id": 4,
"nameService": "flystep",
"amount": 5555,
"metadate": "[{\"coefficient\":9999,\"sity\":\"Moscow\"},{\"coefficient\":4555,\"sity\":\"Piter\"}]",
"correlationId": "c4f31b18-a297-4b25-b427-d87120524a6b",
"datePayment": "2022-11-28T14:31:19.955561Z",
"statusPayment": "complete",
"userСreator": "Tsoy Vadim Batkovich"
}
`
#### 6. Запрос списка платежей по фильтру сервиса RestApiPaymentService

Можно использовать от 1-го до 3-х фильтров
- nameService - название платежа
- amount - сумма платежа
- statusPayment - статус платежа

Запрос `GET https://localhost:7176/api/v1/payment/getbyfilter?nameService=flystep&amount=5555&statusPayment=complete`

Заголовок `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cC.i_xdvKRyOGhOiYfj6iHhOyyOg_rgEYeDhIlMfHXPnvo`

Ответ JSON - успех
`
[
{
"id": 1,
"nameService": "flystep",
"amount": 5555,
"metadate": "[{\"coefficient\":9999,\"sity\":\"Moscow\"},{\"coefficient\":4555,\"sity\":\"Piter\"}]",
"correlationId": "b222934f-0164-471d-aaa9-c43b9baa94d7",
"datePayment": "2022-12-01T00:39:46.168228Z",
"statusPayment": "complete",
"userСreator": "Tsoy Vadim Batkovich"
},
{
"id": 2,
"nameService": "flystep",
"amount": 5555,
"metadate": "[{\"coefficient\":9999,\"sity\":\"Moscow\"},{\"coefficient\":4555,\"sity\":\"Piter\"}]",
"correlationId": "6a17c41b-c222-440b-8c4e-5e6c192d95b4",
"datePayment": "2022-12-01T00:39:49.839166Z",
"statusPayment": "complete",
"userСreator": "Tsoy Vadim Batkovich"
}
]
`
#### 7. Добавление платежа в сервис RestApiPaymentService

Запрос `POST https://localhost:7176/api/v1/payment/add`

Заголовок `Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cC.i_xdvKRyOGhOiYfj6iHhOyyOg_rgEYeDhIlMfHXPnvo`

Тело
`
{
"nameService" : "flystep",
"amount" : 5555,
"metadate" : [
{
"coefficient": 9999,
"sity": "Moscow"
},
{
"coefficient": 4555,
"sity": "Piter"
}
]
}
`

Ответ JSON - успех
`
{
"id": 4,
"nameService": "flystep",
"amount": 5555,
"metadate": "[{\"coefficient\":9999,\"sity\":\"Moscow\"},{\"coefficient\":4555,\"sity\":\"Piter\"}]",
"correlationId": "c4f31b18-a297-4b25-b427-d87120524a6b",
"datePayment": "2022-11-28T14:31:19.955561Z",
"statusPayment": "created",
"userСreator": "Tsoy Vadim Batkovich"
}
`