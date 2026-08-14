"use client";

import React, { useState } from "react";
import "./SignUpPage.css";

export default function SignUpCard() {
    const [formData, setFormData] = useState({
        username: "",
        fullName: "",      
        email: "",
        phone: "",
        address: "",
        bio: "",
        password: "",
        confirmPassword: "",
    });

    const [profilePic, setProfilePic] = useState<File | null>(null);

    const handleFileChange = (
        e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
    ) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (formData.password !== formData.confirmPassword) {
            alert("Passwords do not match");
            return;
        }

        const registerRequest = {
            username: formData.username,
            fullName: formData.fullName,
            email: formData.email,
            phone: formData.phone,
            address: formData.address,
            bio: formData.bio,
            password: formData.password,
        };

        console.log(registerRequest);
    };

    return (
        <div className="signup-container">
            <form className="signup-form" onSubmit={handleSubmit}>
                <h2>Sign Up</h2>

                <label>First Name</label>
                <input
                    type="text"
                    name="username"
                    value={formData.username}
                    onChange={handleFileChange}
                    required
                />

                <label>Last Name</label>
                <input
                    type="text"
                    name="fullName"
                    value={formData.fullName}
                    onChange={handleFileChange}
                    required
                />

                <label>Email</label>
                <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleFileChange}
                    required
                />

                <label>Phone Number</label>
                <input
                    type="text"
                    name="phone"
                    value={formData.phone}
                    onChange={handleFileChange}
                    required
                />

                <label>Address</label>
                <textarea
                    name="address"
                    value={formData.address}
                    onChange={handleFileChange}
                />

                <label>Profile Picture</label>
                <input
                    type="file"
                    accept="image/*"
                    onChange={(e) =>
                        setProfilePic(e.target.files ? e.target.files[0] : null)
                    }
                />

                <label>Bio</label>
                <textarea
                    name="bio"
                    value={formData.bio}
                    onChange={handleFileChange}
                />

                <label>Password</label>
                <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleFileChange}
                    required
                />

                <label>Confirm Password</label>
                <input
                    type="password"
                    name="confirmPassword"
                    value={formData.confirmPassword}
                    onChange={handleFileChange}
                    required
                />

                <button type="submit">Sign Up</button>

                <a href="./login-form">Back To Login Page</a>
            </form>
        </div>
    );
}