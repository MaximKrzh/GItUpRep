select distinct users.name from users inner join messages on users.id = messages.user_id;