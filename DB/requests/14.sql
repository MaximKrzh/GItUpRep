select users.name, messages.text , messages.date
	from  messages inner join users on users.id = messages.user_id
where text like '%100%';