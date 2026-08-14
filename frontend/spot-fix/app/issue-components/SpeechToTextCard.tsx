"use client";

import { useRef, useState } from "react";
import {
    FiMic,
    FiTrash2,
    FiSquare,
} from "react-icons/fi";

import "../issue-components/css/SpeechToTextCard.css";

interface Props {
    onSendSpeech?: (text: string) => void;
}

export default function SpeechToTextCard({ onSendSpeech }: Props) {
    const recognitionRef = useRef<any>(null);

    const [text, setText] = useState("");
    const [isRecording, setIsRecording] = useState(false);

    const startListening = () => {
        const SpeechRecognition =
            (window as any).SpeechRecognition ||
            (window as any).webkitSpeechRecognition;

        if (!SpeechRecognition) {
            alert("Speech Recognition is not supported in this browser.");
            return;
        }

        const recognition = new SpeechRecognition();

        recognition.lang = "ml-IN";
        recognition.continuous = true;
        recognition.interimResults = true;

        recognition.start();

        setIsRecording(true);

        recognition.onresult = (event: any) => {
            let transcript = "";

            for (let i = 0; i < event.results.length; i++) {
                transcript += event.results[i][0].transcript + " ";
            }

            setText(transcript);

            if (onSendSpeech) {
                onSendSpeech(transcript);
            }
        };

        recognition.onend = () => {
            setIsRecording(false);
        };

        recognition.onerror = () => {
            setIsRecording(false);
        };

        recognitionRef.current = recognition;
    };

    const stopListening = () => {
        recognitionRef.current?.stop();
        setIsRecording(false);
    };

    const clearText = () => {
        recognitionRef.current?.abort();
        setText("");
        setIsRecording(false);

        if (onSendSpeech) {
            onSendSpeech("");
        }
    };

    return (
        <div className="speech-card">

            {/* Header */}

            <div className="speech-header">

                <div className="speech-title">
                    <FiMic className="title-icon" />
                    <h2>Describe the Issue</h2>
                </div>

                <button
                    className="clear-btn"
                    onClick={clearText}
                >
                    <FiTrash2 />
                    Clear
                </button>

            </div>

            {/* Text Area */}

            <textarea
                className="text-input"
                placeholder="Speak or type to describe the issue..."
                value={text}
                maxLength={1000}
                onChange={(e) => {
                    setText(e.target.value);

                    if (onSendSpeech) {
                        onSendSpeech(e.target.value);
                    }
                }}
            />

            <div className="character-count">
                {text.length}/1000
            </div>

            {/* Buttons */}

            <div className="button-row">

                <button
                    className="record-btn"
                    onClick={startListening}
                    disabled={isRecording}
                >
                    <FiMic />
                    Start Recording
                </button>

                <button
                    className="stop-btn"
                    onClick={stopListening}
                    disabled={!isRecording}
                >
                    <FiSquare />
                    Stop Recording
                </button>

            </div>

        </div>
    );
}