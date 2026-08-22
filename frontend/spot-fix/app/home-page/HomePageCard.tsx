"use client";

import React, { useState, useEffect, useRef, useCallback } from "react";
import Link from "next/link";
import { Oleo_Script } from "next/font/google";
import style from "./DashboardLayout.module.css";
import TicketViewCard from "@/app/ticket-view/[id]/TicketViewCard";

import {
  FiHome,
  FiSearch,
  FiHeart,
  FiShare2,
  FiMessageSquare,
  FiLogIn
} from "react-icons/fi";

const oleoScript = Oleo_Script({
  weight: "400",
  subsets: ["latin"],
});

interface DashboardLayoutProps {
  onSend?: any[];
}

function FeedCard({ post }: { post: any }) {

  const  ticketId = post.id || post.ticketId;

  return (

    <article className={style.postCard}>
      <div className={style.cardContent}>
        <div className={style.postText}>
          <h3>{post.title || post.complaintType}</h3>
          <p className={style.cardDesc}>{post.desc || post.description}</p>
        </div>

        <div className={style.photoContainer}>
          <div className={style.imagePlaceholder}>🖼️</div>
        </div>
      </div>

      <div className={style.postActions}>
        <button aria-label="Like"><FiHeart /></button>
        <button aria-label="Comment"><FiMessageSquare /></button>
        <button aria-label="Share"><FiShare2 /></button>

        <Link href={`/ticket-view/${ticketId}`}>View</Link>
      </div>

    </article>

  );
}

const EMPTY_POSTS: any[] = [];
export default function DashboardLayout({ onSend = EMPTY_POSTS }: DashboardLayoutProps) {
  const [showSettings, setShowSettings] = useState(false);
  const PAGE_SIZE = 3;

  const [posts, setPosts] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);

  const pageRef = useRef(1);
  const loadingRef = useRef(false);
  const hasMoreRef = useRef(true);
  const onSendRef = useRef(onSend);
  const loaderRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    onSendRef.current = onSend;
  }, [onSend]);

  // Synchronize props when onSend changes initially
  useEffect(() => {
    if (Array.isArray(onSend) && onSend.length > 0) {
      setPosts(onSend.slice(0, PAGE_SIZE));
      const moreAvailable = onSend.length > PAGE_SIZE;
      setHasMore(moreAvailable);
      hasMoreRef.current = moreAvailable;
      pageRef.current = 1;
    } else {
      setPosts([]);
      setHasMore(false);
      hasMoreRef.current = false;
    }
  }, [onSend]);

  const fetchMorePosts = useCallback(() => {
    const data = onSendRef.current;
    if (loadingRef.current || !hasMoreRef.current || !Array.isArray(data)) return;

    loadingRef.current = true;
    setLoading(true);

    setTimeout(() => {
      const currentPage = pageRef.current;
      const start = currentPage * PAGE_SIZE;
      const end = start + PAGE_SIZE;
      const nextPosts = data.slice(start, end);

      if (nextPosts.length === 0) {

        setHasMore(false);
        hasMoreRef.current = false;
        setLoading(false);
        loadingRef.current = false;
        return;

      }

      setPosts((prev) => [...prev, ...nextPosts]);
      pageRef.current = currentPage + 1;

      if (start + PAGE_SIZE >= data.length) {

        setHasMore(false);
        hasMoreRef.current = false;

      }

      setLoading(false);
      loadingRef.current = false;
    }, 500);
  }, []);

  useEffect(() => {
    const target = loaderRef.current;
    if (!target) return;

    const observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting && hasMoreRef.current && !loadingRef.current) {
          fetchMorePosts();
        }
      },
      {
        root: null, // Default to browser viewport for standard scroll detection
        rootMargin: "100px",
        threshold: 0.1,
      }
    );

    observer.observe(target);
    return () => observer.disconnect();
  }, [fetchMorePosts, posts.length]); // Re-subscribe when post count updates

  return (
    <div className={style.mobileWrapper}>
      <div className={style.mobileScreen}>
        {/* Top App Bar */}
        <header className={style.appHeader}>
          <h1 className={`${oleoScript.className} ${style.brandTitle}`}>
            ♻️ Spot Fix
          </h1>
          <div className={style.headerIcons}>
            <button
              className={style.iconBtn}
              aria-label="Favorites"
            >
              ❤️
            </button>

            <div className={style.settingsWrapper}>
              <button
                type="button"
                className={style.settingsButton}
                onClick={() => setShowSettings(prev => !prev)}
              >
                ⚙️ Settings
              </button>

              {showSettings && (
                <div className={style.settingsMenu}>
                  <Link href="/admin-dashboard">
                    🛠️ Admin Dashboard
                  </Link>

                  <Link href="/user-profile">
                    👤 User Profile
                  </Link>

                  <Link href="/settings-page">
                    ⚙️ Account Settings
                  </Link>
                </div>
              )}
            </div>
          </div>
        </header>

        {/* Complaints Feed List */}
        <main className={style.feedContainer}>
          <div className={style.feedList}>
            {posts.map((post, index) => (
              <FeedCard key={post.id || index} post={post} />
            ))}
          </div>

          <div ref={loaderRef} className={style.loadingIndicator}>
            {loading && <p>Loading more...</p>}
            {!hasMore && posts.length > 0 && <p>No more complaints</p>}
          </div>
        </main>

        {/* Floating Action Button */}
        <Link href="/raise-issue" className={style.fabButton}>
          +
        </Link>

        {/* Bottom Navigation */}
        <nav className={style.bottomNav}>
          <Link href="/dashboard" className={`${style.navItem} ${style.active}`}>
            <span><FiHome /></span> HOME
          </Link>
          <Link href="/search" className={style.navItem}>
            <span><FiSearch /></span> SEARCH
          </Link>
          <Link href="/login-form" className={style.navItem}>
            <span><FiLogIn /></span> User Profile
          </Link>
        </nav>
      </div>
    </div>
  );
}
