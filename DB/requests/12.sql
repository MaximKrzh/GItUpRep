select  count(messages.user_id) as 'numb of messages' , users.name , users.id  
	from messages inner join users on messages.user_id=users.id 
where date = '2016-05-24 23:59:57'
group by name