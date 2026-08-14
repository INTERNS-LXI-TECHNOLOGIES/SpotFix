"use client"

import {useState} from "react";


export default function TicketViewCard({onSend}:any){


    const[ticket,setTicket] = useState<number>(0);



    return(

        <div>

<button onClick={() => onSend} > View</button>


    </div>

    );




}