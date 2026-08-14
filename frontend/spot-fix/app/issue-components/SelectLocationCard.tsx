"use client";

import { useState } from "react";
import type { LocationDTO } from "@/src/app/shared/api/models/LocationDTO";
import {
    FiMapPin,
    FiNavigation,
    FiChevronRight,
    FiSearch
} from "react-icons/fi";
import "../issue-components/css/SelectLocationCard.css";

interface SelectLocationCardProps {

  onSendLocation: (location: LocationDTO) => void;

}

export default function SelectLocationCard({ onSendLocation }: SelectLocationCardProps) {
  const [location, setSelectLocation] = useState("");
  const [myLocation, setMyLocation] = useState<LocationDTO | null>(null);
  const [results, setResults] = useState<any[]>([]);

  const searchLocation = async (query: string) => {
    const fixedQuery = `${query}, Palakkad, Kerala, India`;
    const res = await fetch(
      `https://nominatim.openstreetmap.org/search?format=json&q=${encodeURIComponent(fixedQuery)}&countrycodes=in`
    );
    const data = await res.json();
    setResults(data);
  };

  const handleSelectLocation = (event: React.FormEvent) => {
    event.preventDefault();
    if (!location.trim()) return;
    searchLocation(location);
  };

  const selectLocationEvent = (place: any) => {
    const locationDTO: LocationDTO = {
      addressText: place.display_name,
      latitude: Number(place.lat),
      longitude: Number(place.lon),
      landmark: "",
    };

    setMyLocation(locationDTO);

    if (onSendLocation) {
      onSendLocation(locationDTO);
    }
  };

  return (
    <div className="location-card">
      <div className="location-header">
        <FiMapPin className="header-icon"/>
        <div>
          <h2>Location</h2>
          <p>Set the issue location</p>
        </div>
      </div>

      <form className="location-form" onSubmit={handleSelectLocation}>
        <div className="search-box">
          <FiSearch className="search-icon"/>
          <input
            className="location-input"
            type="text"
            placeholder="Search location"
            value={location}
            onChange={(event)=>setSelectLocation(event.target.value)}
          />
          <button type="submit" className="search-btn">
            Search
          </button>
        </div>
      </form>

      <div className="result-container">
        {results.map((place)=>(
          <div
            key={place.place_id}
            className="result-item"
            onClick={()=>selectLocationEvent(place)}
          >
            <div className="result-left">
              <FiNavigation className="location-icon"/>
              <span>{place.display_name}</span>
            </div>
            <FiChevronRight className="arrow-icon"/>
          </div>
        ))}
      </div>

      {myLocation && (
        <div className="selected-location">
          <h4>Selected Location</h4>
          <p>{myLocation.addressText}</p>
        </div>
      )}
    </div>
  );
}
