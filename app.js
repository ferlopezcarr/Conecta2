//Requires
const express = require("express");
const mysql = require("mysql");
const path = require("path");
const bodyParser = require("body-parser");
const config = require("./config");
const expressValidator = require("express-validator");

const session = require("express-session");
const mysqlSession = require("express-mysql-session");

const moment = require('moment');
const multer = require('multer');

const daoUsers = require("./dao_users");
const daoFriends = require("./dao_friends");
const daoQuestions = require("./dao_questions");
 

//Middleware que comprueba si el usuario esta logeado
function logged(request, response, next) {
    
    let encontrado = request.session.user;

    if (encontrado != null) {

        daoU.getUser(encontrado, (err, user) => {
            if(err)
                response.redirect("/login");
            else {
                if(user !== undefined) {
                    response.locals.user = user;
                    next();
                }
                else
                    response.redirect("/login");   
            }
        });

    }
    else
        response.redirect("/login"); 

}//middle
//------------------//

//Base de datos MySQL
const MySQLStore = mysqlSession(session);

const sessionStore = new MySQLStore(config.mysqlConfig);

// Middleware de sesion
const middlewareSession = session({
    saveUninitialized: false,
    secret: "foobar34",
    resave: false,
    store: sessionStore
});


//------ App ------//
const app = express();

app.use(middlewareSession);
app.use(bodyParser.urlencoded({ extended: false }));
app.use(expressValidator({
    customValidators: {
        isDefined: param => { 
            return (param !== undefined);
        },
        isNotDefined: param => { 
            return (param === undefined);
        }
    }
}));

//Middleware Ficheros Estaticos
const ficherosEstaticos = path.join(__dirname,"public");
app.use(express.static(ficherosEstaticos));

//Ficheros Dinamicos
app.set("view engine", "ejs");
app.set("views", path.join(__dirname, "views"));
//-----------------//

//Pool de conexiones
let pool = mysql.createPool({
    database: config.mysqlConfig.database,
    host: config.mysqlConfig.host,
    user: config.mysqlConfig.user,
    password: config.mysqlConfig.password
});

//DAOs
let daoU = new daoUsers.DAOUsers(pool);
let daoF = new daoFriends.DAOFriends(pool);

//Moment
moment.locale('es');

//Multer
const multerFactory = multer({ dest: path.join(__dirname, "/public/icons") });
//app.use(multerFactory.single());

//request.session.messages = [{location, param, msg, value}, ...]
function setFlash(request, resultToArray) {
    request.session.messages = resultToArray;
} 

function getAndClearFlash(request) {
    let msg = request.session.messages;
    delete request.session.messages;
    return msg;
}

//-------- Errores --------//
app.use(function(error, request, response, next) {
    // Código 500: Internal server error
    response.status(500);
    response.render("error", {
        mensaje: error.message,
        pila: error.stack 
    });
 });

//-------- Parsear --------//
function parseEmailAndPass(request) {
    //check Email
    if(!request.checkBody("email", "Email vacío").notEmpty()) {
        request.checkBody("email", "Email inválido").isEmail();
    }
    //check Password
    if(!request.checkBody("pass", "Contraseña vacía").notEmpty()) {
        request.checkBody("pass", "La contraseña solo puede contener letras y números").isAlphanumeric("es-ES");
        request.checkBody("pass", "La contraseña debe estar entre 3 y 40 caracteres").isLength({min: 3, max: 40});
    }
}

function parseRegister(request) {
    //check Email and Password
    parseEmailAndPass(request);
    //check FullName
    if(!request.checkBody("fullName", "Nombre vacío").notEmpty()) {
        request.checkBody("fullName", "El nombre solo puede contener contener letras").isAlpha("es-ES");
    }
    //checkSex
    request.checkBody("sex", "No has indicado tu sexo").isDefined();
    //check Birthday
    if(request.body.birthday !== '')
        request.checkBody("birthday", "Fecha de nacimiento no válida").isBefore();
}

//-------- Edad --------//
function getEdad(birthday) {
    let edad = null;
    let today = moment();

    if(birthday !== undefined)
        if(birthday !== null)
            if(birthday !== "")
                edad = today.diff(birthday, "years"); 

    return edad;
}

// ---------- Acciones sin login ---------- //
//---- Entrar

app.get("/", (request, response, next) => {
    // para que no de error pasamos null para los mensajes flash
	response.redirect("/login");
});

//Para mostrar la pagina de login
app.get("/login", (request, response, next) => {
    // para que no de error pasamos null para los mensajes flash
	response.render("login", {errs: null});
});

//Para recoger la info del formulario y loguear
app.post("/login", (request, response, next) => {

    parseEmailAndPass(request);

    request.getValidationResult().then(result => {
        let errors = result.array();
        
        if (errors.length === 0) {//Si no hay errores al parsear
            daoU.isUserCorrect(request.body.email, request.body.pass, (err, res)=>{
                if(err) next(err);

                if(res) {
                    request.session.user = request.body.email; 
                    response.redirect("/myProfile");
                }
                else {
                    let loginErr = [];
                    loginErr.push({msg: "Dirección de correo y/o contraseña no válidos"});
                    setFlash(request, loginErr);
                    let errs = getAndClearFlash(request);
                    response.render("login", {errs});
                }

            });
        }
        else {
            setFlash(request, errors);
            let errs = getAndClearFlash(request);
            response.render("login", {errs});
        }
    });
});

//---- Registrarse
app.get("/register", (request, response, next) => {
	response.render("register", {errs: null});
});

