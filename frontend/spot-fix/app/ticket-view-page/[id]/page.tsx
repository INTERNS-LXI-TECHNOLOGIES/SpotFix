"use client"
import {useState}  from "react";


import {useParams} from "next/navigation";

export default function TicketView(){

   const params = useParams();





    return(

        <div>
<h2>Ticket ID : {params.id}</h2>

<TicketViewCard/>

        </div>


    );






}

