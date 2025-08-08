# 🧰 Task-CLI

A minimal, extensible command-line task manager written in Java.  
Track your to-dos, deadlines, and tags — all from your terminal.

## 🚀 Features (MVP)
- ✅ Add tasks with title, due date, and tags
- 📋 List tasks (optionally filter by status and sort by due date)
- ✔️ Mark tasks as done
- 🧪 JUnit-based unit tests for core logic

> 📁 Tasks are currently stored in-memory (MVP). Future versions will support file or database persistence.

---

## 🛠️ Usage

### 🔧 Build & Run
From the root directory:

```bash
# Run a command (Windows)
.\gradlew run --args="add --title Buy_milk --due 2025-09-10 --tags home,errands"

# Or on macOS/Linux
./gradlew run --args="add --title Buy_milk --due 2025-09-10 --tags home,errands"