app.post("/register", multerFactory.single("img"), (request, response, next) => {

    parseRegister(request);

    let edad = getEdad(request.body.birthday);
    if(edad === null)
        request.body.birthday = null;

    request.getValidationResult().then(result => {
        let parsingErrs = result.array();

        if (parsingErrs.length === 0) {//Si NO hay errores al parsear
            let fileN = null;
            
            if(request.file)//si se ha subido el fichero
                fileN = request.file.filename;

            let user = daoU.newUser(
                request.body.email,
                request.body.pass,
                request.body.fullName, 
                request.body.sex,
                request.body.birthday,
                0,
                fileN
            );
            
            daoU.insertUser(user, (err, exito)=>{
                if(err) next(err);
                
                if (exito) {//lo de la izq es el nombre de la var en la plantilla
                    request.session.user = user.email;
                    response.redirect("/myProfile");
                }
                else {
                    //mensaje flash de email ya existente
                    let registerErr = [];
                    registerErr.push({msg: "Dirección de correo ya existente"});
                    setFlash(request, registerErr);
                    let errs = getAndClearFlash(request);
                    response.render("register", {errs});
                }
            });
        }
        else {
            setFlash(request, parsingErrs);
            let errs = getAndClearFlash(request);
            response.render("register", {errs});
        }
    });
});

app.get("/modifyProfile", logged, (request, response) => {

    let user = response.locals.user;
    response.render("modifyProfile", {user});
    
});

app.post("/modifyProfile", logged,  multerFactory.single("img"), (request, response) => {
    
    let user = response.locals.user;
    let edad = getEdad(user.birthday);
   
    if (request.body.pass !== undefined) {
        daoU.modifyPass(user.email,request.body.pass,function(err, exito){
            response.redirect("/myProfile");
       });
    }
    if (request.body.fullName !== undefined) {
        daoU.modifyName(user.email,request.body.fullName,function(err, exito){
            response.redirect("/myProfile");
        });
    }
    if (request.body.sex !== undefined) {
        daoU.modifySex(user.email,request.body.sex,function(err, exito){
            response.redirect("/myProfile");
        });
    }
    if (request.body.birthday !== undefined) {
        daoU.modifybirthday(user.email,request.body.birthday,function(err, exito){
            response.redirect("/myProfile");
        });
    }
    if (request.file) {
        daoU.modifyimg(user.email,request.file.filename,function(err, exito){
            response.redirect("/myProfile");
        });
    }
   
});

// ---------- Acciones una vez logueado ---------- //
//---- Mi Perfil ----------------------------------------------------
app.get("/myProfile", logged, (request, response, next) => {

    let user = response.locals.user;

    response.render("myProfile", { user: user, edad: getEdad(user.birthday), userProfile: user });
    
});

//---- Amigos ----------------------------------------------------
app.get("/friends", logged, (request, response, next) => {

    let user = response.locals.user;
    response.locals.nameSearch = null;

    //Listar solicitudes de amistad
    daoF.getfriendshipRequests(user.email, (err, listFriendsRequests) => {
        if(err) next(err);

        //Listar amigos
        daoF.getFullNameFriendsList(user.email, (err, friendList) => {
            if(err) next(err);

            response.render("friends", { user: user, listFriendsRequests, friendList});
        });
    });
});

//-------- Solicitudes de amistad
//---------------- Aceptar amistad
app.post("/acceptFriend", logged, (request, response, next) => {

    let user = response.locals.user;
    let emailOrig = request.body.friendAccept;

    daoF.acceptFriend(emailOrig, user.email, (err) => {
        if(err) next(err);

        //añadir aviso de peticion aceptada a emailOrig
        response.redirect("/friends");
    });
});

//---------------- Rechazar amistad

app.post("/rejectFriend", logged, (request, response, next) => {
    
    let user = response.locals.user;
    console.log(request.params);
    console.log(request.query);

    let emailOrig = request.body.friendReject;

    daoF.rejectFriend(emailOrig, user.email, (err) => {
        if(err) next(err);
        //añadir aviso de peticion rechazada a emailOrig
        response.redirect("/friends");
    });
});

//-------- Busqueda de amigos
app.post("/friends", logged, (request, response, next) => {

    let user = response.locals.user;
    response.locals.nameSearch = request.body.userName;
    
    //Busqueda
    daoU.getUsersNotFriendsListByName(user, response.locals.nameSearch, (err, list) => {
        if(err) next(err);
        
        response.render("searchFriend", { user: user, list });
    });

});

app.get("/searchFriend", logged, (request, response, next) => {

    let user = response.locals.user;
    let nameSearch = response.locals.nameSearch;

    if(nameSearch !== null && nameSearch !== undefined) {
        daoU.getUsersNotFriendsListByName(user, nameSearch, (err, list) => {
            if(err) next(err);
            
            response.render("searchFriend", { user: user, list });
        });
    }
    else
        response.render("searchFriend", { user: user, list: null });
});

app.get("/friendRequest/:email", logged, (request, response, next) => {

    let user = response.locals.user;
    let emailDest = request.params.email;

    daoF.friendRequest(user.email, emailDest, (err) => {
        if(err) next(err);
        //peticion enviada
        response.redirect("/searchFriend");
    });
});

app.get("/profile/:email", logged, (request, response, next) => {

    let email = request.params.email;
    let user = response.locals.user;

    daoU.getUser(email, (err, userProfile) => {
        if(err) next(err);

        if(userProfile !== undefined) {
            let edad = getEdad(userProfile.birthday);

            response.render("myProfile", { user: user, edad: edad, userProfile: userProfile });
        }
        else {
            response.redirect("/friends");
        }
    });
});

//---- Desconectar
app.get("/logout", logged, (request, response, next) => {
    response.locals.user = null;
    request.session.destroy();
    response.redirect("/login");
});

//-------- Servidor --------//
app.listen(config.port, function (err) {
    console.log(`Servidor escuchando en puerto ${config.port}.`);
});
//--------------------------//