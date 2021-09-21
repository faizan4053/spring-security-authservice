insert into auth_user(id,first_name,last_name,email,password
,account_non_expired,account_non_locked,credentials_non_expired,enabled,last_modified_time,
created ,created_by,modified ,modified_by
)
values (5003,'Mohammad','faizan','sam@123.com','$2a$10$U2STWqktwFbvPPsfblVeIuy11vQ1S/0LYLeXQf1ZL0cMXc9HuTEA2',true,true,true,true,'1911-12-03T10:15:30+01:00[Europe/Paris]',
'2021-08-06 00:55:27.87439','Eldorado Auditor','2021-08-06 00:43:00.167118','Eldorado Auditor'
);

insert into user_role values(5003,1);