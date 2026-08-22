import TicketViewCard from "./TicketViewCard";
import { TicketResourceApi } from "@/src/app/shared/api";

interface PageProps {
    params: Promise<{ id: string }>;
}

export default async function TicketView({ params }: PageProps) {
    const { id } = await params;
    const ticketApi = new TicketResourceApi();
    const numericId = Number(id);

    let ticketData = null;

    try {
        // getTicket returns the TicketDTO directly
        ticketData = await ticketApi.getTicket({ id: numericId });
    } catch (error) {
        console.error("Failed to fetch the Ticket:", error);
    }

    return (
        <div>
            <TicketViewCard ticket={ticketData} />
        </div>
    );
}