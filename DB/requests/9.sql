select  messages.date, users.name, messages.text
from (messages inner join users on (messages.user_id=users.id))
having length(messages.text) > 8;
