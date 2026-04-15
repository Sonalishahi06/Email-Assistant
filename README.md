# 📧 AI Email Assistant

An AI-powered Email Assistant that generates smart email replies using a Spring Boot backend, React frontend, and a Chrome Extension.
---

## 🚀 Features

- Generate AI-based email replies  
- Select tone: Professional, Casual, Friendly  
- Clean UI using Material UI (MUI)  
- Fast API communication using Axios  
- Chrome Extension support  
- Copy reply to clipboard  

---

## 🛠️ Tech Stack

Frontend:
- React.js  
- Material UI (MUI)  
- Axios  

Backend:
- Spring Boot (Java)  
- REST API  

Extension:
- Chrome Extension (Manifest V3)  

---


## ⚙️ Setup Instructions

### Backend

cd backend  
./mvnw spring-boot:run  

---

### Frontend

cd frontend  
npm install  
npm start  

---

### Chrome Extension

1. Open chrome://extensions/  
2. Enable Developer Mode  
3. Click "Load Unpacked"  
4. Select the extension folder  

---

## 🌍 API Endpoint

POST http://localhost:8080/api/email/generate  

Request Body:
{
  "emailContent": "Your email text",
  "tone": "professional | casual | friendly"
}

---

## 💡 How It Works

1. User enters email content  
2. Selects tone  
3. Frontend sends request to backend  
4. Backend generates reply  
5. Response is shown in UI  

---

## 🔮 Future Improvements

- Authentication  
- Deployment (Render + Vercel)  
- Better UI/UX  
- More tone options  

---
