"use client";

import { useRef, useState } from "react";
import Webcam from "react-webcam";
import { FiCamera, FiRefreshCw, FiCheck } from "react-icons/fi";
import "./css/CameraCard.css";

export default function CameraCard({ onSendAttachemnts }: { onSendAttachemnts: (file: File | null) => void }) {
    const webcamRef = useRef<Webcam>(null);
    const [image, setImage] = useState<string | null>(null);
    const [showCamera, setShowCamera] = useState(false);

    const capturePhoto = async () => {
        const photo = webcamRef.current?.getScreenshot();

        if (photo) {
            setImage(photo);
            setShowCamera(false);

            // Convert Base64 dataURL to a File object
            const res = await fetch(photo);
            const blob = await res.blob();
            const file = new File([blob], `camera_${Date.now()}.jpg`, { type: "image/jpeg" });

            // Send file to IssueRaise state
            onSendAttachemnts(file);
        }
    };

    const handleRetake = () => {
        setImage(null);
        setShowCamera(true);
        onSendAttachemnts(null);
    };

    return (
        <div className="camera-card">
            {!showCamera && !image && (
                <button className="camera-btn" onClick={() => setShowCamera(true)}>
                    <FiCamera size={20} /> Open Camera
                </button>
            )}

            {showCamera && (
                <div className="camera-preview">
                    <Webcam
                        ref={webcamRef}
                        audio={false}
                        screenshotFormat="image/jpeg"
                        className="webcam"
                    />
                    <button className="capture-btn" onClick={capturePhoto}>
                        <FiCamera size={18} /> Capture
                    </button>
                </div>
            )}

            {image && (
                <div className="image-preview">
                    <img src={image} alt="Captured" className="captured-image" />
                    <p className="success-text">
                        <FiCheck /> Photo Attached
                    </p>
                    <button className="retake-btn" onClick={handleRetake}>
                        <FiRefreshCw /> Retake
                    </button>
                </div>
            )}
        </div>
    );
}