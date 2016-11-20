<%-- 
    Document   : login
    Created on : 17.11.2016, 21:55:36
    Author     : tukal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <script id="ws" >
            var url = "ws://localhost:8080/Customers-1.0-SNAPSHOT/login";
            var webSocket = new WebSocket(url);
            var loginField = document.getElementById('login');
            var passField = document.getElementById('pass');
            var sessionId = "";
            var api_token = getCookie("api_token");
            
            webSocket.onmessage = function(event) {
                var answer = JSON.parse(event.data);
                sessionId = answer.sequence_id;
                var type = answer.type;
                print("MSG: " + type);
                switch(type) {
                    case "VOID":
                        print("VOID");
                        if (api_token != null) loginIn();
                        break;
                    case "CUSTOMER_API_TOKEN":
                        api_token = answer.data.api_token
                        print(api_token);
                        setCookie("api_token", api_token);
                        loginIn();
                        document.location.href = "http://localhost:8080/Customers-1.0-SNAPSHOT/user"
                        break;
                    case "TOKEN_PERIOD_IS_OVER":
                        error("Please, log in");
                        break;
                    case "USER_DOES_NOT_EXISTS":
                        error("User does not exists");
                        break;
                    case "AUTH_SUCCESS":
                        document.location.href = "http://localhost:8080/Customers-1.0-SNAPSHOT/user";
                        break;
                    default:
                        print(JSON.stringify(answer));
                        break;
                };
            };
            
            function print(value) {
                //document.getElementById('output').innerHTML += value + "<br>";
            }
            
            function error(value) {
                document.getElementById('error').innerHTML = value;
            }
            
            class Login {
                constructor(login, pass) {
                    this.login = login;
                    this.pass = pass;
                }
            }

            webSocket.onclose = function (event) {
                print("server is down")
            };
            
            function setCookie(field, value) {
                var c_date = new Date();
                c_date = c_date.setTime(c_date.getTime() + 1000 * 60);
                document.cookie = field + "=" + value + ";expires=" + c_date;
            }

            function getCookie(cookieName) {
                var results = document.cookie.match('(^|;) ?' + cookieName + '=([^;]*)(;|$)');
                if (results)
                    return (results[2]);
                else
                    return null;
            }

            function loginIn() {
                try {
                    var login = new Login(document.getElementById('login').value,document.getElementById('pass').value);
                    setCookie("login",login.login);
                } catch(e) {
                    var login = new Login(getCookie("login"),"");
                }
                if (api_token == null) api_token = "";
                if (login.pass != "") api_token = "";
                event = {
                    type: "CUSTOMER_LOGIN",
                    sequence_id: sessionId,
                    api_token: api_token,
                    data: login
                }
                webSocket.send(JSON.stringify(event));
            };
        </script>
    </head>
    <body>
        <div id ="loginForm">
            <form>
                <p>login: <input id="login" value="login"></p>
                <p>password:<input id="pass" type="password" onsubmit="loginIn()"></p>
                <p><input type="button" value="login" onclick="loginIn()"></p>
            </form>
        </div>
        <div id="error"></div>
        <div id="output"></div>
    </body>
</html>
