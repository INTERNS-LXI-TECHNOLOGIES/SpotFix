"use client";

import styles from "./TicketViewCard.module.css";
import {
    Eye,
    Droplet,
    RotateCw,
    Tag,
    Folder,
    Globe,
    MapPin,
    Building2,
    User,
    Calendar
} from "lucide-react";

interface TicketViewCardProps {
    ticket?: any;
    onSend?: (id: number) => void;
}

export default function TicketViewCard({ ticket, onSend }: TicketViewCardProps) {
    return (
        <div className={styles.cardContainer}>
            <div className={styles.card}>
                <div className={styles.header}>
                    <span className={styles.ticketId}>Ticket Details #{ticket?.id ?? "N/A"}</span>
                </div>

                <h2 className={styles.title}>{ticket?.title || "No Title Provided"}</h2>

                <div className={styles.descriptionBox}>
                    <div className={styles.descriptionHeader}>
                        <Droplet size={14} color="#60a5fa" />
                        <span>Description</span>
                    </div>
                    <p className={styles.descriptionText}>{ticket?.description || "No description available."}</p>
                </div>

                <div className={styles.grid}>
                    <div className={styles.gridItem}>
                        <RotateCw size={16} />
                        <span className={styles.label}>Status:</span>
                        <span className={styles.badgeSuccess}>{ticket?.status || "OPEN"}</span>
                    </div>

                    <div className={styles.gridItem}>
                        <Tag size={16} />
                        <span className={styles.label}>Priority:</span>
                        <span className={styles.badgeWarning}>{ticket?.priority || "LOW"}</span>
                    </div>

                    <div className={styles.gridItem}>
                        <Folder size={16} />
                        <span className={styles.label}>Category:</span>
                        <span>{ticket?.category || "OTHER"}</span>
                    </div>

                    <div className={styles.gridItem}>
                        <Globe size={16} />
                        <span className={styles.label}>Visibility:</span>
                        <span>{ticket?.visibility || "PUBLIC"}</span>
                    </div>

                    <div className={styles.gridItem}>
                        <MapPin size={16} />
                        <span className={styles.label}>Location ID:</span>
                        <span>{ticket?.location?.id ?? ticket?.locationId ?? "N/A"}</span>
                    </div>

                    <div className={styles.gridItem}>
                        <Building2 size={16} />
                        <span className={styles.label}>Ward ID:</span>
                        <span>{ticket?.ward?.id ?? ticket?.wardId ?? "N/A"}</span>
                    </div>

                    <div className={styles.gridItem}>
                        <User size={16} />
                        <span className={styles.label}>Reported By:</span>
                        <span>{ticket?.reportedBy?.id ?? ticket?.reportedById ?? "N/A"}</span>
                    </div>

                    <div className={styles.gridItem}>
                        <Calendar size={16} />
                        <span className={styles.label}>Created Date:</span>
                        <span>
              {ticket?.createdDate ? new Date(ticket.createdDate).toLocaleDateString() : "N/A"}
            </span>
                    </div>
                </div>

                {onSend && (
                    <button className={styles.actionButton} onClick={() => onSend(ticket?.id)}>
                        <Eye size={18} />
                        <span>View Action</span>
                    </button>
                )}
            </div>
        </div>
    );
}