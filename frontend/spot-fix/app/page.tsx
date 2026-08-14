"use client";

import HomePageCard from "./home-page/HomePageCard";
import { TicketResourceApi } from "../src/app/shared/api/apis";
import { TicketDto } from "../src/api/docs";
import { useState, useEffect, useCallback } from "react";

const ticketApi = new TicketResourceApi();

export default function HomePage() {
    const [tickets, setTickets] = useState<TicketDto[]>([]);

    const fetchTickets = useCallback(async () => {
        try {
            const ticketPosts: TicketDto[] = await ticketApi.getAllTickets();
            setTickets(ticketPosts || []);
        } catch (err) {
            console.error("Failed to fetch Data : ", err);
        }
    }, []);

    useEffect(() => {
        fetchTickets();
    }, [fetchTickets]);

    return (
        <div>
            <HomePageCard onSend={tickets} />
        </div>
    );
}