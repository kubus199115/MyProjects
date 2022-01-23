#!/bin/bash
service mysql start

mysql -u root -proot -D mysql -e "update user set plugin='mysql_native_password' where User='root'"
mysql -u root -proot -D mysql -e "flush privileges"

service mysql restart

mysqladmin -u root password root

mysql -u root -proot -e "create database groupsecurity default character set utf8 default collate utf8_unicode_ci"

/home/glassfish5/bin/asadmin start-domain
/home/glassfish5/bin/asadmin deploy /home/SpringHibernateProject-1.0-SNAPSHOT.war

mysql -u root -proot -D groupsecurity -e "insert into users(username, enabled, password) values ('admin', 1, 'b3d0e74121a1cf7c6b579ad246abc1ba52ce0fd52af17dbd003ec53475fe4b9ea4c6a2361e68edd7')"
mysql -u root -proot -D groupsecurity -e "insert into users(username, enabled, password) values ('anna', 1, '0b8475feaddaa073459d27f2663af8894d28112f08e45430b0339d4b6667ec555696d05d96029c67')"
mysql -u root -proot -D groupsecurity -e "insert into users(username, enabled, password) values ('jacek', 1, 'a2defbda86109ce1f0ae53965fad651a438d21220ceed7492128bc32aade06888de1c258234e2933')"
mysql -u root -proot -D groupsecurity -e "insert into users(username, enabled, password) values ('jan', 1, '2f2efda27c3a5fc94da2f46a9b39a7f0a795c839dae841b5220e62f2534ba51f495558255ff6412d')"
mysql -u root -proot -D groupsecurity -e "insert into users(username, enabled, password) values ('marek', 1, 'e627703b1636e6170157a830c19647dd93c3ee65e145776d0518077e6c2b33660fd9464cd58cfc6a')"

mysql -u root -proot -D groupsecurity -e "insert into authorities(authority, username) values ('PLAN_ROLE', 'admin')"
mysql -u root -proot -D groupsecurity -e "insert into authorities(authority, username) values ('ENGIN_ROLE', 'admin')"
mysql -u root -proot -D groupsecurity -e "insert into authorities(authority, username) values ('MECHAN_ROLE', 'jan')"
mysql -u root -proot -D groupsecurity -e "insert into authorities(authority, username) values ('MECHAN_ROLE', 'marek')"
mysql -u root -proot -D groupsecurity -e "insert into authorities(authority, username) values ('MECHAN_ROLE', 'jacek')"
mysql -u root -proot -D groupsecurity -e "insert into authorities(authority, username) values ('MECHAN_ROLE', 'anna')"

mysql -u root -proot -D groupsecurity -e "insert into production_area(area_name) values('obszar produkcyjny nr1')"
mysql -u root -proot -D groupsecurity -e "insert into production_area(area_name) values('obszar produkcyjny nr2')"
mysql -u root -proot -D groupsecurity -e "insert into production_area(area_name) values('obszar produkcyjny nr3')"
mysql -u root -proot -D groupsecurity -e "insert into production_area(area_name) values('obszar produkcyjny nr4')"

while :
do
	sleep 1
done
