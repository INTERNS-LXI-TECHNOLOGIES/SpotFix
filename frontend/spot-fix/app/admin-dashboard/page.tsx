"use client";

import { useState, useEffect, useMemo } from "react";
import Link from "next/link";
import { Configuration, TicketResourceApi } from "@/src/app/shared/api";
import {
  TicketDTO,
  TicketDTOStatusEnum,
  TicketDTOPriorityEnum,
  TicketDTOCategoryEnum,
  TicketDTOVisibilityEnum,
} from "@/src/app/shared/api/models";
import INITIAL_POSTS from "../data-base/posts";

// Backend API configuration pointing to default local port
const config = new Configuration({
  basePath: "http://localhost:8081",
});
const ticketApi = new TicketResourceApi(config);

export default function AdminDashboard() {
  const [tickets, setTickets] = useState<TicketDTO[]>([]);
  const [selectedTicket, setSelectedTicket] = useState<TicketDTO | null>(null);
  const [isLiveMode, setIsLiveMode] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(true);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);

  // Search & Filter state
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedCategory, setSelectedCategory] = useState<string>("ALL");
  const [selectedPriority, setSelectedPriority] = useState<string>("ALL");
  const [selectedStatusTab, setSelectedStatusTab] = useState<string>("ALL");

  // Edit / Form state
  const [editStatus, setEditStatus] = useState<TicketDTOStatusEnum | "">("");
  const [editPriority, setEditPriority] = useState<TicketDTOPriorityEnum | "">("");
  const [editDeptId, setEditDeptId] = useState<number | "">("");
  const [isSaving, setIsSaving] = useState(false);
  const [saveSuccess, setSaveSuccess] = useState(false);

  // Load tickets on mount
  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = async () => {
    setLoading(true);
    setErrorMsg(null);
    try {
      // Attempt live fetch
      const apiTickets = await ticketApi.getAllTickets();
      if (apiTickets && Array.isArray(apiTickets)) {
        setTickets(apiTickets);
        setIsLiveMode(true);
      } else {
        throw new Error("Invalid API response format");
      }
    } catch (err: any) {
  console.error("❌ Ticket API failed:", err);

  if (err?.response) {
    console.error("Status:", err.response.status);
    console.error("Response:", err.response);
  }

  setErrorMsg("Failed to load tickets from backend");
  setIsLiveMode(false);
  setTickets([]);
    } finally {
      setLoading(false);
    }
  };

  // Setup mock tickets if backend is down
  const loadMockTickets = () => {
    const cached = localStorage.getItem("spotfix_admin_tickets");
    if (cached) {
      try {
        const parsed = JSON.parse(cached);
        // Revive Date objects
        const revived = parsed.map((t: any) => ({
          ...t,
          createdDate: new Date(t.createdDate),
          updatedDate: t.updatedDate ? new Date(t.updatedDate) : undefined,
        }));
        setTickets(revived);
        return;
      } catch (e) {
        console.error("Failed to parse cached tickets", e);
      }
    }

    // Transform initial posts to standard TicketDTO objects
    const mapped: TicketDTO[] = INITIAL_POSTS.map((post) => {
      let category: TicketDTOCategoryEnum = TicketDTOCategoryEnum.Other;
      const tagStr = post.tag.toUpperCase();
      if (tagStr.includes("STREET LIGHT")) category = TicketDTOCategoryEnum.StreetLight;
      else if (tagStr.includes("ROAD")) category = TicketDTOCategoryEnum.RoadDamage;
      else if (tagStr.includes("WATER")) category = TicketDTOCategoryEnum.WaterSupply;
      else if (tagStr.includes("GARBAGE")) category = TicketDTOCategoryEnum.Garbage;
      else if (tagStr.includes("DRAINAGE")) category = TicketDTOCategoryEnum.Drainage;
      else if (tagStr.includes("ELECTRICITY")) category = TicketDTOCategoryEnum.Electricity;
      else if (tagStr.includes("TREE")) category = TicketDTOCategoryEnum.Tree;

      // Make categories, statuses, and priorities distributed and interesting
      const statuses = [
        TicketDTOStatusEnum.Open,
        TicketDTOStatusEnum.UnderReview,
        TicketDTOStatusEnum.InProgress,
        TicketDTOStatusEnum.Resolved,
      ];
      const priorities = [
        TicketDTOPriorityEnum.Low,
        TicketDTOPriorityEnum.Medium,
        TicketDTOPriorityEnum.High,
        TicketDTOPriorityEnum.Urgent,
      ];

      const statusVal = statuses[post.id % statuses.length];
      const priorityVal = priorities[post.id % priorities.length];

      return {
        id: post.id,
        title: post.title,
        description: post.desc,
        status: statusVal,
        priority: priorityVal,
        category: category,
        visibility: TicketDTOVisibilityEnum.Public,
        createdDate: new Date(Date.now() - post.id * 2 * 3600 * 1000), // sequential older times
        deleted: false,
        reportedBy: { id: 100 + post.id, login: `citizen_${post.id}` },
        location: {
          addressText: `Near Landmark, Ward ${post.id % 5 + 1}`,
          latitude: 12.97 + post.id * 0.002,
          longitude: 77.59 + post.id * 0.002,
        },
        ward: {
          code: `W${post.id % 5 + 1}`,
          name: `Ward ${post.id % 5 + 1}`,
        },
      };
    });

    setTickets(mapped);
    localStorage.setItem("spotfix_admin_tickets", JSON.stringify(mapped));
  };

  // Sync state variables when a new ticket is selected
  useEffect(() => {
    if (selectedTicket) {
      setEditStatus(selectedTicket.status);
      setEditPriority(selectedTicket.priority);
      setEditDeptId(selectedTicket.assignedDepartment?.id || "");
      setSaveSuccess(false);
    }
  }, [selectedTicket]);

  // Handle saving changes
  const handleSaveChanges = async () => {
    if (!selectedTicket || !selectedTicket.id) return;
    setIsSaving(true);
    setSaveSuccess(false);

    const updatedFields = {
      status: editStatus as TicketDTOStatusEnum,
      priority: editPriority as TicketDTOPriorityEnum,
      assignedDepartment: editDeptId
        ? { id: Number(editDeptId), name: getDepartmentName(Number(editDeptId)), active: true }
        : undefined,
      updatedDate: new Date(),
    };

    if (isLiveMode) {
      try {
        const fullUpdatedTicket = {
          ...selectedTicket,
          ...updatedFields,
        } as TicketDTO;
        const result = await ticketApi.updateTicket({
          id: selectedTicket.id,
          ticketDTO: fullUpdatedTicket,
        });
        // Update local state list
        setTickets((prev) => prev.map((t) => (t.id === result.id ? result : t)));
        setSelectedTicket(result);
        setSaveSuccess(true);
      } catch (err) {
        console.error("Failed to update ticket on backend", err);
        alert("Failed to update ticket on the server. Keeping local updates.");
      }
    } else {
      // Offline LocalStorage path
      const revivedTickets = tickets.map((t) => {
        if (t.id === selectedTicket.id) {
          const updated = { ...t, ...updatedFields };
          setSelectedTicket(updated);
          return updated;
        }
        return t;
      });
      setTickets(revivedTickets);
      localStorage.setItem("spotfix_admin_tickets", JSON.stringify(revivedTickets));
      setSaveSuccess(true);
    }

    setIsSaving(false);
  };

  // Quick department mock mapping
  const getDepartmentName = (id: number) => {
    const depts: Record<number, string> = {
      1: "Electricity Board",
      2: "Water & Sewerage Dept",
      3: "Municipal Waste Management",
      4: "Road Works & Engineering",
      5: "Forest & Parks Authority",
    };
    return depts[id] || "General Administration";
  };

  // Process and filter tickets list
  const filteredTickets = useMemo(() => {
    return tickets.filter((t) => {
      // Search text query
      const matchesSearch =
        (t.title?.toLowerCase() || "").includes(searchQuery.toLowerCase()) ||
        (t.description?.toLowerCase() || "").includes(searchQuery.toLowerCase()) ||
        (t.id?.toString() || "") === searchQuery;

      // Category filter
      const matchesCategory = selectedCategory === "ALL" || t.category === selectedCategory;

      // Priority filter
      const matchesPriority = selectedPriority === "ALL" || t.priority === selectedPriority;

      // Status tab filter
      let matchesStatus = true;
      if (selectedStatusTab !== "ALL") {
        if (selectedStatusTab === "OPEN") {
          matchesStatus = t.status === TicketDTOStatusEnum.Open || t.status === TicketDTOStatusEnum.UnderReview;
        } else if (selectedStatusTab === "IN_PROGRESS") {
          matchesStatus = t.status === TicketDTOStatusEnum.InProgress || t.status === TicketDTOStatusEnum.Assigned;
        } else if (selectedStatusTab === "RESOLVED") {
          matchesStatus = t.status === TicketDTOStatusEnum.Resolved || t.status === TicketDTOStatusEnum.Closed;
        } else if (selectedStatusTab === "REJECTED") {
          matchesStatus = t.status === TicketDTOStatusEnum.Rejected;
        }
      }

      return matchesSearch && matchesCategory && matchesPriority && matchesStatus;
    });
  }, [tickets, searchQuery, selectedCategory, selectedPriority, selectedStatusTab]);

  // Statistics summaries
  const stats = useMemo(() => {
    const total = tickets.length;
    const open = tickets.filter(
      (t) => t.status === TicketDTOStatusEnum.Open || t.status === TicketDTOStatusEnum.UnderReview
    ).length;
    const inProgress = tickets.filter(
      (t) => t.status === TicketDTOStatusEnum.InProgress || t.status === TicketDTOStatusEnum.Assigned
    ).length;
    const resolved = tickets.filter(
      (t) => t.status === TicketDTOStatusEnum.Resolved || t.status === TicketDTOStatusEnum.Closed
    ).length;

    return { total, open, inProgress, resolved };
  }, [tickets]);

  // Style helper: Priority badge styling with glowing neons
  const getPriorityColor = (p: TicketDTOPriorityEnum | undefined) => {
    switch (p) {
      case TicketDTOPriorityEnum.Urgent:
        return "bg-rose-500/10 text-rose-400 border-rose-500/30 shadow-[0_0_12px_rgba(244,63,94,0.15)]";
      case TicketDTOPriorityEnum.High:
        return "bg-amber-500/10 text-amber-400 border-amber-500/30 shadow-[0_0_12px_rgba(245,158,11,0.15)]";
      case TicketDTOPriorityEnum.Medium:
        return "bg-sky-500/10 text-sky-400 border-sky-500/20";
      case TicketDTOPriorityEnum.Low:
      default:
        return "bg-slate-800/80 text-slate-400 border-slate-700/60";
    }
  };

  // Style helper: Status badge styling with glowing neons
  const getStatusColor = (s: TicketDTOStatusEnum | undefined) => {
    switch (s) {
      case TicketDTOStatusEnum.Open:
        return "bg-blue-500/10 text-blue-400 border-blue-500/30 shadow-[0_0_12px_rgba(59,130,246,0.15)]";
      case TicketDTOStatusEnum.UnderReview:
        return "bg-indigo-500/10 text-indigo-400 border-indigo-500/30 shadow-[0_0_12px_rgba(99,102,241,0.15)]";
      case TicketDTOStatusEnum.InProgress:
      case TicketDTOStatusEnum.Assigned:
        return "bg-amber-500/10 text-amber-400 border-amber-500/30 shadow-[0_0_12px_rgba(245,158,11,0.15)]";
      case TicketDTOStatusEnum.Resolved:
      case TicketDTOStatusEnum.Closed:
        return "bg-emerald-500/10 text-emerald-400 border-emerald-500/30 shadow-[0_0_12px_rgba(16,185,129,0.15)]";
      case TicketDTOStatusEnum.Rejected:
        return "bg-slate-800 text-slate-400 border-slate-700/50";
      default:
        return "bg-slate-800 text-slate-400 border-slate-700/50";
    }
  };

  // Convert category identifier to readable label
  const formatCategory = (cat: string | undefined) => {
    if (!cat) return "";
    return cat.replace("_", " ").toLowerCase().replace(/\b\w/g, (c) => c.toUpperCase());
  };

  return (
    <div className="min-h-screen bg-slate-950 font-sans text-slate-100 antialiased selection:bg-indigo-500/30">
      {/* Sleek Dark Header */}
      <header className="sticky top-0 z-20 border-b border-slate-900 bg-slate-950/70 backdrop-blur-md">
        <div className="mx-auto flex max-w-7xl items-center justify-between px-6 py-4">
          <div className="flex items-center gap-3.5">
            <h1 className="text-xl font-extrabold tracking-tight text-white flex items-center gap-2">
              <span className="bg-gradient-to-r from-indigo-500 to-teal-400 bg-clip-text text-transparent">SpotFix</span>
              <span className="font-light text-slate-400 text-sm border-l border-slate-800 pl-2">Admin Portal</span>
            </h1>
            <span
              className={`inline-flex items-center gap-1.5 rounded-full px-2.5 py-0.5 text-[11px] font-medium border ${
                isLiveMode
                  ? "bg-emerald-500/10 text-emerald-400 border-emerald-500/20 shadow-[0_0_10px_rgba(16,185,129,0.1)]"
                  : "bg-amber-500/10 text-amber-400 border-amber-500/20 shadow-[0_0_10px_rgba(245,158,11,0.1)]"
              }`}
            >
              <span className={`h-1.5 w-1.5 rounded-full ${isLiveMode ? "bg-emerald-400" : "bg-amber-400"}`} />
              {isLiveMode ? "API connected" : "Simulated Offline Mode"}
            </span>
          </div>

          <div className="flex items-center gap-4">
            <Link
              href="/home-page"
              className="text-xs font-semibold text-slate-400 hover:text-white border border-slate-800 hover:border-slate-700 bg-slate-900/30 px-3 py-1.5 rounded-lg transition-all"
            >
              Back to App
            </Link>
          </div>
        </div>
      </header>

      <main className="mx-auto max-w-7xl px-6 py-8">
        {/* KPI Stats Board with glowing color accent bars */}
        <section className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
          {[
            { label: "Total Reports", value: stats.total, glowColor: "border-t-indigo-500 shadow-[0_4px_25px_-10px_rgba(99,102,241,0.25)]", valueColor: "text-slate-100", desc: "All logged tickets" },
            { label: "Open & Pending", value: stats.open, glowColor: "border-t-blue-500 shadow-[0_4px_25px_-10px_rgba(59,130,246,0.25)]", valueColor: "text-blue-400", desc: "Requires inspection" },
            { label: "In Progress", value: stats.inProgress, glowColor: "border-t-amber-500 shadow-[0_4px_25px_-10px_rgba(245,158,11,0.25)]", valueColor: "text-amber-400", desc: "Work assigned/active" },
            { label: "Resolved / Closed", value: stats.resolved, glowColor: "border-t-emerald-500 shadow-[0_4px_25px_-10px_rgba(16,185,129,0.25)]", valueColor: "text-emerald-400", desc: "Resolved issues" },
          ].map((item, idx) => (
            <div
              key={idx}
              className={`rounded-xl border border-slate-800/80 bg-slate-900/30 p-5 border-t-2 ${item.glowColor} transition-all hover:bg-slate-900/40`}
            >
              <span className="text-[10px] font-bold uppercase tracking-wider text-slate-500">
                {item.label}
              </span>
              <p className={`mt-2 text-3xl font-extrabold tracking-tight ${item.valueColor}`}>{item.value}</p>
              <span className="mt-1.5 text-[11px] text-slate-500 block">{item.desc}</span>
            </div>
          ))}
        </section>

        {/* Filters and Controls Area */}
        <section className="rounded-xl border border-slate-900 bg-slate-900/10 p-5 backdrop-blur-md mb-6">
          <div className="flex flex-col gap-4 md:flex-row md:items-center md:justify-between">
            {/* Status tab list (Minimal tabs) */}
            <div className="flex flex-wrap gap-1 bg-slate-950/80 p-1 rounded-lg border border-slate-900 self-start">
              {[
                { id: "ALL", label: "All Tickets" },
                { id: "OPEN", label: "Open" },
                { id: "IN_PROGRESS", label: "In Progress" },
                { id: "RESOLVED", label: "Resolved" },
                { id: "REJECTED", label: "Rejected" },
              ].map((tab) => (
                <button
                  key={tab.id}
                  onClick={() => setSelectedStatusTab(tab.id)}
                  className={`rounded-md px-3.5 py-1.5 text-xs font-semibold transition-all ${
                    selectedStatusTab === tab.id
                      ? "bg-indigo-600 text-white shadow-lg shadow-indigo-600/20"
                      : "text-slate-400 hover:text-slate-200"
                  }`}
                >
                  {tab.label}
                </button>
              ))}
            </div>

            {/* Global Search box */}
            <div className="relative flex-1 max-w-sm">
              <svg
                className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-slate-500"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                strokeWidth={2.5}
              >
                <path strokeLinecap="round" strokeLinejoin="round" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
              <input
                type="text"
                placeholder="Search description, title or ID..."
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full rounded-lg border border-slate-900 bg-slate-950 py-2 pl-9 pr-4 text-xs text-white placeholder:text-slate-600 focus:border-indigo-500 focus:outline-none focus:ring-1 focus:ring-indigo-500/40 transition-all"
              />
              {searchQuery && (
                <button
                  onClick={() => setSearchQuery("")}
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-slate-500 hover:text-slate-300 text-xs"
                >
                  Clear
                </button>
              )}
            </div>
          </div>

          <div className="mt-4 pt-4 border-t border-slate-900/60 flex flex-wrap gap-4 items-center">
            {/* Category selection */}
            <div className="flex items-center gap-2">
              <label className="text-xs font-semibold text-slate-500">Category:</label>
              <select
                value={selectedCategory}
                onChange={(e) => setSelectedCategory(e.target.value)}
                className="rounded-lg border border-slate-900 bg-slate-950 px-3 py-1.5 text-xs text-slate-300 outline-none hover:border-slate-800 focus:border-indigo-500"
              >
                <option value="ALL">All Categories</option>
                {Object.values(TicketDTOCategoryEnum).map((c) => (
                  <option key={c} value={c}>
                    {formatCategory(c)}
                  </option>
                ))}
              </select>
            </div>

            {/* Priority selection */}
            <div className="flex items-center gap-2">
              <label className="text-xs font-semibold text-slate-500">Priority:</label>
              <select
                value={selectedPriority}
                onChange={(e) => setSelectedPriority(e.target.value)}
                className="rounded-lg border border-slate-900 bg-slate-950 px-3 py-1.5 text-xs text-slate-300 outline-none hover:border-slate-800 focus:border-indigo-500"
              >
                <option value="ALL">All Priorities</option>
                {Object.values(TicketDTOPriorityEnum).map((p) => (
                  <option key={p} value={p}>
                    {p}
                  </option>
                ))}
              </select>
            </div>

            <span className="text-xs text-slate-500 ml-auto font-medium">
              Showing {filteredTickets.length} of {tickets.length} reports
            </span>
          </div>
        </section>

        {/* Workspace Split Panel Layout */}
        <section className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
          {/* Left Panel: Ticket List */}
          <div className="lg:col-span-7 bg-slate-900/10 rounded-xl border border-slate-900 shadow-xl overflow-hidden">
            <div className="border-b border-slate-900 px-5 py-4 bg-slate-900/30">
              <h2 className="text-xs font-bold uppercase tracking-wider text-slate-400">Incident Feed</h2>
            </div>

            {loading ? (
              <div className="flex flex-col items-center justify-center py-20 text-slate-500">
                <svg className="animate-spin h-6 w-6 mb-2 text-indigo-400" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z" />
                </svg>
                <span className="text-xs">Fetching report logs...</span>
              </div>
            ) : filteredTickets.length === 0 ? (
              <div className="text-center py-20 text-slate-500">
                <p className="text-sm font-medium">No tickets match your filter criteria.</p>
                <button
                  onClick={() => {
                    setSearchQuery("");
                    setSelectedCategory("ALL");
                    setSelectedPriority("ALL");
                    setSelectedStatusTab("ALL");
                  }}
                  className="mt-3 text-xs font-bold text-indigo-400 hover:text-indigo-300"
                >
                  Reset all filters
                </button>
              </div>
            ) : (
              <div className="divide-y divide-slate-900/60 max-h-[600px] overflow-y-auto">
                {filteredTickets.map((t) => (
                  <div
                    key={t.id}
                    onClick={() => setSelectedTicket(t)}
                    className={`p-4 transition-all duration-150 cursor-pointer flex gap-4 items-start ${
                      selectedTicket?.id === t.id
                        ? "bg-indigo-950/20 border-l-4 border-indigo-500 pl-3 shadow-[inset_0_1px_0_rgba(255,255,255,0.02)]"
                        : "hover:bg-slate-900/30 pl-4"
                    }`}
                  >
                    <div className="flex-1 min-w-0">
                      <div className="flex items-center gap-2 mb-1.5">
                        <span className="text-[11px] font-bold text-slate-500 font-mono tracking-tight">
                          #{t.id}
                        </span>
                        <span className="text-xs font-semibold text-slate-800">•</span>
                        <span className="text-[11px] font-semibold text-indigo-400 uppercase tracking-wider">
                          {formatCategory(t.category)}
                        </span>
                        <span className="text-xs text-slate-500 ml-auto font-mono text-[10px]">
                          {t.createdDate.toLocaleDateString("en-US", {
                            month: "short",
                            day: "numeric",
                            hour: "2-digit",
                            minute: "2-digit",
                          })}
                        </span>
                      </div>

                      <h3 className="text-xs font-bold text-slate-200 truncate mb-1.5">{t.title}</h3>
                      <p className="text-xs text-slate-400 line-clamp-2 leading-relaxed mb-3">
                        {t.description || "No description provided."}
                      </p>

                      <div className="flex flex-wrap items-center gap-1.5">
                        <span
                          className={`rounded px-2 py-0.5 text-[10px] font-semibold border ${getPriorityColor(
                            t.priority
                          )}`}
                        >
                          {t.priority}
                        </span>
                        <span
                          className={`rounded px-2 py-0.5 text-[10px] font-semibold border ${getStatusColor(
                            t.status
                          )}`}
                        >
                          {t.status?.replace("_", " ")}
                        </span>
                        {t.assignedDepartment && (
                          <span className="rounded px-2 py-0.5 text-[10px] font-semibold border bg-slate-950 text-slate-400 border-slate-900">
                            {t.assignedDepartment.name}
                          </span>
                        )}
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* Right Panel: Detail Inspector */}
          <div className="lg:col-span-5">
            {selectedTicket ? (
              <div className="bg-slate-900/10 rounded-xl border border-slate-900 shadow-xl overflow-hidden sticky top-[90px]">
                <div className="border-b border-slate-900 px-5 py-4 bg-slate-900/30 flex items-center justify-between">
                  <h2 className="text-xs font-bold uppercase tracking-wider text-slate-400">Inspect Ticket</h2>
                  <button
                    onClick={() => setSelectedTicket(null)}
                    className="text-xs text-slate-500 hover:text-slate-300 font-semibold"
                  >
                    Deselect
                  </button>
                </div>

                <div className="p-5">
                  <div className="mb-4">
                    <span className="text-[10px] font-mono font-bold tracking-widest uppercase text-indigo-400">
                      Ticket #{selectedTicket.id} &bull; {formatCategory(selectedTicket.category)}
                    </span>
                    <h3 className="text-sm font-extrabold text-white mt-0.5">{selectedTicket.title}</h3>
                  </div>

                  <div className="mb-5 bg-slate-950/80 rounded-lg p-3 border border-slate-900/60">
                    <h4 className="text-[10px] font-bold uppercase tracking-wider text-slate-500 mb-1.5">
                      Citizen Description
                    </h4>
                    <p className="text-xs text-slate-300 leading-relaxed whitespace-pre-line">
                      {selectedTicket.description || "No detailed description was provided."}
                    </p>
                  </div>

                  {/* Metadata fields */}
                  <div className="grid grid-cols-2 gap-4 mb-6 border-b border-slate-900/60 pb-5">
                    <div>
                      <span className="text-[10px] font-bold text-slate-500 block uppercase">Reported By</span>
                      <span className="text-xs font-semibold text-slate-300">
                        {selectedTicket.reportedBy?.login || "Anonymous citizen"}
                      </span>
                    </div>

                    <div>
                      <span className="text-[10px] font-bold text-slate-500 block uppercase">Date Logged</span>
                      <span className="text-xs font-semibold text-slate-300">
                        {selectedTicket.createdDate.toLocaleString()}
                      </span>
                    </div>

                    {selectedTicket.location && (
                      <div className="col-span-2">
                        <span className="text-[10px] font-bold text-slate-500 block uppercase">Location Address</span>
                        <span className="text-xs font-semibold text-slate-300">
                          {selectedTicket.location.addressText || "Unknown Area"}
                        </span>
                        {selectedTicket.location.latitude && selectedTicket.location.longitude && (
                          <span className="block text-[10px] font-mono text-slate-500 mt-0.5">
                            Coords: {selectedTicket.location.latitude.toFixed(5)},{" "}
                            {selectedTicket.location.longitude.toFixed(5)}
                          </span>
                        )}
                      </div>
                    )}
                  </div>

                  {/* Action Forms */}
                  <div className="space-y-4">
                    <h4 className="text-xs font-bold text-slate-300 uppercase tracking-wide">Manage Dispatch</h4>

                    <div>
                      <label className="text-[10px] font-bold text-slate-500 uppercase block mb-1">
                        Status Level
                      </label>
                      <select
                        value={editStatus}
                        onChange={(e) => setEditStatus(e.target.value as TicketDTOStatusEnum)}
                        className="w-full rounded-lg border border-slate-900 bg-slate-950 px-3 py-2 text-xs text-slate-300 outline-none hover:border-slate-800 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500/20 transition-colors"
                      >
                        {Object.values(TicketDTOStatusEnum).map((st) => (
                          <option key={st} value={st}>
                            {st.replace("_", " ")}
                          </option>
                        ))}
                      </select>
                    </div>

                    <div>
                      <label className="text-[10px] font-bold text-slate-500 uppercase block mb-1">
                        Priority Level
                      </label>
                      <select
                        value={editPriority}
                        onChange={(e) => setEditPriority(e.target.value as TicketDTOPriorityEnum)}
                        className="w-full rounded-lg border border-slate-900 bg-slate-950 px-3 py-2 text-xs text-slate-300 outline-none hover:border-slate-800 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500/20 transition-colors"
                      >
                        {Object.values(TicketDTOPriorityEnum).map((pr) => (
                          <option key={pr} value={pr}>
                            {pr}
                          </option>
                        ))}
                      </select>
                    </div>

                    <div>
                      <label className="text-[10px] font-bold text-slate-500 uppercase block mb-1">
                        Assign Department
                      </label>
                      <select
                        value={editDeptId}
                        onChange={(e) => setEditDeptId(e.target.value === "" ? "" : Number(e.target.value))}
                        className="w-full rounded-lg border border-slate-900 bg-slate-950 px-3 py-2 text-xs text-slate-300 outline-none hover:border-slate-800 focus:border-indigo-500 focus:ring-1 focus:ring-indigo-500/20 transition-colors"
                      >
                        <option value="">Unassigned</option>
                        <option value="1">Electricity Board</option>
                        <option value="2">Water & Sewerage Dept</option>
                        <option value="3">Municipal Waste Management</option>
                        <option value="4">Road Works & Engineering</option>
                        <option value="5">Forest & Parks Authority</option>
                      </select>
                    </div>

                    <div className="pt-2 flex flex-col gap-2">
                      <button
                        onClick={handleSaveChanges}
                        disabled={isSaving}
                        className="w-full rounded-lg bg-indigo-600 py-2.5 text-xs font-semibold text-white shadow-lg shadow-indigo-600/20 hover:bg-indigo-500 active:scale-[0.99] disabled:opacity-50 transition-all duration-200"
                      >
                        {isSaving ? "Updating logs..." : "Apply & Save Dispatch"}
                      </button>

                      {saveSuccess && (
                        <div className="text-center rounded-lg bg-emerald-500/10 border border-emerald-500/30 py-2 shadow-[0_0_12px_rgba(16,185,129,0.1)]">
                          <span className="text-[10px] font-semibold text-emerald-400">
                            ✓ Ticket modifications saved successfully!
                          </span>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            ) : (
              <div className="bg-slate-900/5 border border-dashed border-slate-800 p-12 text-center text-slate-500 rounded-xl shadow-xl">
                <svg
                  className="mx-auto h-8 w-8 text-slate-700 mb-3"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                  strokeWidth={1.5}
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
                  />
                </svg>
                <p className="text-xs font-semibold text-slate-400">No report selected</p>
                <p className="text-[11px] text-slate-600 mt-1">
                  Select an incident from the feed to edit status details or dispatch departments.
                </p>
              </div>
            )}
          </div>
        </section>
      </main>
    </div>
  );
}
