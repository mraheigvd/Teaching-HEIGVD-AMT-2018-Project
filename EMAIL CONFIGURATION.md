# EMAIL CONFIGURATION

1. Follow the configuration given in : [Payara configuration tutorial](https://medium.com/@swhp/sending-email-with-payara-and-gmail-56b0b5d56882)
2. Edit the database with SQL : 
   1. ALTER TABLE `user`  ADD `password_is_expired` TINYINT(1) NOT NULL DEFAULT '0'  AFTER `token_validate`;

