"use client";

import { useState } from "react";
import { FiImage, FiTrash2 } from "react-icons/fi";
import "../issue-components/css/AttachmentsCard.css";

export default function AttachmentsCard({ onSendAttachments }: { onSendAttachments: (file: File | null) => void }) {
    const [file, setFile] = useState<File | null>(null);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const selectedFile = e.target.files?.[0] || null;
        setFile(selectedFile);
        onSendAttachments(selectedFile); // Immediately pass to parent page
    };

    const handleClear = () => {
        setFile(null);
        onSendAttachments(null);
    };

    return (
        <div className="attachment-card">
            <input
                id="fileInput"
                type="file"
                className="file-input"
                accept="image/*, .heic, .heif, .avif, .webp, .pdf, .doc, .docx"
                onChange={handleFileChange}
            />

            <label htmlFor="fileInput" className="upload-box">
                <FiImage className="upload-icon" />
                <h3>Add Photo</h3>
                <p>{file ? file.name : "Tap to choose from gallery"}</p>
                <small>JPG, PNG up to 10MB</small>
            </label>

            {file && (
                <div className="button-group">
                    <button type="button" className="clear-btn" onClick={handleClear}>
                        <FiTrash2 /> Clear Selection
                    </button>
                </div>
            )}
        </div>
    );
}