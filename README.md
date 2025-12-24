<div align="center">

# ☕ Zar Cafe Management System

### A Modern Point of Sale Solution for Coffee Shops

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![NetBeans](https://img.shields.io/badge/Apache%20NetBeans-1B6AC6?style=for-the-badge&logo=apache-netbeans&logoColor=white)
![License](https://img.shields.io/badge/License-Educational-green?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)
![Version](https://img.shields.io/badge/Version-2.0.0-blue?style=for-the-badge)
![Updated](https://img.shields.io/badge/Updated-December%202025-orange?style=for-the-badge)

<p align="center">
  <strong>A college-level Point of Sale (POS) desktop application developed as a software engineering project.</strong><br>
  This system streamlines coffee shop operations using a structured <b>Java Swing</b> interface and <b>MySQL</b> database.
</p>

[Features](#-key-features) • [Installation](#️-installation--setup-guide) • [Screenshots](#-application-tour) • [Team](#-team-members)

</div>

---

## 📑 Table of Contents

<details>
<summary>Click to expand</summary>

- [Application Tour](#-application-tour)
- [Key Features](#-key-features)
- [Tech Stack](#️-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#️-installation--setup-guide)
- [Project Structure](#-project-structure)
- [Troubleshooting](#-troubleshooting)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [Team Members](#-team-members)
- [Changelog](#-changelog)
- [Acknowledgments](#-acknowledgments)

</details>

---

## 📸 Application Tour
The application follows a structured user flow (A to E) for intuitive navigation.

### 1. Onboarding & Authentication
| Welcome Screen | Login | Signup |
|:---:|:---:|:---:|
| ![Welcome](screenshots/welcome.png) | ![Login](screenshots/login.png) | ![Signup](screenshots/signup.png) |
| *Entry Point (A)* | *Secure Access (B)* | *New User Registration (B)* |

### 2. Core Modules
| Order System (User) | Profile & Wallet | Admin Dashboard |
|:---:|:---:|:---:|
| ![Order](screenshots/order.png) | ![Profile](screenshots/profile.png) | ![Admin](screenshots/admin.png) |
| *Main POS Interface (D)* | *User Profile & History* | *Sales & Management (E)* |

### 3. Support & Info
| About Us | Contact Us |
|:---:|:---:|
| ![About](screenshots/about.png) | ![Contact](screenshots/contact.png) |
| *Team Info (C)* | *Support Channels (C)* |

---

## 🚀 Key Features

### 👤 A. & B. User Access Control
* **Welcome Hub:** centralized entry point guiding users to login or register.
* **Role-Based Login:** secure authentication separating 'Admin' access from standard 'User' access.
* **Smart Signup:** streamlined registration form with duplicate phone validation.
* **🆕 Show/Hide Password:** toggle checkbox for password visibility in both fields.
* **🆕 Modern UI:** transparent text fields with rounded borders for a sleek look.

### 💰 Wallet System (NEW!)
* **Electronic Wallet:** each customer has a wallet balance for cashless transactions.
* **Transaction History:** complete log of deposits and withdrawals with timestamps.
* **Secure Payments:** deduct from wallet balance during checkout.

### 🛒 D. Order Processing (The Core)
* **Visual Menu:** categorized selection for Coffee, Bakery, and Desserts.
* **Dynamic Cart:** real-time bill calculation and item management.
* **🆕 Discount Support:** orders now support subtotal, discount, and final total.
* **Receipt Generation:** automated generation of order summaries.

### 👤 Profile & Order History (NEW!)
* **Profile Dashboard:** view wallet balance and personal info.
* **Order History Table:** browse all past orders in a clean table.
* **🆕 Quick Details (Double-Click):** double-click any order to see full item breakdown in a popup.

### 📊 E. Admin Administration
* **Sales Overview:** view daily earnings and transaction logs.
* **🆕 Full CRUD Control:** add, edit, and delete users with cascade deletion.
* **🆕 Safe Delete:** delete any user along with all their orders automatically.
* **Menu Management:** add new products, update prices, and manage inventory.

### ℹ️ C. Information Center
* **About & Contact:** dedicated interfaces providing project information and developer contact details.

---

## 🛠️ Tech Stack

<div align="center">

| Component | Technology | Purpose |
|:---------:|:----------:|:--------|
| ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white) | Java (JDK 8+) | Core Programming Language |
| ![Swing](https://img.shields.io/badge/Swing-007396?style=flat-square&logo=java&logoColor=white) | Java Swing | GUI Framework |
| ![MySQL](https://img.shields.io/badge/MySQL-005C84?style=flat-square&logo=mysql&logoColor=white) | MySQL 5.7+ | Database Management |
| ![JDBC](https://img.shields.io/badge/JDBC-4479A1?style=flat-square&logo=java&logoColor=white) | MySQL Connector | Database Connectivity |
| ![NetBeans](https://img.shields.io/badge/NetBeans-1B6AC6?style=flat-square&logo=apache-netbeans&logoColor=white) | Apache NetBeans | Development IDE |

</div>

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

| Requirement | Version | Download Link |
|:-----------:|:-------:|:-------------:|
| ☕ Java JDK | 8+ | [Download](https://www.oracle.com/java/technologies/downloads/) |
| 🗄️ MySQL Server | 5.7+ | [Download](https://dev.mysql.com/downloads/) |
| 💻 Apache NetBeans | Latest | [Download](https://netbeans.apache.org/download/) |

> 💡 **Tip:** You can also use [XAMPP](https://www.apachefriends.org/) which includes MySQL Server.

---

## ⚙️ Installation & Setup Guide

### ⚡ Quick Start
```bash
# 1. Clone the repository
git clone https://github.com/yourusername/ZarCafe.git

# 2. Import database/database_setup.sql into MySQL

# 3. Open project in NetBeans and run!
```

### 1. Database Setup 🗄️
1.  Open your SQL Server (Workbench or XAMPP).
2.  Import **`database_setup.sql`** from the `database/` folder.
3.  This creates the `ZAR_CAFE` DB and tables.

### 2. Configuration 🔌
Ensure database credentials match your local machine in `src/db/DBConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/ZAR_CAFE";
private static final String USER = "root"; // Check your username
private static final String PASSWORD = ""; // Check your password
```

### 3. Add JDBC Driver 📦
1.  Right-click your project → **Properties** → **Libraries**.
2.  Click **Add JAR/Folder** and select `lib/mysql-connector-java-x.x.x.jar`.

### 4. Run 🚀
Right-click the project and select **Run**.
* **Default Admin Credentials:**
    * Phone: `123`
    * Password: `admin123`
    
    > **Note:** For security purposes, please change these credentials after the first login or via the database.

---

## 📂 Project Structure
```
ZarCafe/
├── 📁 src/
│   ├── 📁 ui/           # Java Swing GUI classes
│   ├── 📁 db/           # Database connection & queries
│   └── 📁 images/       # Icons and assets
├── 📁 database/
│   └── 📄 database_setup.sql
├── 📁 lib/
│   └── 📄 mysql-connector-java-x.x.x.jar
├── 📁 screenshots/
└── 📄 README.md
```

---

## 📊 Database Schema

<div align="center">

```mermaid
erDiagram
    CUSTOMERS ||--o{ ORDERS : places
    CUSTOMERS ||--o{ WALLET_TRANSACTIONS : has
    ORDERS ||--|{ ORDER_ITEMS : contains
    
    CUSTOMERS {
        int CUSTOMER_ID PK
        varchar USER_NAME
        varchar PHONE_NUMBER UK
        varchar PASSWORD
        varchar CUSTOMER_ADDRESS
        varchar ROLE
        double WALLET_BALANCE
    }
    
    MENU_ITEMS {
        int ITEM_ID PK
        varchar ITEM_NAME UK
        double PRICE
        int QUANTITY
        varchar IMAGE_PATH
    }
    
    ORDERS {
        int ORDER_ID PK
        int CUSTOMER_ID FK
        date ORDER_DATE
        double SUBTOTAL
        double DISCOUNT
        double TOTAL_PRICE
    }
    
    ORDER_ITEMS {
        int DETAIL_ID PK
        int ORDER_ID FK
        varchar ITEM_NAME
        int QUANTITY
        double PRICE
    }
    
    WALLET_TRANSACTIONS {
        int TRANSACTION_ID PK
        int CUSTOMER_ID FK
        varchar TYPE
        double AMOUNT
        datetime TRANSACTION_DATE
    }
```

</div>

### 📋 Table Descriptions

| Table | Description | Key Fields |
|:-----:|-------------|------------|
| `customers` | User accounts with wallet | `PHONE_NUMBER` (unique), `WALLET_BALANCE` |
| `menu_items` | Products with inventory | `ITEM_NAME`, `PRICE`, `QUANTITY` |
| `orders` | Transactions with discounts | `SUBTOTAL`, `DISCOUNT`, `TOTAL_PRICE` |
| `order_items` | Items per order | `QUANTITY`, links to `orders` |
| `wallet_transactions` | 🆕 Wallet activity log | `TYPE` (Deposit/Withdraw), `AMOUNT` |

### 🔗 Database Relationships

| Relationship | Type | Cascade |
|:------------:|:----:|:-------:|
| Customers → Orders | One-to-Many | ✅ ON DELETE CASCADE |
| Customers → Wallet_Transactions | One-to-Many | ✅ ON DELETE CASCADE |
| Orders → Order_Items | One-to-Many | ✅ ON DELETE CASCADE |

### 🍽️ Sample Menu Items

| Item | Price (EGP) |
|:----:|:-----------:|
| ☕ Coffee | 50 |
| 🥛 Latte | 100 |
| 🍰 Cake | 80 |
| 🥪 Sandwich | 110 |
| 🍨 Ice Cream | 30 |
| 🍵 Tea | 40 |

---

## ❓ Troubleshooting

| Issue | Solution |
|-------|----------|
| `ClassNotFoundException: com.mysql.cj.jdbc.Driver` | Ensure JDBC driver is added to project libraries |
| `Access denied for user 'root'` | Verify credentials in `DBConnection.java` |
| Database connection failed | Check if MySQL service is running |
| Tables not found | Re-import `database_setup.sql` |

---

## 🔮 Future Enhancements

| Priority | Feature | Status |
|:--------:|---------|:------:|
| ✅ Done | Wallet system with transactions | ✅ Completed v2.0 |
| ✅ Done | Order history with details popup | ✅ Completed v2.0 |
| ✅ Done | User profile dashboard | ✅ Completed v2.0 |
| 🔴 High | Export sales reports to PDF/Excel | 📋 Planned |
| 🟡 Medium | Customer loyalty program | 💭 Idea |
| 🟡 Medium | Multi-language support | 💭 Idea |
| 🟢 Low | Dark mode theme | 💭 Idea |
| 🟢 Low | Mobile companion app | 💭 Idea |

---

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

1. 🍴 **Fork** the repository
2. 🔧 **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. 💾 **Commit** your changes (`git commit -m 'Add AmazingFeature'`)
4. 📤 **Push** to the branch (`git push origin feature/AmazingFeature`)
5. 🔃 **Open** a Pull Request

> Please read our contribution guidelines before submitting a PR.

---

## 👨‍💻 Team Members

<div align="center">

This project was proudly developed by:

| 👤 Member | Role |
|:---------:|:----:|
| **Abdullah Darahem** | 💻 Developer |
| **Zeyad Salim** | 💻 Developer |
| **Rana Ehgannam** | 💻 Developer |
| **Rewan Reda** | 💻 Developer |
| **Rawaa Elsayed** | 💻 Developer |
| **Rawan Osama** | 💻 Developer |

</div>

---

## � Changelog

### 🚀 Version 2.0.0 (December 2025)

#### 💰 New Features
- **Wallet System:** Electronic wallet for cashless payments
- **Transaction History:** Complete log of all wallet activities
- **Profile Dashboard:** View balance and personal information
- **Order History:** Browse past orders in a table format
- **Quick Details Popup:** Double-click orders to see item breakdown

#### 🛠️ Improvements
- **Show/Hide Password:** Toggle visibility in login & signup forms
- **Modern UI:** Transparent fields with rounded borders
- **Discount Support:** Orders now track subtotal, discount, and total
- **Cascade Delete:** Safe user deletion with automatic cleanup
- **Menu Inventory:** Track product quantities and images

#### 🔧 Technical Updates
- Removed unused `GENDER` column from database
- Added `WALLET_BALANCE` to customers table
- New `wallet_transactions` table for financial tracking
- Implemented `ON DELETE CASCADE` for data integrity
- Parameterized constructors for secure data passing

### 📦 Version 1.0.0 (December 2025)
- 🎉 Initial release
- ✅ User authentication (Login/Signup)
- ✅ Order management system
- ✅ Admin dashboard
- ✅ Basic POS functionality

---

## �🙏 Acknowledgments

- 🎓 Our professors and mentors for guidance
- ☕ Coffee for keeping us awake during development
- 📚 Online resources and documentation
- 💡 Open source community for inspiration

---

## 📄 License

This project is developed for **educational purposes** as part of a Software Engineering course.

```
MIT License - Feel free to use this project for learning purposes.
```

---

<div align="center">

### ⭐ Star this repository if you found it helpful!

**Made with ❤️ by the Zar Cafe Team**

*© 2025 Zar Cafe System. All Rights Reserved.*

[⬆ Back to Top](#-zar-cafe-management-system)

</div>