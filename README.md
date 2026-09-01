# Flowwise — Financial Intelligence for Modern Merchants

> **"Know your cash. Grow your business."**

Flowwise is a financial intelligence web application engineered for small and growing merchants. It consolidates fragmented merchant bank accounts, invoices, receivables, and vendor payables into a real-time liquidity command center.

---

## Key Features

* **Obsidian Financial Console**: High-contrast, near-black dark interface inspired by modern trading and technology platforms.
* **Real-Time Cash Flow Analytics**: 6-month rolling view of cash inflows vs outflows.
* **Business Health Index (0–100)**: Algorithmic health scoring combining liquidity velocity, working capital cushion, and projected cash runway.
* **Ask Flowwise UI Panel**: Natural language query preview for asking business financial questions (*"Can I afford ₹80,000 of inventory this week?"*).
* **Authentic Indian Rupee Formatting**: Consistent `₹3,42,800` currency presentation.
* **Synthetic Demo Data**: Pre-loaded with synthetic merchant profiles, GSTIN identifiers, and transaction logs.

---

## Tech Stack & Architecture

Flowwise is structured into clean frontend and backend layers:

```text
flowwise/
├── frontend/   # Next.js 15 App Router, TypeScript, Tailwind CSS
└── backend/    # Spring Boot 3, Java 17, Maven, PostgreSQL-ready JPA
```

### Frontend
- **Framework**: Next.js 15 (App Router)
- **Language**: TypeScript
- **Styling**: Tailwind CSS v4 (Obsidian Dark System with `@theme` design tokens)
- **Icons**: Lucide React
- **Visualizations**: Custom SVG vector graphics for zero overhead

### Backend Foundation
- **Framework**: Spring Boot 3.2+
- **JDK**: Java 17+
- **Build Tool**: Maven
- **Database Architecture**: PostgreSQL-ready Spring Data JPA Entities

---

## Quickstart & Setup

### Prerequisites
- **Node.js**: v18.17+ or Node 20+
- **npm**: v9+
- **Java**: JDK 17+ (optional for backend runtime)

### Running the Frontend Console

1. Navigate to the `frontend/` directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Launch the development server:
   ```bash
   npm run dev
   ```

4. Open [http://localhost:3000](http://localhost:3000) in your browser.
   - Access the Marketing Landing Page at `/`
   - Access the Merchant Console Dashboard at `/dashboard`

---

## Project Structure

```text
flowwise/
├── frontend/
│   ├── app/
│   │   ├── layout.tsx            # Obsidian root layout
│   │   ├── page.tsx              # Marketing Landing Page
│   │   ├── globals.css           # Theme tokens & cyber grid utilities
│   │   └── dashboard/
│   │       ├── layout.tsx        # Application shell layout
│   │       └── page.tsx          # Merchant Overview Console
│   ├── components/
│   │   ├── ui/                   # Design System primitives (Button, Card, Badge, MetricCard, Input, Modal)
│   │   ├── layout/               # Navbars, Sidebars, Topbars, MobileNav
│   │   ├── dashboard/            # Health Card, Ask Flowwise, SVG Chart, Transactions
│   │   └── landing/              # Hero, Trust Strip, Capability Pillars, Dashboard Preview, Final CTA
│   ├── lib/
│   │   ├── utils.ts              # formatINR & class merging helpers
│   │   └── mock-data.ts          # Synthetic merchant datasets
│   └── types/
│       └── index.ts              # TypeScript domain types
└── backend/
    ├── pom.xml                   # Maven project descriptor
    └── src/
        ├── main/java/com/flowwise/
        │   ├── FlowwiseApplication.java
        │   ├── controller/HealthController.java
        │   └── entity/MerchantProfileEntity.java
        └── resources/
            └── application.yml   # Spring Boot & PostgreSQL database configuration
```

---

## License

Flowwise Platform Inc. Proprietary — All rights reserved.
