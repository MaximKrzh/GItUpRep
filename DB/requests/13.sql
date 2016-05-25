select count(messages.user_id) as 'numb of messages',users.name,
		date_format(date ,'%d %M %Y' ) as mes_date,	date_format(curdate(),'%d %M %Y') as date_today
	from messages inner join users on (users.id = messages.user_id)
    where date_format(date ,'%d %M %Y' ) =   date_format(curdate(),'%d %M %Y')
group by name
having count(messages.user_id) >= 1;

