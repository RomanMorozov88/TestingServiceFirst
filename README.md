# TestingServiceFirst

Для запуска java -jar testing.jar

http://localhost:8010/v2/api-docs - для получения документации от Swagger

name и email должны быть регистронезависимые - name нигде не используется, но вот проверка email в БД не зависит от регистра.

Последняя(необязательная) часть не реализована но по обращению к /exit выключение происходит.

Используется JWT с секретным ключём. Для его получения достаточно обратиться по "profiles/set"- который так же сделан доступным для всех-
и в Header ответа будет возвращён парамерт Authorization с токеном вида: Bearer xxx.xxx.xxx
Для доступа к требующим авторизации страницам нужно установить в Header запрос этот токен в таком же виде (Authorization : Bearer xxx.xxx.xxx)
```
желательно придерживаться экосистемы spring.
При разработке использовать в качестве базы Postgres
cross-origin должно быть отключено.
доступ к сервису возможен только при наличием токена ‘secret’ во всех остальных случаях кроме “GET /exit” возвращать 401 название и реализация на ваше усмотрение, инструкция для передачи токена должна прилагаться в месте с тестовым заданием.
сервис должен запускатся на 8010 порту
name и email должны быть регистронезависимые 
Добавить фильтр при регистрации на проверку уникальности поля email в случае если Email есть в базе возвращать 409 статус
к исходникам должен прилагаться артефакт приложения
Для данного приложения реализуйте и подключил OpenApi (swagger)
Версия java не выше 11
Сборщик Maven

endpoints:

POST /profiles/set 
Создает запись профиля и присваивает ему id
Request:
принимает json следующей структурой:

{
    “name”: string
    “email”: string
    “age”: int
}

Responses:
в случае успеха возвращает id записи пользователя

status 200
{
    “idUser”: int
}

В случае не корректного email
status 400

{
    “msg”: string
}

В случае если email уже передавался (реализовать через фильтр)
status 403

{
    “msg”: string
}

GET /profiles/last
Возвращает последний созданный профиль

Responses:
status 200
{
    “id”: int
    “name”: string
    “email”: string
    “age”: int
“created”: timestamp
}



GET /profiles
Возвращает все созданные профили

Responses:
status 200
[{
    “id”: int
    “name”: string
    “email”: string
    “age”: int
“created”: timestamp
}...]

GET /profiles/{ID}
Возвращает профиль по его ID

Responses:
status 200
{
    “id”: int
    “name”: string
    “email”: string
    “age”: int
“created”: timestamp
}

status 404 
в случае если запись не найдена
{
    “msg”: string
}





POST /profiles/get
Возвращает профиль по email

Request:
принимает json следующей структурой:

{
    “email”: string
}

Responses:
status 200
{
    “id”: int
    “name”: string
    “email”: string
    “age”: int
“created”: timestamp
}

status 404 
в случае если запись не найдена

{
    “msg”: string
}

GET /error/last
Возвращает сообщение последней ошибки

Responses:
status 200
{
    “msg”: string
    “created”: timestamp
}


Не обязательная часть задания:

GET /exit
Производит закрытия приложение с редиректом на страницу /exit-success (название вариативно) с надписью ‘приложение закрыто’ допускаются и другие варианты информирования о закрытие.

```
