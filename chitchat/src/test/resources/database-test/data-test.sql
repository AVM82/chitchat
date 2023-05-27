INSERT INTO users
VALUES (DEFAULT, 'testUser2', 'useremail2@gmail.com',
        '$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
        true, true, true, true),
       (DEFAULT, 'testUser1', 'useremail1@gmail.com',
        '$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
        true, true, true, true),
       (DEFAULT, 'testUser3', 'useremail3@gmail.com',
        '$2a$12$jT.nKuZmve3EWeKLlmuLYeNFCNh9ezb7zSXYSjKrHTwilPSKu.1LS',
        true, true, true, true);

-- INSERT INTO roles
-- VALUES (DEFAULT, 'ADMIN'),
--        (DEFAULT, 'USER');


-- INSERT INTO users_roles
-- VALUES (1, 1),
--        (1, 2),
--        (2, 2),
--        (3, 2);

INSERT INTO languages
VALUES ('uk', 'українська'),
       ('en', 'english'),
       ('de', 'deutsch');

INSERT INTO categories
VALUES (DEFAULT, 'Computer Science and IT', 10),
       (DEFAULT, 'Casual Conversation', 5),
       (DEFAULT, 'How beer can help you understand the theory of relativity', 3);

INSERT INTO chitchats
VALUES (DEFAULT, 1, 'test header', 1, 'test description', 'en', 'A1', 10, '2023-05-01T12:00', null),
       (DEFAULT, 2, 'lets go', 2, 'speak with me', 'de', 'NATIVE', 2, '2023-06-01T12:00', null),
       (DEFAULT, 3, 'lets go testing', 3, 'test this chat', 'en', 'A2', 7, '2023-05-21T10:10', null),
       (DEFAULT, 1, 'lets go', 2, 'test me', 'de', 'B1', 2, '2023-05-21T09:10', null);

INSERT INTO chitchat_users
VALUES (1, 1),
       (2, 2);

-- INSERT INTO permissions
-- VALUES (DEFAULT, 'FREE'),
--        (DEFAULT, 'SUBSCRIPTION'),
--        (DEFAULT, 'PAID');

-- INSERT INTO users_permissions
-- VALUES (1, 1),
--        (2, 1),
--        (3, 1);

INSERT INTO reminder_data
VALUES (1, '2023-05-31 14:00:00.000000', 'https://1.11', false, 'en'),
       (2, '2023-05-20 13:00:00.000000', 'https://1.22', false, 'en'),
       (3, '2023-05-02 11:25:03.040000', null, false, 'en'),
       (4, '2023-05-11 14:05:12.100000', null, false, 'en');

INSERT INTO reminder_emails
VALUES (1, 'useremail2@gmail.com'),
       (2, 'useremail3@gmail.com');

INSERT INTO user_data
VALUES (1, 'UserForTests', 'TestUser', null, 'MALE', '2023-04-01', 'uk'),
       (2, 'UserUser', 'Tester', null, 'FEMALE', '2023-05-01', 'en'),
       (3, 'ForTests', 'TesterUser', null, 'MALE', '2023-05-05', 'uk');

INSERT INTO messages
VALUES (DEFAULT, 3, 2, 'Hello test message!', '2023-05-07 18:15:01.002000', 'CHAT'),
       (DEFAULT, 2, 1, 'Test message!', '2023-05-12 16:11:05.032000', 'CHAT'),
       (DEFAULT, 1, 1, 'Test message two!', '2023-05-04 13:11:25.040000', 'CHAT');

INSERT INTO message_users
VALUES (1, 2, false),
       (2, 1, true),
       (3, 1, false);

INSERT INTO translation
VALUES (1, 'title_confirm_participate', 'en', 'Hello, it is test message!'),
       (2, 'title_confirm_create', 'en', 'Hello, it is message!'),
       (3, 'title_confirm_participate', 'en', 'Hello, it is test!'),
       (4, 'email_confirm_participate_chitchat', 'en', 'Hello test message!')

