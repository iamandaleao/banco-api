const API_URL = "http://localhost:8080/api/clientes"; // troque para URL do backend se for deploy online

const form = document.getElementById("clienteForm");
const clientesList = document.getElementById("clientesList");

// Listar clientes
async function listarClientes() {
    clientesList.innerHTML = "";
    const res = await fetch(API_URL);
    const clientes = await res.json();

    clientes.forEach(c => {
        const li = document.createElement("li");
        li.textContent = `${c.nome} - ${c.cpf} - ${c.email}`;
        clientesList.appendChild(li);
    });
}

// Adicionar cliente
form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const cliente = {
        nome: document.getElementById("nome").value,
        cpf: document.getElementById("cpf").value,
        email: document.getElementById("email").value,
        dataNascimento: document.getElementById("dataNascimento").value,
        telefone: document.getElementById("telefone").value
    };

    const res = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cliente)
    });

    if (res.ok) {
        alert("Cliente adicionado!");
        form.reset();
        listarClientes();
    } else {
        const error = await res.text();
        alert("Erro: " + error);
    }
});

listarClientes();
