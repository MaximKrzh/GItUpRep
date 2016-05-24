select  messages.date, users.name, messages.text
from (messages inner join users on (messages.user_id=users.id))
where users.name='name'
order by date 
