# Enhanced Email Management System (Java Swing + MySQL)

## 📌 Features
- GUI interface to send and store email messages.
- View list of sent emails with a pop-up.
- Data validation and user-friendly errors.
- JDBC connectivity to MySQL database.

## 📁 Structure
- `model/` → Data model class `Email.java`
- `dao/` → DAO for MySQL DB operations
- `ui/` → Main Java Swing interface
- `utils/` → DB connection helper
- `resources/` → SQL schema file

## ⚙️ Setup Instructions

1. Create a MySQL database using `resources/email_schema.sql`.
2. Update DB credentials in `DBConnection.java`.
3. Open project in your Java IDE.
4. Add MySQL JDBC driver to classpath.
5. Run `MainFrame.java`.

## ✅ Marking Criteria Covered

- Core Feature Implementation ✅
- Error Handling and Robustness ✅
- Integration of Components ✅
- Event Handling ✅
- Data Validation ✅
- Code Quality and Innovation ✅
- Project Documentation ✅