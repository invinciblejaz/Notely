# 📒 Notely — Cloud Notes App

Notely is a simple and clean notes app built for Android using **Firebase Authentication** and **Cloud Firestore**.  
Each user has their own private notes in the cloud — create, edit, delete and sync notes across multiple devices.

---

## 🧰 Tech Stack
- **Java**
- **Firebase Authentication**
- **Cloud Firestore**
- **Material Components**
- **RecyclerView**

---

## ✨ Features
- 🔐 Email & Password authentication  
- ☁ Cloud-synced notes (Firestore per user)  
- ✏ Create, edit & delete notes  
- 📌 Pin / Unpin notes — pinned notes always appear on top  
- 🕒 Notes sorted by latest timestamp  
- 🗂 Grid layout for notes using RecyclerView  
- 🚪 Logout support (clears session)

---

## 📱 Screenshots

| Login | Notes List | Add Note | Edit Note |
|-------|------------|----------|-----------|
| <img src="./screenshots/01_login.png" width="210"/> | <img src="./screenshots/02_notes_list.jpg" width="210"/> | <img src="./screenshots/03_add_note.png" width="210"/> | <img src="./screenshots/04_edit_note.png" width="210"/> |

---

## 📦 Download APK
You can install and test the app without Android Studio:

➡ **Download release APK here:**  
https://github.com/invinciblejaz/Notely/releases

---

## 🚀 Project Structure (Overview)
app/
└ java/
└ com.myapp.notely/
├─ LoginActivity
├─ SignupActivity
├─ MainActivity
├─ AddNoteActivity
├─ EditNoteActivity
├─ adapters/
├─ models/
└─ utils/


---

## 🛡 Security
- `google-services.json` is **not committed**
- `.idea/` and other IDE-generated files are **ignored** using `.gitignore`

---

## 💡 Future Improvements (planned)
- 🔍 Search notes
- 🌙 Dark Theme
- 📤 Export notes / share note as text
- 📱 Better UI for pinned notes section
- ⚡ Offline caching (Room + Firestore sync)

---

### 🧑‍💻 Author
**Jass (invinciblejaz)**  
If you’d like to collaborate or give feedback, feel free to open an issue or reach out.

---

⭐ If you like this project, consider giving the repo a **star**!



