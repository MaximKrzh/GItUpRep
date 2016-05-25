select count(messages.user_id),users.name
	from messages inner join users on (users.id = messages.user_id)
group by name
having count(messages.user_id) >= 3;