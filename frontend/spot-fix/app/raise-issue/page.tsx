"use client";

import { useState, useEffect } from "react";
import SelectLocationCard from "../issue-components/SelectLocationCard";
import SpeechToTextCard from "../issue-components/SpeechToTextCard";
import AttachmentsCard from "../issue-components/AttachmentsCard";
import CameraCard from "../issue-components/CameraCard";
import { TicketResourceApi, LocationResourceApi ,AttachmentResourceApi} from "@/src/app/shared/api";
import type { LocationDTO } from "@/src/app/shared/api/models/LocationDTO";
import type {AttachmentDto} from "@/src/app/shared/api/models/AttachmentDTO";

import "./IssueRaise.css";




const convertFileToBase64 = (file: File): Promise<string> => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      const result = reader.result as string;
      // Extract only the raw base64 string
      const base64Clean = result.split(",")[1] || result;
      resolve(base64Clean);
    };
    reader.onerror = (error) => reject(error);
  });
};




export default function IssueRaise() {
  const [speech, setSpeech] = useState("");
  const [selectedLocation, setSelectedLocation] = useState<LocationDTO | undefined>(undefined);
  const [attachments, setAttachments] = useState<File | null>(null);
  const [showMessage, setShowMessage] = useState(true);
  const [isSavingLocation, setIsSavingLocation] = useState(false);

  const locationApi = new LocationResourceApi();
  const ticketApi = new TicketResourceApi();
  const attachmentsApi = new AttachmentResourceApi();

  const eventSpeech = (mySpeech: string) => {
    setSpeech(mySpeech);
  };

  const eventSelectLocation = async (mySelectLocation: LocationDTO) => {
    try {
      setIsSavingLocation(true);
      if (!mySelectLocation.id) {
        const savedLoc = await locationApi.createLocation({
          locationDTO: {
            addressText: mySelectLocation.addressText || "Selected Location",
            latitude: mySelectLocation.latitude,
            longitude: mySelectLocation.longitude,
            landmark: mySelectLocation.landmark,
          },
        });
        setSelectedLocation(savedLoc);
      } else {
        setSelectedLocation(mySelectLocation);
      }
    } catch (err) {
      console.error("Failed to save location to DB:", err);
      alert("Error saving location. Please try again.");
    } finally {
      setIsSavingLocation(false);
    }
  };



  // now working # attachments

  const eventAttachments = (myAttachments: any) => {
    setAttachments(myAttachments);

    console.log(attachments);

  };





  useEffect(() => {
    const timer = setTimeout(() => {
      setShowMessage(false);
    }, 5000);

    return () => clearTimeout(timer);
  }, []);

  const locationSelect = () => {
    if (!navigator.geolocation) {
      alert("Geolocation is not supported by your browser.");
      return;
    }

    setIsSavingLocation(true);
    navigator.geolocation.getCurrentPosition(
        async (position) => {
          try {
            const lat = position.coords.latitude;
            const lng = position.coords.longitude;

            const savedLoc = await locationApi.createLocation({
              locationDTO: {
                addressText: `GPS Point (${lat.toFixed(4)}, ${lng.toFixed(4)})`,
                latitude: lat,
                longitude: lng,
              },
            });

            setSelectedLocation(savedLoc);
            alert("Current location saved successfully!");
          } catch (error) {
            console.error("Failed to store GPS location in DB:", error);
            alert("Could not save location to database.");
          } finally {
            setIsSavingLocation(false);
          }
        },
        (error) => {
          console.error("Location access error:", error);
          setIsSavingLocation(false);
          alert("Failed to access GPS location.");
        }
    );
  };

  const ticketAndAttachmentsCreation = async () => {
    if (!selectedLocation || !selectedLocation.id) {
      alert("Please select and save a location first.");
      return;
    }

    try {
      // 1. Create Ticket
      const createdTicket = await ticketApi.createTicket({

        ticketDTO: {
          title: speech.slice(0, 50) || "Untitled Issue",
          description: speech,
          location: { id: selectedLocation.id },
          status: "OPEN",
          priority: "MEDIUM",
          visibility: "PUBLIC",
          category: "OTHER",
          createdDate: new Date(),
          deleted: false,
          reportedBy: { id: 1 },
          ward: { id: 1500 },
          assignedDepartment:{id:1500},
        },
      });

      console.log("Ticket created:", createdTicket);

      // 2. Create Attachment (If selected)
      if (attachments && createdTicket?.id) {
        const base64Data = await convertFileToBase64(attachments);

        const attachmentPayload: AttachmentDTO = {
          id: null,
          attachmentType: attachments.type.startsWith("image/") ? "IMAGE" : "DOCUMENT",
          fileName: attachments.name,
          filePath: base64Data,
          fileType: attachments.type,
          fileSize: attachments.size,
          checksum: null,
          uploadedDate: new Date(),
          transcript: null,
          durationSeconds: null,
          language: "en",
          deleted: false,
          updatedDate: null,
          deletedDate: null,
          ticket: createdTicket, // Full TicketDTO pass ചെയ്യുക
          uploadedBy: createdTicket.reportedBy || ({ id: 1 } as any), // Safe UserDTO fallback
        };

        await attachmentsApi.createAttachment({
          attachmentDTO: attachmentPayload,
        });

        console.log("Attachment created successfully!");
      }

      alert("Ticket & Attachment successfully created!");
      setAttachments(null);
      setSpeech("");
    } catch (error: any) {
      console.error("Full API Error:", error);
      alert("Failed to process request. Check network or file size.");
    }
  };



  return (
      <div className="page-overlay">
        <div className="mobile-frame">
          {/* Top Header Bar */}
          <div className="top-header">
            <h1 className="header-title">♻️ Spot Fix</h1>
            <div className="header-icons">
              <span>❤️</span>
              <span>✈️</span>
            </div>
          </div>

          {/* Content Body */}
          <div className="content-body">
            {showMessage && (
                <div className="welcome-message">
                  🗣️ Hello! Welcome to <strong>Fix My Area</strong>. Please tell me the complaint you would like to register.
                </div>
            )}

            <div className="cards-container">
              <div className="card-item">
                <SpeechToTextCard onSendSpeech={eventSpeech} />
              </div>

              <div className="card-item">
                <AttachmentsCard onSendAttachments={eventAttachments} />
              </div>

              <div className="card-item">
                <CameraCard onSendAttachemnts={eventAttachments} />
              </div>

              <div className="card-item">
                <SelectLocationCard onSendLocation={eventSelectLocation} />
              </div>
            </div>
          </div>

          {/* Actions & Buttons Footer */}
          <div className="footer-actions">
            <button
                onClick={locationSelect}
                disabled={isSavingLocation}
                className="btn-location"
            >
              {isSavingLocation ? "Saving Location..." : "📍 Use Current Location"}
            </button>
            <button
                onClick={() => {
                  ticketAndAttachmentsCreation();
                }}
                disabled={!selectedLocation?.id || isSavingLocation}
                className="btn-submit"
            >
              🚀 Create Ticket
            </button>

            {selectedLocation?.id && (
                <p className="success-text">
                  ✅ Location saved successfully (ID: {selectedLocation.id})
                </p>
            )}
          </div>
        </div>
      </div>
  );
}