-- INSERT INTO translation
-- VALUES ('email_confirm_create_chat', 'Hello! You have scheduled a new Chitchat.
-- Date: %s
-- Category: %s
-- Language: %s
-- Level: %s
--
-- Here are a few steps to follow:
--
-- 1. Follow the link to Google Calendar: %s
-- 2. In Google Calendar, click the "Add Google Meet video conference" button.
-- 3. Copy the link to the video conference that appears on the Google Calendar page by clicking the \"Copy\" button.
-- 4. Click the "Save" button in Google Calendar to not forget about the meeting.
-- 5. Go to the Chitchat page using this link: %s
--    and paste the copied link into the "Video conference link" field.
--
-- Now, the link to the video conference will be sent to all meeting participants via email and will be available on your Chitchat page shortly before the start.
--
-- You can discuss any questions with the participants before the meeting in the internal text chat on the Chitchat page.
--
-- If you have any questions, feel free to contact support for further assistance.
--
-- We wish you successful meetings in Chitchat!', 'Hallo,
-- sie haben einen neuen Chitchat geplant.
-- Datum: %s
-- Kategorie: %s
-- Sprache: %s
-- Level: %s
--
-- Hier sind ein paar Schritte, die Sie befolgen sollten:
--
-- 1. Folgen Sie dem Link zu Google Kalender: %s
-- 2. Klicken Sie in Google Kalender auf die Schaltfläche "Google Meet Videokonferenz hinzufügen".
-- 3. Kopieren Sie den Link zur Videokonferenz, der auf der Google Kalender-Seite angezeigt wird, indem Sie auf die Schaltfläche "Kopieren" klicken.
-- 4. Klicken Sie auf die Schaltfläche "Speichern" in Google Kalender, um das Meeting nicht zu vergessen.
-- 5. Gehen Sie zur Chitchat-Seite über folgenden Link: %s
--    und fügen Sie den kopierten Link in das Feld "Videokonferenz-Link" ein.
--
-- Der Link zur Videokonferenz wird nun allen Teilnehmern per E-Mail zugesendet und steht kurz vor Beginn auch auf Ihrer Chitchat-Seite zur Verfügung.
--
-- Sie können vor dem Meeting eventuelle Fragen mit den Teilnehmern im internen Textchat auf der Chitchat-Seite besprechen.
--
-- Wenn Sie Fragen haben, können Sie sich gerne an den Support wenden.
--
-- Wir wünschen Ihnen erfolgreiche Meetings in Chitchat!', 'Привіт! Ви запланували новий Chitchat.
-- Дата: %s
-- Категорія: %s
-- Мова: %s
-- Рівень: %s
--
-- Ось кілька кроків, які потрібно виконати:
--
-- 1. Перейдіть за посиланням до Google Календаря: %s
-- 2. У Google Календарі натисніть кнопку "Додати відеоконференцію Google Meet"
-- 3. Скопіюйте посилання на відеоконференцію,яке з''явиться на сторінці Google Календаря, натиснувши кнопку "Скопіювати".
-- 4. Натисніть кнопку "Зберегти" у Google Календарі, щоб не забути про зустріч.
-- 5. Перейдіть на сторінку Chitchat по цьому посиланню: %s
--    та вставте скопійоване посилання у поле "Посилання на відеоконференцію".
--
-- Тепер посилання на відеоконференцію буде надіслано всім учасникам зустрічі по електронній пошті та буде доступне на сторінці Чіт-чату незадовго до початку.
--
-- Ви можете обговорити будь-які питання з учасниками перед зустріччю у внутрішньому текстовому чаті на сторінці Чіт-чату.
--
-- Якщо у вас виникнуть питання, звертайтеся до служби підтримки для отримання допомоги.
--
-- Бажаємо успішних зустрічей у Chitchat!'),
--
--        ('title_reminder', 'Your chitchat will start soon', 'Ihr Chitchat beginnt bald', 'Ваш Chitchat скоро розпочнеться');
