// Shared script for login, register, and todos pages
const SERVER_URL = "http://localhost:8080";
const token = localStorage.getItem("token");

// Login page logic
function login() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  fetch(`${SERVER_URL}/auth/login`, {
    method: "POST",
    headers: { "content-type": "application/json" },
    body: JSON.stringify({ email, password }),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(data.message || "Registration failed..");
      } else {
        return response.json();
      }
    })
    .then((data) => {
      localStorage.setItem("token", data.Token);
      window.location.href = "todos.html";
    })
    .catch((error) => {
      alert(error.message);
    });
}

// Register page logic
function register() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  fetch(`${SERVER_URL}/auth/register`, {
    method: "POST",
    headers: { "content-type": "application/json" },
    body: JSON.stringify({ email, password }),
  })
    .then((response) => {
      if (response.ok) {
        alert("Registration successfully..");
        window.location.href = "login.html";
      } else {
        return response.json().then((data) => {
          throw new Error(data.message || "Registration failed..");
        });
      }
    })
    .catch((error) => {
      alert(error.message);
    });
}

// Todos page logic
function createTodoCard(todo) {
  const card = document.createElement("div");
  card.className = "todo-card";

  const checkBox = document.createElement("input");
  checkBox.type = "checkbox";
  checkBox.checked = todo.isCompleted === true;
  checkBox.addEventListener("change", function () {
    const updatedTodo = { ...todo, isCompleted: checkBox.checked };
    updateTodoStatus(updatedTodo);
  });

  const span = document.createElement("span");
  span.textContent = todo.title;

  const deleteBtn = document.createElement("button");
  deleteBtn.textContent = "X";
  deleteBtn.onclick = function () {
    deleteTodo(todo.id);
  };

  card.appendChild(checkBox);
  card.appendChild(span);
  card.appendChild(deleteBtn);

  return card;
}

function loadTodos() {
  if (!token) {
    alert("You are not logged in.. Please login to view Todo..");
    window.location.href = "login.html";
    return;
  }
  fetch(`${SERVER_URL}/api/v1/todo`, {
    method: "GET",
    headers: { Authorization: `Bearer ${token}` },
  })
    .then((response) => {
      if (!response.ok) {
        console.log(response);
        throw new Error(data.message || "Failed to get Todos...");
      }
      return response.json();
    })
    .then((todos) => {
      const todoList = document.getElementById("todo-list");
      todoList.innerHTML = "";

      if (!todos || todos.length == 0) {
        todoList.innerHTML = `<p id = "error-message">No Todos yet. Add one below.</p>`;
      } else {
        todos.forEach((todo) => {
          todoList.appendChild(createTodoCard(todo));
        });
      }
    })
    .catch((error) => {
      document.getElementById(
        "todo-list"
      ).innerHTML = `<p style = "red">No Todos yet. Add one below.</p>`;
    });
}
function addTodo() {
  const input = document.getElementById("new-todo");
  const todoText = input.value.trim();

  fetch(`${SERVER_URL}/api/v1/todo/create`, {
    method: "POST",
    headers: {
      "content-type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify({ title: todoText, isCompleted: false }),
  })
    .then((response) => {
      console.log(response);

      if (!response.ok) {
        throw new Error(data.message || "Failed to Add Todo");
      }
      return response.json;
    })
    .then((newTodo) => {
      input.value = "";
      loadTodos();
    })
    .catch((error) => {
      alert(error.message);
    });
}

function updateTodoStatus(todo) {
  fetch(`${SERVER_URL}/api/v1/todo/update`, {
    method: "PUT",
    headers: {
      "content-type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(todo),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(data.message || "Failed to update Todo");
      }
      return response.json;
    })
    .then(() => loadTodos())
    .catch((error) => {
      alert(error.message);
    });
}

function deleteTodo(id) {
  fetch(`${SERVER_URL}/api/v1/todo/delete/${id}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${token}` },
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error(data.message || "Failed to delete Todo");
      }
      return response.text();
    })
    .then(() => loadTodos())
    .catch((error) => {
      alert(error.message);
    });
}

// Page-specific initializations
document.addEventListener("DOMContentLoaded", function () {
  if (document.getElementById("todo-list")) {
    loadTodos();
  }
});
