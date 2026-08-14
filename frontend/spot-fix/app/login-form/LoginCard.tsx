"use client"

import {useState} from "react";
import {useRouter} from "next/navigation";
import style from "./LoginCard.module.css";

export default function LoginCard({onSend}:any){
const router=useRouter();
const[username,setUserName] = useState("");
const[password,setPassword] = useState("");

const handleGoogleLogin = () => {
    console.log("Google login clicked");
};

const handleLogin=(username:string,password:string) => {
    if(username.trim() === "" || password.trim() === ""){
        alert("please enter username and password");
        return;
    }
    alert("login scuccessful");
    router.push("./home-page");
}

    return(

<div className ={style.container}>

<div className = {style.logo}>Smart Civic</div>
    <form  onSubmit = {(e) => {
             e.preventDefault();
        handleLogin(username,password);
        }}>
    <input className ={style.input} type ="text"  name = "username"  placeholder ="enter username" onChange={(e) => setUserName(e.target.value)} />

    <input className ={style.input} type = "password" name = "password" placeholder= "enter Password" onChange={(e) =>setPassword(e.target.value)}/>

    <input className={style.loginBtn} type = "submit" value = "Enter" />
    <input className={style.resetBtn} type = "reset" value = "Clear" />


<div className ={style.or}>OR</div>
<a
    href="http://localhost:8080/oauth2/authorization/google"
    className={style.google}
    onClick={handleGoogleLogin}
>
    Continue with Google
</a>

    <a href ="./signup-page" className={style.signup}>Sign up</a>
    <a href ="./home-page" className={style.home}>Home</a>
    </form>

</div>
        );






    }