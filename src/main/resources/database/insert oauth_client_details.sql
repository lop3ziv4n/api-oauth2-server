
INSERT INTO oauth.oauth_client_details 
	(client_id, resource_ids, client_secret, scope, 
    authorized_grant_types, web_server_redirect_uri, authorities,
    access_token_validity, refresh_token_validity, additional_information, autoapprove) 
VALUES ('internal-client', 'blb_rest_api', 'internal-client-secret', 'read,write',
		'authorization_code,implicit,password,refresh_token', NULL,'CLIENT',
        120, 600, NULL, NULL); 
        
INSERT INTO oauth.oauth_client_details 
	(client_id,resource_ids,client_secret,scope,
    authorized_grant_types,web_server_redirect_uri,authorities,
    access_token_validity,refresh_token_validity,additional_information,autoapprove)
VALUES ('external-client', 'blb_rest_api', 'external-client-secret', 'read',
	'authorization_code,implicit,password,refresh_token', NULL, 'GUEST',
    30, 60, NULL, NULL);

INSERT INTO oauth.oauth_user
	(login, password) 
VALUES
	('admin', '$2a$12$YFX2hUF9POFMblnLeZb8o.rqRuwKqoYGx6CrPLF96ox5EgHdreg8G');
    
INSERT INTO oauth.oauth_user
	(login, password) 
VALUES
	('user', '$2y$10$IDfVFcDrm3QQW4NltDuS9uf0A.Pc08ddZtbH0gv3.ObKP.cVnkl5G');
    
INSERT INTO oauth.oauth_role
	(code)
VALUES
	('ROLE_ADMIN');

INSERT INTO oauth.oauth_role
	(code)
VALUES
	('ROLE_USER');
       
INSERT INTO oauth.oauth_user_role
	(user_id, role_id)
VALUES
	(1, 1);
    
INSERT INTO oauth.oauth_user_role
	(user_id, role_id)
VALUES
	(1, 2);

INSERT INTO oauth.oauth_user_role
	(user_id, role_id)
VALUES
	(2, 2);
