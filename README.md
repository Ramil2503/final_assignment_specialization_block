## final_assignment_specialization_block
1. Используя команду cat в терминале операционной системы Linux, создать
два файла Домашние животные (заполнив файл собаками, кошками,
хомяками) и Вьючные животными заполнив файл Лошадьми, верблюдами и
ослы), а затем объединить их. Просмотреть содержимое созданного файла.
Переименовать файл, дав ему новое имя (Друзья человека).
![task1](img/task1.png)
2. Создать директорию, переместить файл туда.
![task2](img/task2.png)
3. Подключить дополнительный репозиторий MySQL. Установить любой пакет
из этого репозитория.
![task3](img/task3.png)
![task3(2)](img/task3(2).png)
4. Установить и удалить deb-пакет с помощью dpkg.
![task4](img/task4.png)
5. Выложить историю команд в терминале ubuntu
6. Нарисовать диаграмму, в которой есть класс родительский класс, домашние
животные и вьючные животные, в составы которых в случае домашних
животных войдут классы: собаки, кошки, хомяки, а в класс вьючные животные
войдут: Лошади, верблюды и ослы).
![classDiagram](img/ClassDiagram.png)
7. В подключенном MySQL репозитории создать базу данных “Друзья
человека”
```sql
CREATE DATABASE Human_friends;
```
8. Создать таблицы с иерархией из диаграммы в БД
```sql
USE Friends_database;

CREATE TABLE animal_types
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Type_name VARCHAR(20)
);

INSERT INTO animal_types (Type_name)
VALUES ('wild'),
('domestic');

CREATE TABLE pack_animals
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Species_name VARCHAR(20),
    Type_id INT,
    FOREIGN KEY (Type_id) REFERENCES animal_types (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO pack_animals (Species_name, Type_id)
VALUES ('Horses', 1),
('Donkeys', 1),
('Camels', 1);

CREATE TABLE pet_animals
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Species_name VARCHAR(20),
    Type_id INT,
    FOREIGN KEY (Type_id) REFERENCES animal_types (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO pet_animals (Species_name, Type_id)
VALUES ('Cats', 2),
('Dogs', 2),
('Hamsters', 2);

CREATE TABLE dogs
(
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(20),
    Birth_date DATE,
    Commands VARCHAR(50),
    Species_id INT,
    FOREIGN KEY (Species_id) REFERENCES pet_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);
```
9. Заполнить низкоуровневые таблицы именами(животных), командами
которые они выполняют и датами рождения
```sql
INSERT INTO cats (Name, Birthday, Commands, Genus_id)
VALUES ('Whiskers', '2015-05-12', 'meow', 1),
('Fluffy', '2018-02-25', 'purr', 1),  
('Mittens', '2019-08-10', 'play', 1); 

INSERT INTO dogs (Name, Birthday, Commands, Genus_id)
VALUES ('Rex', '2020-10-01', 'come, sit, fetch', 2),
('Buddy', '2019-06-15', 'stay, lie down', 2),  
('Max', '2017-04-20', 'sit, stay, shake', 2), 
('Rocky', '2021-09-05', 'fetch, heel', 2);

INSERT INTO hamsters (Name, Birthday, Commands, Genus_id)
VALUES ('Tiny', '2021-11-12', 'spin', 3),
('Speedy', '2020-12-08', 'run', 3),  
('Nibbles', '2022-01-05', 'explore', 3), 
('Cheeks', '2023-02-14', 'eat, sleep', 3);

INSERT INTO horses (Name, Birthday, Commands, Genus_id)
VALUES ('Thunder', '2019-07-25', 'trot, canter, jump', 1),
('Spirit', '2020-03-18', 'gallop, trot', 1),  
('Midnight', '2018-11-30', 'walk, trot, jump', 1), 
('Duke', '2021-01-12', 'walk, canter', 1);

INSERT INTO donkeys (Name, Birthday, Commands, Genus_id)
VALUES ('Eeyore', '2022-05-06', 'bray', 2),
('Burrito', '2021-09-15', 'bray, graze', 2),  
('Jack', '2023-02-20', 'walk', 2), 
('Jenny', '2020-12-10', 'bray, carry load', 2);

INSERT INTO camels (Name, Birthday, Commands, Genus_id)
VALUES ('Sahara', '2020-08-22', 'turn, walk', 3),
('Gobi', '2019-06-10', 'stand, kneel', 3),  
('Atlas', '2018-10-05', 'carry, rest', 3), 
('Desert', '2021-03-30', 'guide, stop', 3);
```
10. Удалив из таблицы верблюдов, т.к. верблюдов решили перевезти в другой
питомник на зимовку. Объединить таблицы лошади, и ослы в одну таблицу.
```sql
SET SQL_SAFE_UPDATES = 0;
DELETE FROM camels;

SELECT Name, Birthday, Commands FROM horses
UNION SELECT  Name, Birthday, Commands FROM donkeys;
```
11. Создать новую таблицу “молодые животные” в которую попадут все
животные старше 1 года, но младше 3 лет и в отдельном столбце с точностью
до месяца подсчитать возраст животных в новой таблице
```sql
CREATE TEMPORARY TABLE animals AS 
SELECT *, 'Лошади' as genus FROM horses
UNION SELECT *, 'Ослы' AS genus FROM donkeys
UNION SELECT *, 'Собаки' AS genus FROM dogs
UNION SELECT *, 'Кошки' AS genus FROM cats
UNION SELECT *, 'Хомяки' AS genus FROM hamsters;

CREATE TABLE yang_animal AS
SELECT Name, Birthday, Commands, genus, TIMESTAMPDIFF(MONTH, Birthday, CURDATE()) AS Age_in_month
FROM animals WHERE Birthday BETWEEN ADDDATE(curdate(), INTERVAL -3 YEAR) AND ADDDATE(CURDATE(), INTERVAL -1 YEAR);
 
SELECT * FROM yang_animal;
```
12. Объединить все таблицы в одну, при этом сохраняя поля, указывающие на
прошлую принадлежность к старым таблицам.
```sql
SELECT h.Name, h.Birthday, h.Commands, pa.Genus_name, ya.Age_in_month 
FROM horses h
LEFT JOIN yang_animal ya ON ya.Name = h.Name
LEFT JOIN packed_animals pa ON pa.Id = h.Genus_id
UNION 
SELECT d.Name, d.Birthday, d.Commands, pa.Genus_name, ya.Age_in_month 
FROM donkeys d 
LEFT JOIN yang_animal ya ON ya.Name = d.Name
LEFT JOIN packed_animals pa ON pa.Id = d.Genus_id
UNION
SELECT c.Name, c.Birthday, c.Commands, ha.Genus_name, ya.Age_in_month 
FROM cats c
LEFT JOIN yang_animal ya ON ya.Name = c.Name
LEFT JOIN home_animals ha ON ha.Id = c.Genus_id
UNION
SELECT d.Name, d.Birthday, d.Commands, ha.Genus_name, ya.Age_in_month 
FROM dogs d
LEFT JOIN yang_animal ya ON ya.Name = d.Name
LEFT JOIN home_animals ha ON ha.Id = d.Genus_id
UNION
SELECT hm.Name, hm.Birthday, hm.Commands, ha.Genus_name, ya.Age_in_month 
FROM hamsters hm
LEFT JOIN yang_animal ya ON ya.Name = hm.Name
LEFT JOIN home_animals ha ON ha.Id = hm.Genus_id;
```
13. Создать класс с Инкапсуляцией методов и наследованием по диаграмме.
14. Написать программу, имитирующую работу реестра домашних животных.
В программе должен быть реализован следующий функционал:
14.1 Завести новое животное
14.2 определять животное в правильный класс
14.3 увидеть список команд, которое выполняет животное
14.4 обучить животное новым командам
14.5 Реализовать навигацию по меню
15.Создайте класс Счетчик, у которого есть метод add(), увеличивающий̆
значение внутренней̆int переменной̆на 1 при нажатие “Завести новое
животное” Сделайте так, чтобы с объектом такого типа можно было работать в
блоке try-with-resources. Нужно бросить исключение, если работа с объектом
типа счетчик была не в ресурсном try и/или ресурс остался открыт. Значение
считать в ресурсе try, если при заведения животного заполнены все поля.