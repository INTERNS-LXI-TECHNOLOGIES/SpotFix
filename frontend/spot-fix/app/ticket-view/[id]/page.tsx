"use client"

import {useState} from "react";
import TicketViewCard from "./TicketViewCard";

export default function TicketView(){

const[ticket,setTicket] = useState<number>(0);


const  ticketViewEvent = (num: number) =>{


setTicket(num);

console.log("Clicked : ",num);


    }


    return(


        <div>

<TicketViewCard   onSend={ticketViewEvent}  />


        </div>






    );







}