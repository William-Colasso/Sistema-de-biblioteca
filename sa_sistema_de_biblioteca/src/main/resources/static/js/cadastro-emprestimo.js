// cadastro-emprestimo.js (versão sem TomSelect)

document.addEventListener("DOMContentLoaded", async () => {
    await preencherLivros();
    await preencherUsuarios();
    await preencherEmprestimosCadastrados();
    updateEmprestimo()

    let leftArrow = document.getElementById("left");
    let rightArrow = document.getElementById("right");
    let containerEmprestimo = document.getElementById("containerEmprestimo");

    leftArrow.addEventListener("click", () => {
        containerEmprestimo.classList.remove("edit");
        containerEmprestimo.classList.add("register");
        rightArrow.style.display = "flex";
        leftArrow.style.display = "none";
    });

    rightArrow.addEventListener("click", () => {
        containerEmprestimo.classList.remove("register");
        containerEmprestimo.classList.add("edit");
        leftArrow.style.display = "flex";
        rightArrow.style.display = "none";
    });

    const form = document.getElementById("formEmprestimo");
    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const userId = document.getElementById("user").value;
        const livroId = document.getElementById("livro").value;
        const dataEmprestimo = document.getElementById("dataEmprestimo").value;
        const dataDevolucaoPrevista = document.getElementById("dataDevolucaoPrevista").value;
        const devolvido = document.getElementById("devolvido").value === "true";

        if (!userId || !livroId || !dataEmprestimo || !dataDevolucaoPrevista) {
            alert("Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        const emprestimo = {
            user: { idUser: Number(userId) },
            livro: { idLivro: Number(livroId) },
            dataEmprestimo,
            dataDevolucaoPrevista,
            devolvido
        };

        try {
            const response = await fetch("/loan/create", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(emprestimo),
            });

            if (response.ok) {
                alert("Empréstimo cadastrado com sucesso!");
                window.location.reload()
            } else {
                const errorData = await response.json();
                alert("Erro ao cadastrar empréstimo: " + (errorData.message || response.statusText));
            }
        } catch (error) {
            alert("Erro na requisição: " + error.message);
            console.error(error);
        }
    });
});

async function preencherEmprestimosCadastrados() {
    try {
        const response = await fetch("/loan");
        if (!response.ok) throw new Error("Erro ao buscar empréstimos.");

        const emprestimos = await response.json();
        const select = document.getElementById("emprestimosCadastrados");
        select.innerHTML = '<option value="">Selecione um empréstimo</option>';

        emprestimos.forEach((e) => {
            const option = document.createElement("option");
            option.value = JSON.stringify(e);
            option.textContent = `${e.livro.titulo} — ${e.user.nome}`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao buscar empréstimos:", error);
    }
}

async function preencherLivros() {
    try {
        const response = await fetch("/book");
        if (!response.ok) throw new Error("Erro ao buscar livros.");

        const livros = await response.json();
        const select = document.getElementById("livro");
        select.innerHTML = '<option value="">Selecione um livro</option>';

        livros.forEach((livro) => {
            const option = document.createElement("option");
            option.value = livro.idLivro;
            option.textContent = livro.titulo || `Livro #${livro.idLivro}`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao buscar livros:", error);
    }
}

async function preencherUsuarios() {
    try {
        const response = await fetch("/user");
        if (!response.ok) throw new Error("Erro ao buscar usuários.");

        const users = await response.json();
        const select = document.getElementById("user");
        select.innerHTML = '<option value="">Selecione um usuário</option>';

        users.forEach((user) => {
            const option = document.createElement("option");
            option.value = user.idUser;
            option.textContent = user.nome || `Usuário #${user.idUser}`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error("Erro ao buscar usuários:", error);
    }



    
}

function updateEmprestimo() {
        
    let emprestimosCadastradosSelect = document.getElementById("emprestimosCadastrados")
    let editData = document.getElementById("dataEditDevolucaoPrevista")
    let devolvidoEdit = document.getElementById("devolvidoEdit")
    let editEmprestimoButton = document.getElementById("editEmprestimo")
    let emprestimo;

    emprestimosCadastradosSelect.addEventListener("input", () => {
        let e = JSON.parse(emprestimosCadastradosSelect.value)
        emprestimo = e;
        editData.value = e.dataDevolucaoPrevista
        devolvidoEdit.value = e.devolvido


    });

    editEmprestimoButton.addEventListener("click", async (e)=>{
        e.preventDefault()
        if(emprestimosCadastradosSelect.value == ""){
            alert("Por favor selecione um empréstimo!")
            return;
        }

        const mapBody = new Map()

        mapBody.set("dataDevolucaoPrevista", editData.value)
        mapBody.set("devolvido", devolvidoEdit.value === "true")


        console.log(emprestimo)
        const bodyResponse = {
            dataDevolucaoPrevista:editData.value,
            devolvido: (devolvidoEdit.value === "true")
        }
        const RESPOSE = await fetch(`/loan/update/${emprestimo.idEmprestimo}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(bodyResponse)
        })

        if(RESPOSE.status == 200){
            alert("Empréstimo editado!");
            window.location.reload()
        }
    })

}
