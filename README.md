# Руководство пользователя
## Важно
Дизайн окон на протяжении всех версий приложения может меняться, но описанный здесь основной функционал
приложения будет оставаться таким же.
## Окно авторизации
При запуске приложения открывается окно авторизации, состоящее
из двух полей ввода: 
- Email
- Пароль

Все поля ввода обязательны и проходят следующую валидацию:
- Email - не должен быть занят
- Пароль - от 6 до 20 символов, должны быть цифры, буквы, а также должны отсутствовать пробельные символы

В нижней части экрана находятся кнопки авторизации и смены режима авторизации на регистрацию.

Если пользователь ранее уже авторизовывался, но ещё не вышел из профиля, авторизация произойдёт автоматически.

После нажатия на кнопку "Авторизоваться" приложение ищет связанный с данным email-ом профиль,
после чего проверяет введенный пароль.
После успешной авторизации, окно меняется на [главное](#главное-окно).

<p align="center">
<img src="https://user-images.githubusercontent.com/71221953/186124489-3b027cff-63eb-4f15-b219-f6f940d9c6aa.jpg" width="250" alt="Auth screen">
</p>

## Окно регистрации
Окно регистрации состоит из следующих полей ввода:
из двух полей ввода: 
- Email
- Пароль
- Имя
- Фамилия

Все поля ввода обязательны проходят следующую валидацию:
- Email - не должен быть занят
- Пароль - от 6 до 20 символов, должны быть цифры, буквы, а также должны отсутствовать пробельные символы
- Имя и фамилия - от 1 до 20 символов

В нижней части экрана находятся кнопки регистрации и смены режима авторизации на вход.

После нажатия на кнопку "Зарегистрироваться" приложение создает новый профиль,
после чего отправляет сохраняет его в базе данных.
После успешной регистрации, окно меняется на [главное](#главное-окно).

<p align="center">
<img src="https://user-images.githubusercontent.com/71221953/186125281-8f96874b-52bc-4cb1-8842-92261a4f09c7.jpg" width="250" alt="Register screen">
</p>

## Главное окно
После успешной авторизации в верхней панели с названием текущего окна с правой стороны появится кнопка
выхода из текущего профиля, по нажатии на которую пользователя перекидывает на
[окно авторизации](#окно-авторизации), а текущая сессия очищается.

В нижней части экрана находится панель навигации по основным окнам:
- [Главное окно](#главное-окно)
- [Окно истории созданных списков покупок](#окно-истории-созданных-списков)

Если ввести номер списка покупок в соответствующее поле ввода, а затем нажать на кнопку
"Открыть список покупок", откроется [окно деталей списка](#окно-деталей-списка).

Если нажать на кнопку "Создать новый список", откроется [окно создания списка покупок](#окно-создания-списка).

<p align="center">
<img src="https://user-images.githubusercontent.com/71221953/186126211-7c584925-4d32-4888-99f4-00c764f39dbd.jpg" width="250" alt="Home screen">
</p>

## Окно истории созданных списков
В данном окне есть две секции:
- Активные списки - созданные Вами списки, которые еще не выполнили
- Выполненные списки - созданные Вами списки, которые кто-то выполнил

В каждом списке есть своя информация, например:
- Количество пунктов
- Кто выполнил список (только для выполненного списка)
- Когда выполнили список (только для выполненного списка)

Если нажать на любой из списков, откроется [окно деталей списка](#окно-деталей-списка).

<p align="center">
<img src="https://user-images.githubusercontent.com/71221953/186127032-5f7fde6a-48fe-4f91-9d3e-8f5445803b74.jpg" width="250" alt="Lists history screen">
</p>

## Окно деталей списка
В данном окне находится список пунктов. В зависимости от состояния окна, эти
пункты либо можно отмечать выполненными (выполнение списка), либо нет (только просмотр списка).

Если данное окно открыто для выполнения списка, в правом нижнем углу экрана
будет находиться кнопка выполнения списка, при нажатии на которую список
выполнится, а автору этого списка отправится уведомление о том, что его список выполнили.

<p align="center">
<img src="https://user-images.githubusercontent.com/71221953/186127928-c7d67f7a-26fd-4edb-9d77-bc38cc907c59.jpg" width="250" alt="List details screen">
</p>

<p align="center">
<img src="https://user-images.githubusercontent.com/71221953/186128455-b0691518-3f77-4658-bbf0-0b9add82e685.jpg" width="250" alt="Notification about completing">
</p>

## Окно создания списка
Данное окно состоит из списка пунктов. При нажатии на любой из пунктов откроется
[окно изменения пункта](#окно-изменения-пункта).

Если же список пунктов пустой, на экране будет подсказка о том, что
при нажатии на кнопку добавления пункта справа внизу откроется [окно добавления пункта](#окно-изменения-пункта).

Также ниже кнопки добавления пункта находится кнопка сохранения списка, при нажатии
на которую откроется [окно результата создания списка](#окно-результата-создания-списка).

<p align="center">
<img src="https://user-images.githubusercontent.com/71221953/186129646-a518b6a3-75f1-4dce-b0e0-6497edb53ebf.jpg" width="250" alt="Create list screen">
</p>

## Окно изменения пункта
Данное окно состоит из двух полей ввода:
- Название пункта
- Количество штук

Все поля ввода обязательны и проходят следующую валидацию:
- Название пункта - от 2 до 30 символов
- Количество штук - целое число от 1 до 30

Ниже полей ввода находится кнопка сохранения пункта, при нажатии
на которую назад возвращается [окно создания списка)[#окно-создания-списка]
с добавленным или измененным пунктом.

<p align="center">
<img src="https://user-images.githubusercontent.com/71221953/186130172-3da6df2b-5a69-47c8-a053-650201e6f15e.jpg" width="250" alt="Add item to list screen">
</p>

## Окно результата создания списка
В данном окне находится сообщения о том, что список успешно создан.
Ниже этого сообщения находятся кнопки:
- Поделиться номером списка - открывается системное окно, в котором можно поделиться номером списка покупок
- Назад на главную - закрывает текущее окно и возвращается на [главное](#главное-окно).

Также пользователю отправляется уведомление, что номер нового
списка покупок успешно скопирован клавиатурой.

<p align="center">
<img src="https://user-images.githubusercontent.com/71221953/186131071-b8c85c61-90bc-4f90-8adf-aa3efb7db5bf.jpg" width="250" alt="Add item to list screen">
</p>
