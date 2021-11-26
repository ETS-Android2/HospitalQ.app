var mongodb = require('mongodb');
var objectID = mongodb.ObjectID;
var crypto = require('crypto');
var express = require('express');
var bodyParser = require('body-parser');

var genRandomString = function(length){
    return crypto.randomBytes(Math.ceil(length/2))
        .toString('hex')
        .slice(0,length);
};

var sha512 = function(password, salt){
    var hash = crypto.createHmac('sha512', salt);
    hash.update(password);
    var value = hash.digest('hex');
    return{
        salt:salt,
        passwordHash:value
    };
};

function saltHashPassword(userPassword){
    var salt = genRandomString(16);
    var passwordData = sha512(userPassword, salt);
    return passwordData;
}

function checkHashPassword(userPasword, salt){
    var passwordData = sha512(userPasword, salt);
    return passwordData;
}

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

var MongoClient = mongodb.MongoClient;
var url = 'mongodb+srv://pervasif:pervasifsukses@smartwaste0.h3hja.mongodb.net/hospitalQ?retryWrites=true&w=majority'
MongoClient.connect(url, {useNewUrlParser: true}, function(err, client){
    if(err)
        console.log('Unable to connect to the mongoDB server Error', err);
        else{

            app.post('/register', (request, response, next)=>{
                var post_data = request.body;
                var plain_password = post_data.password;
                var hash_data = saltHashPassword(plain_password);
                var password = hash_data.passwordHash;
                var salt = hash_data.salt;
                var email = post_data.email;
                var firstname = post_data.firstname;
                var lastname = post_data.lastname;
                var hari = post_data.hari;
                var bulan = post_data.bulan;
                var tahun = post_data.tahun;
                var alamat = post_data.alamat;
                var departement = post_data.departement;
                var userrole = post_data.userrole;

                var insertJson = {
                    'email': email,
                    'password': password,
                    'salt': salt,
                    'firstname': firstname,
                    'lastname': lastname,
                    'hari': hari,
                    'bulan': bulan,
                    'tahun': tahun,
                    'alamat': alamat,
                    'departement':departement,
                    'userrole': userrole
                };
                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number != 0){
                            response.json('Email already exists');
                            console.log('Email already exists');
                        }else{
                            db.collection('user')
                                .insertOne(insertJson, function(error, res){
                                    response.json('Registration Success');
                                    console.log('Registration Success');
                                })
                        }
                    })
            });
            
            app.post('/login', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            response.json('Email not exists');
                            console.log('Email not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    if(hashed_password == encrypted_password){
                                        response.json(user.userrole);
                                        console.log('Login success'+user.email);
                                    }else{
                                        response.json('Wrong password');
                                        console.log('Wrong password'+user.email);
                                    }
                                })
                        }
                    })
            });

            app.post('/getuser', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            res.json('Email not exists');
                            console.log('Email not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    if(hashed_password == encrypted_password){
                                        var dataUser = {
                                            'email': user.email,
                                            'firstname': user.firstname,
                                            'lastname': user.lastname,
                                            'hari': user.hari,
                                            'bulan': user.bulan,
                                            'tahun': user.tahun,
                                            'alamat': user.alamat,
                                            'departement': user.departement,
                                            'userrole': user.userrole
                                        };
                                        response.json(dataUser);
                                        console.log('User Found');
                                    }else{
                                        response.json('Wrong password');
                                        console.log('Wrong password');
                                    }
                                })
                        }
                    })
            });

            app.post('/updateuser', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var firstname = post_data.firstname;
                var lastname = post_data.lastname;
                var hari = post_data.hari;
                var bulan = post_data.bulan;
                var tahun = post_data.tahun;
                var alamat = post_data.alamat;
                var departement = post_data.departement;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number != 0){
                            db.collection('user')
                            .updateOne({'email':email}, { $set:{
                                'firstname': firstname,
                                'lastname': lastname,
                                'hari': hari,
                                'bulan': bulan,
                                'tahun': tahun,
                                'alamat': alamat,
                                'departement':departement,
                                }
                            })
                            response.json('Update Success');
                            console.log('Update Success');
                        }else{
                            response.json('Account not exists');
                            console.log('Account not exists');
                        }
                    })
            });

            app.post('/getalldoctor', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            res.json('Email not exists');
                            console.log('Email not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    if(hashed_password == encrypted_password){
                                        db.collection('user')
                                        .find({'userrole':'1'}).count(function(err,number){
                                            if(number == 0){
                                                res.json('Doctor not exists');
                                                console.log('Doctor not exists');
                                            }else{
                                                db.collection('user')
                                                .find({'userrole':'1'}).toArray(function(err,doktor){
                                                    response.json(doktor);
                                                    console.log('Doctor exists');
                                                })
                                            }
                                        })
                                    }else{
                                        response.json('Wrong password');
                                        console.log('Wrong password');
                                    }
                                })
                        }
                    })
            });

            app.post('/getdoctorbydepartement', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;
                var departement = post_data.departement;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            res.json('Email not exists');
                            console.log('Email not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    if(hashed_password == encrypted_password){
                                        db.collection('user')
                                        .find({'departement': departement}).count(function(err,number){
                                            if(number == 0){
                                                res.json('Doctor not exists');
                                                console.log('Doctor not exists');
                                            }else{
                                                db.collection('user')
                                                .find({'departement': departement}).toArray(function(err,doktor){
                                                    response.json(doktor);
                                                    console.log('Doctor exists');
                                                })
                                            }
                                        })
                                    }else{
                                        response.json('Wrong password');
                                        console.log('Wrong password');
                                    }
                                })
                        }
                    })
            });

            app.post('/addantrian', (request, response, next)=>{
                var post_data = request.body;
                var user_name = post_data.user_name;
                var doktor_name = post_data.doktor_name;
                var doktor_email = post_data.doktor_email;

                var toantrian_user = {
                    'doktor_email': doktor_email,
                    'user_name': user_name,
                    'doktor_name': doktor_name
                };
                var db = client.db('hospitalQ');

                db.collection('antrian')
                    .find({'doktor_name':doktor_name}).count(function(err,number){
                        if(number == 0){
                            var toantrian = {
                                'doktor_email': doktor_email,
                                'doktor_name': doktor_name,
                                'current_antrian': 1,
                                'antrian': 1
                            };
                            db.collection('antrian')
                                .insertOne(toantrian, function(error, res){
                                })
                            db.collection('antrian_user')
                                .insertOne(toantrian_user, function(error, res){
                                    response.json('Add Success');
                                    console.log('Add Success');
                                })
                        }else{
                            db.collection('antrian')
                            .findOne({'doktor_name':doktor_name}, function(err,antrian){
                                db.collection('antrian')
                                .updateOne({'doktor_name':doktor_name}, { $set:{
                                    'doktor_email': doktor_email,
                                    'doktor_name': doktor_name,
                                    'current_antrian': 1,
                                    'antrian': antrian.antrian + 1
                                }
                                })
                            })
                            db.collection('antrian_user')
                                .insertOne(toantrian_user, function(error, res){
                                    response.json('Add Success');
                                    console.log('Add Success');
                                })
                        }
                    })
            });

            app.post('/getcurrentdoctorantrian', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            res.json('Account not exists');
                            console.log('Account not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    var doktor_name = user.email
                                    if(hashed_password == encrypted_password){
                                        db.collection('antrian')
                                        .findOne({'doktor_email':doktor_name}, function(err,antrian){
                                            response.json(antrian.current_antrian);
                                            console.log('User Found');
                                        })
                                    }else{
                                        response.json('Must Login');
                                        console.log('Must Login');
                                    }
                                })
                        }
                    })

            });

            app.post('/getdoctorantrian', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            res.json('Account not exists');
                            console.log('Account not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    var doktor_name = user.email
                                    if(hashed_password == encrypted_password){
                                        db.collection('antrian')
                                        .findOne({'doktor_email':doktor_name}, function(err,antrian){
                                            response.json(antrian.antrian);
                                            console.log('User Found');
                                        })
                                    }else{
                                        response.json('Must Login');
                                        console.log('Must Login');
                                    }
                                })
                        }
                    })

            });

            app.post('/getuserantrian', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            res.json('Account not exists');
                            console.log('Account not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    var user_name = user.email
                                    if(hashed_password == encrypted_password){
                                        db.collection('antrian_user')
                                        .find({'user_name':user_name}).toArray(function(err,antrian){
                                            response.json(antrian);
                                            console.log('User Found');
                                        })
                                    }else{
                                        response.json('Must Login');
                                        console.log('Must Login');
                                    }
                                })
                        }
                    })

            });

            app.post('/getallantrian', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            res.json('Account not exists');
                            console.log('Account not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    var user_name = user.firstname
                                    if(hashed_password == encrypted_password){
                                        db.collection('antrian')
                                        .find({}).toArray(function(err,antrian){
                                            response.json(antrian);
                                            console.log('User Found');
                                        })
                                    }else{
                                        response.json('Must Login');
                                        console.log('Must Login');
                                    }
                                })
                        }
                    })

            });

            app.post('/zeroantrian', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            res.json('Account not exists');
                            console.log('Account not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    var doktor_name = user.email
                                    var doktor_email = user.firstname
                                    if(hashed_password == encrypted_password){
                                        db.collection('antrian')
                                        .find({'doktor_email':doktor_name}).count(function(err,number){
                                            if(number == 0){
                                                var toantrian = {
                                                    'doktor_email': doktor_name,
                                                    'doktor_name': doktor_email,
                                                    'current_antrian': 0,
                                                    'antrian': 0
                                                };
                                                db.collection('antrian')
                                                    .insertOne(toantrian, function(error, res){
                                                        response.json('success');
                                                        console.log('success');
                                                    })
                                            }else{
                                                db.collection('antrian')
                                                .findOne({'doktor_email':doktor_name}, function(err,antrian){
                                                    db.collection('antrian')
                                                    .updateOne({'doktor_email':doktor_name}, { $set:{
                                                        'doktor_email': doktor_name,
                                                        'doktor_name': doktor_email,
                                                        'current_antrian': 0,
                                                        'antrian': 0
                                                    }
                                                    })
                                                    response.json('success');
                                                    console.log('success');
                                                })
                                            }
                                        })
                                    }else{
                                        response.json('Must Login');
                                        console.log('Must Login');
                                    }
                                })
                        }
                    })

            });

            app.post('/incrementantrian', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            res.json('Account not exists');
                            console.log('Account not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    var doktor_name = user.email
                                    var doktor_email = user.firstname
                                    if(hashed_password == encrypted_password){
                                        db.collection('antrian')
                                        .find({'doktor_email':doktor_name}).count(function(err,number){
                                            if(number == 0){
                                                var toantrian = {
                                                    'doktor_email': doktor_name,
                                                    'doktor_name': doktor_email,
                                                    'current_antrian': 0,
                                                    'antrian': 0
                                                };
                                                db.collection('antrian')
                                                    .insertOne(toantrian, function(error, res){
                                                        response.json('success');
                                                        console.log('success');
                                                    })
                                            }else{
                                                db.collection('antrian')
                                                .findOne({'doktor_email':doktor_name}, function(err,antrian){
                                                    db.collection('antrian')
                                                    .updateOne({'doktor_email':doktor_name}, { $set:{
                                                        'doktor_email': doktor_name,
                                                        'doktor_name': doktor_email,
                                                        'current_antrian': antrian.current_antrian + 1,
                                                        'antrian': antrian.antrian
                                                    }
                                                    })
                                                    response.json('success');
                                                    console.log('success');
                                                })
                                            }
                                        })
                                    }else{
                                        response.json('Must Login');
                                        console.log('Must Login');
                                    }
                                })
                        }
                    })
            });

            app.post('/adddepartement', (request, response, next)=>{
                var post_data = request.body;
                var departement = post_data.departement;

                var insertJson = {
                    'departement':departement,
                };
                var db = client.db('hospitalQ');

                db.collection('departement')
                    .find({'departement':departement}).count(function(err,number){
                        if(number != 0){
                            response.json('Departement already exists');
                            console.log('Departement already exists');
                        }else{
                            db.collection('departement')
                                .insertOne(insertJson, function(error, res){
                                    response.json('Add Departement Success');
                                    console.log('Add Departement Success');
                                })
                        }
                    })
            });
            
            app.post('/getdepartement', (request, response, next)=>{
                var post_data = request.body;
                var email = post_data.email;
                var userPassword = post_data.password;

                var db = client.db('hospitalQ');

                db.collection('user')
                    .find({'email':email}).count(function(err,number){
                        if(number == 0){
                            res.json('Account not exists');
                            console.log('Account not exists');
                        }else{
                            db.collection('user')
                                .findOne({'email':email}, function(err,user){
                                    var salt = user.salt;
                                    var hashed_password = checkHashPassword(userPassword,salt).passwordHash;
                                    var encrypted_password = user.password;
                                    if(hashed_password == encrypted_password){
                                        db.collection('departement')
                                        .find({}).toArray(function(err,departement){
                                            response.json(departement);
                                            console.log('User Found');
                                        })
                                    }else{
                                        response.json('Must Login');
                                        console.log('Must Login');
                                    }
                                })
                        }
                    })
            });

            app.listen(process.env.PORT || 3000, () => {
                console.log('Connected to mongoDB server, WebService running on port 3000');
            })
        }
});