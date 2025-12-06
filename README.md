# EMV TLV Parser

This app parses raw hex input and displays the decoded tags, lengths, values, and readable interpretations in a clean structured UI.
---

## 🚀 Features

-  Supports single-byte and multi-byte EMV tags
-  Supports standard EMV tags (5A, 57, 9F02, 9F10, 9F26, 9A, 9C, etc.)
-  Detects malformed or truncated TLV structures

---

## 📸 Screenshots

 <img width="336" height="662" alt="splashscreen" src="https://github.com/user-attachments/assets/1f46942b-69b1-421b-85c3-c8643a1d9a4d" />
 <img width="334" height="691" alt="parseddata" src="https://github.com/user-attachments/assets/56ea3fc9-a63d-4e9f-8030-8c1af0a26c98" />
 <img width="336" height="657" alt="invaliddata" src="https://github.com/user-attachments/assets/4137e7a6-8c6a-4d33-949b-87fbac4ac11a" />

---

## 🧪 Full Unit Test Coverage

Includes tests for:

- Correct parsing
- Truncated or invalid TLV
- Unknown tags

---

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: XML Layouts, RecyclerView
- **Build Tool**: Gradle

---

## 📦 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/akozie/EMV-TLV-Parser.git
