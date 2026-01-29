# 📊 Expense Tracker App (Project 2 of 10)

An **Android Expense Tracker application** built using **Kotlin** and **Jetpack Compose**, focused on tracking daily expenses, filtering by **year and month**, and visualizing data using **charts**.  
This project demonstrates **modern Android architecture** using **MVVM, Room, Hilt, and Navigation Compose**.

---

## 🚀 Features

- 🧾 Add, view, and delete expenses
- 📅 Select **Year & Month** to filter expenses
- 📊 Category-wise **percentage-based Pie Chart**
- 💰 Total expense calculation for selected month
- 🧠 Clean MVVM architecture
- 💾 Offline data storage using Room
- ⚡ Dependency Injection with Hilt

---

## 🛠 Tech Stack

- **Language:** Kotlin  
- **UI:** Jetpack Compose (Material 3)  
- **Architecture:** MVVM  
- **Database:** Room  
- **Dependency Injection:** Hilt  
- **Navigation:** Navigation Compose  
- **Charts:** Canvas API  

---

## 📸 Screenshots

### 🚀 Splash Screen
![Splash Screen](screenshots/SplashScreen.jpg)

### 🏠 Home Screen
![Home Screen](screenshots/HomeScreen.jpg)

### ➕ Add Expense Dialog
![Add Dialog](screenshots/AddDialog.jpg)

### 📅 MonthlyScreen
![Year Month Selector](screenshots/MonthlyScreen.jpg)

### 📊 Monthly Screen(Year wise)
![Analytics Screen](screenshots/MonthlyScreen2.jpg)

---

## 🧩 Architecture Overview

```text
UI (Compose)
 ↓
ViewModel
 ↓
Repository
 ↓
Room Database
