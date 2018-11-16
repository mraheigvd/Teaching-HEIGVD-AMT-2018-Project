## HOW TO ?

How to use AMT Gamification W1 ?

First of all, clone this repo and build the WAR for using the latest sources, then:

- Adapt the file ``db.properties`` by using with your Docker IP and ports
- ``cd Teaching-HEIGVD-AMT-2018-Project``
- ``mvn clean install``
- ``cp -f target/Gamification-WP1.war Docker/``
- ``cd Docker``
- ``docker-compose up --build``

After that you only need to open a browser and go to: ``http://<YOUR_DOCKER_IP_MACHINE>:9876/Gamification-WP1/``

Explanation:

- You'll find a db directory into Docker folder which contains the db_scheme.sql. This SQL script represents
the database scheme of the database amt_project. This is used into the docker-compose.yml by the statement ``docker-entrypoint-initdb.d```
- docker-compose.yml will launch one instance of MySQL and glassfish accessible via ``http://<YOUR_DOCKER_IP_MACHINE>:9876/Gamification-WP1/``

**Quick tip for rebuild:**

```bash
cd Teaching-HEIGVD-AMT-2018-Project/ && mvn clean install && cp -f target/Gamification-WP1.war Docker
docker ps -a -q | xargs docker stop && docker ps -a -q | xargs docker rm -v
cd Teaching-HEIGVD-AMT-2018-Project/Docker && docker-compose up
```
