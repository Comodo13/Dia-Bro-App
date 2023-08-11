# Dia-Bro-App
Backend part of diabetis and pregnancy sypmtoms tracking app. (2 Hackatons Prize winner!!)


Online Demo:
http://34.118.48.240:3000/patient

Problem:
Patients often dont write down their blood tests results. Forget to take medications, appoinment. They also loos track of health check ups.

Local installation:
To run locally download frontend: 
git clone https://github.com/elisemax/client.git
And backend: git clone https://github.com/Comodo13/Dia-Bro-App.git

Inside client folder:
1. npm install
2. npm start

Inside Dia-Bro-app folder:
1. java -jar deploy.jar

Run IRIS for Health Community edition docker-container:
1. sudo docker run --name iris -d --publish 1972:1972 --publish 52773:52773 containers.intersystems.com/intersystems/iris-community:2022.1.0.209.0 --check-caps false

For communication with FHIR REST API I implemented my custom mapper, but then I found out amazing connector library hapi-fhir-base, hapi-fhir-client.
Last week my FHIR server from Hackaton stopped working so I had to mock data in code for demo reasons.

For communication with IRIS for Health I used intersystems-jdbc connector. There is support of Hibernate (ORM for Java).
Because of IRIS for Health internal NoSQL structure, I wanted to test IRIS amazing speed performance with pure SQL.
Maven successfully does job of ZPM: adding ingintersystems-jdbc.jar to project library and compiling.

For communication with FrontEnd we use REST. UI layout is generated at backend part for optimization reasons. But at the same time we expose standart rest api for other consumer applications.


Our team:
1. https://community.intersystems.com/user/dzmitry-rabotkin
2. https://community.intersystems.com/user/maria-muzychuk



