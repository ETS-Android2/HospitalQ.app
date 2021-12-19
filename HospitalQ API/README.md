# HospitalQ API
### <i>!!This app created using NodeJS and ExpressJS</i>

#

## Features:
- Store sensor data trash from node gateway to database
- Authentication user and have 3 user role (admin, doctor, and patient)
- CRUD user
- CRUD queue
- CRUD doctor departement

#

## Library Dependencies
- body-parser
- crypto
- express
- mongodb

#

## Demo app:
You can access this demo API to https://hospitaq-api.herokuapp.com/

#

## How to use this project ?
If you want reverse engineering this project app:
1. Clone this folder
2. Make MongoDB database with user, departement, antrian_user, and antrian collection.
2. Set url mongoDB database in index.js
3. set port according to your system port availability in index.js 
4. Run npm install
5. Run npm run

<i>if need help, feel free to contact us! MatthewBrandon21</i>

#

## API Documentation
variable that not spesified type, that is string. Use HTTP JSON body to request this API.
- /register --> POST --> email, password, firstname, lastname, birthdate, alamat, departement, userrole (int (0 -> patient, 1 -> doktor, 2 -> admin)), ppicture (blob)
- /login --> POST --> email, password
- /getuser --> POST --> email, password
- /deleteuser --> POST --> email, password, emailtodelete
- /updateuser --> POST --> email, firstname, lastname, birthdate, alamat, departement, ppicture (blob)
- /getalldoctor --> POST --> email, password
- /getadoctor --> POST --> email, password, doctoremail
- /getdoctorbydepartement --> POST --> email, password, departement
- /getcurrentdoctorantrian --> POST --> email, password
- /getdokterantrian --> POST --> email, password
- /getuserantrian --> POST --> email, password
- /getallantrian --> POST --> email, password
- /zeroantrian --> POST --> email, password
- /deleteantrian --> POST --> user_name, doktor_name, doktor_email
- /getdepartement --> POST --> email, password
- /addantrian --> POST --> user_name, doktor_name, doktor_email
- /adddepartement --> POST --> departement
- /deletedepartement --> POST --> email, password, departementtodelete
- /updatedepartement --> POST --> email, password, departement, departementtoupdate